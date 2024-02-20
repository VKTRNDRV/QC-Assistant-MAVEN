package com.example.qcassistantmaven.service.transfer;

import com.example.qcassistantmaven.domain.dto.app.AppTransferDTO;
import com.example.qcassistantmaven.domain.dto.sponsor.SponsorTransferDTO;
import com.example.qcassistantmaven.domain.dto.tag.TagTransferDTO;
import com.example.qcassistantmaven.domain.dto.transfer.ClinicalEntitiesTransferDTO;
import com.example.qcassistantmaven.domain.entity.app.BaseApp;
import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.sponsor.BaseSponsor;
import com.example.qcassistantmaven.domain.entity.study.BaseStudy;
import com.example.qcassistantmaven.domain.entity.tag.BaseTag;
import com.example.qcassistantmaven.service.DestinationService;
import com.example.qcassistantmaven.service.app.BaseAppService;
import com.example.qcassistantmaven.service.sponsor.BaseSponsorService;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public abstract class BaseTransferService {

    protected DestinationService destinationService;

    protected ModelMapper modelMapper;

    protected Gson gson;

    @Autowired
    public BaseTransferService(DestinationService destinationService, ModelMapper modelMapper, Gson gson) {
        this.destinationService = destinationService;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    protected <T extends BaseSponsor> List<SponsorTransferDTO> mapSponsorsToTransferDTO(Collection<T> sponsors) {
        List<SponsorTransferDTO> transferDTOs = new ArrayList<>();
        for(T sponsor : sponsors){
            transferDTOs.add(this.modelMapper
                    .map(sponsor, SponsorTransferDTO.class));
        }

        return transferDTOs;
    }

    protected <T extends BaseApp> List<AppTransferDTO> mapAppsToTransferDTO(Collection<T> apps) {
        List<AppTransferDTO> transferDTOs = new ArrayList<>();
        for(T app : apps){
            transferDTOs.add(this.modelMapper
                    .map(app, AppTransferDTO.class));
        }

        return transferDTOs;
    }

    protected <T extends BaseTag> List<TagTransferDTO> mapTagsToTransferDTO(Collection<T> tags) {
        List<TagTransferDTO> transferDTOs = new ArrayList<>();
        for(T tag : tags){
            TagTransferDTO dto = this.modelMapper
                    .map(tag, TagTransferDTO.class);
            dto.setStudies(tag.getStudies().stream()
                    .map(BaseStudy::getName)
                    .collect(Collectors.toList()));
            dto.setDestinations(tag.getDestinations().stream()
                    .map(Destination::getName)
                    .collect(Collectors.toList()));

            transferDTOs.add(dto);
        }

        return transferDTOs;
    }

    public abstract ClinicalEntitiesTransferDTO exportEntities();

    public abstract void importEntities(ClinicalEntitiesTransferDTO entitiesJSON);

    protected abstract <T extends BaseSponsorService> T getSponsorService();

    protected abstract <T extends BaseAppService> T getAppService();


    protected <T extends BaseSponsor> void importSponsors(String json, Class<T> sponsorClass) {
        SponsorTransferDTO[] dtos = this.gson
                .fromJson(json, SponsorTransferDTO[].class);
        List<T> sponsors = new ArrayList<>();
        for(SponsorTransferDTO sponsorDTO : dtos){
            if(getSponsorService().findFirstByName(
                    sponsorDTO.getName()).isPresent()) continue;
            T sponsor = this.modelMapper
                    .map(sponsorDTO, sponsorClass);
            sponsors.add(sponsor);
        }

        getSponsorService().saveAll(sponsors);
    }

    protected <T extends BaseApp> void importApps(String json, Class<T> appClass) {
        AppTransferDTO[] dtos = this.gson
                .fromJson(json, AppTransferDTO[].class);
        List<T> apps = new ArrayList<>();
        for(AppTransferDTO appDTO : dtos){
            if(getAppService().findFirstByName(
                    appDTO.getName()).isPresent()) continue;
            T app = this.modelMapper
                    .map(appDTO, appClass);
            apps.add(app);
        }

        getAppService().saveAll(apps);
    }
}
