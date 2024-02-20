package com.example.qcassistantmaven.service.transfer;

import com.example.qcassistantmaven.domain.dto.study.transfer.IqviaStudyTransferDTO;
import com.example.qcassistantmaven.domain.dto.study.transfer.MedidataStudyTransferDTO;
import com.example.qcassistantmaven.domain.dto.tag.TagTransferDTO;
import com.example.qcassistantmaven.domain.dto.transfer.ClinicalEntitiesTransferDTO;
import com.example.qcassistantmaven.domain.entity.app.BaseApp;
import com.example.qcassistantmaven.domain.entity.app.IqviaApp;
import com.example.qcassistantmaven.domain.entity.app.MedidataApp;
import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.sponsor.IqviaSponsor;
import com.example.qcassistantmaven.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistantmaven.domain.entity.study.IqviaStudy;
import com.example.qcassistantmaven.domain.entity.study.MedidataStudy;
import com.example.qcassistantmaven.domain.entity.study.environment.IqviaEnvironment;
import com.example.qcassistantmaven.domain.entity.study.environment.MedidataEnvironment;
import com.example.qcassistantmaven.domain.entity.tag.IqviaTag;
import com.example.qcassistantmaven.domain.entity.tag.MedidataTag;
import com.example.qcassistantmaven.service.DestinationService;
import com.example.qcassistantmaven.service.app.IqviaAppService;
import com.example.qcassistantmaven.service.sponsor.IqviaSponsorService;
import com.example.qcassistantmaven.service.study.IqviaStudyService;
import com.example.qcassistantmaven.service.tag.IqviaTagService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IqviaTransferService extends BaseTransferService{

    private IqviaSponsorService sponsorService;

    private IqviaAppService appService;

    private IqviaTagService tagService;

    private IqviaStudyService studyService;

    public IqviaTransferService(DestinationService destinationService,
                                ModelMapper modelMapper,
                                Gson gson,
                                IqviaSponsorService sponsorService,
                                IqviaAppService appService,
                                IqviaTagService tagService,
                                IqviaStudyService studyService) {
        super(destinationService, modelMapper, gson);
        this.sponsorService = sponsorService;
        this.appService = appService;
        this.tagService = tagService;
        this.studyService = studyService;
    }

    @Override
    public ClinicalEntitiesTransferDTO exportEntities(){
        List<IqviaSponsor> sponsors = this.sponsorService.getEntities();
        List<IqviaApp> apps = this.appService.getEntities();
        List<IqviaTag> tags = this.tagService.getEntities();
        List<IqviaStudy> studies = this.studyService.getEntities();

        ClinicalEntitiesTransferDTO entities = new ClinicalEntitiesTransferDTO();
        entities.setSponsors(this.gson.toJson(super.mapSponsorsToTransferDTO(sponsors)))
                .setApps(this.gson.toJson(super.mapAppsToTransferDTO(apps)))
                .setTags(this.gson.toJson(super.mapTagsToTransferDTO(tags)))
                .setStudies(this.gson.toJson(this.mapStudiesToTransferDTO(studies)));

        return entities;
    }

    private List<IqviaStudyTransferDTO> mapStudiesToTransferDTO(List<IqviaStudy> studies) {
        List<IqviaStudyTransferDTO> transferDTOs = new ArrayList<>();
        for(IqviaStudy study : studies){
            IqviaStudyTransferDTO studyTransferDTO = this.modelMapper
                    .map(study, IqviaStudyTransferDTO.class);

            IqviaEnvironment env = study.getEnvironment();

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
            super.importSponsors(entitiesJSON.getSponsors(), IqviaSponsor.class);
        }

        if(!entitiesJSON.getApps().trim().isEmpty()){
            super.importApps(entitiesJSON.getApps(), IqviaApp.class);
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
        List<IqviaTag> tags = new ArrayList<>();
        List<Destination> allDestinations = this.destinationService.getEntities();
        List<IqviaStudy> allStudies = this.studyService.getEntities();
        for(TagTransferDTO tagDTO : dtos){
            IqviaTag tag = this.modelMapper.map(tagDTO, IqviaTag.class);

            List<Destination> tagDestinations = new ArrayList<>();
            for(String name : tagDTO.getDestinations()){
                for(Destination destination : allDestinations){
                    if(name.equals(destination.getName())){
                        tagDestinations.add(destination);
                        break;
                    }
                }
            }

            List<IqviaStudy> tagStudies = new ArrayList<>();
            for(String name : tagDTO.getStudies()){
                for(IqviaStudy study : allStudies){
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
        IqviaStudyTransferDTO[] dtos = this.gson
                .fromJson(json, IqviaStudyTransferDTO[].class);
        List<IqviaStudy> studies = this.mapStudyDTOsToEntities(List.of(dtos));
        this.studyService.saveAll(studies);
    }
    public List<IqviaStudy> mapStudyDTOsToEntities(Iterable <IqviaStudyTransferDTO> dtos){
        IqviaSponsor unknownSponsor = this.sponsorService.getUnknownSponsor();
        List<IqviaApp> apps = this.appService.getEntities();
        List<IqviaStudy> studies = new ArrayList<>();

        for(IqviaStudyTransferDTO studyDTO : dtos){
            if(this.studyService.findFirstByName(
                    studyDTO.getName()).isPresent()) continue;

            IqviaStudy study = this.modelMapper.map(studyDTO, IqviaStudy.class);
            Optional<IqviaSponsor> sponsor = this.sponsorService
                    .findFirstByName(studyDTO.getSponsor());
            if(sponsor.isPresent()){
                study.setSponsor(sponsor.get());
            }else{
                study.setSponsor(unknownSponsor);
            }

            List<IqviaApp> siteApps = new ArrayList<>();
            List<IqviaApp> patientApps = new ArrayList<>();

            for(String appName : studyDTO.getEnvironment().getSiteApps()){
                for(IqviaApp app : apps){
                    if(app.getName().equals(appName)){
                        siteApps.add(app);
                        break;
                    }
                }
            }

            for(String appName : studyDTO.getEnvironment().getPatientApps()){
                for(IqviaApp app : apps){
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
    protected IqviaAppService getAppService() {
        return this.appService;
    }

    @Override
    protected IqviaSponsorService getSponsorService() {
        return this.sponsorService;
    }
}
