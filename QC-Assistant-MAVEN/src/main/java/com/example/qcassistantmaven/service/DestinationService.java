package com.example.qcassistantmaven.service;

import com.example.qcassistantmaven.domain.dto.destination.*;
import com.example.qcassistantmaven.domain.dto.language.LanguageTransferDTO;
import com.example.qcassistantmaven.domain.dto.transfer.DestinationLangsTransferDTO;
import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.destination.Language;
import com.example.qcassistantmaven.domain.enums.item.PlugType;
import com.example.qcassistantmaven.domain.enums.item.SimType;
import com.example.qcassistantmaven.repository.DestinationRepository;
import com.example.qcassistantmaven.repository.LanguageRepository;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DestinationService {

    private DestinationRepository destinationRepository;

    private LanguageRepository languageRepository;

    private ModelMapper modelMapper;

    private Gson gson;

    @Autowired
    public DestinationService(DestinationRepository destinationRepository, LanguageRepository languageRepository, ModelMapper modelMapper, Gson gson) {
        this.destinationRepository = destinationRepository;
        this.languageRepository = languageRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @PostConstruct
    public void initUnknown(){
        if(this.destinationRepository.findFirstByName(
                BaseEntity.UNKNOWN).isPresent()){
            return;
        }

        Destination destination = new Destination()
                .setName(BaseEntity.UNKNOWN)
                .setLanguages(new ArrayList<>())
                .setSimType(SimType.NONE)
                .setPlugType(PlugType.C)
                .setRequiresInvoice(TrinaryBoolean.UNKNOWN)
                .setRequiresSpecialModels(TrinaryBoolean.UNKNOWN)
                .setRequiresUnusedDevices(TrinaryBoolean.UNKNOWN);

        this.destinationRepository.save(destination);
    }

    public DestinationLangsTransferDTO exportDestinationsAndLangs(){
        return new DestinationLangsTransferDTO()
                .setDestinations(this.gson.toJson(exportDestinations()))
                .setLanguages(this.gson.toJson(exportLanguages()));
    }

    public List<DestinationTransferDTO> exportDestinations(){
        List<DestinationTransferDTO> destinations = this.getEntities()
                .stream()
                .map(d -> this.modelMapper.map(d, DestinationTransferDTO.class))
                .collect(Collectors.toList());

        return destinations;
    }

    public List<LanguageTransferDTO> exportLanguages(){
        List<LanguageTransferDTO> languages = this.languageRepository.findAll()
                .stream()
                .map(l -> this.modelMapper.map(l, LanguageTransferDTO.class))
                .collect(Collectors.toList());

        return languages;
    }

    public void addDestination(DestinationAddDto destinationAddDto) {
        validateAddDestination(destinationAddDto);
        Destination destination = this.modelMapper.map(destinationAddDto, Destination.class);
        List<Language> languages = getLanguagesByName(destinationAddDto.getSelectedLanguages());
        destination.setLanguages(languages);
        this.destinationRepository.save(destination);
    }

    private List<Language> getLanguagesByName(List<String> langNames) {
        List<Language> languages = new ArrayList<>();
        for(String langName : langNames){
            Optional<Language> language = this
                    .languageRepository.findFirstByName(langName);
            language.ifPresent(languages::add);
        }
        return languages;
    }

    private void validateEditDestination(DestinationEditDto editDto) {
        if(editDto.getSelectedLanguages() == null ||
                editDto.getSelectedLanguages().size() == 0){
            throw new RuntimeException("No languages selected");
        }
        validateNameNotBlank(editDto.getName());
        if(!this.destinationRepository.findById(editDto.getId())
                .get().getName()
                .equals(editDto.getName())){
            validateUniqueName(editDto.getName());
        }
    }

    private void validateAddDestination(DestinationAddDto addDto) {
        if(addDto.getSelectedLanguages() == null ||
                addDto.getSelectedLanguages().size() == 0){
            throw new RuntimeException("No languages selected");
        }
        validateNameNotBlank(addDto.getName());
        validateUniqueName(addDto.getName());
    }

    public List<DestinationDisplayDto> displayDestinations(){
        List<Destination> entities = this.getEntities();
        List<DestinationDisplayDto> dtos = new ArrayList<>();
        for(Destination entity : entities){
            DestinationDisplayDto dto = this.modelMapper
                    .map(entity, DestinationDisplayDto.class);
            dto.setLanguages(String.join(", ",
                    entity.getLanguages().stream()
                            .map(Language::getName)
                            .collect(Collectors.toList())));
            dtos.add(dto);
        }

        return dtos.stream()
                .sorted((d1, d2) -> d1.getName().compareTo(d2.getName()))
                .collect(Collectors.toList());
    }

    public DestinationEditDto getDestinationEditById(Long id){
        Destination destination = this.destinationRepository.findById(id).get();
        return this.modelMapper.map(destination, DestinationEditDto.class);
    }

    public void editDestination(DestinationEditDto editDto) {
        validateEditDestination(editDto);
        Destination destination = this.modelMapper.map(editDto, Destination.class);
        destination.setLanguages(getLanguagesByName(editDto.getSelectedLanguages()));
        this.destinationRepository.save(destination);
    }


    private void validateNameNotBlank(String name){
        if(name == null || name.trim().isEmpty()){
            throw new RuntimeException("Name cannot be null");
        }
    }

    private void validateUniqueName(String name){
        if(this.destinationRepository.findFirstByName(name).isPresent()){
            throw new RuntimeException("Destination '" + name + "' already present");
        }
    }

    public List<Destination> getEntities(){
        return this.destinationRepository
                .findAllByNameNot(BaseEntity.UNKNOWN);
    }

    public Destination getUnknownDestinationEntity() {
        return this.destinationRepository
                .findFirstByName(BaseEntity.UNKNOWN)
                .get();
    }

    public List<DestinationNameDto> getTagDestinations() {
        List<DestinationNameDto> destinationDTOs = this.getEntities().stream()
                .map(d -> this.modelMapper
                        .map(d, DestinationNameDto.class))
                .sorted((d1, d2) -> d1.getName()
                        .compareTo(d2.getName()))
                .collect(Collectors.toList());

        return destinationDTOs;
    }

    public void importDestinationsAndLangs(DestinationLangsTransferDTO importDTO) {
        String langsJson = importDTO.getLanguages();
        String destinationsJson = importDTO.getDestinations();
        if(!langsJson.trim().isEmpty()){
            this.importLanguages(importDTO.getLanguages());
        }

        if(!destinationsJson.trim().isEmpty()){
            this.importDestinations(importDTO.getDestinations());
        }
    }

    private void importLanguages(String json) {
        LanguageTransferDTO[] transferDTOs = this.gson
                .fromJson(json, LanguageTransferDTO[].class);

        for(LanguageTransferDTO transferDTO : transferDTOs){
            if(this.languageRepository
                    .findFirstByName(transferDTO.getName())
                    .isPresent()){
                continue;
            }

            Language language = this.modelMapper.map(transferDTO, Language.class);
            this.languageRepository.save(language);
        }
    }

    private void importDestinations(String json) {
        DestinationTransferDTO[] importDTOs = this.gson
                .fromJson(json, DestinationTransferDTO[].class);

        for(DestinationTransferDTO importDTO : importDTOs){
            if(importDTO.getLanguages().isEmpty() ||
                    this.destinationRepository
                    .findFirstByName(importDTO.getName())
                    .isPresent()){
                continue;
            }

            Destination destination = this.modelMapper
                    .map(importDTO, Destination.class);

            destination.setLanguages(this.getLanguagesByName(
                    importDTO.getLanguages()
                            .stream()
                            .map(LanguageTransferDTO::getName)
                            .collect(Collectors.toList())));

            this.destinationRepository.save(destination);
        }
    }
}
