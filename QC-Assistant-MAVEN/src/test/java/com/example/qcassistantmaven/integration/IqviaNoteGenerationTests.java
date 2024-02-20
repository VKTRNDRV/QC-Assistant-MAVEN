package com.example.qcassistantmaven.integration;

import com.example.qcassistantmaven.domain.dto.orderNotes.IqviaOrderNotesDto;
import com.example.qcassistantmaven.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.sponsor.IqviaSponsor;
import com.example.qcassistantmaven.domain.entity.study.IqviaStudy;
import com.example.qcassistantmaven.domain.entity.study.environment.IqviaEnvironment;
import com.example.qcassistantmaven.domain.enums.item.PlugType;
import com.example.qcassistantmaven.domain.enums.item.SimType;
import com.example.qcassistantmaven.domain.note.Note;
import com.example.qcassistantmaven.domain.note.noteText.NoteText;
import com.example.qcassistantmaven.domain.order.IqviaOrder;
import com.example.qcassistantmaven.service.noteGeneration.IqviaNoteGenerationService;
import com.example.qcassistantmaven.service.orderParse.IqviaOrderParseService;
import com.example.qcassistantmaven.service.study.IqviaStudyService;
import com.example.qcassistantmaven.unit.orderParse.IqviaTestOrderInput;
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
public class IqviaNoteGenerationTests {

    private IqviaOrderParseService orderParseService;

    private IqviaNoteGenerationService noteGenerationService;

    private IqviaStudyService studyService;

    private static Long UNKNOWN_STUDY_ID;

    private static Destination SPECIAL_REQUIREMENTS_DESTINATION;

    private static IqviaStudy SPECIAL_DOCS_STUDY;

    private static IqviaStudy SPECIAL_ENV_STUDY;

    @Autowired
    public IqviaNoteGenerationTests(IqviaOrderParseService orderParseService,
                                    IqviaNoteGenerationService noteGenerationService,
                                    IqviaStudyService studyService) {
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

        IqviaSponsor placeholderSponsor = new IqviaSponsor();
        placeholderSponsor.setName("Placeholder Sponsor")
                .setAreStudyNamesSimilar(TrinaryBoolean.TRUE);

        IqviaEnvironment placeholderEnvironment = new IqviaEnvironment()
                .setSiteApps(new ArrayList<>())
                .setPatientApps(new ArrayList<>());

        placeholderEnvironment.setIsOsSeparated(TrinaryBoolean.FALSE)
                .setIsDestinationSeparated(TrinaryBoolean.FALSE)
                .setIsSitePatientSeparated(TrinaryBoolean.FALSE);

        SPECIAL_DOCS_STUDY = new IqviaStudy()
                .setSponsor(placeholderSponsor)
                .setEnvironment(placeholderEnvironment)
                .setContainsTranslatedLabels(TrinaryBoolean.TRUE)
                .setContainsTranslatedDocs(TrinaryBoolean.TRUE)
                .setContainsSepSitePatientLabels(TrinaryBoolean.TRUE)
                .setIsGsgPlain(TrinaryBoolean.FALSE);
        SPECIAL_DOCS_STUDY.setName("Special Docs Study").setId(UNKNOWN_STUDY_ID);

        IqviaEnvironment specialEnvironment = new IqviaEnvironment()
                .setSiteApps(new ArrayList<>())
                .setPatientApps(new ArrayList<>());

        specialEnvironment.setIsOsSeparated(TrinaryBoolean.TRUE)
                .setIsDestinationSeparated(TrinaryBoolean.TRUE)
                .setIsSitePatientSeparated(TrinaryBoolean.TRUE);

        SPECIAL_ENV_STUDY = new IqviaStudy()
                .setSponsor(placeholderSponsor)
                .setEnvironment(specialEnvironment)
                .setContainsTranslatedLabels(TrinaryBoolean.FALSE)
                .setContainsTranslatedDocs(TrinaryBoolean.FALSE)
                .setContainsSepSitePatientLabels(TrinaryBoolean.TRUE)
                .setIsGsgPlain(TrinaryBoolean.FALSE);
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
        IqviaOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(IqviaTestOrderInput.IOS_DEVICES_ORDER));

        order.setDestination(SPECIAL_REQUIREMENTS_DESTINATION);

        IqviaOrderNotesDto notesFromService = this
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
    public void testLabelDocsStudyNoteGeneration(){
        IqviaOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(IqviaTestOrderInput.AFW_DEVICES_AND_MULTIPLE_DOC_COPIES_ORDER));

        order.setStudy(SPECIAL_DOCS_STUDY);

        IqviaOrderNotesDto notesFromService = this
                .noteGenerationService.generateNotes(order);

        List<String> expectedNotesTexts = new ArrayList<>();
        expectedNotesTexts.add(NoteText.CONTAINS_TRANSLATED_LABELS);
        expectedNotesTexts.add(NoteText.SITE_PATIENT_SEP_LABELS);
        expectedNotesTexts.add(NoteText.VERIFY_STUDY_GSG);
        expectedNotesTexts.add(NoteText.VERIFY_PHONE_GSG);
        expectedNotesTexts.add(NoteText.MULTIPLE_COPIES_DOCS_REQ);


        filterNotes(notesFromService.getShellCheckNotes(), expectedNotesTexts);
        filterNotes(notesFromService.getDocumentationNotes(), expectedNotesTexts);
        filterNotes(notesFromService.getAndroidNotes(), expectedNotesTexts);
        filterNotes(notesFromService.getIosNotes(), expectedNotesTexts);

        Assertions.assertEquals(0, expectedNotesTexts.size(),
                String.join(", ", expectedNotesTexts));
    }

    @Test
    public void testEnvStudyNoteGeneration(){
        IqviaOrder order = this.orderParseService.parseOrder(new RawOrderInputDto()
                .setRawText(IqviaTestOrderInput.IOS_DEVICES_ORDER   ));

        order.setStudy(SPECIAL_ENV_STUDY);

        IqviaOrderNotesDto notesFromService = this
                .noteGenerationService.generateNotes(order);

        List<String> expectedNotesTexts = new ArrayList<>();
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
