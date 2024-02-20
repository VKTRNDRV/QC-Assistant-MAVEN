package com.example.qcassistantmaven.service.noteGeneration;

import com.example.qcassistantmaven.domain.dto.orderNotes.MedidataOrderNotesDto;
import com.example.qcassistantmaven.domain.entity.app.MedidataApp;
import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistantmaven.domain.entity.study.MedidataStudy;
import com.example.qcassistantmaven.domain.entity.study.environment.MedidataEnvironment;
import com.example.qcassistantmaven.domain.entity.tag.MedidataTag;
import com.example.qcassistantmaven.domain.enums.OrderType;
import com.example.qcassistantmaven.domain.enums.Severity;
import com.example.qcassistantmaven.domain.enums.item.SimType;
import com.example.qcassistantmaven.domain.item.accessory.MedidataAccessory;
import com.example.qcassistantmaven.domain.item.device.android.phone.MedidataAndroidPhone;
import com.example.qcassistantmaven.domain.item.device.ios.ipad.MedidataIPad;
import com.example.qcassistantmaven.domain.note.Note;
import com.example.qcassistantmaven.domain.note.noteText.NoteText;
import com.example.qcassistantmaven.domain.order.AccessoryRepository;
import com.example.qcassistantmaven.domain.order.DocumentRepository;
import com.example.qcassistantmaven.domain.order.MedidataOrder;
import com.example.qcassistantmaven.service.study.MedidataStudyService;
import com.example.qcassistantmaven.service.tag.MedidataTagService;
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
public class MedidataNoteGenerationService extends NoteGenerationService {

    private MedidataStudyService studyService;

    private MedidataTagService tagService;

    private static final String ABBVIE = "Abbvie";

    @Autowired
    public MedidataNoteGenerationService(ModelMapper modelMapper, MedidataStudyService studyService, MedidataTagService tagService) {
        super(modelMapper);
        this.studyService = studyService;
        this.tagService = tagService;
    }

    public MedidataOrderNotesDto generateNotes(MedidataOrder order) {
        MedidataOrderNotesDto notes = new MedidataOrderNotesDto();
        notes.setItems(super.mapDevices(order))
                .setShellCheckNotes(this.genShellCheckNotes(order))
                .setDocumentationNotes(this.genDocumentationNotes(order));
        notes.sortItems();

        if(order.getDeviceRepository().containsIosDevices()){
            notes.setIosNotes(this.genIosNotes(order));
        }

        if(order.getDeviceRepository().containsAndroidDevices()){
            notes.setAndroidNotes(this.genAndroidNotes(order));
        }

        this.getApplicableTags(order).forEach(notes::addTagNote);

        if(!order.isStudyUnknown()){
            notes.setStudy(studyService.getStudyInfoById(
                    order.getStudy().getId()));
        }

        return notes;
    }

    private List<MedidataTag> getApplicableTags(MedidataOrder order) {
        return this.tagService.getEntities().stream()
                .filter(t -> super.isTagApplicable(t, order))
                .collect(Collectors.toList());

    }

