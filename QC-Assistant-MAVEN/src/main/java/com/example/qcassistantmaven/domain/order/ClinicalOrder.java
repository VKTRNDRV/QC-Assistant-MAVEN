package com.example.qcassistantmaven.domain.order;

import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.destination.Language;
import com.example.qcassistantmaven.domain.entity.sponsor.BaseSponsor;
import com.example.qcassistantmaven.domain.entity.study.BaseStudy;
import com.example.qcassistantmaven.domain.enums.OrderType;
import com.example.qcassistantmaven.domain.enums.item.OperatingSystem;
import com.example.qcassistantmaven.domain.enums.item.ShellType;
import com.example.qcassistantmaven.domain.item.device.Device;

import java.util.Collection;

public abstract class ClinicalOrder {

    protected OrderType orderType;
    protected Destination destination;
    protected Collection<Language> requestedLanguages;
    protected String orderTermComments;
    protected DeviceRepository deviceRepository;
    protected AccessoryRepository accessoryRepository;



    public Destination getDestination() {
        return destination;
    }

    public ClinicalOrder setDestination(Destination destination) {
        this.destination = destination;
        return this;
    }

    public Collection<Language> getRequestedLanguages() {
        return requestedLanguages;
    }

    public ClinicalOrder setRequestedLanguages(Collection<Language> requestedLanguages) {
        this.requestedLanguages = requestedLanguages;
        return this;
    }

    public String getOrderTermComments() {
        return orderTermComments;
    }

    public ClinicalOrder setOrderTermComments(String orderTermComments) {
        this.orderTermComments = orderTermComments;
        return this;
    }

    public DeviceRepository getDeviceRepository() {
        return deviceRepository;
    }

    public ClinicalOrder setDeviceRepository(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
        return this;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public ClinicalOrder setOrderType(OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    public AccessoryRepository getAccessoryRepository() {
        return accessoryRepository;
    }

    public ClinicalOrder setAccessoryRepository(AccessoryRepository accessoryRepository) {
        this.accessoryRepository = accessoryRepository;
        return this;
    }

    public boolean containsPatientDevices(){
        return deviceRepository.containsShellType(ShellType.PHONE);
    }

    public boolean containsSiteDevices(){
        return deviceRepository.containsShellType(ShellType.TABLET);
    }

    public boolean isEnglishRequested() {
        if(requestedLanguages.size() > 1) return false;
        if(requestedLanguages.size() == 1){
            for(Language lang : requestedLanguages){
                if(lang.getName()
                        .equals(Language.ENGLISH)){
                    return true;
                }
            }

            return false;
        } else {
            if(destination.isEnglishSpeaking()){
                return true;
            }else {
                return false;
            }
        }
    }

    public boolean areMultipleLanguagesRequested() {
        return requestedLanguages.size() > 1;
    }

    public boolean areNoLanguagesDetected() {
        return requestedLanguages.isEmpty();
    }

    public boolean requestsUnusualLanguages() {
        if(destination.isUnknown()) return false;
        for(Language language : requestedLanguages){
            if(language.getName().equals(Language.ENGLISH)){
                continue;
            }

            if(!destination.containsLanguage(language)){
                return true;
            }
        }

        return false;
    }

    public abstract boolean isStudyUnknown();

    public abstract <T extends BaseStudy> T getStudy();

    public boolean containsShellType(ShellType shellType) {
        return this.deviceRepository.containsShellType(shellType);
    }

    public boolean containsOperatingSystem(OperatingSystem operatingSystem) {
        return this.deviceRepository.containsOperatingSystem(operatingSystem);
    }
}
