package com.example.qcassistantmaven.service.app;

import com.example.qcassistantmaven.domain.dto.app.AppAddDto;
import com.example.qcassistantmaven.domain.dto.app.AppEditDto;
import com.example.qcassistantmaven.domain.entity.app.BaseApp;
import com.example.qcassistantmaven.domain.entity.app.MedidataApp;
import com.example.qcassistantmaven.repository.DestinationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public abstract class BaseAppService {

    protected ModelMapper modelMapper;

    @Autowired
    public BaseAppService(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    protected void validateNameNotBlank(String name){
        if(name == null || name.trim().isEmpty()){
            throw new RuntimeException("Name cannot be blank");
        }
    }

    protected void validateNewApp(AppAddDto appAddDto) {
        appAddDto.trimStringFields();
        String name = appAddDto.getName();
        this.validateNameNotBlank(name);
        this.validateUniqueName(name);
    }

    protected void validateEditApp(AppEditDto appEditDto){
        appEditDto.trimStringFields();
        String name = appEditDto.getName();
        validateNameNotBlank(name);

        // if app name changed - validate unique
        if(!this.getAppRepository().findById(appEditDto.getId()).get()
                .getName().trim()
                .equals(name)){
            validateUniqueName(name);
        }
    }

    public AppEditDto getEditAppById(Long id) {
        return this.modelMapper.map(
                this.getAppRepository().findById(id).get(),
                AppEditDto.class);
    }

    public List<AppEditDto> getAllEditApps() {
        return this.getEntities().stream().map((a) -> this.modelMapper
                        .map(a, AppEditDto.class))
                .sorted((a1,a2) -> a1.getName().compareTo(a2.getName()))
                .collect(Collectors.toList());
    }

    protected void validateUniqueName(String name){
        if(this.findFirstByName(name).isPresent()){
            throw new RuntimeException("App '" + name + "' already present");
        }
    }

    public abstract void addApp(AppAddDto appAddDto);

    public abstract void editApp(AppEditDto editDto);

    protected abstract <T extends BaseApp> CrudRepository<T, Long> getAppRepository();

    public abstract <T extends BaseApp> List<T> getEntities();

    public abstract <T extends BaseApp> Optional<T> findFirstByName(String name);

    public <T extends BaseApp> void saveAll(Collection<T> apps){
        getAppRepository().saveAll(apps);
    }
}
