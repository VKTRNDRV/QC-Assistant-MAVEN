package com.example.qcassistantmaven.service.app;

import com.example.qcassistantmaven.domain.dto.app.AppAddDto;
import com.example.qcassistantmaven.domain.dto.app.AppEditDto;
import com.example.qcassistantmaven.domain.entity.app.MedidataApp;
import com.example.qcassistantmaven.repository.app.MedidataAppRepository;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MedidataAppService extends BaseAppService{

    private MedidataAppRepository appRepository;

    @Autowired
    public MedidataAppService(MedidataAppRepository appRepository,
                              ModelMapper modelMapper) {
        super(modelMapper);
        this.appRepository = appRepository;
    }

    @PostConstruct
    public void init(){
        if(this.appRepository.count() > 0){
            return;
        }

        MedidataApp patientCloud = new MedidataApp();
        patientCloud.setName(MedidataApp.PATIENT_CLOUD)
                .setRequiresCamera(TrinaryBoolean.TRUE);

        MedidataApp content = new MedidataApp();
        content.setName(MedidataApp.RAVE_ECONSENT)
                .setRequiresCamera(TrinaryBoolean.FALSE);

        this.appRepository.save(patientCloud);
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
        MedidataApp app = super.modelMapper.map(appAddDto, MedidataApp.class);
        this.appRepository.save(app);
    }

    @Override
    public void editApp(AppEditDto editDto) {
        validateEditApp(editDto);
        MedidataApp editedApp = this.modelMapper
                .map(editDto, MedidataApp.class);
        this.appRepository.save(editedApp);
    }

    @Override
    protected MedidataAppRepository getAppRepository(){
        return this.appRepository;
    }


    @Override
    public List<MedidataApp> getEntities() {
        return getAppRepository().findAll();
    }

    @Override
    public Optional<MedidataApp> findFirstByName(String name) {
        return getAppRepository().findFirstByName(name);
    }
}
