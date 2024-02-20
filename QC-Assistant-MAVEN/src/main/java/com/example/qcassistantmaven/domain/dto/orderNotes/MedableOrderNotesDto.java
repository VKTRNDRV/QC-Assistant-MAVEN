package com.example.qcassistantmaven.domain.dto.orderNotes;

import com.example.qcassistantmaven.domain.dto.study.info.MedableStudyInfoDto;
import com.example.qcassistantmaven.domain.entity.BaseEntity;
import com.example.qcassistantmaven.domain.entity.tag.BaseTag;
import com.example.qcassistantmaven.domain.enums.TagType;
import com.example.qcassistantmaven.domain.note.Note;

import java.util.List;

public class MedableOrderNotesDto extends OrderNotesDto{

    private MedableStudyInfoDto study;


    public MedableStudyInfoDto getStudy() {
        return study;
    }

    public MedableOrderNotesDto setStudy(MedableStudyInfoDto study) {
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
