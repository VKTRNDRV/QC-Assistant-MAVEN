package com.example.qcassistantmaven.domain.order;

import com.example.qcassistantmaven.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistantmaven.domain.entity.study.MedidataStudy;
import com.example.qcassistantmaven.domain.enums.item.SimType;
import com.example.qcassistantmaven.domain.item.device.ios.ipad.MedidataIPad;
import com.example.qcassistantmaven.regex.MedidataOrderInputRegex;
import com.example.qcassistantmaven.util.TrinaryBoolean;

public class MedidataOrder extends ClinicalOrder{
    private MedidataStudy study;
    private MedidataSponsor sponsor;
    private SimType simType;
    private DocumentRepository documentRepository;



    @Override
    public MedidataStudy getStudy() {
        return study;
    }

    public MedidataOrder setStudy(MedidataStudy study) {
        this.study = study;
        return this;
    }

    public MedidataOrder setSponsor(MedidataSponsor sponsor) {
        this.sponsor = sponsor;
        return this;
    }

    public DocumentRepository getDocumentRepository() {
        return documentRepository;
    }

    public MedidataOrder setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
        return this;
    }

    public SimType getSimType() {
        return simType;
    }

    public MedidataOrder setSimType(SimType simType) {
        this.simType = simType;
        return this;
    }

    @Override
    public boolean containsPatientDevices(){
        if(study.getIsPatientDeviceIpad().equals(TrinaryBoolean.TRUE)){
            return orderTermComments.contains(MedidataOrderInputRegex.SINGLE_USER);
        }else{
            if(deviceRepository.containsDevice(MedidataIPad.MINI.getShortName())){
                return true;
            }else{
                return super.containsPatientDevices();
            }
        }
    }


    @Override
    public boolean containsSiteDevices() {
        if(study.getIsPatientDeviceIpad().equals(TrinaryBoolean.TRUE)){
            return orderTermComments.contains(
                    MedidataOrderInputRegex.MULTI_USER);
        }else {
            for (MedidataIPad iPadConst : MedidataIPad.values()) {
                if (!iPadConst.getShortName().equals(MedidataIPad.MINI.getShortName()) &&
                        deviceRepository.containsDevice(iPadConst.getShortName())) {
                    return true;
                }
            }

            return false;
        }
    }

    @Override
    public boolean isStudyUnknown(){
        if(this.study == null){
            return true;
        }

        return this.study.isUnknown();
    }
}