    private Collection<Note> genShellCheckNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        if(order.getDestination().isUnknown()){
            notes.add(new Note(Severity.HIGH, NoteText.ADD_UNKNOWN_DESTINATION));
        }
        notes.addAll(this.genShellCheckAccessoryNotes(order));
        notes.addAll(this.genShellCheckDeviceNotes(order));
        return notes;
    }

    private Collection<Note> genDocumentationNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        if(order.getStudy().isUnknown()){
            notes.add(new Note(Severity.HIGH, NoteText.ADD_UNKNOWN_STUDY));
        }
        notes.addAll(this.genDocsNotes(order));
        notes.addAll(this.genLabelsNotes(order));
        notes.addAll(super.genCommentNotes(order));
        return notes;
    }

    private Collection<Note> genIosNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.addAll(this.genIosDeviceNotes(order));
        notes.addAll(this.genAirWatchNotes(order));
        return notes;
    }

    private Collection<Note> genAndroidNotes(MedidataOrder order){
        Collection<Note> notes = new ArrayList<>();
        notes.addAll(this.genAndroidDeviceNotes(order));
        notes.addAll(this.genAirWatchNotes(order));

        return notes;
    }

    private Collection<Note> genAndroidDeviceNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();

        notes.add(new Note(Severity.LOW, NoteText.VERIFY_SCREEN_LOCK_NONE));
        notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_AUTO_DOWNLOADS));
        if(order.getDeviceRepository().containsDevice(
                MedidataAndroidPhone.GALAXY_S7.getShortName())){
            notes.add(new Note(Severity.LOW, NoteText.VERIFY_S7_BATTERY));
        }

        notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_NO_DUPLICATES_AW));

        notes.addAll(this.genHubLoggingNotes(order));
        notes.addAll(super.genLanguageNotes(order));

        if(order.getOrderType().equals(OrderType.PROD)){
            notes.addAll(this.genAndroidAppNotes(order));
        }else{
            notes.add(new Note(Severity.MEDIUM, NoteText.UAT_CHECK_APPS));
        }

        return notes;
    }

    private Collection<Note> genAndroidAppNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();

        notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_APPS_GREEN_CHECK));

        List<MedidataApp> patientApps = order.getStudy().getEnvironment().getPatientApps();
        if(patientApps.size() > 1){
            notes.add(new Note(Severity.MEDIUM, String.format(
                    NoteText.CONFIRM_PATIENT_APPS_INSTALLED,
                    super.getAppNamesList(patientApps))));
        }

        return notes;
    }

    private Collection<Note> genIosDeviceNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();

        notes.addAll(this.genHubLoggingNotes(order));
        notes.addAll(super.genLanguageNotes(order));

        if(order.getOrderType().equals(OrderType.PROD)){
            notes.addAll(this.genIosAppNotes(order));
        }else{
            notes.add(new Note(Severity.MEDIUM, NoteText.UAT_CHECK_APPS));
        }

        return notes;
    }

    private Collection<Note> genAirWatchNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        MedidataEnvironment environment = order.getStudy().getEnvironment();

        notes.add(new Note(Severity.LOW, NoteText.VERIFY_NO_DECOM_TAG));

        if(order.getOrderType().equals(OrderType.PROD)){
            if(environment.getIsLegacy().equals(TrinaryBoolean.TRUE)){
                notes.add(new Note(Severity.MEDIUM,
                        NoteText.VERIFY_RETROFIT_TAG));
                if(!order.getSimType().equals(SimType.NONE)){
                    notes.add(new Note(Severity.MEDIUM,
                            NoteText.VERIFY_LEGACY_APN_TAG));
                }
            }

            notes.addAll(super.getBaseEnvironmentNotes(order));
        }else{
            notes.add(new Note(Severity.MEDIUM, NoteText.CAREFUL_UAT_ENVIRONMENT));
        }

        return notes;
    }

    private Collection<Note> genIosAppNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        MedidataStudy study = order.getStudy();

        if(order.containsSiteDevices()){
            List<MedidataApp> siteApps = study.getEnvironment().getSiteApps();
            if(siteApps.size() > 1){
                notes.add(new Note(Severity.MEDIUM, String.format(
                        NoteText.CONFIRM_SITE_APPS_INSTALLED,
                        super.getAppNamesList(siteApps))));
            }

            if(study.containsSiteApp(MedidataApp.PATIENT_CLOUD)){
                notes.add(new Note(Severity.LOW, NoteText.VERIFY_P_CLOUD_MULTIUSER_MODE));
            }
        }

        if(order.containsPatientDevices()){
            List<MedidataApp> patientApps = study.getEnvironment().getPatientApps();
            if(patientApps.size() > 1){
                notes.add(new Note(Severity.MEDIUM, String.format(
                        NoteText.CONFIRM_PATIENT_APPS_INSTALLED,
                        super.getAppNamesList(patientApps))));
            }

            if(study.containsPatientApp(MedidataApp.PATIENT_CLOUD)){
                notes.add(new Note(Severity.LOW, NoteText.VERIFY_P_CLOUD_UPDATED));

                if(study.getIsPatientDeviceIpad().equals(TrinaryBoolean.TRUE) || order
                        .getDeviceRepository().containsDevice(MedidataIPad.MINI.getShortName())){
                    notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_P_CLOUD_SINGLEUSER_MODE_TABLET));
                }
            }
        }

        notes.add(new Note(Severity.LOW, NoteText.VERIFY_APPS_GREEN_CHECK));

        return notes;
    }

    private Collection<Note> genHubLoggingNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        switch (order.getSimType()) {
            case VF_GLOBAL:
                notes.add(new Note(Severity.LOW, NoteText.HUB_LOG_VF_GLOBAL));
                if(!order.getStudy().getEnvironment().getIsLegacy().equals(TrinaryBoolean.TRUE)){
                    notes.add(new Note(Severity.LOW, NoteText.VERIFY_VFGO_APN));
                }
                break;
            case SIMON_IOT:
                notes.add(new Note(Severity.MEDIUM, NoteText.HUB_LOG_SIMON_IOT));
                if(!order.getStudy().getEnvironment().getIsLegacy().equals(TrinaryBoolean.TRUE)){
                    notes.add(new Note(Severity.LOW, NoteText.VERIFY_SIMON_APN));
                }
                break;
            case NONE:
                if (order.getDestination().getName().equals(Destination.EGYPT)) {
                    notes.add(new Note(Severity.MEDIUM, NoteText.EGYPT_NO_SIM_HUB_LOG));
                } else {
                    notes.add(new Note(Severity.MEDIUM, NoteText.HUB_LOG_NO_SIM));
                }
                break;
        }

        return notes;
    }


    private Collection<Note> genLabelsNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        MedidataStudy study = order.getStudy();

        notes.add(new Note(Severity.LOW, NoteText.CONFIRM_LABEL_DETAILS));
        notes.add(new Note(Severity.LOW, NoteText.CONFIRM_NO_PRINTING_ERRORS));


        if(order.containsSiteDevices() && order.containsPatientDevices()){
            notes.add(new Note(Severity.LOW, NoteText.CONFIRM_SITE_PATIENT_LABELS));
        }

        if(study.getIsPatientDeviceIpad().equals(TrinaryBoolean.TRUE) &&
                order.containsPatientDevices()){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_PATIENT_IPAD_LABEL));
        }

        if(!study.getContainsTranslatedLabels().equals(
                TrinaryBoolean.FALSE) && !order.isEnglishRequested()){
            notes.addAll(this.genTranslatedLabelsNotes(order));
        }

        return notes;
    }

    private Collection<Note> genTranslatedLabelsNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        notes.add(new Note(Severity.LOW, NoteText.VERIFY_LABEL_TYPE));

        switch (order.getStudy().getContainsTranslatedLabels()){
            case UNKNOWN:
                notes.add(new Note(Severity.MEDIUM, NoteText.CHECK_FOR_TRANSLATED_LABELS));
                break;
            case TRUE:
                notes.add(new Note(Severity.MEDIUM, NoteText.CONTAINS_TRANSLATED_LABELS));
                break;
        }

        return notes;
    }

    private Collection<Note> genDocsNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        MedidataSponsor sponsor = order.getStudy().getSponsor();
        DocumentRepository documents = order.getDocumentRepository();

        notes.add(new Note(Severity.LOW, NoteText.SEPARATE_BUILD_DOCS));

        if(sponsor.getName().equals(ABBVIE)){
            notes.add(new Note(Severity.MEDIUM, NoteText.INCLUDE_ABBVIE_DOC));
        }

        if(sponsor.getAreStudyNamesSimilar().equals(TrinaryBoolean.TRUE)){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_STUDY_NAME_DOCS));
        }

        if(!order.getStudy().getContainsEditableWelcomeLetter()
                .equals(TrinaryBoolean.FALSE)){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_WELCOME_LETTER));
        }

        if(documents.areMultipleCopiesRequested()){
            notes.add(new Note(Severity.MEDIUM, NoteText.MULTIPLE_COPIES_DOCS_REQ));
        }

        if(documents.areUserGuidesRequested()){
            notes.add(new Note(Severity.LOW, NoteText.CONFIRM_DOCS_PRINTED));
            notes.add(new Note(Severity.LOW, NoteText.CONFIRM_CORRECT_EDGE));
            if(!order.isEnglishRequested()){
                notes.addAll(this.genTranslatedDocNotes(order));
            }
        }

        if(order.containsSiteDevices()){
            notes.addAll(this.genCartonInsertsNotes(order));
        }

        return notes;
    }

    private Collection<Note> genCartonInsertsNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        MedidataStudy study = order.getStudy();
        if(study.isUnknown()){
            notes.add(new Note(Severity.MEDIUM, NoteText.PATIENT_CLOUD_INSERT_UNKNOWN));
            notes.add(new Note(Severity.LOW, NoteText.RAVE_ECONSENT_INSERT_UNKNOWN));
        }else{
            if(study.containsSiteApp(MedidataApp.PATIENT_CLOUD)){
                notes.add(new Note(Severity.MEDIUM, NoteText.INCLUDE_PATIENT_CLOUD_INSERTS));
            }

            if(study.containsSiteApp(MedidataApp.RAVE_ECONSENT)){
                notes.add(new Note(Severity.MEDIUM, NoteText.INCLUDE_RAVE_ECONSENT_INSERTS));
            }
        }

        return notes;
    }

    private Collection<Note> genTranslatedDocNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        switch (order.getStudy().getContainsTranslatedDocs()){
            case UNKNOWN:
                notes.add(new Note(Severity.MEDIUM, NoteText.CHECK_FOR_TRANSLATED_DOCS));
                break;
            case TRUE:
                notes.add(new Note(Severity.MEDIUM, NoteText.STUDY_CONTAINS_TRANSLATED_DOCS));
                notes.add(new Note(Severity.LOW, NoteText.CONFIRM_TRANSLATED_DOCS_LANG));
                break;
            default:
                break;
        }

        return notes;
    }

    private Collection<Note> genShellCheckDeviceNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();

        Optional<List<String>> duplicates = order
                .getDeviceRepository().getDuplicateSerials();
        if(duplicates.isPresent()){
            notes.add(new Note(Severity.HIGH, String.format(
                    NoteText.DUPLICATE_SERIALS,
                    String.join(", ", duplicates.get()))));
        }

        notes.addAll(super.genSpecialDeviceReqNotes(order));
        return notes;
    }

    private Collection<Note> genShellCheckAccessoryNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        Destination destination = order.getDestination();
        notes.addAll(super.getDestinationAccessoryNotes(order));

        if(!destination.isUnknown()){
            if(!destination.getSimType()
                    .equals(order.getSimType())){
                notes.add(new Note(Severity.HIGH, NoteText.SIM_TYPE_NOT_MATCHING));
            }
        }else{
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_SIM_TYPE));
        }


        if(order.getDeviceRepository()
                .containsAndroidDevices()){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_AFW_CHARGERS));
        }

        if(order.containsSiteDevices()){
            notes.addAll(this.genStylusHeadphonesNotes(order));

            if(order.getStudy().getIsPatientDeviceIpad()
                    .equals(TrinaryBoolean.TRUE)){
                notes.add(new Note(Severity.LOW, NoteText.IPAD_PATIENT_DEVICE_CASE));
            }
        }

        return notes;
    }

    private Collection<Note> genStylusHeadphonesNotes(MedidataOrder order) {
        Collection<Note> notes = new ArrayList<>();
        AccessoryRepository accessories = order.getAccessoryRepository();

        if(accessories.containsAccessory(
                MedidataAccessory.HEADPHONES.getShortName())){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_HEADPHONES_PRESENT));
        }
        if(accessories.containsAccessory(
                MedidataAccessory.STYLUS.getShortName())){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_STYLUS_PRESENT));
        }

        switch (order.getStudy().getIncludesHeadphonesStyluses()){
            case TRUE:
                notes.add(new Note(Severity.LOW, NoteText.STUDY_CONTAINS_HEADPH_STYLUSES));
                if(!accessories.containsAccessory(
                        MedidataAccessory.HEADPHONES.getShortName())){
                    notes.add(new Note(Severity.HIGH, NoteText.HEADPHONES_NOT_DETECTED));
                }

                if(!accessories.containsAccessory(
                        MedidataAccessory.STYLUS.getShortName())){
                    notes.add(new Note(Severity.HIGH, NoteText.STYLUSES_NOT_DETECTED));
                }
                break;

            case UNKNOWN:
                notes.add(new Note(Severity.LOW, NoteText.CONFIRM_IF_HEADPH_STYLUSES_NEEDED));
                break;

            default:
                break;
        }

        return notes;
    }
}
