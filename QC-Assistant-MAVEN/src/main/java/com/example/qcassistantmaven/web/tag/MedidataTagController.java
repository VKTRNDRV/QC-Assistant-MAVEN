package com.example.qcassistantmaven.web.tag;

import com.example.qcassistantmaven.domain.dto.tag.TagAddDto;
import com.example.qcassistantmaven.domain.dto.tag.TagEditDto;
import com.example.qcassistantmaven.service.DestinationService;
import com.example.qcassistantmaven.service.study.MedidataStudyService;
import com.example.qcassistantmaven.service.tag.MedidataTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/medidata/tags")
public class MedidataTagController {

    private MedidataTagService tagService;
    private DestinationService destinationService;
    private MedidataStudyService studyService;

    @Autowired
    public MedidataTagController(MedidataTagService tagService,
                                 DestinationService destinationService,
                                 MedidataStudyService studyService) {
        this.tagService = tagService;
        this.destinationService = destinationService;
        this.studyService = studyService;
    }

    @GetMapping("/add")
    public ModelAndView getAddStudy(ModelAndView mav){
        mav.addObject("tagAddDto", new TagAddDto());
        addStudiesAndDestinations(mav);
        mav.setViewName("medidata-tags-add");
        return mav;
    }

    @PostMapping("/add")
    public ModelAndView postAddStudy(@ModelAttribute(name = "tagAddDto")
                                         TagAddDto tagAddDto,
                                     ModelAndView mav,
                                     RedirectAttributes redirectAttributes){
        try {
            this.tagService.addTag(tagAddDto);
        }catch (RuntimeException exc){
            mav.addObject("tagAddDto", tagAddDto);
            mav.addObject("error", true);
            mav.addObject("message", exc.getMessage());
            mav.setViewName("medidata-tags-add");
            return mav;
        }

        redirectAttributes.addFlashAttribute("success", true);
        mav.setViewName("redirect:/");
        return mav;
    }

    @GetMapping
    public ModelAndView getAllTags(ModelAndView mav){
        mav.addObject("tags", this.tagService.getDisplayTags());
        mav.setViewName("medidata-tags");
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditTag(@PathVariable Long id,
                                   ModelAndView mav){
        addStudiesAndDestinations(mav);
        mav.addObject("tagEditDto", this.tagService.getTagEdit(id));
        mav.setViewName("medidata-tags-edit");
        return mav;
    }

    @PostMapping("/edit")
    public ModelAndView putEditTag(@ModelAttribute("tagEditDto") TagEditDto tagEditDto,
                                   ModelAndView mav,
                                   RedirectAttributes redirectAttributes){

        try{
            this.tagService.editTag(tagEditDto);
        }catch (RuntimeException exc){
            mav.addObject("tagEditDto", tagEditDto);
            mav.addObject("error", true);
            mav.addObject("message", exc.getMessage());
            addStudiesAndDestinations(mav);
            mav.setViewName("medidata-tags-edit");
            return mav;
        }

        redirectAttributes.addFlashAttribute("success", true);
        mav.setViewName("redirect:/");
        return mav;
    }

    private ModelAndView addStudiesAndDestinations(ModelAndView modelAndView){
        modelAndView.addObject("destinations", this
                .destinationService.getTagDestinations());
        modelAndView.addObject("studies", this.studyService.getTagStudies());

        return modelAndView;
    }
}
