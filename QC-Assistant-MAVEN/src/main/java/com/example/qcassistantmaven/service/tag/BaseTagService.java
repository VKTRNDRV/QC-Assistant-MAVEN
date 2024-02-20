package com.example.qcassistantmaven.service.tag;

import com.example.qcassistantmaven.domain.dto.tag.TagAddDto;
import com.example.qcassistantmaven.domain.dto.tag.TagDisplayDto;
import com.example.qcassistantmaven.domain.dto.tag.TagEditDto;
import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.study.BaseStudy;
import com.example.qcassistantmaven.domain.entity.tag.BaseTag;
import com.example.qcassistantmaven.domain.entity.tag.MedidataTag;
import com.example.qcassistantmaven.domain.enums.OrderType;
import com.example.qcassistantmaven.domain.enums.item.OperatingSystem;
import com.example.qcassistantmaven.domain.enums.item.ShellType;
import com.example.qcassistantmaven.repository.DestinationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public abstract class BaseTagService {

    public static final int MAX_NOTE_LENGTH = 254;
    private static final String N_A = "N/A";

    protected ModelMapper modelMapper;

    protected DestinationRepository destinationRepository;

    @Autowired
    public BaseTagService(ModelMapper modelMapper, DestinationRepository destinationRepository){
        this.modelMapper = modelMapper;
        this.destinationRepository = destinationRepository;
    }

    public abstract <T extends BaseTag> List<T> getEntities();
    public abstract void addTag(TagAddDto tagAddDto);
    public abstract void editTag(TagEditDto tagEditDto);
//    public abstract <T extends BaseTag> T getTagById(Long id);
    public abstract List<TagDisplayDto> getDisplayTags();
    public abstract TagEditDto getTagEdit(Long id);

    protected void validateTagAdd(TagAddDto tagAddDto) {
        String noteText = tagAddDto.getText();
        validateTagText(noteText);
    }

    protected void validateTagText(String tagText){
        if(tagText.trim().isEmpty()){
            throw new RuntimeException("Note text cannot be blank");
        }

        if(tagText.length() > MAX_NOTE_LENGTH){
            throw new RuntimeException("Note text length cannot be over 254");
        }
    }

    protected void validateTagEdit(TagEditDto tagEditDto){
        validateTagText(tagEditDto.getText());
    }


    protected List<Destination> getDestinationsByNames(Collection<String> destinationNames){
        List<Destination> destinations = new ArrayList<>();
        for(String destinationName : destinationNames){
            Optional<Destination> opt = this.destinationRepository
                    .findFirstByName(destinationName);
            opt.ifPresent(destinations::add);
        }

        return destinations;
    }

    protected <T extends BaseTag> List<T> sortTagsForDisplay(List<T> tags){
        return tags.stream().sorted(Comparator
                        .comparingInt((T t) -> t.getSeverity().ordinal())
                        .thenComparingInt(t -> t.getType().ordinal()))
                .collect(Collectors.toList());
    }

    protected <T extends BaseTag> List<TagDisplayDto> mapToDisplayDTOs(List<T> tags){
        tags = sortTagsForDisplay(tags);

        List<TagDisplayDto> displayDTOs = new ArrayList<>();
        for(T tag : tags){
            displayDTOs.add(this.mapToDisplayDTO(tag));
        }

        return displayDTOs;
    }

    private <T extends BaseTag> TagDisplayDto mapToDisplayDTO(T tag) {
        TagDisplayDto dto = new TagDisplayDto();
        dto.setId(tag.getId())
                .setText(tag.getText())
                .setSeverity(tag.getSeverity().name())
                .setType(tag.getType().name());

        if(tag.getOrderType().equals(OrderType.OTHER)){
            dto.setOrderType(N_A);
        }else{
            dto.setOrderType(tag.getOrderType().name());
        }

        if(tag.getShellType().equals(ShellType.OTHER)){
            dto.setShellType(N_A);
        }else{
            dto.setShellType(tag.getShellType().name());
        }

        if(tag.getOperatingSystem().equals(OperatingSystem.OTHER)){
            dto.setOperatingSystem(N_A);
        }else{
            dto.setOperatingSystem(tag.getOperatingSystem().name());
        }

        List<String> destinationNames = tag.getDestinations()
                .stream().map(Destination::getName)
                .collect(Collectors.toList());
        if(destinationNames.isEmpty()){
            dto.setDestinations(N_A);
        }else{
            dto.setDestinations(String.join(", ", destinationNames));
        }

        List<String> studyNames = tag.getStudies().stream()
                .map(BaseStudy::getName)
                .collect(Collectors.toList());
        if(studyNames.isEmpty()){
            dto.setStudies(N_A);
        }else{
            dto.setStudies(String.join(", ", studyNames));
        }

        return dto;
    }

    protected <T extends BaseTag> TagEditDto mapToTagEditDto(T tag){
        TagEditDto editDto = this.modelMapper
                .map(tag, TagEditDto.class);

        editDto.setSpecialFields(tag);

        return editDto;
    }
    public abstract  <T extends BaseTag> void saveAll(Collection<T> tags);
}
