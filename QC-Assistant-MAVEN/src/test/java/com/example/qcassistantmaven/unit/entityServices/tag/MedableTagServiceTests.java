package com.example.qcassistantmaven.unit.entityServices.tag;

import com.example.qcassistantmaven.domain.dto.tag.TagAddDto;
import com.example.qcassistantmaven.domain.dto.tag.TagDisplayDto;
import com.example.qcassistantmaven.domain.dto.tag.TagEditDto;
import com.example.qcassistantmaven.repository.tag.MedableTagRepository;
import com.example.qcassistantmaven.service.tag.BaseTagService;
import com.example.qcassistantmaven.service.tag.MedableTagService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MedableTagServiceTests {

    private MedableTagService tagService;

    private MedableTagRepository tagRepository;

    @Autowired
    public MedableTagServiceTests(MedableTagService tagService,
                                  MedableTagRepository tagRepository) {
        this.tagService = tagService;
        this.tagRepository = tagRepository;
    }

    @Test
    public <T> void getDisplayTags_ReturnsCorrectCount(){
        List<T> fromService = (List<T>) this
                .tagService.getDisplayTags();

        Assertions.assertEquals(fromService.size(),
                tagRepository.count());

        if(!fromService.isEmpty()){
            Assertions.assertEquals(fromService.get(0).getClass(),
                    TagDisplayDto.class);
        }
    }

    @Test
    public void addTag_BlankTextThrowsException(){
        TagAddDto blankNameTag = new TagAddDto();
        blankNameTag.setText("   ");

        Assertions.assertThrows(RuntimeException.class,
                () -> tagService.addTag(blankNameTag));
    }

    @Test
    public void addTag_TooLongTextThrowsException(){
        TagAddDto longTextTag = new TagAddDto();
        StringBuilder tagText = new StringBuilder();
        tagText.append("a".repeat(BaseTagService
                .MAX_NOTE_LENGTH + 1));
        longTextTag.setText(tagText.toString());

        Assertions.assertThrows(RuntimeException.class,
                () -> tagService.addTag(longTextTag));
    }

    @Test
    public void editTag_BlankTextThrowsException(){
        TagEditDto blankNameTag = new TagEditDto();
        blankNameTag.setText("   ");

        Assertions.assertThrows(RuntimeException.class,
                () -> tagService.editTag(blankNameTag));
    }

    @Test
    public void editTag_TooLongTextThrowsException(){
        TagEditDto longTextTag = new TagEditDto();
        StringBuilder tagText = new StringBuilder();
        tagText.append("a".repeat(BaseTagService
                .MAX_NOTE_LENGTH + 1));
        longTextTag.setText(tagText.toString());

        Assertions.assertThrows(RuntimeException.class,
                () -> tagService.editTag(longTextTag));
    }
}
