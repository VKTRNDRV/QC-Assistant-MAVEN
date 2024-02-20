package com.example.qcassistantmaven.web.study;

import com.example.qcassistantmaven.domain.dto.study.add.IqviaStudyAddDto;
import com.example.qcassistantmaven.domain.dto.study.add.MedableStudyAddDto;
import com.example.qcassistantmaven.domain.dto.study.edit.IqviaStudyEditDto;
import com.example.qcassistantmaven.domain.dto.study.edit.MedableStudyEditDto;
import com.example.qcassistantmaven.domain.dto.study.info.IqviaStudyInfoDto;
import com.example.qcassistantmaven.domain.dto.study.info.MedableStudyInfoDto;
import com.example.qcassistantmaven.service.app.IqviaAppService;
import com.example.qcassistantmaven.service.app.MedableAppService;
import com.example.qcassistantmaven.service.sponsor.MedableSponsorService;
import com.example.qcassistantmaven.service.study.MedableStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("medable/studies")
public class MedableStudyController {

    private MedableStudyService studyService;
    private MedableSponsorService sponsorService;
    private MedableAppService appService;

    @Autowired
    public MedableStudyController(MedableStudyService studyService,
                                  MedableSponsorService sponsorService,
                                  MedableAppService appService) {
        this.studyService = studyService;
        this.sponsorService = sponsorService;
        this.appService = appService;
    }

    @GetMapping("/add")
    public String getAddStudy(Model model){
        addSponsorsAndApps(model);
        model.addAttribute("studyAddDto",
                new MedableStudyAddDto());

        return "medable-studies-add";
    }

    @PostMapping("/add")
    public String addStudy(@ModelAttribute MedableStudyAddDto studyAddDto,
                           Model model, RedirectAttributes redirectAttributes){
        try {
            this.studyService.addStudy(studyAddDto);
        }catch (RuntimeException exc){
            model.addAttribute("studyAddDto",
                    studyAddDto);
            addSponsorsAndApps(model);
            this.appService.getAllEditApps();
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "medable-studies-add";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }

    @GetMapping
    public String getViewEditStudies(Model model){
        model.addAttribute("studies", this.studyService.displayAllStudies());
        return "medable-studies";
    }

    @GetMapping("/edit/{id}")
    public String getEditStudy(@PathVariable Long id, Model model){
        addSponsorsAndApps(model);
        model.addAttribute("studyEditDto", this.studyService.getStudyEditById(id));
        return "medable-studies-edit";
    }

    @PostMapping("/edit")
    public String editStudy(@ModelAttribute MedableStudyEditDto studyEditDto,
                            Model model, RedirectAttributes redirectAttributes){
        try {
            this.studyService.editStudy(studyEditDto);
        }catch (RuntimeException exc){
            model.addAttribute("studyEditDto", studyEditDto);
            addSponsorsAndApps(model);
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "medable-studies-edit";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<MedableStudyInfoDto> getStudyInfo(@PathVariable Long id){
        try {
            MedableStudyInfoDto studyInfoDto = this
                    .studyService.getStudyInfoById(id);
            return ResponseEntity.ok(studyInfoDto);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    private Model addSponsorsAndApps(Model model) {
        model.addAttribute("sponsors",
                this.sponsorService.displayAllSponsors());
        model.addAttribute("apps",
                this.appService.getAllEditApps());

        return model;
    }
}
