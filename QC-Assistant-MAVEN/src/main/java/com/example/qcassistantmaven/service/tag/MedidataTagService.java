package com.example.qcassistantmaven.service.tag;

import com.example.qcassistantmaven.domain.dto.tag.TagAddDto;
import com.example.qcassistantmaven.domain.dto.tag.TagDisplayDto;
import com.example.qcassistantmaven.domain.dto.tag.TagEditDto;
import com.example.qcassistantmaven.domain.entity.study.MedidataStudy;
import com.example.qcassistantmaven.domain.entity.tag.BaseTag;
import com.example.qcassistantmaven.domain.entity.tag.MedidataTag;
import com.example.qcassistantmaven.repository.DestinationRepository;
import com.example.qcassistantmaven.repository.study.MedidataStudyRepository;
import com.example.qcassistantmaven.repository.tag.MedidataTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MedidataTagService extends BaseTagService{

    private MedidataTagRepository tagRepository;

    private MedidataStudyRepository studyRepository;

    @Autowired
    public MedidataTagService(ModelMapper modelMapper, DestinationRepository destinationRepository, MedidataTagRepository tagRepository, MedidataStudyRepository studyRepository) {
        super(modelMapper, destinationRepository);
        this.tagRepository = tagRepository;
        this.studyRepository = studyRepository;
    }

    @Override
    public List<MedidataTag> getEntities() {
        return this.tagRepository.findAll();
    }

    @Override
    public void addTag(TagAddDto tagAddDto) {
        super.validateTagAdd(tagAddDto);
        MedidataTag tag = this.mapToEntity(tagAddDto);
        this.tagRepository.save(tag);
    }

    private MedidataTag mapToEntity(TagAddDto tagAddDto) {
        MedidataTag tag = super.modelMapper
                .map(tagAddDto, MedidataTag.class);
        tag.setDestinations(super.getDestinationsByNames(
                tagAddDto.getDestinations()));
        tag.setStudies(this.getStudiesByNames(
                tagAddDto.getStudies()));

        return tag;
    }

    private MedidataTag mapToEntity(TagEditDto tagAddDto) {
        MedidataTag tag = super.modelMapper
                .map(tagAddDto, MedidataTag.class);
        tag.setDestinations(super.getDestinationsByNames(
                tagAddDto.getDestinations()));
        tag.setStudies(this.getStudiesByNames(
                tagAddDto.getStudies()));

        return tag;
    }

    private List<MedidataStudy> getStudiesByNames(List<String> studyNames) {
        List<MedidataStudy> studies = new ArrayList<>();
        for(String studyName : studyNames){
            Optional<MedidataStudy> study = this.studyRepository
                    .findFirstByName(studyName);
            study.ifPresent(studies::add);
        }

        return studies;
    }

    @Override
    public List<TagDisplayDto> getDisplayTags(){
        return super.mapToDisplayDTOs(this.tagRepository.findAll());
    }

    @Override
    public void editTag(TagEditDto tagEditDto) {
        super.validateTagEdit(tagEditDto);
        MedidataTag tag = this.mapToEntity(tagEditDto);
        this.tagRepository.save(tag);
    }

    @Override
    public TagEditDto getTagEdit(Long id) {
        return super.mapToTagEditDto(this.tagRepository
                .findById(id).orElseThrow());
    }

    @Override
    public <T extends BaseTag> void saveAll(Collection<T> tags) {
        this.tagRepository.saveAll((Collection<MedidataTag>) tags);
    }
}
