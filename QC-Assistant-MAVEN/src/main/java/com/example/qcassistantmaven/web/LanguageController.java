package com.example.qcassistantmaven.web;

import com.example.qcassistantmaven.domain.dto.language.LanguageDto;
import com.example.qcassistantmaven.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/languages")
public class LanguageController {

    private LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @GetMapping("/all")
    public String getLanguages(Model model){
        List<LanguageDto> languages = this.languageService.getAllLanguages();
        model.addAttribute("languages", languages);
        return "languages-all";
    }

    @GetMapping("/edit/{id}")
    public String getEditLanguage(@PathVariable Long id, Model model){
        model.addAttribute("language", this.languageService.getLanguageById(id));
        return "languages-edit";
    }

    @PostMapping("/edit")
    public String editLanguage(@ModelAttribute LanguageDto languageDto, Model model,
                               RedirectAttributes redirectAttributes){
        try {
            this.languageService.editLanguage(languageDto);
        }catch (RuntimeException exc){
            model.addAttribute("language", languageDto);
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "languages-edit";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }


    @GetMapping("/add")
    public String getAddLanguage(){
        return "languages-add";
    }

    @PostMapping("/add")
    public String addLanguage(@ModelAttribute LanguageDto languageDto, Model model,
                              RedirectAttributes redirectAttributes){
        try {
            this.languageService.addLanguage(languageDto);
        }catch (RuntimeException exc){
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "languages-add";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }
}
