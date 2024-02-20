package com.example.qcassistantmaven.service.transfer;

import com.example.qcassistantmaven.domain.dto.study.transfer.MedidataStudyTransferDTO;
import com.example.qcassistantmaven.domain.dto.tag.TagTransferDTO;
import com.example.qcassistantmaven.domain.dto.transfer.ClinicalEntitiesTransferDTO;
import com.example.qcassistantmaven.domain.entity.app.BaseApp;
import com.example.qcassistantmaven.domain.entity.app.MedidataApp;
import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistantmaven.domain.entity.study.MedidataStudy;
import com.example.qcassistantmaven.domain.entity.study.environment.MedidataEnvironment;
import com.example.qcassistantmaven.domain.entity.tag.MedidataTag;
import com.example.qcassistantmaven.service.DestinationService;
import com.example.qcassistantmaven.service.app.MedidataAppService;
import com.example.qcassistantmaven.service.sponsor.MedidataSponsorService;
import com.example.qcassistantmaven.service.study.MedidataStudyService;
import com.example.qcassistantmaven.service.tag.MedidataTagService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedidataTransferService extends BaseTransferService {

    private MedidataSponsorService sponsorService;

    private MedidataAppService appService;

    private MedidataTagService tagService;

    private MedidataStudyService studyService;

    @Autowired
    public MedidataTransferService(DestinationService destinationService,
                                   MedidataSponsorService sponsorService,
                                   MedidataAppService appService,
                                   MedidataTagService tagService,
                                   MedidataStudyService studyService,
                                   ModelMapper modelMapper,
                                   Gson gson) {
        super(destinationService, modelMapper, gson);
        this.sponsorService = sponsorService;
        this.appService = appService;
        this.tagService = tagService;
        this.studyService = studyService;
    }

    @Override
    public ClinicalEntitiesTransferDTO exportEntities(){
        List<MedidataSponsor> sponsors = this.sponsorService.getEntities();
        List<MedidataApp> apps = this.appService.getEntities();
        List<MedidataTag> tags = this.tagService.getEntities();
        List<MedidataStudy> studies = this.studyService.getEntities();

        ClinicalEntitiesTransferDTO entities = new ClinicalEntitiesTransferDTO();
        entities.setSponsors(this.gson.toJson(super.mapSponsorsToTransferDTO(sponsors)))
                .setApps(this.gson.toJson(super.mapAppsToTransferDTO(apps)))
                .setTags(this.gson.toJson(super.mapTagsToTransferDTO(tags)))
                .setStudies(this.gson.toJson(this.mapStudiesToTransferDTO(studies)));

        return entities;
    }

    private List<MedidataStudyTransferDTO> mapStudiesToTransferDTO(Collection<MedidataStudy> studies) {
        List<MedidataStudyTransferDTO> transferDTOs = new ArrayList<>();
        for(MedidataStudy study : studies){
            MedidataStudyTransferDTO studyTransferDTO = this.modelMapper
                    .map(study, MedidataStudyTransferDTO.class);

            MedidataEnvironment env = study.getEnvironment();

            studyTransferDTO
                    .setSponsor(study.getSponsor().getName());
            studyTransferDTO.getEnvironment()
                    .setIsLegacy(env.getIsLegacy().name())
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
            super.importSponsors(entitiesJSON.getSponsors(), MedidataSponsor.class);
        }

        if(!entitiesJSON.getApps().trim().isEmpty()){
            super.importApps(entitiesJSON.getApps(), MedidataApp.class);
        }

        if(!entitiesJSON.getStudies().trim().isEmpty()){
            this.importStudies(entitiesJSON.getStudies());
        }

        if(!entitiesJSON.getTags().trim().isEmpty()){
            this.importTags(entitiesJSON.getTags());
        }
    }

    @Override
    protected MedidataAppService getAppService() {
        return this.appService;
    }

    @Override
    protected MedidataSponsorService getSponsorService() {
        return this.sponsorService;
    }

    private void importTags(String json) {
        TagTransferDTO[] dtos = this.gson.fromJson(json, TagTransferDTO[].class);
        List<MedidataTag> tags = new ArrayList<>();
        List<Destination> allDestinations = this.destinationService.getEntities();
        List<MedidataStudy> allStudies = this.studyService.getEntities();
        for(TagTransferDTO tagDTO : dtos){
            MedidataTag tag = this.modelMapper.map(tagDTO, MedidataTag.class);

            List<Destination> tagDestinations = new ArrayList<>();
            for(String name : tagDTO.getDestinations()){
                for(Destination destination : allDestinations){
                    if(destination.getName().equals(name)){
                        tagDestinations.add(destination);
                        break;
                    }
                }
            }

            List<MedidataStudy> tagStudies = new ArrayList<>();
            for(String name : tagDTO.getStudies()){
                for(MedidataStudy study : allStudies){
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
        MedidataStudyTransferDTO[] dtos = this.gson
                .fromJson(json, MedidataStudyTransferDTO[].class);
        List<MedidataStudy> studies = this.mapStudyDTOsToEntities(List.of(dtos));
        this.studyService.saveAll(studies);
    }
    public List<MedidataStudy> mapStudyDTOsToEntities(Iterable <MedidataStudyTransferDTO> dtos){
        MedidataSponsor unknownSponsor = this.sponsorService.getUnknownSponsor();
        List<MedidataApp> apps = this.appService.getEntities();
        List<MedidataStudy> studies = new ArrayList<>();

        for(MedidataStudyTransferDTO studyDTO : dtos){
            if(this.studyService.findFirstByName(
                    studyDTO.getName()).isPresent()) continue;

            MedidataStudy study = this.modelMapper.map(studyDTO, MedidataStudy.class);
            Optional<MedidataSponsor> sponsor = this.sponsorService
                    .findFirstByName(studyDTO.getSponsor());
            if(sponsor.isPresent()){
                study.setSponsor(sponsor.get());
            }else{
                study.setSponsor(unknownSponsor);
            }

            List<MedidataApp> siteApps = new ArrayList<>();
            List<MedidataApp> patientApps = new ArrayList<>();

            for(String appName : studyDTO.getEnvironment().getSiteApps()){
                for(MedidataApp app : apps){
                    if(app.getName().equals(appName)){
                        siteApps.add(app);
                        break;
                    }
                }
            }

            for(String appName : studyDTO.getEnvironment().getPatientApps()){
                for(MedidataApp app : apps){
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
}
