package com.example.qcassistantmaven.service.app;

import com.example.qcassistantmaven.domain.dto.app.AppAddDto;
import com.example.qcassistantmaven.domain.dto.app.AppEditDto;
import com.example.qcassistantmaven.domain.entity.app.IqviaApp;
import com.example.qcassistantmaven.domain.entity.app.MedableApp;
import com.example.qcassistantmaven.domain.entity.app.MedidataApp;
import com.example.qcassistantmaven.repository.app.IqviaAppRepository;
import com.example.qcassistantmaven.repository.app.MedableAppRepository;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedableAppService extends BaseAppService{

    private MedableAppRepository appRepository;

    public static final String CONTENT_APP_NAME = "Content";

    @Autowired
    public MedableAppService(ModelMapper modelMapper, MedableAppRepository appRepository) {
        super(modelMapper);
        this.appRepository = appRepository;
    }

    @PostConstruct
    public void init(){
        if(this.appRepository.count() > 0){
            return;
        }

        MedableApp content = new MedableApp();
        content.setName(CONTENT_APP_NAME)
                .setRequiresCamera(TrinaryBoolean.FALSE);

        this.appRepository.save(content);
    }

//    @Override
//    public List<AppEditDto> getAllEditApps() {
//        return this.getEntities().stream().map((a) -> this.modelMapper
//                        .map(a, AppEditDto.class))
//                .sorted((a1,a2) -> a1.getName().compareTo(a2.getName()))
//                .collect(Collectors.toList());
//    }

    @Override
    public void addApp(AppAddDto appAddDto) {
        validateNewApp(appAddDto);
        MedableApp app = super.modelMapper.map(appAddDto, MedableApp.class);
        this.appRepository.save(app);
    }

    @Override
    public void editApp(AppEditDto editDto) {
        validateEditApp(editDto);
        MedableApp editedApp = this.modelMapper
                .map(editDto, MedableApp.class);
        this.appRepository.save(editedApp);
    }

    @Override
    protected MedableAppRepository getAppRepository(){
        return this.appRepository;
    }


    @Override
    public List<MedableApp> getEntities() {
        return getAppRepository().findAll();
    }

    @Override
    public Optional<MedableApp> findFirstByName(String name) {
        return getAppRepository().findFirstByName(name);
    }
}
