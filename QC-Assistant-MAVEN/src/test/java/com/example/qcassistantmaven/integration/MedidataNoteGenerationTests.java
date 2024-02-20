package com.example.qcassistantmaven.integration;

import com.example.qcassistantmaven.domain.dto.orderNotes.MedidataOrderNotesDto;
import com.example.qcassistantmaven.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.sponsor.MedidataSponsor;
import com.example.qcassistantmaven.domain.entity.study.MedidataStudy;
import com.example.qcassistantmaven.domain.entity.study.environment.MedidataEnvironment;
import com.example.qcassistantmaven.domain.enums.item.PlugType;
import com.example.qcassistantmaven.domain.enums.item.SimType;
import com.example.qcassistantmaven.domain.note.Note;
import com.example.qcassistantmaven.domain.note.noteText.NoteText;
import com.example.qcassistantmaven.domain.order.MedidataOrder;
import com.example.qcassistantmaven.service.noteGeneration.MedidataNoteGenerationService;
import com.example.qcassistantmaven.service.orderParse.MedidataOrderParseService;
import com.example.qcassistantmaven.service.study.MedidataStudyService;
import com.example.qcassistantmaven.unit.orderParse.MedidataTestOrderInput;
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
public class MedidataNoteGenerationTests {

    private MedidataOrderParseService orderParseService;

    private MedidataNoteGenerationService noteGenerationService;

    private MedidataStudyService studyService;

    private static Long UNKNOWN_STUDY_ID;

    private static Destination SPECIAL_REQUIREMENTS_DESTINATION;

    private static MedidataStudy SPECIAL_DOCS_STUDY;

    private static MedidataStudy SPECIAL_ENV_STUDY;

    @Autowired
    public MedidataNoteGenerationTests(MedidataOrderParseService orderParseService,
                                       MedidataNoteGenerationService noteGenerationService,
                                       MedidataStudyService studyService) {
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

        MedidataSponsor placeholderSponsor = new MedidataSponsor();
        placeholderSponsor.setName("Placeholder Sponsor")
                .setAreStudyNamesSimilar(TrinaryBoolean.TRUE);

        MedidataEnvironment placeholderEnvironment = new MedidataEnvironment()
                .setIsLegacy(TrinaryBoolean.FALSE)
                .setSiteApps(new ArrayList<>())
                .setPatientApps(new ArrayList<>());

        placeholderEnvironment.setIsOsSeparated(TrinaryBoolean.FALSE)
                .setIsDestinationSeparated(TrinaryBoolean.FALSE)
                .setIsSitePatientSeparated(TrinaryBoolean.FALSE);

        SPECIAL_DOCS_STUDY = new MedidataStudy()
                .setSponsor(placeholderSponsor)
                .setEnvironment(placeholderEnvironment)
                .setContainsTranslatedLabels(TrinaryBoolean.TRUE)
                .setContainsTranslatedDocs(TrinaryBoolean.TRUE)
                .setContainsEditableWelcomeLetter(TrinaryBoolean.TRUE)
                .setIncludesHeadphonesStyluses(TrinaryBoolean.TRUE)
                .setIsPatientDeviceIpad(TrinaryBoolean.FALSE);
        SPECIAL_DOCS_STUDY.setName("Special Docs Study").setId(UNKNOWN_STUDY_ID);

        MedidataEnvironment specialEnvironment = new MedidataEnvironment()
                .setIsLegacy(TrinaryBoolean.TRUE)
                .setSiteApps(new ArrayList<>())
                .setPatientApps(new ArrayList<>());

        specialEnvironment.setIsOsSeparated(TrinaryBoolean.TRUE)
                .setIsDestinationSeparated(TrinaryBoolean.TRUE)
                .setIsSitePatientSeparated(TrinaryBoolean.TRUE);

        SPECIAL_ENV_STUDY = new MedidataStudy()
                .setSponsor(placeholderSponsor)
                .setEnvironment(specialEnvironment)
                .setContainsTranslatedLabels(TrinaryBoolean.FALSE)
                .setContainsTranslatedDocs(TrinaryBoolean.FALSE)
                .setContainsEditableWelcomeLetter(TrinaryBoolean.FALSE)
                .setIncludesHeadphonesStyluses(TrinaryBoolean.FALSE)
                .setIsPatientDeviceIpad(TrinaryBoolean.FALSE);
        SPECIAL_ENV_STUDY.setName("Special Env Study").setId(UNKNOWN_STUDY_ID);
    }

