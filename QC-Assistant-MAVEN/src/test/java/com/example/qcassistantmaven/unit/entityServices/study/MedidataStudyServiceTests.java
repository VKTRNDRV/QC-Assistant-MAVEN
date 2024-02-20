package com.example.qcassistantmaven.unit.entityServices.study;

import com.example.qcassistantmaven.domain.dto.study.StudyDisplayDto;
import com.example.qcassistantmaven.domain.dto.study.add.MedidataStudyAddDto;
import com.example.qcassistantmaven.domain.dto.study.edit.MedidataStudyEditDto;
import com.example.qcassistantmaven.domain.dto.study.environment.add.MedidataEnvironmentAddDto;
import com.example.qcassistantmaven.domain.dto.study.environment.edit.MedidataEnvironmentEditDto;
import com.example.qcassistantmaven.domain.dto.study.info.MedidataStudyInfoDto;
import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.domain.entity.study.MedidataStudy;
import com.example.qcassistantmaven.repository.study.MedidataStudyRepository;
import com.example.qcassistantmaven.service.study.MedidataStudyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MedidataStudyServiceTests {

    private MedidataStudyService studyService;

    private MedidataStudyRepository studyRepository;

    @Autowired
    public MedidataStudyServiceTests(MedidataStudyService studyService,
                                     MedidataStudyRepository studyRepository) {
        this.studyService = studyService;
        this.studyRepository = studyRepository;
    }

    @Test
    public void getEntities_DoesNotReturnUNKNOWN(){
        List<MedidataStudy> studies = this
                .studyService.getEntities();

        for(MedidataStudy study : studies){
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
        MedidataStudy fromMethod = studyService
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
        MedidataStudy fromRepository = this.studyRepository
                .findFirstByName(BaseEntity.UNKNOWN)
                .get();

        MedidataStudyEditDto fromMethod = this.studyService
                .getStudyEditById(fromRepository.getId());

        Assertions.assertEquals(fromRepository.getName(),
                fromMethod.getName());
    }

    @Test
    public void getStudyInfoById_ReturnsCorrectStudy(){
        MedidataStudy fromRepository = this.studyRepository
                .findFirstByName(BaseEntity.UNKNOWN)
                .get();

        MedidataStudyInfoDto fromMethod = this.studyService
                .getStudyInfoById(fromRepository.getId());

        Assertions.assertEquals(fromRepository.getName(),
                fromMethod.getName());
    }

    @Test
    public void addStudy_BlankName(){
        MedidataStudyAddDto study = new MedidataStudyAddDto();
        study.setName("   ");

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.addStudy(study));
    }

    @Test
    public void addStudy_UniqueName(){
        MedidataStudyAddDto study = new MedidataStudyAddDto();
        study.setName(BaseEntity.UNKNOWN);

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.addStudy(study));
    }

    @Test
    public void addStudy_NoApps(){
        MedidataStudyAddDto study = new MedidataStudyAddDto();
        study.setName("No Apps Study")
                .setEnvironment(new MedidataEnvironmentAddDto()
                        .setSiteApps(new ArrayList<>())
                        .setPatientApps(new ArrayList<>()));

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.addStudy(study));
    }

    @Test
    public void editStudy_BlankName(){
        MedidataStudyEditDto study = new MedidataStudyEditDto();
        study.setName("   ");

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.editStudy(study));
    }

    @Test
    public void editStudy_UniqueName(){
        MedidataStudyEditDto study = new MedidataStudyEditDto();
        study.setName(BaseEntity.UNKNOWN);

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.editStudy(study));
    }

    @Test
    public void editStudy_NoApps(){
        MedidataStudyEditDto study = new MedidataStudyEditDto();
        study.setName("No Apps Study")
                .setEnvironment(new MedidataEnvironmentEditDto()
                        .setSiteApps(new ArrayList<>())
                        .setPatientApps(new ArrayList<>()));

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.editStudy(study));
    }
}
