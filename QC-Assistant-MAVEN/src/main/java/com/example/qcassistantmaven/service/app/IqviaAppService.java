package com.example.qcassistantmaven.service.app;

import com.example.qcassistantmaven.domain.dto.app.AppAddDto;
import com.example.qcassistantmaven.domain.dto.app.AppEditDto;
import com.example.qcassistantmaven.domain.entity.app.IqviaApp;
import com.example.qcassistantmaven.repository.app.IqviaAppRepository;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IqviaAppService extends BaseAppService{

    private IqviaAppRepository appRepository;

    public static final String SCRIBE_APP_NAME = "Scribe";
    private static final String CONTENT_APP_NAME = "Content";

    @Autowired
    public IqviaAppService(ModelMapper modelMapper, IqviaAppRepository appRepository) {
        super(modelMapper);
        this.appRepository = appRepository;
    }

    @PostConstruct
    public void init(){
        if(this.appRepository.count() > 0){
            return;
        }

        IqviaApp scribe = new IqviaApp();
        scribe.setName(SCRIBE_APP_NAME)
                .setRequiresCamera(TrinaryBoolean.TRUE);

        IqviaApp content = new IqviaApp();
        content.setName(CONTENT_APP_NAME)
                .setRequiresCamera(TrinaryBoolean.FALSE);

        this.appRepository.save(scribe);
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
        IqviaApp app = super.modelMapper.map(appAddDto, IqviaApp.class);
        this.appRepository.save(app);
    }

    @Override
    public void editApp(AppEditDto editDto) {
        validateEditApp(editDto);
        IqviaApp editedApp = this.modelMapper
                .map(editDto, IqviaApp.class);
        this.appRepository.save(editedApp);
    }

    @Override
    protected IqviaAppRepository getAppRepository(){
        return this.appRepository;
    }


    @Override
    public List<IqviaApp> getEntities() {
        return getAppRepository().findAll();
    }

    @Override
    public Optional<IqviaApp> findFirstByName(String name) {
        return getAppRepository().findFirstByName(name);
    }
}