    @BeforeEach
    public void setStudyIDs(){
        UNKNOWN_STUDY_ID = this.studyService.getUnknownStudy().getId();
        SPECIAL_DOCS_STUDY.setId(UNKNOWN_STUDY_ID);
        SPECIAL_ENV_STUDY.setId(UNKNOWN_STUDY_ID);
    }

    @Test
    public void testDestinationNoteGeneration(){
        MedidataOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(MedidataTestOrderInput.HEADPHONES_STYLUSES_DOCUMENTS_ORDER));

        order.setDestination(SPECIAL_REQUIREMENTS_DESTINATION);

        MedidataOrderNotesDto notesFromService = this
                .noteGenerationService.generateNotes(order);

        List<String> expectedNotes = new ArrayList<>();
        expectedNotes.add(NoteText.SIM_TYPE_NOT_MATCHING);
        expectedNotes.add(NoteText.CONFIRM_INVOICE_APPROVED);
        expectedNotes.add(String.format(NoteText.VERIFY_PLUG_TYPE,
                        SPECIAL_REQUIREMENTS_DESTINATION.getPlugType()));
        expectedNotes.add( NoteText.VERIFY_HEADPHONES_PRESENT);
        expectedNotes.add(NoteText.VERIFY_STYLUS_PRESENT);
        expectedNotes.add(NoteText.VERIFY_DVC_MODELS);
        expectedNotes.add(NoteText.VERIFY_UNUSED_DEVICES);
        expectedNotes.add(NoteText.VERIFY_BOX_SERIALS);



        filterNotes(notesFromService.getShellCheckNotes(), expectedNotes);
        filterNotes(notesFromService.getDocumentationNotes(), expectedNotes);
        filterNotes(notesFromService.getAndroidNotes(), expectedNotes);
        filterNotes(notesFromService.getIosNotes(), expectedNotes);

        Assertions.assertEquals(0, expectedNotes.size(),
                String.join(", ", expectedNotes));
    }

    @Test
    public void testLabelDocsStudyNoteGeneration(){
        MedidataOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(MedidataTestOrderInput.HEADPHONES_STYLUSES_DOCUMENTS_ORDER));

        order.setStudy(SPECIAL_DOCS_STUDY);

        MedidataOrderNotesDto notesFromService = this
                .noteGenerationService.generateNotes(order);

        List<String> expectedNotesTexts = new ArrayList<>();
        expectedNotesTexts.add(NoteText.STUDY_CONTAINS_TRANSLATED_DOCS);
        expectedNotesTexts.add(NoteText.CONTAINS_TRANSLATED_LABELS);
        expectedNotesTexts.add(NoteText.VERIFY_LABEL_TYPE);
        expectedNotesTexts.add(NoteText.CONFIRM_NO_PRINTING_ERRORS);
        expectedNotesTexts.add(NoteText.CONFIRM_TRANSLATED_DOCS_LANG);
        expectedNotesTexts.add(NoteText.CONFIRM_LABEL_DETAILS);

        filterNotes(notesFromService.getShellCheckNotes(), expectedNotesTexts);
        filterNotes(notesFromService.getDocumentationNotes(), expectedNotesTexts);
        filterNotes(notesFromService.getAndroidNotes(), expectedNotesTexts);
        filterNotes(notesFromService.getIosNotes(), expectedNotesTexts);

        Assertions.assertEquals(0, expectedNotesTexts.size(),
                String.join(", ", expectedNotesTexts));
    }

    @Test
    public void testEnvStudyNoteGeneration(){
        MedidataOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(MedidataTestOrderInput.HEADPHONES_STYLUSES_DOCUMENTS_ORDER));

        order.setStudy(SPECIAL_ENV_STUDY);

        MedidataOrderNotesDto notesFromService = this
                .noteGenerationService.generateNotes(order);

        List<String> expectedNotesTexts = new ArrayList<>();
        expectedNotesTexts.add(NoteText.SEPARATE_BUILD_DOCS);
        expectedNotesTexts.add(NoteText.IS_OS_SEPARATED);
        expectedNotesTexts.add(NoteText.IS_SITE_PATIENT_SEPARATED);
        expectedNotesTexts.add(NoteText.IS_DESTINATION_SEPARATED);
        expectedNotesTexts.add(NoteText.CAREFUL_SIMILAR_STUDY_NAMES);
        expectedNotesTexts.add(NoteText.HUB_LOG_VF_GLOBAL);
        expectedNotesTexts.add(NoteText.VERIFY_RETROFIT_TAG);
        expectedNotesTexts.add(NoteText.VERIFY_LEGACY_APN_TAG);
        expectedNotesTexts.add(NoteText.CONFIRM_APPROPRIATE_GROUP);

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
