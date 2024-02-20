package com.example.qcassistantmaven.web.sponsor;

import com.example.qcassistantmaven.domain.dto.sponsor.SponsorAddDto;
import com.example.qcassistantmaven.domain.dto.sponsor.SponsorEditDto;
import com.example.qcassistantmaven.service.sponsor.IqviaSponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("iqvia/sponsors")
public class IqviaSponsorController {

    private IqviaSponsorService sponsorService;

    @Autowired
    public IqviaSponsorController(IqviaSponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }

    @GetMapping
    public String getAllSponsors(Model model){
        model.addAttribute("sponsors", this.sponsorService.displayAllSponsors());
        return "iqvia-sponsors";
    }

    @GetMapping("/add")
    public String getAddSponsor(Model model){
        model.addAttribute("sponsorAddDto",
                new SponsorAddDto());

        return "iqvia-sponsors-add";
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
            return "iqvia-sponsors-add";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String getEditSponsor(@PathVariable Long id, Model model){
        model.addAttribute("sponsorEditDto",
                this.sponsorService.getSponsorEditById(id));
        return "iqvia-sponsors-edit";
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
            return "iqvia-sponsors-edit";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }
}
