package com.example.qcassistantmaven.service.study;

import com.example.qcassistantmaven.domain.dto.study.StudyDisplayDto;
import com.example.qcassistantmaven.domain.entity.study.BaseStudy;
import com.example.qcassistantmaven.domain.entity.study.MedableStudy;
import com.example.qcassistantmaven.domain.entity.study.MedidataStudy;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public abstract class BaseStudyService {

    protected ModelMapper modelMapper;

    @Autowired
    public BaseStudyService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    protected void validateNameNotBlank(String name){
        if(name == null || name.trim().isEmpty()){
            throw new RuntimeException("Name cannot be blank");
        }
    }

    protected void validateAppsCount(List<String> siteApps, List<String> patientApps){
        if(siteApps == null || siteApps.size() == 0){
            throw new RuntimeException("No Site apps selected");
        }
        if(patientApps == null || patientApps.size() == 0){
            throw new RuntimeException("No Patient apps selected");
        }
    }

    public List<StudyDisplayDto> displayAllStudies() {
        return this.getEntities().stream()
                .map(s -> new StudyDisplayDto()
                        .setId(s.getId())
                        .setName(s.getName())
                        .setSponsor(s.getSponsor().getName()))
                .sorted((s1,s2) -> {
                    int result = s1.getSponsor().compareTo(s2.getSponsor());
                    if(result == 0){
                        result = s1.getName().compareTo(s2.getName());
                    }
                    return result;
                })
                .collect(Collectors.toList());
    }

    public abstract <T extends BaseStudy> List<T> getEntities();

    public abstract <T extends BaseStudy> T getUnknownStudy();

    public abstract List<StudyDisplayDto> getTagStudies();

    public abstract <T extends BaseStudy> Optional<T> findFirstByName(String name);

    public abstract <T extends BaseStudy> void saveAll(Collection<T> studies);
}
