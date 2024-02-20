package com.example.qcassistantmaven.service.study;

import com.example.qcassistantmaven.domain.dto.study.add.MedidataStudyAddDto;
import com.example.qcassistantmaven.domain.dto.study.StudyDisplayDto;
import com.example.qcassistantmaven.domain.dto.study.edit.MedidataStudyEditDto;
import com.example.qcassistantmaven.domain.dto.study.info.MedidataStudyInfoDto;
import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.domain.entity.app.MedidataApp;
import com.example.qcassistantmaven.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistantmaven.domain.entity.study.BaseStudy;
import com.example.qcassistantmaven.domain.entity.study.MedidataStudy;
import com.example.qcassistantmaven.domain.entity.study.environment.MedidataEnvironment;
import com.example.qcassistantmaven.repository.app.MedidataAppRepository;
import com.example.qcassistantmaven.repository.sponsor.MedidataSponsorRepository;
import com.example.qcassistantmaven.repository.study.MedidataStudyRepository;
import com.example.qcassistantmaven.repository.study.environment.MedidataEnvironmentRepository;
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
public class MedidataStudyService extends BaseStudyService{

    private MedidataStudyRepository studyRepository;
    private MedidataAppRepository appRepository;
    private MedidataSponsorRepository sponsorRepository;
    private MedidataEnvironmentRepository environmentRepository;


