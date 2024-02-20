package com.example.qcassistantmaven.unit.entityServices.study;

import com.example.qcassistantmaven.domain.dto.study.StudyDisplayDto;
import com.example.qcassistantmaven.domain.dto.study.add.MedableStudyAddDto;
import com.example.qcassistantmaven.domain.dto.study.edit.MedableStudyEditDto;
import com.example.qcassistantmaven.domain.dto.study.environment.add.MedableEnvironmentAddDto;
import com.example.qcassistantmaven.domain.dto.study.environment.edit.MedableEnvironmentEditDto;
import com.example.qcassistantmaven.domain.dto.study.info.MedableStudyInfoDto;
import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.domain.entity.study.MedableStudy;
import com.example.qcassistantmaven.repository.study.MedableStudyRepository;
import com.example.qcassistantmaven.service.study.MedableStudyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MedableStudyServiceTests {

    private MedableStudyService studyService;

    private MedableStudyRepository studyRepository;

    @Autowired
    public MedableStudyServiceTests(MedableStudyService studyService,
                                  MedableStudyRepository studyRepository) {
        this.studyService = studyService;
        this.studyRepository = studyRepository;
    }

    @Test
    public void getEntities_DoesNotReturnUNKNOWN(){
        List<MedableStudy> studies = this
                .studyService.getEntities();

        for(MedableStudy study : studies){
            if(study.getName().equals(BaseEntity.UNKNOWN)){
                Assertions.fail("UNKNOWN study found");
            }
        }

        Assertions.assertTrue(true);
    }

    @Test
    public void getTagStudies_DoesNotReturnUNKNOWN(){
        List<StudyDisplayDto> studies = this
                .studyService.getTagStudies();

        for(StudyDisplayDto study : studies){
            if(study.getName().equals(BaseEntity.UNKNOWN)){
                Assertions.fail("UNKNOWN study found");
            }
        }

        Assertions.assertTrue(true);
    }

    @Test
    public void getUnknownStudy_ReturnsUnknown(){
        MedableStudy fromMethod = studyService
                .getUnknownStudy();

        Assertions.assertEquals(fromMethod.getName(),
                BaseEntity.UNKNOWN);
    }

    @Test
    public void displayAllStudies_DoesNotReturnUnknownPlusCorrectCount(){
        List<StudyDisplayDto> fromMethod = this
                .studyService.displayAllStudies();

        Assertions.assertEquals(fromMethod.size(),
                studyRepository.count() - 1);

        for(StudyDisplayDto study : fromMethod){
            if(study.getName().equals(BaseEntity.UNKNOWN)){
                Assertions.fail("UNKNOWN study found");
            }
        }
    }

    @Test
    public void getStudyEditById_ReturnsCorrectStudy(){
        MedableStudy fromRepository = this.studyRepository
                .findFirstByName(BaseEntity.UNKNOWN)
                .get();

        MedableStudyEditDto fromMethod = this.studyService
                .getStudyEditById(fromRepository.getId());

        Assertions.assertEquals(fromRepository.getName(),
                fromMethod.getName());
    }

    @Test
    public void getStudyInfoById_ReturnsCorrectStudy(){
        MedableStudy fromRepository = this.studyRepository
                .findFirstByName(BaseEntity.UNKNOWN)
                .get();

        MedableStudyInfoDto fromMethod = this.studyService
                .getStudyInfoById(fromRepository.getId());

        Assertions.assertEquals(fromRepository.getName(),
                fromMethod.getName());
    }

    @Test
    public void addStudy_BlankName(){
        MedableStudyAddDto study = new MedableStudyAddDto();
        study.setName("   ");

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.addStudy(study));
    }

    @Test
    public void addStudy_UniqueName(){
        MedableStudyAddDto study = new MedableStudyAddDto();
        study.setName(BaseEntity.UNKNOWN);

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.addStudy(study));
    }

    @Test
    public void addStudy_NoApps(){
        MedableStudyAddDto study = new MedableStudyAddDto();
        study.setName("No Apps Study")
                .setEnvironment(new MedableEnvironmentAddDto()
                        .setSiteApps(new ArrayList<>())
                        .setPatientApps(new ArrayList<>()));

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.addStudy(study));
    }

    @Test
    public void editStudy_BlankName(){
        MedableStudyEditDto study = new MedableStudyEditDto();
        study.setName("   ");

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.editStudy(study));
    }

    @Test
    public void editStudy_UniqueName(){
        MedableStudyEditDto study = new MedableStudyEditDto();
        study.setName(BaseEntity.UNKNOWN);

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.editStudy(study));
    }

    @Test
    public void editStudy_NoApps(){
        MedableStudyEditDto study = new MedableStudyEditDto();
        study.setName("No Apps Study")
                .setEnvironment(new MedableEnvironmentEditDto()
                        .setSiteApps(new ArrayList<>())
                        .setPatientApps(new ArrayList<>()));

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.editStudy(study));
    }
}
