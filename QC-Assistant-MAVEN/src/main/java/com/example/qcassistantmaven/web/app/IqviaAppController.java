package com.example.qcassistantmaven.web.app;

import com.example.qcassistantmaven.domain.dto.app.AppAddDto;
import com.example.qcassistantmaven.domain.dto.app.AppEditDto;
import com.example.qcassistantmaven.service.app.IqviaAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/iqvia/apps")
public class IqviaAppController {

    private IqviaAppService appService;

    @Autowired
    public IqviaAppController(IqviaAppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public String getApps(Model model){
        model.addAttribute("apps",
                this.appService.getAllEditApps());
        return "iqvia-apps-all";
    }

    @GetMapping("/add")
    public String getAddApp(Model model){
        model.addAttribute("appAddDto",
                new AppAddDto());
        return "iqvia-apps-add";
    }

    @PostMapping("/add")
    public String addApp(@ModelAttribute AppAddDto appAddDto,
                         Model model, RedirectAttributes redirectAttributes){
        try {
            this.appService.addApp(appAddDto);
        }catch (RuntimeException exception){
            model.addAttribute("error", true);
            model.addAttribute("message", exception.getMessage());
            return "iqvia-apps-add";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String getEditApp(@PathVariable Long id, Model model){
        model.addAttribute("appEditDto",
                this.appService.getEditAppById(id));
        return "iqvia-apps-edit";
    }

    @PostMapping("/edit")
    public String editApp(@ModelAttribute AppEditDto editDto,
                          Model model, RedirectAttributes redirectAttributes){
        try {
            this.appService.editApp(editDto);
        }catch (RuntimeException exc){
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "iqvia-apps-edit";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }
}
