package com.example.qcassistantmaven.service.study;

import com.example.qcassistantmaven.domain.dto.study.StudyDisplayDto;
import com.example.qcassistantmaven.domain.dto.study.edit.IqviaStudyEditDto;
import com.example.qcassistantmaven.domain.dto.study.add.IqviaStudyAddDto;
import com.example.qcassistantmaven.domain.dto.study.info.IqviaStudyInfoDto;
import com.example.qcassistantmaven.domain.dto.study.info.MedidataStudyInfoDto;
import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.domain.entity.app.IqviaApp;
import com.example.qcassistantmaven.domain.entity.sponsor.BaseSponsor;
import com.example.qcassistantmaven.domain.entity.sponsor.IqviaSponsor;
import com.example.qcassistantmaven.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistantmaven.domain.entity.study.BaseStudy;
import com.example.qcassistantmaven.domain.entity.study.IqviaStudy;
import com.example.qcassistantmaven.domain.entity.study.MedidataStudy;
import com.example.qcassistantmaven.domain.entity.study.environment.IqviaEnvironment;
import com.example.qcassistantmaven.domain.entity.study.environment.MedidataEnvironment;
import com.example.qcassistantmaven.repository.app.IqviaAppRepository;
import com.example.qcassistantmaven.repository.sponsor.IqviaSponsorRepository;
import com.example.qcassistantmaven.repository.study.IqviaStudyRepository;
import com.example.qcassistantmaven.repository.study.environment.IqviaEnvironmentRepository;
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
public class IqviaStudyService extends BaseStudyService{

    private IqviaStudyRepository studyRepository;
    private IqviaAppRepository appRepository;
    private IqviaSponsorRepository sponsorRepository;
    private IqviaEnvironmentRepository environmentRepository;

