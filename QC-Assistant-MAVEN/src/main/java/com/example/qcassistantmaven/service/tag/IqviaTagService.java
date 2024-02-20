package com.example.qcassistantmaven.service.tag;

import com.example.qcassistantmaven.domain.dto.tag.TagAddDto;
import com.example.qcassistantmaven.domain.dto.tag.TagDisplayDto;
import com.example.qcassistantmaven.domain.dto.tag.TagEditDto;
import com.example.qcassistantmaven.domain.entity.study.IqviaStudy;
import com.example.qcassistantmaven.domain.entity.tag.BaseTag;
import com.example.qcassistantmaven.domain.entity.tag.IqviaTag;
import com.example.qcassistantmaven.domain.entity.tag.MedidataTag;
import com.example.qcassistantmaven.repository.DestinationRepository;
import com.example.qcassistantmaven.repository.study.IqviaStudyRepository;
import com.example.qcassistantmaven.repository.tag.IqviaTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class IqviaTagService extends BaseTagService{

    private IqviaTagRepository tagRepository;

    private IqviaStudyRepository studyRepository;

    @Autowired
    public IqviaTagService(ModelMapper modelMapper, DestinationRepository destinationRepository, IqviaTagRepository tagRepository, IqviaStudyRepository studyRepository) {
        super(modelMapper, destinationRepository);
        this.tagRepository = tagRepository;
        this.studyRepository = studyRepository;
    }

    @Override
    public List<IqviaTag> getEntities() {
        return this.tagRepository.findAll();
    }

    @Override
    public void addTag(TagAddDto tagAddDto) {
        super.validateTagAdd(tagAddDto);
        IqviaTag tag = this.mapToEntity(tagAddDto);
        this.tagRepository.save(tag);
    }

    private IqviaTag mapToEntity(TagAddDto tagAddDto) {
        IqviaTag tag = super.modelMapper
                .map(tagAddDto, IqviaTag.class);
        tag.setDestinations(super.getDestinationsByNames(
                tagAddDto.getDestinations()));
        tag.setStudies(this.getStudiesByNames(
                tagAddDto.getStudies()));

        return tag;
    }

    private IqviaTag mapToEntity(TagEditDto tagAddDto) {
        IqviaTag tag = super.modelMapper
                .map(tagAddDto, IqviaTag.class);
        tag.setDestinations(super.getDestinationsByNames(
                tagAddDto.getDestinations()));
        tag.setStudies(this.getStudiesByNames(
                tagAddDto.getStudies()));

        return tag;
    }

    private List<IqviaStudy> getStudiesByNames(List<String> studyNames) {
        List<IqviaStudy> studies = new ArrayList<>();
        for(String studyName : studyNames){
            Optional<IqviaStudy> study = this.studyRepository
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
        IqviaTag tag = this.mapToEntity(tagEditDto);
        this.tagRepository.save(tag);
    }

//    @Override
//    public IqviaTag getTagById(Long id) {
//        return null;
//    }

    @Override
    public TagEditDto getTagEdit(Long id) {
        return super.mapToTagEditDto(this.tagRepository
                .findById(id).orElseThrow());
    }

    @Override
    public <T extends BaseTag> void saveAll(Collection<T> tags) {
        this.tagRepository.saveAll((Collection<IqviaTag>) tags);
    }
}
