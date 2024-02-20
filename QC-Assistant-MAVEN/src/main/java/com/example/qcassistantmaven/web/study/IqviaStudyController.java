package com.example.qcassistantmaven.web.study;

import com.example.qcassistantmaven.domain.dto.study.info.IqviaStudyInfoDto;
import com.example.qcassistantmaven.domain.dto.study.add.IqviaStudyAddDto;
import com.example.qcassistantmaven.domain.dto.study.edit.IqviaStudyEditDto;
import com.example.qcassistantmaven.service.app.IqviaAppService;
import com.example.qcassistantmaven.service.sponsor.IqviaSponsorService;
import com.example.qcassistantmaven.service.study.IqviaStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("iqvia/studies")
public class IqviaStudyController {

    private IqviaStudyService studyService;
    private IqviaSponsorService sponsorService;
    private IqviaAppService appService;

    @Autowired
    public IqviaStudyController(IqviaStudyService studyService, IqviaSponsorService sponsorService, IqviaAppService appService) {
        this.studyService = studyService;
        this.sponsorService = sponsorService;
        this.appService = appService;
    }

    @GetMapping("/add")
    public String getAddStudy(Model model){
        addSponsorsAndApps(model);
        model.addAttribute("studyAddDto",
                new IqviaStudyAddDto());

        return "iqvia-studies-add";
    }

    @PostMapping("/add")
    public String addStudy(@ModelAttribute IqviaStudyAddDto studyAddDto,
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
            return "iqvia-studies-add";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }

    @GetMapping
    public String getViewEditStudies(Model model){
        model.addAttribute("studies", this.studyService.displayAllStudies());
        return "iqvia-studies";
    }

    @GetMapping("/edit/{id}")
    public String getEditStudy(@PathVariable Long id, Model model){
        addSponsorsAndApps(model);
        model.addAttribute("studyEditDto", this.studyService.getStudyEditById(id));
        return "iqvia-studies-edit";
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<IqviaStudyInfoDto> getStudyInfo(@PathVariable Long id){
        try {
            IqviaStudyInfoDto studyInfoDto = this
                    .studyService.getStudyInfoById(id);
            return ResponseEntity.ok(studyInfoDto);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/edit")
    public String editStudy(@ModelAttribute IqviaStudyEditDto studyEditDto,
                            Model model, RedirectAttributes redirectAttributes){
        try {
            this.studyService.editStudy(studyEditDto);
        }catch (RuntimeException exc){
            model.addAttribute("studyEditDto", studyEditDto);
            addSponsorsAndApps(model);
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "iqvia-studies-edit";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }

    private Model addSponsorsAndApps(Model model) {
        model.addAttribute("sponsors",
                this.sponsorService.displayAllSponsors());
        model.addAttribute("apps",
                this.appService.getAllEditApps());

        return model;
    }
}