    @Autowired
    public MedidataStudyService(MedidataStudyRepository studyRepository,
                                MedidataAppRepository appRepository,
                                MedidataSponsorRepository sponsorRepository,
                                MedidataEnvironmentRepository environmentRepository,
                                ModelMapper modelMapper) {
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
            MedidataStudy study = new MedidataStudy();
            study.setName(BaseEntity.UNKNOWN)
                    .setFolderURL(BaseEntity.UNKNOWN);
            study.setContainsTranslatedDocs(TrinaryBoolean.UNKNOWN)
                    .setContainsTranslatedLabels(TrinaryBoolean.UNKNOWN)
                    .setContainsEditableWelcomeLetter(TrinaryBoolean.UNKNOWN)
                    .setIncludesHeadphonesStyluses(TrinaryBoolean.UNKNOWN)
                    .setIsPatientDeviceIpad(TrinaryBoolean.UNKNOWN);


            Optional<MedidataSponsor> optSponsor = this.sponsorRepository
                    .findFirstByName(BaseEntity.UNKNOWN);
            if(optSponsor.isPresent()){
                study.setSponsor(optSponsor.get());
            }else {
                MedidataSponsor sponsor = new MedidataSponsor();
                sponsor.setName(BaseEntity.UNKNOWN)
                        .setAreStudyNamesSimilar(TrinaryBoolean.UNKNOWN);
                this.sponsorRepository.save(sponsor);

                study.setSponsor(sponsor);
            }

            MedidataEnvironment environment = new MedidataEnvironment();
            environment.setPatientApps(new ArrayList<>())
                    .setSiteApps(new ArrayList<>())
                    .setIsLegacy(TrinaryBoolean.UNKNOWN)
                    .setIsSitePatientSeparated(TrinaryBoolean.UNKNOWN)
                    .setIsDestinationSeparated(TrinaryBoolean.UNKNOWN)
                    .setIsOsSeparated(TrinaryBoolean.UNKNOWN);
            this.environmentRepository.save(environment);

            study.setEnvironment(environment);

            this.studyRepository.save(study);
        }
    }

    public void addStudy(MedidataStudyAddDto studyAddDto) {
        validateStudyAdd(studyAddDto);
        studyAddDto.trimStringFields();
        MedidataStudy study = mapToEntity(studyAddDto);
        this.environmentRepository.save(study.getEnvironment());
        this.studyRepository.save(study);
    }

    public void editStudy(MedidataStudyEditDto studyEditDto){
        validateStudyEdit(studyEditDto);
        studyEditDto.trimStringFields();
        MedidataStudy study = mapToEntity(studyEditDto);
        this.environmentRepository.save(study.getEnvironment());
        this.studyRepository.save(study);
    }

    private MedidataStudy mapToEntity(MedidataStudyEditDto studyEditDto) {
        MedidataStudy study = this.modelMapper.map(studyEditDto, MedidataStudy.class);
        List<MedidataApp> siteApps = getAppsByName(
                studyEditDto.getEnvironment().getSiteApps());
        List<MedidataApp> patientApps =
                getAppsByName(studyEditDto.getEnvironment().getPatientApps());
        MedidataSponsor sponsor = this.sponsorRepository
                .findFirstByName(studyEditDto.getSponsor()).orElseThrow();
        study.setSponsor(sponsor)
                .getEnvironment()
                .setSiteApps(siteApps)
                .setPatientApps(patientApps);
        return study;
    }

    private MedidataStudy mapToEntity(MedidataStudyAddDto studyAddDto) {
        MedidataStudy study = this.modelMapper.map(studyAddDto, MedidataStudy.class);
        MedidataSponsor sponsor = this.sponsorRepository
                .findFirstByName(studyAddDto.getSponsor()).orElseThrow();
        List<MedidataApp> siteApps = getAppsByName(
                studyAddDto.getEnvironment().getSiteApps());
        List<MedidataApp> patientApps = getAppsByName(
                studyAddDto.getEnvironment().getPatientApps());

        study.setSponsor(sponsor)
                .getEnvironment()
                .setSiteApps(siteApps)
                .setPatientApps(patientApps);
        return study;
    }

    private List<MedidataApp> getAppsByName(List<String> appNames) {
        List<MedidataApp> apps = new ArrayList<>();
        for(String name : appNames){
            Optional<MedidataApp> app = this.appRepository.findFirstByName(name);
            app.ifPresent(apps::add);
        }

        return apps;
    }

    private void validateStudyAdd(MedidataStudyAddDto studyAddDto) {
        studyAddDto.trimStringFields();
        super.validateNameNotBlank(studyAddDto.getName());
        validateUniqueName(studyAddDto.getName());
        validateAppsCount(studyAddDto.getEnvironment().getSiteApps(),
                studyAddDto.getEnvironment().getPatientApps());
    }

    private void validateStudyEdit(MedidataStudyEditDto studyEditDto) {
        studyEditDto.trimStringFields();
        super.validateNameNotBlank(studyEditDto.getName());
        if(!this.studyRepository.findById(studyEditDto.getId()).get().getName()
                .equals(studyEditDto.getName())){
            validateUniqueName(studyEditDto.getName());
        }
        validateAppsCount(studyEditDto.getEnvironment().getSiteApps(),
                studyEditDto.getEnvironment().getPatientApps());
    }

    private void validateUniqueName(String name){
        if(this.studyRepository.findFirstByName(name).isPresent()){
            throw new RuntimeException("Study '" + name + "' already present");
        }
    }

    public MedidataStudyEditDto getStudyEditById(Long id) {
        MedidataStudy study = this.studyRepository
                .findById(id).orElseThrow();
        MedidataStudyEditDto editDto = this.modelMapper
                .map(study, MedidataStudyEditDto.class);
        editDto.setManualFields(study);
        return editDto;
    }

    @Override
    public List<MedidataStudy> getEntities() {
        return this.studyRepository
                .findAllByNameNot(BaseEntity.UNKNOWN);
    }

    @Override
    public MedidataStudy getUnknownStudy() {
        return this.studyRepository
                .findFirstByName(BaseEntity.UNKNOWN)
                .get();
    }

    public MedidataStudyInfoDto getStudyInfoById(Long id) {
        MedidataStudy study = this.studyRepository
                .findById(id).orElseThrow();

        MedidataStudyInfoDto dto = this.modelMapper
                .map(study, MedidataStudyInfoDto.class);
        dto.setSpecialFields(study);

        return dto;
    }

    @Override
    public List<StudyDisplayDto> getTagStudies() {
        return this.displayAllStudies();
    }

    @Override
    public Optional<MedidataStudy> findFirstByName(String name) {
        return this.studyRepository.findFirstByName(name);
    }

    @Override
    public <T extends BaseStudy> void saveAll(Collection<T> studies) {
        Collection<MedidataStudy> casted = (Collection<MedidataStudy>) studies;
        this.environmentRepository.saveAll(casted.stream()
                .map(MedidataStudy::getEnvironment)
                .collect(Collectors.toList()));
        this.studyRepository.saveAll(casted);
    }
}
