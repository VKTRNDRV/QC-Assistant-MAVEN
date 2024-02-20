package com.example.qcassistantmaven.service.tag;

import com.example.qcassistantmaven.domain.dto.tag.TagAddDto;
import com.example.qcassistantmaven.domain.dto.tag.TagDisplayDto;
import com.example.qcassistantmaven.domain.dto.tag.TagEditDto;
import com.example.qcassistantmaven.domain.entity.study.MedableStudy;
import com.example.qcassistantmaven.domain.entity.tag.BaseTag;
import com.example.qcassistantmaven.domain.entity.tag.IqviaTag;
import com.example.qcassistantmaven.domain.entity.tag.MedableTag;
import com.example.qcassistantmaven.domain.entity.tag.MedidataTag;
import com.example.qcassistantmaven.repository.DestinationRepository;
import com.example.qcassistantmaven.repository.study.MedableStudyRepository;
import com.example.qcassistantmaven.repository.tag.MedableTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MedableTagService extends BaseTagService{

    private MedableTagRepository tagRepository;

    private MedableStudyRepository studyRepository;

    @Autowired
    public MedableTagService(ModelMapper modelMapper, DestinationRepository destinationRepository, MedableTagRepository tagService, MedableStudyRepository studyRepository) {
        super(modelMapper, destinationRepository);
        this.tagRepository = tagService;
        this.studyRepository = studyRepository;
    }

    @Override
    public List<MedableTag> getEntities() {
        return this.tagRepository.findAll();
    }

    @Override
    public void addTag(TagAddDto tagAddDto) {
        super.validateTagAdd(tagAddDto);
        MedableTag tag = this.mapToEntity(tagAddDto);
        this.tagRepository.save(tag);
    }

    private MedableTag mapToEntity(TagAddDto tagAddDto) {
        MedableTag tag = super.modelMapper
                .map(tagAddDto, MedableTag.class);
        tag.setDestinations(super.getDestinationsByNames(
                tagAddDto.getDestinations()));
        tag.setStudies(this.getStudiesByNames(
                tagAddDto.getStudies()));

        return tag;
    }

    private List<MedableStudy> getStudiesByNames(List<String> studyNames) {
        List<MedableStudy> studies = new ArrayList<>();
        for(String studyName : studyNames){
            Optional<MedableStudy> study = this.studyRepository
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
        MedableTag tag = this.mapToEntity(tagEditDto);
        this.tagRepository.save(tag);
    }

    private MedableTag mapToEntity(TagEditDto tagAddDto) {
        MedableTag tag = super.modelMapper
                .map(tagAddDto, MedableTag.class);
        tag.setDestinations(super.getDestinationsByNames(
                tagAddDto.getDestinations()));
        tag.setStudies(this.getStudiesByNames(
                tagAddDto.getStudies()));

        return tag;
    }

//    @Override
//    public MedableTag getTagById(Long id) {
//        return null;
//    }

    @Override
    public TagEditDto getTagEdit(Long id) {
        return super.mapToTagEditDto(this.tagRepository
                .findById(id).orElseThrow());
    }

    @Override
    public <T extends BaseTag> void saveAll(Collection<T> tags) {
        this.tagRepository.saveAll((Collection<MedableTag>) tags);
    }
}
