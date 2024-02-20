package com.example.qcassistantmaven.integration;

import com.example.qcassistantmaven.domain.dto.orderNotes.MedableOrderNotesDto;
import com.example.qcassistantmaven.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.sponsor.MedableSponsor;
import com.example.qcassistantmaven.domain.entity.study.MedableStudy;
import com.example.qcassistantmaven.domain.entity.study.environment.MedableEnvironment;
import com.example.qcassistantmaven.domain.enums.item.PlugType;
import com.example.qcassistantmaven.domain.enums.item.SimType;
import com.example.qcassistantmaven.domain.note.Note;
import com.example.qcassistantmaven.domain.note.noteText.NoteText;
import com.example.qcassistantmaven.domain.order.MedableOrder;
import com.example.qcassistantmaven.service.noteGeneration.MedableNoteGenerationService;
import com.example.qcassistantmaven.service.orderParse.MedableOrderParseService;
import com.example.qcassistantmaven.service.study.MedableStudyService;
import com.example.qcassistantmaven.unit.orderParse.MedableTestOrderInput;
import com.example.qcassistantmaven.util.TrinaryBoolean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootTest
public class MedableNoteGenerationTests {

    private MedableOrderParseService orderParseService;

    private MedableNoteGenerationService noteGenerationService;

    private MedableStudyService studyService;

    private static Long UNKNOWN_STUDY_ID;

    private static Destination SPECIAL_REQUIREMENTS_DESTINATION;

    private static MedableStudy SPECIAL_ENV_STUDY;

    @Autowired
    public MedableNoteGenerationTests(MedableOrderParseService orderParseService,
                                      MedableNoteGenerationService noteGenerationService,
                                      MedableStudyService studyService) {
        this.orderParseService = orderParseService;
        this.noteGenerationService = noteGenerationService;
        this.studyService = studyService;
    }

    @BeforeAll
    public static void setUp(){
        SPECIAL_REQUIREMENTS_DESTINATION = new Destination()
                .setName("Special Requirements Destination")
                .setLanguages(new ArrayList<>())
                .setPlugType(PlugType.G)
                .setSimType(SimType.SIMON_IOT)
                .setRequiresSpecialModels(TrinaryBoolean.TRUE)
                .setRequiresUnusedDevices(TrinaryBoolean.TRUE)
                .setRequiresInvoice(TrinaryBoolean.TRUE);

        MedableSponsor placeholderSponsor = new MedableSponsor();
        placeholderSponsor.setName("Placeholder Sponsor")
                .setAreStudyNamesSimilar(TrinaryBoolean.TRUE);

        MedableEnvironment specialEnvironment = new MedableEnvironment()
                .setSiteApps(new ArrayList<>())
                .setPatientApps(new ArrayList<>())
                .setContainsChinaGroup(TrinaryBoolean.TRUE);
        specialEnvironment.setIsOsSeparated(TrinaryBoolean.TRUE)
                .setIsDestinationSeparated(TrinaryBoolean.TRUE)
                .setIsSitePatientSeparated(TrinaryBoolean.TRUE);

        SPECIAL_ENV_STUDY = new MedableStudy()
                .setSponsor(placeholderSponsor)
                .setEnvironment(specialEnvironment);
        SPECIAL_ENV_STUDY.setName("Special Env Study").setId(UNKNOWN_STUDY_ID);
    }

    @BeforeEach
    public void setStudyIDs(){
        UNKNOWN_STUDY_ID = this.studyService.getUnknownStudy().getId();
        SPECIAL_ENV_STUDY.setId(UNKNOWN_STUDY_ID);
    }

    @Test
    public void testDestinationNoteGeneration(){
        MedableOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(MedableTestOrderInput.AFW_DEVICES_ORDER));

        order.setDestination(SPECIAL_REQUIREMENTS_DESTINATION);

        MedableOrderNotesDto notesFromService = this
                .noteGenerationService.generateNotes(order);

        List<String> expectedNotes = new ArrayList<>();
        expectedNotes.add(NoteText.CONFIRM_INVOICE_APPROVED);
        expectedNotes.add(String.format(NoteText.VERIFY_PLUG_TYPE,
                SPECIAL_REQUIREMENTS_DESTINATION.getPlugType()));
        expectedNotes.add(NoteText.VERIFY_DVC_MODELS);
        expectedNotes.add(NoteText.VERIFY_UNUSED_DEVICES);
        expectedNotes.add(NoteText.VERIFY_BOX_SERIALS);
        expectedNotes.add(NoteText.VERIFY_SERIALS);


        filterNotes(notesFromService.getShellCheckNotes(), expectedNotes);
        filterNotes(notesFromService.getDocumentationNotes(), expectedNotes);
        filterNotes(notesFromService.getAndroidNotes(), expectedNotes);
        filterNotes(notesFromService.getIosNotes(), expectedNotes);

        Assertions.assertEquals(0, expectedNotes.size(),
                String.join(", ", expectedNotes));
    }

    @Test
    public void testMedicalDevicesAndSpecialEnvironmentOrder(){
        MedableOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(MedableTestOrderInput.EVERIONS_PLUS_ENGLISH_REQ_ORDER));

        order.setStudy(SPECIAL_ENV_STUDY);

        MedableOrderNotesDto notesFromService = this
                .noteGenerationService.generateNotes(order);

        List<String> expectedNotesTexts = new ArrayList<>();
        expectedNotesTexts.add(NoteText.BUILD_DOCS_BY_HAND);
        expectedNotesTexts.add(NoteText.MEDICAL_DVCS_TL_QC);
        expectedNotesTexts.add(NoteText.IS_OS_SEPARATED);
        expectedNotesTexts.add(NoteText.IS_SITE_PATIENT_SEPARATED);
        expectedNotesTexts.add(NoteText.IS_DESTINATION_SEPARATED);
        expectedNotesTexts.add(NoteText.VERIFY_HUB_CREDENTIALS);
        expectedNotesTexts.add(NoteText.VERIFY_SIMS_ACTIVE);
        expectedNotesTexts.add(NoteText.VERIFY_APPS_GREEN_CHECK);

        filterNotes(notesFromService.getShellCheckNotes(), expectedNotesTexts);
        filterNotes(notesFromService.getDocumentationNotes(), expectedNotesTexts);
        filterNotes(notesFromService.getAndroidNotes(), expectedNotesTexts);
        filterNotes(notesFromService.getIosNotes(), expectedNotesTexts);

        Assertions.assertEquals(0, expectedNotesTexts.size(),
                String.join(", ", expectedNotesTexts));
    }

    private void filterNotes(Collection<Note> actualNotes, List<String> expectedNoteTexts) {
        for (int i = 0; i < expectedNoteTexts.size(); i++) {
            String expectedNoteText = expectedNoteTexts.get(i);
            for(Note addedNote : actualNotes){
                if(addedNote.getText().equals(expectedNoteText)){
                    expectedNoteTexts.remove(i);
                    i--;
                    break;
                }
            }
        }
    }
}
