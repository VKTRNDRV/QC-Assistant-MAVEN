package com.example.qcassistantmaven.unit.entityServices.study;

import com.example.qcassistantmaven.domain.dto.study.StudyDisplayDto;
import com.example.qcassistantmaven.domain.dto.study.add.IqviaStudyAddDto;
import com.example.qcassistantmaven.domain.dto.study.edit.IqviaStudyEditDto;
import com.example.qcassistantmaven.domain.dto.study.environment.add.IqviaEnvironmentAddDto;
import com.example.qcassistantmaven.domain.dto.study.environment.edit.IqviaEnvironmentEditDto;
import com.example.qcassistantmaven.domain.dto.study.info.IqviaStudyInfoDto;
import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.domain.entity.study.IqviaStudy;
import com.example.qcassistantmaven.repository.study.IqviaStudyRepository;
import com.example.qcassistantmaven.service.study.IqviaStudyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class IqviaStudyServiceTests {

    private IqviaStudyService studyService;

    private IqviaStudyRepository studyRepository;

    @Autowired
    public IqviaStudyServiceTests(IqviaStudyService studyService,
                                  IqviaStudyRepository studyRepository) {
        this.studyService = studyService;
        this.studyRepository = studyRepository;
    }

    @Test
    public void getEntities_DoesNotReturnUNKNOWN(){
        List<IqviaStudy> studies = this
                .studyService.getEntities();

        for(IqviaStudy study : studies){
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
        IqviaStudy fromMethod = studyService
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
        IqviaStudy fromRepository = this.studyRepository
                .findFirstByName(BaseEntity.UNKNOWN)
                .get();

        IqviaStudyEditDto fromMethod = this.studyService
                .getStudyEditById(fromRepository.getId());

        Assertions.assertEquals(fromRepository.getName(),
                fromMethod.getName());
    }

    @Test
    public void getStudyInfoById_ReturnsCorrectStudy(){
        IqviaStudy fromRepository = this.studyRepository
                .findFirstByName(BaseEntity.UNKNOWN)
                .get();

        IqviaStudyInfoDto fromMethod = this.studyService
                .getStudyInfoById(fromRepository.getId());

        Assertions.assertEquals(fromRepository.getName(),
                fromMethod.getName());
    }

    @Test
    public void addStudy_BlankName(){
        IqviaStudyAddDto study = new IqviaStudyAddDto();
        study.setName("   ");

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.addStudy(study));
    }

    @Test
    public void addStudy_UniqueName(){
        IqviaStudyAddDto study = new IqviaStudyAddDto();
        study.setName(BaseEntity.UNKNOWN);

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.addStudy(study));
    }

    @Test
    public void addStudy_NoApps(){
        IqviaStudyAddDto study = new IqviaStudyAddDto();
        study.setName("No Apps Study")
                .setEnvironment(new IqviaEnvironmentAddDto()
                        .setSiteApps(new ArrayList<>())
                        .setPatientApps(new ArrayList<>()));

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.addStudy(study));
    }

    @Test
    public void editStudy_BlankName(){
        IqviaStudyEditDto study = new IqviaStudyEditDto();
        study.setName("   ");

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.editStudy(study));
    }

    @Test
    public void editStudy_UniqueName(){
        IqviaStudyEditDto study = new IqviaStudyEditDto();
        study.setName(BaseEntity.UNKNOWN);

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.editStudy(study));
    }

    @Test
    public void editStudy_NoApps(){
        IqviaStudyEditDto study = new IqviaStudyEditDto();
        study.setName("No Apps Study")
                .setEnvironment(new IqviaEnvironmentEditDto()
                        .setSiteApps(new ArrayList<>())
                        .setPatientApps(new ArrayList<>()));

        Assertions.assertThrows(RuntimeException.class,
                () -> studyService.editStudy(study));
    }
}
