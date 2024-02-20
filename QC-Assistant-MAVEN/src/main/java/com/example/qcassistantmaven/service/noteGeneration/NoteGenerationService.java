package com.example.qcassistantmaven.service.noteGeneration;

import com.example.qcassistantmaven.domain.dto.item.ItemNameSerialDto;
import com.example.qcassistantmaven.domain.entity.app.BaseApp;
import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.study.BaseStudy;
import com.example.qcassistantmaven.domain.entity.study.MedidataStudy;
import com.example.qcassistantmaven.domain.entity.study.environment.BaseEnvironment;
import com.example.qcassistantmaven.domain.entity.tag.BaseTag;
import com.example.qcassistantmaven.domain.entity.tag.MedidataTag;
import com.example.qcassistantmaven.domain.enums.Severity;
import com.example.qcassistantmaven.domain.enums.item.PlugType;
import com.example.qcassistantmaven.domain.item.device.Device;
import com.example.qcassistantmaven.domain.item.sim.SerializedSIM;
import com.example.qcassistantmaven.domain.note.Note;
import com.example.qcassistantmaven.domain.note.noteText.NoteText;
import com.example.qcassistantmaven.domain.order.ClinicalOrder;
import com.example.qcassistantmaven.domain.order.MedidataOrder;
import com.example.qcassistantmaven.domain.order.SimRepository;
import com.example.qcassistantmaven.regex.OrderInputRegex;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public abstract class NoteGenerationService {

    protected ModelMapper modelMapper;
    protected static final int LARGE_ORDER_COUNT = 10;

    @Autowired
    protected NoteGenerationService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    protected Collection<ItemNameSerialDto> mapDevices(ClinicalOrder order) {
        Collection<ItemNameSerialDto> deviceDTOs = new ArrayList<>();
        order.getDeviceRepository().getDevices().forEach(d -> {
                deviceDTOs.add(this.mapDeviceToSerialDTO(d));
        });
        return deviceDTOs;
    }

    private ItemNameSerialDto mapDeviceToSerialDTO(Device device) {
        return this.modelMapper.map(device, ItemNameSerialDto.class);
    }

    protected <T extends ClinicalOrder> Collection<Note> genCommentNotes(T order) {
        Collection<Note> notes = new ArrayList<>();
        String orderTermComments = order.getOrderTermComments();
        Pattern pattern = Pattern.compile(OrderInputRegex.SPECIAL_INSTRUCTIONS_REGEX);
        Matcher matcher = pattern.matcher(orderTermComments);
        if(matcher.find()){
            notes.add(new Note(Severity.MEDIUM, NoteText.SPECIAL_INSTRUCTIONS));
        }

        if(orderTermComments.contains(OrderInputRegex.NOTE_STRING)){
            notes.add(new Note(Severity.MEDIUM, NoteText.CHECK_NOTE_OTC));
        }

        return notes;
    }

    protected String getAppNamesList(Collection<? extends BaseApp> apps){
        return apps.stream().map(BaseApp::getName)
                .collect(Collectors.joining(", "));
    }

    protected Collection<ItemNameSerialDto> mapSims(SimRepository simRepository) {
        Collection<ItemNameSerialDto> simDTOs = new ArrayList<>();
        simRepository.getSims().forEach(
                s -> simDTOs.add(this.mapSimToSerialDto(s)));

        return simDTOs;
    }

    private ItemNameSerialDto mapSimToSerialDto(SerializedSIM sim){
        return this.modelMapper.map(sim, ItemNameSerialDto.class);
    }

    protected <T extends ClinicalOrder> Collection<Note> getDestinationAccessoryNotes(T order){
        Collection<Note> notes = new ArrayList<>();
        Destination destination = order.getDestination();

        if(destination.getRequiresInvoice().equals(TrinaryBoolean.TRUE)){
            notes.add(new Note(Severity.LOW, NoteText.CONFIRM_INVOICE_APPROVED));
        }

        if(order.getDeviceRepository().count() > LARGE_ORDER_COUNT &&
                !destination.getName().equals(Destination.ISRAEL)){
            notes.add(new Note(Severity.LOW, NoteText.VERIFY_CHARGER_COUNT));
        }

        if(!destination.isUnknown()){
            if(!destination.getPlugType().equals(PlugType.C)){
                notes.add(new Note(Severity.MEDIUM, String.format(
                        NoteText.VERIFY_PLUG_TYPE, destination.getPlugType().name())));
            }
        }else{
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_UNKNOWN_PLUG_TYPE));
        }

        return notes;
    }

    protected <T extends ClinicalOrder> Collection<Note> genSpecialDeviceReqNotes(T order) {
        Collection<Note> notes = new ArrayList<>();
        notes.add(new Note(Severity.LOW, NoteText.VERIFY_SERIALS));
        Destination destination = order.getDestination();

        switch (destination.getRequiresSpecialModels()){
            case TRUE: notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_DVC_MODELS));
                break;
            case UNKNOWN: notes.add(new Note(Severity.MEDIUM, NoteText.CHECK_DESTINATION_FOR_SPECIAL_MODELS));
                break;
            default:
                break;
        }

        if(destination.getRequiresUnusedDevices()
                .equals(TrinaryBoolean.TRUE)){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_UNUSED_DEVICES));
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_BOX_SERIALS));
        }

        if(destination.getName().equals(Destination.TURKEY)){
            notes.add(new Note(Severity.MEDIUM, NoteText.VERIFY_CE_MARKS));
        }

        return notes;
    }

    protected <T extends ClinicalOrder> Collection<Note> getBaseEnvironmentNotes(T order){
        Collection<Note> notes = new ArrayList<>();

        notes.add(new Note(Severity.LOW, NoteText.VERIFY_APPS_GREEN_CHECK));

        if(!order.getStudy().getSponsor().getAreStudyNamesSimilar()
                .equals(TrinaryBoolean.FALSE)){
            notes.add(new Note(Severity.MEDIUM, NoteText.CAREFUL_SIMILAR_STUDY_NAMES));
        }

        BaseEnvironment environment = order.getStudy().getEnvironment();
        TrinaryBoolean isSitePatientSeparated = environment.getIsSitePatientSeparated();
        TrinaryBoolean isDestinationSeparated = environment.getIsDestinationSeparated();
        TrinaryBoolean isOsSeparated = environment.getIsOsSeparated();
        if(!(isDestinationSeparated.equals(TrinaryBoolean.FALSE) &&
                isSitePatientSeparated.equals(TrinaryBoolean.FALSE) &&
                isOsSeparated.equals(TrinaryBoolean.FALSE))){

            notes.add(new Note(Severity.MEDIUM, NoteText.CONFIRM_APPROPRIATE_GROUP));

            if(isSitePatientSeparated.equals(TrinaryBoolean.TRUE)){
                notes.add(new Note(Severity.MEDIUM, NoteText.IS_SITE_PATIENT_SEPARATED));
            }

            if(isDestinationSeparated.equals(TrinaryBoolean.TRUE)){
                notes.add(new Note(Severity.MEDIUM, NoteText.IS_DESTINATION_SEPARATED));
            }

            if(isOsSeparated.equals(TrinaryBoolean.TRUE)){
                notes.add(new Note(Severity.MEDIUM, NoteText.IS_OS_SEPARATED));
            }
        }

        if(order.containsSiteDevices() && this
                .anyRequiresCamera(environment.getSiteApps())){
            notes.add(new Note(Severity.LOW, NoteText.SITE_APPS_CAMERA));
        }

        if(order.containsPatientDevices() && this
                .anyRequiresCamera(environment.getPatientApps())){
            notes.add(new Note(Severity.LOW, NoteText.PATIENT_APPS_CAMERA));
        }

        return notes;
    }

    protected <T extends BaseApp> boolean anyRequiresCamera(Collection<T> apps){
        for(T app : apps){
            if(app.getRequiresCamera().equals(TrinaryBoolean.TRUE)){
                return true;
            }
        }

        return false;
    }

    protected <T extends ClinicalOrder> Collection<Note> genLanguageNotes(T order) {
        Collection<Note> notes = new ArrayList<>();

        if(order.getDestination().isUnknown()){
            notes.add(new Note(Severity.MEDIUM, NoteText.UNKNOWN_DESTINATION));
        }

        if(order.areNoLanguagesDetected()){
            notes.add(new Note(Severity.MEDIUM, NoteText.NO_LANGUAGES_DETECTED));
            return notes;
        }

        if(order.isEnglishRequested()){
            Note note = new Note(Severity.MEDIUM, NoteText.ENGLISH_REQUESTED);
            if(order.getDestination().isEnglishSpeaking()){
                note.setSeverity(Severity.LOW);
            }
            notes.add(note);

        }else{
            if(order.areMultipleLanguagesRequested()){
                notes.add(new Note(Severity.MEDIUM, NoteText.MULTIPLE_LANGS_REQUESTED));
            }else {
                notes.add(new Note(Severity.LOW, NoteText.CHANGE_DEVICE_LANGUAGE));
            }

            if(order.requestsUnusualLanguages()){
                notes.add(new Note(Severity.HIGH, NoteText.UNUSUAL_LANGUAGES_REQUESTED));
            }
        }

        return notes;
    }

    protected <T extends BaseTag, O extends ClinicalOrder> boolean isTagApplicable(T tag, O order) {
        if(tag.hasOrderTypePrecondition() && !order
                .getOrderType().equals(tag.getOrderType())){
            return false;
        }

        if(tag.hasShellTypePrecondition() && !order
                .containsShellType(tag.getShellType())){
            return false;
        }

        if(tag.hasOperatingSystemPrecondition() && !order
                .containsOperatingSystem(tag.getOperatingSystem())){
            return false;
        }

        if(tag.hasDestinationPrecondition()){
            boolean isDestinationMatched = false;
            String orderDestinationName = order.getDestination().getName();
            for(Destination tagDestination : tag.getDestinations()){
                if(orderDestinationName.equals(
                        tagDestination.getName())){
                    isDestinationMatched = true;
                    break;
                }
            }

            if(!isDestinationMatched){
                return false;
            }
        }

        if(tag.hasStudyPrecondition()){
            boolean isStudyMatched = false;
            String orderStudyName = order.getStudy().getName();
            for(BaseStudy tagStudy : tag.getStudies()){
                if(orderStudyName.equals(tagStudy.getName())){
                    isStudyMatched = true;
                    break;
                }
            }

            if(!isStudyMatched){
                return false;
            }
        }


        return true;
    }
}
