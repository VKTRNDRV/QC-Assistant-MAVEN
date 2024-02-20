package com.example.qcassistantmaven.web.study;

import com.example.qcassistantmaven.domain.dto.study.add.MedidataStudyAddDto;
import com.example.qcassistantmaven.domain.dto.study.edit.MedidataStudyEditDto;
import com.example.qcassistantmaven.domain.dto.study.info.MedidataStudyInfoDto;
import com.example.qcassistantmaven.service.app.MedidataAppService;
import com.example.qcassistantmaven.service.sponsor.MedidataSponsorService;
import com.example.qcassistantmaven.service.study.MedidataStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("medidata/studies")
public class MedidataStudyController {

    private MedidataStudyService studyService;

    private MedidataSponsorService sponsorService;

    private MedidataAppService appService;


    @Autowired
    public MedidataStudyController(MedidataStudyService studyService, MedidataSponsorService sponsorService, MedidataAppService appService) {
        this.studyService = studyService;
        this.sponsorService = sponsorService;
        this.appService = appService;
    }


    @GetMapping("/add")
    public String getAddStudy(Model model){
        addSponsorsAndApps(model);
        model.addAttribute("studyAddDto",
                new MedidataStudyAddDto());

        return "medidata-studies-add";
    }

    @PostMapping("/add")
    public String addStudy(@ModelAttribute MedidataStudyAddDto studyAddDto,
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
            return "medidata-studies-add";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }

    @GetMapping
    public String getViewEditStudies(Model model){
        model.addAttribute("studies", this.studyService.displayAllStudies());
        return "medidata-studies";
    }

    @GetMapping("/edit/{id}")
    public String getEditStudy(@PathVariable Long id, Model model){
        addSponsorsAndApps(model);
        model.addAttribute("studyEditDto", this.studyService.getStudyEditById(id));
        return "medidata-studies-edit";
    }

    @PostMapping("/edit")
    public String editStudy(@ModelAttribute MedidataStudyEditDto studyEditDto,
                            Model model, RedirectAttributes redirectAttributes){
        try {
            this.studyService.editStudy(studyEditDto);
        }catch (RuntimeException exc){
            model.addAttribute("studyEditDto", studyEditDto);
            addSponsorsAndApps(model);
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "medidata-studies-edit";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }

    @GetMapping("/info/{id}")
    public ResponseEntity<MedidataStudyInfoDto> getStudyInfo(@PathVariable Long id){
        try {
            MedidataStudyInfoDto studyInfoDto = this
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
