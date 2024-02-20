package com.example.qcassistantmaven.web.sponsor;

import com.example.qcassistantmaven.domain.dto.sponsor.SponsorAddDto;
import com.example.qcassistantmaven.domain.dto.sponsor.SponsorEditDto;
import com.example.qcassistantmaven.service.sponsor.MedableSponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/medable/sponsors")
public class MedableSponsorController {

    private MedableSponsorService sponsorService;

    @Autowired
    public MedableSponsorController(MedableSponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }

    @GetMapping
    public String getAllSponsors(Model model){
        model.addAttribute("sponsors", this.sponsorService.displayAllSponsors());
        return "medable-sponsors";
    }

    @GetMapping("/add")
    public String getAddSponsor(Model model){
        model.addAttribute("sponsorAddDto",
                new SponsorAddDto());

        return "medable-sponsors-add";
    }

    @PostMapping("/add")
    public String addSponsor(@ModelAttribute SponsorAddDto sponsorAddDto,
                             Model model, RedirectAttributes redirectAttributes){
        try {
            this.sponsorService.addSponsor(sponsorAddDto);
        }catch (RuntimeException exc){
            model.addAttribute("sponsorAddDto", sponsorAddDto);
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "medable-sponsors-add";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String getEditSponsor(@PathVariable Long id, Model model){
        model.addAttribute("sponsorEditDto",
                this.sponsorService.getSponsorEditById(id));
        return "medable-sponsors-edit";
    }

    @PostMapping("/edit")
    public String editSponsor(@ModelAttribute SponsorEditDto sponsorEditDto,
                              Model model, RedirectAttributes redirectAttributes){
        try {
            this.sponsorService.editSponsor(sponsorEditDto);
        }catch (RuntimeException exc){
            model.addAttribute("sponsorEditDto", sponsorEditDto);
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "medable-sponsors-edit";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }
}
