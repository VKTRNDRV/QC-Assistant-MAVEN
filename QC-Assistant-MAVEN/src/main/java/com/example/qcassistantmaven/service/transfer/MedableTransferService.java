package com.example.qcassistantmaven.service.transfer;

import com.example.qcassistantmaven.domain.dto.study.transfer.IqviaStudyTransferDTO;
import com.example.qcassistantmaven.domain.dto.study.transfer.MedableStudyTransferDTO;
import com.example.qcassistantmaven.domain.dto.tag.TagTransferDTO;
import com.example.qcassistantmaven.domain.dto.transfer.ClinicalEntitiesTransferDTO;
import com.example.qcassistantmaven.domain.entity.app.BaseApp;
import com.example.qcassistantmaven.domain.entity.app.IqviaApp;
import com.example.qcassistantmaven.domain.entity.app.MedableApp;
import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.sponsor.IqviaSponsor;
import com.example.qcassistantmaven.domain.entity.sponsor.MedableSponsor;
import com.example.qcassistantmaven.domain.entity.study.IqviaStudy;
import com.example.qcassistantmaven.domain.entity.study.MedableStudy;
import com.example.qcassistantmaven.domain.entity.study.MedidataStudy;
import com.example.qcassistantmaven.domain.entity.study.environment.IqviaEnvironment;
import com.example.qcassistantmaven.domain.entity.study.environment.MedableEnvironment;
import com.example.qcassistantmaven.domain.entity.tag.IqviaTag;
import com.example.qcassistantmaven.domain.entity.tag.MedableTag;
import com.example.qcassistantmaven.service.DestinationService;
import com.example.qcassistantmaven.service.app.MedableAppService;
import com.example.qcassistantmaven.service.sponsor.MedableSponsorService;
import com.example.qcassistantmaven.service.study.MedableStudyService;
import com.example.qcassistantmaven.service.tag.MedableTagService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedableTransferService extends BaseTransferService{

    private MedableSponsorService sponsorService;

    private MedableAppService appService;

    private MedableTagService tagService;

    private MedableStudyService studyService;

    public MedableTransferService(DestinationService destinationService,
                                  ModelMapper modelMapper,
                                  Gson gson,
                                  MedableSponsorService sponsorService,
                                  MedableAppService appService,
                                  MedableTagService tagService,
                                  MedableStudyService studyService) {
        super(destinationService, modelMapper, gson);
        this.sponsorService = sponsorService;
        this.appService = appService;
        this.tagService = tagService;
        this.studyService = studyService;
    }

    @Override
    public ClinicalEntitiesTransferDTO exportEntities(){
        List<MedableSponsor> sponsors = this.sponsorService.getEntities();
        List<MedableApp> apps = this.appService.getEntities();
        List<MedableTag> tags = this.tagService.getEntities();
        List<MedableStudy> studies = this.studyService.getEntities();

        ClinicalEntitiesTransferDTO entities = new ClinicalEntitiesTransferDTO();
        entities.setSponsors(this.gson.toJson(super.mapSponsorsToTransferDTO(sponsors)))
                .setApps(this.gson.toJson(super.mapAppsToTransferDTO(apps)))
                .setTags(this.gson.toJson(super.mapTagsToTransferDTO(tags)))
                .setStudies(this.gson.toJson(this.mapStudiesToTransferDTO(studies)));

        return entities;
    }

    private List<MedableStudyTransferDTO> mapStudiesToTransferDTO(List<MedableStudy> studies) {
        List<MedableStudyTransferDTO> transferDTOs = new ArrayList<>();
        for(MedableStudy study : studies){
            MedableStudyTransferDTO studyTransferDTO = this.modelMapper
                    .map(study, MedableStudyTransferDTO.class);

            MedableEnvironment env = study.getEnvironment();

            studyTransferDTO
                    .setSponsor(study.getSponsor().getName());
            studyTransferDTO.getEnvironment()
                    .setPatientApps(env.getPatientApps()
                            .stream().map(BaseApp::getName)
                            .collect(Collectors.toList()))
                    .setSiteApps(env.getSiteApps()
                            .stream().map(BaseApp::getName)
                            .collect(Collectors.toList()));

            transferDTOs.add(studyTransferDTO);
        }

        return transferDTOs;
    }

    @Override
    public void importEntities(ClinicalEntitiesTransferDTO entitiesJSON) {
        if(!entitiesJSON.getSponsors().trim().isEmpty()){
            super.importSponsors(entitiesJSON.getSponsors(), MedableSponsor.class);
        }

        if(!entitiesJSON.getApps().trim().isEmpty()){
            super.importApps(entitiesJSON.getApps(), MedableApp.class);
        }

        if(!entitiesJSON.getStudies().trim().isEmpty()){
            this.importStudies(entitiesJSON.getStudies());
        }

        if(!entitiesJSON.getTags().trim().isEmpty()){
            this.importTags(entitiesJSON.getTags());
        }
    }

    private void importTags(String json) {
        TagTransferDTO[] dtos = this.gson.fromJson(json, TagTransferDTO[].class);
        List<MedableTag> tags = new ArrayList<>();
        List<Destination> allDestinations = this.destinationService.getEntities();
        List<MedableStudy> allStudies = this.studyService.getEntities();
        for(TagTransferDTO tagDTO : dtos){
            MedableTag tag = this.modelMapper.map(tagDTO, MedableTag.class);

            List<Destination> tagDestinations = new ArrayList<>();
            for(String name : tagDTO.getDestinations()){
                for(Destination destination : allDestinations){
                    if(Objects.equals(name, destination.getName())){
                        tagDestinations.add(destination);
                        break;
                    }
                }
            }

            List<MedableStudy> tagStudies = new ArrayList<>();
            for(String name : tagDTO.getStudies()){
                for(MedableStudy study : allStudies){
                    if(study.getName().equals(name)){
                        tagStudies.add(study);
                        break;
                    }
                }
            }

            tag.setDestinations(tagDestinations);
            tag.setStudies(tagStudies);

            tags.add(tag);
        }

        this.tagService.saveAll(tags);
    }

    private void importStudies(String json) {
        MedableStudyTransferDTO[] dtos = this.gson
                .fromJson(json, MedableStudyTransferDTO[].class);
        List<MedableStudy> studies = this.mapStudyDTOsToEntities(List.of(dtos));
        this.studyService.saveAll(studies);
    }

    public List<MedableStudy> mapStudyDTOsToEntities(Iterable <MedableStudyTransferDTO> dtos){
        MedableSponsor unknownSponsor = this.sponsorService.getUnknownSponsor();
        List<MedableApp> apps = this.appService.getEntities();
        List<MedableStudy> studies = new ArrayList<>();

        for(MedableStudyTransferDTO studyDTO : dtos){
            if(this.studyService.findFirstByName(
                    studyDTO.getName()).isPresent()) continue;

            MedableStudy study = this.modelMapper.map(studyDTO, MedableStudy.class);
            Optional<MedableSponsor> sponsor = this.sponsorService
                    .findFirstByName(studyDTO.getSponsor());
            if(sponsor.isPresent()){
                study.setSponsor(sponsor.get());
            }else{
                study.setSponsor(unknownSponsor);
            }

            List<MedableApp> siteApps = new ArrayList<>();
            List<MedableApp> patientApps = new ArrayList<>();

            for(String appName : studyDTO.getEnvironment().getSiteApps()){
                for(MedableApp app : apps){
                    if(app.getName().equals(appName)){
                        siteApps.add(app);
                        break;
                    }
                }
            }

            for(String appName : studyDTO.getEnvironment().getPatientApps()){
                for(MedableApp app : apps){
                    if(app.getName().equals(appName)){
                        patientApps.add(app);
                        break;
                    }
                }
            }

            study.getEnvironment().setSiteApps(siteApps);
            study.getEnvironment().setPatientApps(patientApps);

            studies.add(study);
        }

        return studies;
    }

    @Override
    protected MedableAppService getAppService() {
        return this.appService;
    }

    @Override
    protected MedableSponsorService getSponsorService() {
        return this.sponsorService;
    }
}
