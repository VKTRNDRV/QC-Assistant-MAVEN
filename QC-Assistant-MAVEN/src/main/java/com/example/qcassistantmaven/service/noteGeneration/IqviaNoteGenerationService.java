package com.example.qcassistantmaven.service.noteGeneration;

import com.example.qcassistantmaven.domain.dto.item.ItemNameSerialDto;
import com.example.qcassistantmaven.domain.dto.orderNotes.IqviaOrderNotesDto;
import com.example.qcassistantmaven.domain.entity.tag.IqviaTag;
import com.example.qcassistantmaven.domain.enums.OrderType;
import com.example.qcassistantmaven.domain.enums.Severity;
import com.example.qcassistantmaven.domain.enums.item.ConnectorType;
import com.example.qcassistantmaven.domain.enums.item.OperatingSystem;
import com.example.qcassistantmaven.domain.enums.item.ShellType;
import com.example.qcassistantmaven.domain.item.accessory.IqviaAccessory;
import com.example.qcassistantmaven.domain.note.Note;
import com.example.qcassistantmaven.domain.note.noteText.NoteText;
import com.example.qcassistantmaven.domain.order.AccessoryRepository;
import com.example.qcassistantmaven.domain.order.DeviceRepository;
import com.example.qcassistantmaven.domain.order.IqviaOrder;
import com.example.qcassistantmaven.service.study.IqviaStudyService;
import com.example.qcassistantmaven.service.tag.IqviaTagService;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IqviaNoteGenerationService extends NoteGenerationService{

    private IqviaStudyService studyService;
    private IqviaTagService tagService;

    @Autowired
    public IqviaNoteGenerationService(ModelMapper modelMapper, IqviaStudyService studyService, IqviaTagService tagService) {
        super(modelMapper);
        this.studyService = studyService;
        this.tagService = tagService;
    }

    public IqviaOrderNotesDto generateNotes(IqviaOrder order){
        IqviaOrderNotesDto notes = new IqviaOrderNotesDto();
        Collection<ItemNameSerialDto> serializedItems = new ArrayList<>();
        serializedItems.addAll(super.mapDevices(order));
        serializedItems.addAll(super.mapSims(order.getSimRepository()));
        notes.setItems(serializedItems)
                .sortItems();

        notes.setShellCheckNotes(this.genShellCheckNotes(order))
                .setDocumentationNotes(this.genDocumentationNotes(order));

        DeviceRepository devices = order.getDeviceRepository();
        if(devices.containsIosDevices()){
            notes.setIosNotes(this.genIosNotes(order));
        }
        if(devices.containsAndroidDevices()){
            notes.setAndroidNotes(this.genAndroidNotes(order));
        }
        if(devices.containsWindowsDevices()){
            notes.setWindowsNotes(this.genWindowsNotes(order));
        }

        this.getApplicableTags(order).forEach(notes::addTagNote);

        if(!order.getStudy().isUnknown()){
            notes.setStudy(studyService.getStudyInfoById(
                    order.getStudy().getId()));
        }


        return notes;
    }

    private List<IqviaTag> getApplicableTags(IqviaOrder order) {
        return this.tagService.getEntities().stream()
                .filter(t -> super.isTagApplicable(t, order))
                .collect(Collectors.toList());
    }

    private Collection<Note> genWindowsNotes(IqviaOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.add(new Note(Severity.LOW, NoteText.TEST_CAMERA_VOICE));
        notes.add(new Note(Severity.MEDIUM, NoteText.CONFIRM_BATTERY_BEFORE_ENROLL));
        notes.addAll(this.genStandardDeviceNotes(order));
        if(order.getOrderType().equals(OrderType.PROD)){
            notes.addAll(super.getBaseEnvironmentNotes(order));
        }else{
            notes.add(new Note(Severity.MEDIUM, NoteText.CAREFUL_UAT_ENVIRONMENT));
        }

        return notes;
    }

    private Collection<Note> genAndroidNotes(IqviaOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.addAll(this.genStandardDeviceNotes(order));
        if(order.getDeviceRepository().containsDevicesOfOsAndShell(
                OperatingSystem.ANDROID, ShellType.TABLET)){
            notes.add(new Note(Severity.LOW, NoteText.VERIFY_SWITCH_TO_MOBILE));
        }
        if(order.getDeviceRepository().containsAndroidDevices()){
            notes.add(new Note(Severity.LOW, NoteText.VERIFY_NO_DUPLICATES_AW));
        }

        notes.addAll(this.genAirWatchNotes(order));

        return notes;
    }

    private Collection<Note> genIosNotes(IqviaOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.addAll(this.genStandardDeviceNotes(order));
        notes.addAll(this.genAirWatchNotes(order));

        return notes;
    }

    private Collection<Note> genAirWatchNotes(IqviaOrder order) {
        Collection<Note> notes = new ArrayList<>();

        if(order.getOrderType().equals(OrderType.PROD)){
            notes.addAll(super.getBaseEnvironmentNotes(order));

        }else{
            notes.add(new Note(Severity.MEDIUM, NoteText.CAREFUL_UAT_ENVIRONMENT));
        }
        return notes;
    }

    private Collection<Note> genStandardDeviceNotes(IqviaOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_HUB_CREDENTIALS));
        if(!order.getSimRepository().isEmpty()){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_SIMS_ACTIVE));
        }
        if(order.getOrderType().equals(OrderType.UAT)){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_LUNA_STEPS));
        }

        notes.addAll(super.genLanguageNotes(order));

        return notes;
    }

    private Collection<Note> genDocumentationNotes(IqviaOrder order) {
        Collection<Note> notes = new ArrayList<>();
        if(order.getStudy().isUnknown()){
            notes.add(new Note(Severity.HIGH, NoteText.ADD_UNKNOWN_STUDY));
        }

        notes.addAll(super.genCommentNotes(order));

        if(!order.getSimRepository().isEmpty()){
            notes.add(new Note(Severity.MEDIUM, NoteText.SIM_LABELS));
        }

        if(order.getOrderType().equals(OrderType.UAT)){
            notes.add(new Note(Severity.LOW, NoteText.NO_DOCS_LABELS_UAT));
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_UAT_BUILD_DOC));
        }else{
            notes.addAll(this.genDocsNotes(order));
            notes.addAll(this.genLabelNotes(order));
        }

        return notes;
    }

    private Collection<Note> genLabelNotes(IqviaOrder order) {
        Collection<Note> notes = new ArrayList<>();

        switch (order.getStudy().getContainsTranslatedLabels()){
            case TRUE:
                notes.add(new Note(Severity.MEDIUM, NoteText.CONTAINS_TRANSLATED_LABELS));
                break;
            case UNKNOWN:
                notes.add(new Note(Severity.MEDIUM, NoteText.CHECK_FOR_TRANSLATED_LABELS));
                break;
            default:
                break;
        }

        switch (order.getStudy().getContainsSepSitePatientLabels()){
            case TRUE:
                notes.add(new Note(Severity.MEDIUM, NoteText.SITE_PATIENT_SEP_LABELS));
                break;
            case UNKNOWN:
                notes.add(new Note(Severity.MEDIUM, NoteText.CHECK_SEP_SITE_PATIENT_LABELS));
                break;
            default:
                break;
        }

        if(!order.getDestination().isEnglishSpeaking() && order.isEnglishRequested()){
            notes.add(new Note(Severity.LOW, NoteText.ENGLISH_LABELS_OK));
        }

        return notes;
    }

    private Collection<Note> genDocsNotes(IqviaOrder order) {
        Collection<Note> notes = new ArrayList<>();

        if(order.getDocumentRepository().areMultipleCopiesRequested()){
            notes.add(new Note(Severity.MEDIUM, NoteText.MULTIPLE_COPIES_DOCS_REQ));
        }

        if(!order.getStudy().getIsGsgPlain().equals(TrinaryBoolean.TRUE)){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_STUDY_GSG));
            notes.add(new Note(Severity.LOW, NoteText.VERIFY_MODEL_GSG));
            notes.add(new Note(Severity.LOW, NoteText.VERIFY_PHONE_GSG));
        }

        return notes;
    }

    private Collection<Note> genShellCheckNotes(IqviaOrder order) {
        Collection<Note> notes = new ArrayList<>();
        if(order.getDestination().isUnknown()){
            Note unknownDestination = new Note(Severity.HIGH,
                    NoteText.ADD_UNKNOWN_DESTINATION);
            notes.add(unknownDestination);
        }

        notes.addAll(this.genShellCheckAccessoryNotes(order));
        notes.addAll(this.genShellCheckDeviceNotes(order));

        return notes;
    }

    private Collection<Note> genShellCheckDeviceNotes(IqviaOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.addAll(super.genSpecialDeviceReqNotes(order));
        Optional<List<String>> duplicates = order
                .getDeviceRepository().getDuplicateSerials();
        if(duplicates.isPresent()){
            notes.add(new Note(Severity.HIGH, String.format(
                    NoteText.DUPLICATE_SERIALS,
                    String.join(", ", duplicates.get()))));
        }
        return notes;
    }

    private Collection<Note> genShellCheckAccessoryNotes(IqviaOrder order) {
        Collection<Note> notes = new ArrayList<>();
        AccessoryRepository accessories = order.getAccessoryRepository();
        notes.addAll(super.getDestinationAccessoryNotes(order));
        if(order.getDeviceRepository()
                .containsDvcsWithConnector(ConnectorType.MICRO_USB)){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_AFW_CHARGERS));
        }

        if(accessories.containsAnyOfTheFollowing(List.of(
                IqviaAccessory.IPAD_STYLUS.getShortName(),
                IqviaAccessory.TABLET_STYLUS.getShortName()))){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_STYLUS_PRESENT));
        }

        if(accessories.containsAnyOfTheFollowing(List.of(
                IqviaAccessory.APPLE_HEADPHONES.getShortName(),
                IqviaAccessory.SAMSUNG_HEADPHONES.getShortName(),
                IqviaAccessory.MOTOROLA_HEADPHONES.getShortName()))){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_HEADPHONES_PRESENT));
        }

        if(accessories.containsAnyOfTheFollowing(List.of(
                IqviaAccessory.TABLET_STAND.getShortName(),
                IqviaAccessory.PHONE_STAND.getShortName()))){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_STANDS_PRESENT));
        }

        return notes;
    }
}
