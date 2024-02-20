package com.example.qcassistantmaven.unit.entityServices.app;

import com.example.qcassistantmaven.domain.dto.app.AppAddDto;
import com.example.qcassistantmaven.domain.dto.app.AppEditDto;
import com.example.qcassistantmaven.domain.entity.app.IqviaApp;
import com.example.qcassistantmaven.repository.app.IqviaAppRepository;
import com.example.qcassistantmaven.service.app.IqviaAppService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class IqviaAppServiceTests {

    private IqviaAppService appService;

    private IqviaAppRepository appRepository;

    @Autowired
    public IqviaAppServiceTests(IqviaAppService appService,
                                   IqviaAppRepository appRepository) {
        this.appService = appService;
        this.appRepository = appRepository;
    }

    @Test
    public void testAddAppWithBlankName() {
        AppAddDto appAddDto = new AppAddDto()
                .setName("  ");

        Assertions.assertThrows(RuntimeException.class, () ->
                appService.addApp(appAddDto));
    }

    @Test
    public void testAddAppWithExistingName() {
        AppAddDto appAddDto = new AppAddDto()
                .setName(IqviaAppService.SCRIBE_APP_NAME);


        Assertions.assertThrows(RuntimeException.class, () ->
                appService.addApp(appAddDto));
    }

    @Test
    public void testEditAppWithBlankName() {
        AppEditDto editDto = new AppEditDto()
                .setName("  ");

        Assertions.assertThrows(RuntimeException.class, () ->
                appService.editApp(editDto));
    }

    @Test
    public <T> void getAllEditApps_ReturnsCorrectCountAndType(){
        List<T> fromService = (List<T>) this
                .appService.getAllEditApps();

        Assertions.assertEquals(fromService.size(),
                appRepository.count());

        Assertions.assertEquals(fromService.get(0).getClass(),
                AppEditDto.class);
    }

    @Test
    public void getEditAppById_ReturnsCorrectApp(){
        IqviaApp scribe = this.appRepository
                .findFirstByName(IqviaAppService.SCRIBE_APP_NAME)
                .get();

        AppEditDto fromService = this.appService
                .getEditAppById(scribe.getId());

        Assertions.assertEquals(scribe.getName(),
                fromService.getName());
    }
}
