package com.example.qcassistantmaven.domain.dto.orderNotes;

import com.example.qcassistantmaven.domain.dto.item.ItemNameSerialDto;
import com.example.qcassistantmaven.domain.entity.tag.BaseTag;
import com.example.qcassistantmaven.domain.entity.tag.MedidataTag;
import com.example.qcassistantmaven.domain.item.device.Device;
import com.example.qcassistantmaven.domain.note.Note;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class OrderNotesDto {

    protected Collection<ItemNameSerialDto> items;
    protected Collection<Note> shellCheckNotes;
    protected Collection<Note> documentationNotes;
    protected Collection<Note> iosNotes;
    protected Collection<Note> androidNotes;

    public OrderNotesDto() {
        this.items = new ArrayList<>();
        this.shellCheckNotes = new ArrayList<>();
        this.documentationNotes = new ArrayList<>();
        this.iosNotes = new ArrayList<>();
        this.androidNotes = new ArrayList<>();
    }

    public Collection<ItemNameSerialDto> getItems() {
        return items;
    }

    public OrderNotesDto setItems(Collection<ItemNameSerialDto> items) {
        this.items = items;
        return this;
    }

    public Collection<Note> getShellCheckNotes() {
        return shellCheckNotes;
    }

    public OrderNotesDto setShellCheckNotes(Collection<Note> shellCheckNotes) {
        this.shellCheckNotes = shellCheckNotes;
        return this;
    }

    public Collection<Note> getDocumentationNotes() {
        return documentationNotes;
    }

    public OrderNotesDto setDocumentationNotes(Collection<Note> documentationNotes) {
        this.documentationNotes = documentationNotes;
        return this;
    }

    public Collection<Note> getIosNotes() {
        return iosNotes;
    }

    public OrderNotesDto setIosNotes(Collection<Note> iosNotes) {
        this.iosNotes = iosNotes;
        return this;
    }

    public Collection<Note> getAndroidNotes() {
        return androidNotes;
    }

    public OrderNotesDto setAndroidNotes(Collection<Note> androidNotes) {
        this.androidNotes = androidNotes;
        return this;
    }

    public void addDevice(Device device){
        this.items.add(new ItemNameSerialDto()
                .setShortName(device.getShortName())
                .setSerial(device.getSerial()));
    }

    public boolean containsIosDevices(){
        return this.iosNotes.size() > 0;
    }

    public boolean containsAndroidDevices(){
        return this.androidNotes.size() > 0;
    }

    public void sortItems() {
        this.setItems(items.stream()
                .sorted((i1,i2) -> i1.getShortName()
                        .compareTo(i2.getShortName()))
                .collect(Collectors.toList()));
    }

    public <T extends BaseTag> void addTagNote(T tag) {
        Note note = new Note(tag.getSeverity(), tag.getText());
        switch (tag.getType()){
            case SHELLCHECK: shellCheckNotes.add(note);
                break;
            case DOCUMENTATION: documentationNotes.add(note);
                break;
            case IOS: iosNotes.add(note);
                break;
            case AFW: androidNotes.add(note);
                break;
        }
    }
}