    @Autowired
    public IqviaStudyService(ModelMapper modelMapper,
                             IqviaStudyRepository studyRepository,
                             IqviaAppRepository appRepository,
                             IqviaSponsorRepository sponsorRepository,
                             IqviaEnvironmentRepository environmentRepository) {
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
            IqviaStudy study = new IqviaStudy();
            study.setName(BaseEntity.UNKNOWN)
                    .setFolderURL(BaseEntity.UNKNOWN);
            study.setContainsTranslatedDocs(TrinaryBoolean.UNKNOWN)
                    .setContainsTranslatedLabels(TrinaryBoolean.UNKNOWN)
                    .setContainsSepSitePatientLabels(TrinaryBoolean.UNKNOWN)
                    .setIsGsgPlain(TrinaryBoolean.UNKNOWN);


            Optional<IqviaSponsor> optSponsor = this.sponsorRepository
                    .findFirstByName(BaseEntity.UNKNOWN);
            if(optSponsor.isPresent()){
                study.setSponsor(optSponsor.get());
            }else {
                IqviaSponsor sponsor = new IqviaSponsor();
                sponsor.setName(BaseEntity.UNKNOWN)
                        .setAreStudyNamesSimilar(TrinaryBoolean.UNKNOWN);
                this.sponsorRepository.save(sponsor);

                study.setSponsor(sponsor);
            }

            IqviaEnvironment environment = new IqviaEnvironment();
            environment.setPatientApps(new ArrayList<>())
                    .setSiteApps(new ArrayList<>())
                    .setIsSitePatientSeparated(TrinaryBoolean.UNKNOWN)
                    .setIsDestinationSeparated(TrinaryBoolean.UNKNOWN)
                    .setIsOsSeparated(TrinaryBoolean.UNKNOWN);
            this.environmentRepository.save(environment);

            study.setEnvironment(environment);

            this.studyRepository.save(study);
        }
    }

    public void addStudy(IqviaStudyAddDto studyAddDto) {
        validateStudyAdd(studyAddDto);
        studyAddDto.trimStringFields();
        IqviaStudy study = mapToEntity(studyAddDto);
        this.environmentRepository.save(study.getEnvironment());
        this.studyRepository.save(study);
    }

    private void validateStudyAdd(IqviaStudyAddDto studyAddDto) {
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

    private IqviaStudy mapToEntity(IqviaStudyAddDto studyAddDto) {
        IqviaStudy study = this.modelMapper.map(studyAddDto, IqviaStudy.class);
        IqviaSponsor sponsor = this.sponsorRepository
                .findFirstByName(studyAddDto.getSponsor()).orElseThrow();
        List<IqviaApp> siteApps = getAppsByName(
                studyAddDto.getEnvironment().getSiteApps());
        List<IqviaApp> patientApps = getAppsByName(
                studyAddDto.getEnvironment().getPatientApps());

        study.setSponsor(sponsor)
                .getEnvironment()
                .setSiteApps(siteApps)
                .setPatientApps(patientApps);
        return study;
    }

    public IqviaStudyEditDto getStudyEditById(Long id) {
        IqviaStudy study = this.studyRepository
                .findById(id).orElseThrow();
        IqviaStudyEditDto editDto = this.modelMapper
                .map(study, IqviaStudyEditDto.class);
        editDto.setManualFields(study);
        return editDto;
    }

    private List<IqviaApp> getAppsByName(List<String> appNames) {
        List<IqviaApp> apps = new ArrayList<>();
        for(String name : appNames){
            Optional<IqviaApp> app = this.appRepository.findFirstByName(name);
            app.ifPresent(apps::add);
        }

        return apps;
    }

    public void editStudy(IqviaStudyEditDto studyEditDto){
        validateStudyEdit(studyEditDto);
        studyEditDto.trimStringFields();
        IqviaStudy study = mapToEntity(studyEditDto);
        this.environmentRepository.save(study.getEnvironment());
        this.studyRepository.save(study);
    }

    private IqviaStudy mapToEntity(IqviaStudyEditDto studyEditDto) {
        IqviaStudy study = this.modelMapper.map(studyEditDto, IqviaStudy.class);
        List<IqviaApp> siteApps = getAppsByName(
                studyEditDto.getEnvironment().getSiteApps());
        List<IqviaApp> patientApps =
                getAppsByName(studyEditDto.getEnvironment().getPatientApps());
        IqviaSponsor sponsor = this.sponsorRepository
                .findFirstByName(studyEditDto.getSponsor()).orElseThrow();
        study.setSponsor(sponsor)
                .getEnvironment()
                .setSiteApps(siteApps)
                .setPatientApps(patientApps);
        return study;
    }

    private void validateStudyEdit(IqviaStudyEditDto studyEditDto) {
        studyEditDto.trimStringFields();
        super.validateNameNotBlank(studyEditDto.getName());
        if(!this.studyRepository.findById(studyEditDto.getId()).get().getName()
                .equals(studyEditDto.getName())){
            validateUniqueName(studyEditDto.getName());
        }
        validateAppsCount(studyEditDto.getEnvironment().getSiteApps(),
                studyEditDto.getEnvironment().getPatientApps());
    }

    @Override
    public List<IqviaStudy> getEntities() {
        return this.studyRepository.findAllByNameNot(BaseEntity.UNKNOWN);
    }

//    @Override
//    public List<StudyDisplayDto> displayAllStudies() {
//        return getEntities().stream()
//                .map(s -> new StudyDisplayDto()
//                        .setId(s.getId())
//                        .setName(s.getName())
//                        .setSponsor(s.getSponsor().getName()))
//                .sorted((s1,s2) -> {
//                    int result = s1.getSponsor().compareTo(s2.getSponsor());
//                    if(result == 0){
//                        result = s1.getName().compareTo(s2.getName());
//                    }
//                    return result;
//                })
//                .collect(Collectors.toList());
//    }

    public IqviaStudyInfoDto getStudyInfoById(Long id) {
        IqviaStudy study = this.studyRepository
                .findById(id).orElseThrow();

        IqviaStudyInfoDto dto = this.modelMapper
                .map(study, IqviaStudyInfoDto.class);
        dto.setSpecialFields(study);

        return dto;
    }

    @Override
    public IqviaStudy getUnknownStudy() {
        return this.studyRepository
                .findFirstByName(BaseEntity.UNKNOWN)
                .get();
    }

    @Override
    public List<StudyDisplayDto> getTagStudies() {
        return this.displayAllStudies();
    }

    @Override
    public <T extends BaseStudy> void saveAll(Collection<T> studies) {
        Collection<IqviaStudy> casted = (Collection<IqviaStudy>) studies;
        this.environmentRepository.saveAll(casted.stream()
                .map(IqviaStudy::getEnvironment)
                .collect(Collectors.toList()));
        this.studyRepository.saveAll(casted);
    }

    @Override
    public Optional<IqviaSponsor> findFirstByName(String name) {
        return this.sponsorRepository.findFirstByName(name);
    }
}
