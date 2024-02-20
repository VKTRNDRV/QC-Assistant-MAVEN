package com.example.qcassistantmaven.service.study;

import com.example.qcassistantmaven.domain.dto.study.StudyDisplayDto;
import com.example.qcassistantmaven.domain.dto.study.add.MedableStudyAddDto;
import com.example.qcassistantmaven.domain.dto.study.edit.MedableStudyEditDto;
import com.example.qcassistantmaven.domain.dto.study.info.MedableStudyInfoDto;
import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.domain.entity.app.MedableApp;
import com.example.qcassistantmaven.domain.entity.sponsor.MedableSponsor;
import com.example.qcassistantmaven.domain.entity.study.BaseStudy;
import com.example.qcassistantmaven.domain.entity.study.IqviaStudy;
import com.example.qcassistantmaven.domain.entity.study.MedableStudy;
import com.example.qcassistantmaven.domain.entity.study.environment.MedableEnvironment;
import com.example.qcassistantmaven.repository.app.MedableAppRepository;
import com.example.qcassistantmaven.repository.sponsor.MedableSponsorRepository;
import com.example.qcassistantmaven.repository.study.MedableStudyRepository;
import com.example.qcassistantmaven.repository.study.environment.MedableEnvironmentRepository;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedableStudyService extends BaseStudyService{

    private MedableStudyRepository studyRepository;
    private MedableAppRepository appRepository;
    private MedableSponsorRepository sponsorRepository;
    private MedableEnvironmentRepository environmentRepository;

    @Autowired
    public MedableStudyService(ModelMapper modelMapper,
                               MedableStudyRepository studyRepository,
                               MedableAppRepository appRepository,
                               MedableSponsorRepository sponsorRepository,
                               MedableEnvironmentRepository environmentRepository) {
        super(modelMapper);
        this.studyRepository = studyRepository;
        this.appRepository = appRepository;
        this.sponsorRepository = sponsorRepository;
        this.environmentRepository = environmentRepository;
    }

    @PostConstruct
    public void initUnknown(){
        if(this.studyRepository.findFirstByName(
                BaseEntity.UNKNOWN).isEmpty()){
            MedableStudy study = new MedableStudy();
            study.setName(BaseEntity.UNKNOWN)
                    .setFolderURL(BaseEntity.UNKNOWN);


            Optional<MedableSponsor> optSponsor = this.sponsorRepository
                    .findFirstByName(BaseEntity.UNKNOWN);
            if(optSponsor.isPresent()){
                study.setSponsor(optSponsor.get());
            }else {
                MedableSponsor sponsor = new MedableSponsor();
                sponsor.setName(BaseEntity.UNKNOWN)
                        .setAreStudyNamesSimilar(TrinaryBoolean.UNKNOWN);
                this.sponsorRepository.save(sponsor);

                study.setSponsor(sponsor);
            }

            MedableEnvironment environment = new MedableEnvironment();
            environment.setPatientApps(new ArrayList<>())
                    .setSiteApps(new ArrayList<>())
                    .setContainsChinaGroup(TrinaryBoolean.UNKNOWN)
                    .setIsSitePatientSeparated(TrinaryBoolean.UNKNOWN)
                    .setIsDestinationSeparated(TrinaryBoolean.UNKNOWN)
                    .setIsOsSeparated(TrinaryBoolean.UNKNOWN);
            this.environmentRepository.save(environment);

            study.setEnvironment(environment);

            this.studyRepository.save(study);
        }
    }

    public void addStudy(MedableStudyAddDto studyAddDto) {
        validateStudyAdd(studyAddDto);
        studyAddDto.trimStringFields();
        MedableStudy study = mapToEntity(studyAddDto);
        this.environmentRepository.save(study.getEnvironment());
        this.studyRepository.save(study);
    }

    private void validateStudyAdd(MedableStudyAddDto studyAddDto) {
        studyAddDto.trimStringFields();
        super.validateNameNotBlank(studyAddDto.getName());
        validateUniqueName(studyAddDto.getName());
        validateAppsCount(studyAddDto.getEnvironment().getSiteApps(),
                studyAddDto.getEnvironment().getPatientApps());
    }

    private void validateUniqueName(String name){
        if(this.studyRepository.findFirstByName(name).isPresent()){
            throw new RuntimeException("Study \"" + name + "\" already present");
        }
    }

    private MedableStudy mapToEntity(MedableStudyAddDto studyAddDto) {
        MedableStudy study = this.modelMapper.map(studyAddDto, MedableStudy.class);
        MedableSponsor sponsor = this.sponsorRepository
                .findFirstByName(studyAddDto.getSponsor()).orElseThrow();
        List<MedableApp> siteApps = getAppsByName(
                studyAddDto.getEnvironment().getSiteApps());
        List<MedableApp> patientApps = getAppsByName(
                studyAddDto.getEnvironment().getPatientApps());

        study.setSponsor(sponsor)
                .getEnvironment()
                .setSiteApps(siteApps)
                .setPatientApps(patientApps);
        return study;
    }

    private List<MedableApp> getAppsByName(List<String> appNames) {
        List<MedableApp> apps = new ArrayList<>();
        for(String name : appNames){
            Optional<MedableApp> app = this.appRepository.findFirstByName(name);
            app.ifPresent(apps::add);
        }

        return apps;
    }

    public MedableStudyEditDto getStudyEditById(Long id) {
        MedableStudy study = this.studyRepository
                .findById(id).orElseThrow();
        MedableStudyEditDto editDto = this.modelMapper
                .map(study, MedableStudyEditDto.class);
        editDto.setManualFields(study);
        return editDto;
    }

    public void editStudy(MedableStudyEditDto studyEditDto) {
        validateStudyEdit(studyEditDto);
        studyEditDto.trimStringFields();
        MedableStudy study = mapToEntity(studyEditDto);
        this.environmentRepository.save(study.getEnvironment());
        this.studyRepository.save(study);
    }

    private void validateStudyEdit(MedableStudyEditDto studyEditDto) {
        studyEditDto.trimStringFields();
        super.validateNameNotBlank(studyEditDto.getName());
        if(!this.studyRepository.findById(studyEditDto.getId()).get().getName()
                .equals(studyEditDto.getName())){
            validateUniqueName(studyEditDto.getName());
        }
        validateAppsCount(studyEditDto.getEnvironment().getSiteApps(),
                studyEditDto.getEnvironment().getPatientApps());
    }

    private MedableStudy mapToEntity(MedableStudyEditDto studyEditDto) {
        MedableStudy study = this.modelMapper.map(studyEditDto, MedableStudy.class);
        List<MedableApp> siteApps = getAppsByName(
                studyEditDto.getEnvironment().getSiteApps());
        List<MedableApp> patientApps = getAppsByName(
                studyEditDto.getEnvironment().getPatientApps());
        MedableSponsor sponsor = this.sponsorRepository
                .findFirstByName(studyEditDto.getSponsor()).orElseThrow();
        study.setSponsor(sponsor)
                .getEnvironment()
                .setSiteApps(siteApps)
                .setPatientApps(patientApps);
        return study;
    }

//    @Override
//    public List<StudyDisplayDto> displayAllStudies() {
//        return getEntities().stream()
//                .map(s -> new StudyDisplayDto()
//                        .setId(s.getId())
//                        .setSponsor(s.getSponsor().getName())
//                        .setName(s.getName()))
//                .sorted((s1,s2) -> {
//                    int result = s1.getSponsor().compareTo(s2.getSponsor());
//                    if(result == 0){
//                        result = s1.getName().compareTo(s2.getName());
//                    }
//                    return result;
//                })
//                .collect(Collectors.toList());
//    }

    @Override
    public List<MedableStudy> getEntities(){
        return this.studyRepository.findAllByNameNot(BaseEntity.UNKNOWN);
    }

    public MedableStudyInfoDto getStudyInfoById(Long id) {
        MedableStudy study = this.studyRepository
                .findById(id).orElseThrow();

        MedableStudyInfoDto dto = this.modelMapper
                .map(study, MedableStudyInfoDto.class);
        dto.setSpecialFields(study);

        return dto;
    }

    @Override
    public MedableStudy getUnknownStudy() {
        return this.studyRepository.findFirstByName(
                BaseEntity.UNKNOWN).get();
    }

    @Override
    public List<StudyDisplayDto> getTagStudies() {
        return this.displayAllStudies();
    }

    @Override
    public <T extends BaseStudy> void saveAll(Collection<T> studies) {
        Collection<MedableStudy> casted = (Collection<MedableStudy>) studies;
        this.environmentRepository.saveAll(casted.stream()
                .map(MedableStudy::getEnvironment)
                .collect(Collectors.toList()));
        this.studyRepository.saveAll(casted);
    }

    @Override
    public Optional<MedableStudy> findFirstByName(String name) {
        return this.studyRepository.findFirstByName(name);
    }
}
