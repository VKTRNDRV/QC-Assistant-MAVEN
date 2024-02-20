package com.example.qcassistantmaven.service.noteGeneration;

import com.example.qcassistantmaven.domain.dto.item.ItemNameSerialDto;
import com.example.qcassistantmaven.domain.dto.orderNotes.MedableOrderNotesDto;
import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.tag.IqviaTag;
import com.example.qcassistantmaven.domain.entity.tag.MedableTag;
import com.example.qcassistantmaven.domain.enums.OrderType;
import com.example.qcassistantmaven.domain.enums.Severity;
import com.example.qcassistantmaven.domain.enums.item.ConnectorType;
import com.example.qcassistantmaven.domain.enums.item.OperatingSystem;
import com.example.qcassistantmaven.domain.enums.item.ShellType;
import com.example.qcassistantmaven.domain.item.accessory.MedableAccessory;
import com.example.qcassistantmaven.domain.item.device.medical.MedableMedicalDevice;
import com.example.qcassistantmaven.domain.note.Note;
import com.example.qcassistantmaven.domain.note.noteText.NoteText;
import com.example.qcassistantmaven.domain.order.AccessoryRepository;
import com.example.qcassistantmaven.domain.order.DeviceRepository;
import com.example.qcassistantmaven.domain.order.IqviaOrder;
import com.example.qcassistantmaven.domain.order.MedableOrder;
import com.example.qcassistantmaven.service.study.MedableStudyService;
import com.example.qcassistantmaven.service.tag.MedableTagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedableNoteGenerationService extends NoteGenerationService{

    private MedableStudyService studyService;
    private MedableTagService tagService;

    @Autowired
    public MedableNoteGenerationService(ModelMapper modelMapper, MedableStudyService studyService, MedableTagService tagService) {
        super(modelMapper);
        this.studyService = studyService;
        this.tagService = tagService;
    }

    public MedableOrderNotesDto generateNotes(MedableOrder order){
        MedableOrderNotesDto notes = new MedableOrderNotesDto();
        Collection<ItemNameSerialDto> serializedItems = new ArrayList<>();
        serializedItems.addAll(super.mapSims(order.getSimRepository()));
        serializedItems.addAll(super.mapDevices(order));
        notes.setItems(serializedItems)
                .sortItems();

        notes.setShellCheckNotes(this.genShellCheckNotes(order))
                .setDocumentationNotes(this.genDocumentationNotes(order));

        if(order.getDeviceRepository().containsIosDevices()){
            notes.setIosNotes(this.genIosNotes(order));
        }

        if(order.getDeviceRepository().containsAndroidDevices()){
            notes.setAndroidNotes(this.genAndroidNotes(order));
        }

        this.getApplicableTags(order).forEach(notes::addTagNote);

        if(!order.getStudy().isUnknown()){
            notes.setStudy(studyService.getStudyInfoById(
                    order.getStudy().getId()));
        }


        return notes;
    }

    private List<MedableTag> getApplicableTags(MedableOrder order) {
        return this.tagService.getEntities().stream()
                .filter(t -> super.isTagApplicable(t, order))
                .collect(Collectors.toList());
    }

    private Collection<Note> genAndroidNotes(MedableOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.addAll(this.genStandardDeviceNotes(order));
        notes.add(new Note(Severity.LOW, NoteText.VERIFY_AFW_UPDATED));
        if(order.getDeviceRepository().containsAndroidDevices()){
            notes.add(new Note(Severity.LOW, NoteText.VERIFY_NO_DUPLICATES_AW));
        }
        if(order.getDeviceRepository().containsDevicesOfOsAndShell(
                OperatingSystem.ANDROID, ShellType.TABLET)){
            notes.add(new Note(Severity.LOW, NoteText.VERIFY_SWITCH_TO_MOBILE));
        }

        notes.addAll(this.genAirWatchNotes(order));

        return notes;
    }

    private Collection<Note> genIosNotes(MedableOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.addAll(this.genStandardDeviceNotes(order));
        notes.addAll(this.genAirWatchNotes(order));

        return notes;
    }

    private Collection<Note> genAirWatchNotes(MedableOrder order) {
        Collection<Note> notes = new ArrayList<>();
        if(order.getOrderType().equals(OrderType.PROD)){
            notes.addAll(super.getBaseEnvironmentNotes(order));
        }else {
            notes.add(new Note(Severity.MEDIUM, NoteText.CAREFUL_UAT_ENVIRONMENT));
        }

        Note checkChinaGroup = new Note(Severity.LOW, NoteText.CHECK_CHINA_GROUP);
        Note containsChinaGroup = new Note(Severity.MEDIUM, NoteText.CONTAINS_CHINA_GROUP);
        Note confirmHongKong = new Note(Severity.LOW, NoteText.CONFIRM_CHINA_GROUP_HONG_KONG);

        if(order.getDestination().getName().equals(Destination.HONG_KONG)){
            switch (order.getStudy().getEnvironment().getContainsChinaGroup()){
                case TRUE:
                    confirmHongKong.setSeverity(Severity.HIGH);
                    notes.add(containsChinaGroup);
                    notes.add(confirmHongKong);
                    break;
                case UNKNOWN:
                    checkChinaGroup.setSeverity(Severity.MEDIUM);
                    notes.add(checkChinaGroup);
                    notes.add(confirmHongKong);
                    break;
                default:
                    break;
            }
        }else if(order.getDestination().getName().equals(Destination.CHINA)) {
            switch (order.getStudy().getEnvironment().getContainsChinaGroup()) {
                case TRUE:
                    confirmHongKong.setSeverity(Severity.HIGH);
                    notes.add(confirmHongKong);
                    break;
                case UNKNOWN:
                    notes.add(checkChinaGroup);
                    break;
                default:
                    break;
            }
        }else {
            switch (order.getStudy().getEnvironment().getContainsChinaGroup()) {
                case TRUE:
                    notes.add(containsChinaGroup);
                    break;
                case UNKNOWN:
                    notes.add(checkChinaGroup);
                    break;
                default:
                    break;
            }
        }

        return notes;
    }

    private Collection<Note> genStandardDeviceNotes(MedableOrder order) {
        Collection<Note> notes = new ArrayList<>();
        Note differentSitePatientLangs = new Note(Severity.LOW,
                NoteText.OS_APPS_LANGS_MIGHT_DIFFER);
        if(order.areMultipleLanguagesRequested()){
            differentSitePatientLangs.setSeverity(Severity.MEDIUM);
        }
        notes.add(differentSitePatientLangs);
        notes.addAll(super.genLanguageNotes(order));
        notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_HUB_CREDENTIALS));
        if(!order.isEnglishRequested()){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_APPS_LANGUAGES));
        }

        if(order.getOrderType().equals(OrderType.UAT)){
            notes.add(new Note(Severity.MEDIUM, NoteText.UAT_SIMS_DEACTIVATED));
        }else{
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_SIMS_ACTIVE));
        }

        return notes;
    }

    private Collection<Note> genDocumentationNotes(MedableOrder order) {
        Collection<Note> notes = new ArrayList<>();
        if(order.getStudy().isUnknown()){
            notes.add(new Note(Severity.HIGH, NoteText.ADD_UNKNOWN_STUDY));
        }

        notes.add(new Note(Severity.LOW, NoteText.CONFIRM_ASSET_LABEL));
        notes.add(new Note(Severity.LOW, NoteText.CONFIRM_ASSET_NUM_ON_LABEL));
        if(order.containsSiteDevices()){
            notes.add(new Note(Severity.MEDIUM, NoteText.CONFIRM_MEDABLE_SITE_CARTONS));
        }
        if(order.getDeviceRepository().containsShellType(ShellType.MEDICAL)){
            notes.add(new Note(Severity.MEDIUM, NoteText.BUILD_DOCS_BY_HAND));
        }

        notes.addAll(super.genCommentNotes(order));

        return notes;
    }

    private Collection<Note> genShellCheckNotes(MedableOrder order) {
        Collection<Note> notes = new ArrayList<>();
        Destination destination = order.getDestination();
        if(destination.isUnknown()){
            notes.add(new Note(Severity.HIGH, NoteText.ADD_UNKNOWN_DESTINATION));
        }

        notes.addAll(this.genShellCheckAccessoryNotes(order));
        notes.addAll(this.genShellCheckDeviceNotes(order));

        return notes;
    }

    private Collection<Note> genShellCheckDeviceNotes(MedableOrder order) {
        Collection<Note> notes = new ArrayList<>(super
                .genSpecialDeviceReqNotes(order));
        Optional<List<String>> duplicates = order
                .getDeviceRepository().getDuplicateSerials();
        if(duplicates.isPresent()){
            notes.add(new Note(Severity.HIGH, String.format(
                    NoteText.DUPLICATE_SERIALS,
                    String.join(", ", duplicates.get()))));
        }
        return notes;
    }

    private Collection<Note> genShellCheckAccessoryNotes(MedableOrder order) {
        Collection<Note> notes = new ArrayList<>();
        DeviceRepository devices = order.getDeviceRepository();
        AccessoryRepository accessories = order.getAccessoryRepository();
        notes.addAll(super.getDestinationAccessoryNotes(order));

        if(devices.containsDvcsWithConnector(ConnectorType.MICRO_USB)){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_AFW_CHARGERS));
        }

        if(accessories.containsAccessory(
                MedableAccessory.MOTOROLA_HEADPHONES.getShortName())){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_HEADPHONES_PRESENT));
        }

        if(accessories.containsAccessory(
                MedableAccessory.SCREEN_PROTECTOR.getShortName())){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_SCREEN_PROTECTOR_PRESENT));
        }

        if(devices.containsShellType(ShellType.MEDICAL)){
            notes.add(new Note(Severity.MEDIUM, NoteText.MEDICAL_DVCS_TL_QC));

            if(devices.containsAnyOfTheFollowing(List.of(
                    MedableMedicalDevice.IWATCH_SERIES_5.getShortName(),
                    MedableMedicalDevice.WATCH_SERIES_6.getShortName(),
                    MedableMedicalDevice.WATCH_SE.getShortName(),
                    MedableMedicalDevice.WATCH_SE_2ND_GEN.getShortName()))){

                notes.add(new Note(Severity.MEDIUM, NoteText.CONFIRM_IWATCHES_CHARGED));
            }
        }

        return notes;
    }
}
