package com.example.qcassistantmaven.domain.dto.orderNotes;

import com.example.qcassistantmaven.domain.dto.study.info.MedidataStudyInfoDto;
import com.example.qcassistantmaven.domain.entity.BaseEntity;

public class MedidataOrderNotesDto extends OrderNotesDto{

    private MedidataStudyInfoDto study;


    public MedidataStudyInfoDto getStudy() {
        return study;
    }

    public MedidataOrderNotesDto setStudy(MedidataStudyInfoDto study) {
        this.study = study;
        return this;
    }

    public boolean isStudyUnknown(){
        if(this.study == null){
            return true;
        }

        if(this.study.getName().equals(BaseEntity.UNKNOWN)){
            return true;
        }

        return false;
    }
}
