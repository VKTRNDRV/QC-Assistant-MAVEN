package com.example.qcassistantmaven.web.app;

import com.example.qcassistantmaven.domain.dto.app.AppAddDto;
import com.example.qcassistantmaven.domain.dto.app.AppEditDto;
import com.example.qcassistantmaven.service.app.MedidataAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/medidata/apps")
public class MedidataAppController {

    private MedidataAppService appService;

    @Autowired
    public MedidataAppController(MedidataAppService appService) {
        this.appService = appService;
    }

    @GetMapping
    public String getApps(Model model){
        model.addAttribute("apps",
                this.appService.getAllEditApps());
        return "medidata-apps";
    }

    @GetMapping("/add")
    public String getAddApp(Model model){
        model.addAttribute("medidataAppAddDto",
                new AppAddDto());
        return "medidata-apps-add";
    }


    @PostMapping("/add")
    public String addApp(@ModelAttribute AppAddDto appAddDto,
                         Model model, RedirectAttributes redirectAttributes){
        try {
            this.appService.addApp(appAddDto);
        }catch (RuntimeException exception){
            model.addAttribute("error", true);
            model.addAttribute("message", exception.getMessage());
            return "medidata-apps-add";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    public String getEditApp(@PathVariable Long id, Model model){
        model.addAttribute("medidataAppEditDto",
                this.appService.getEditAppById(id));
        return "medidata-apps-edit";
    }

    @PostMapping("/edit")
    public String editApp(@ModelAttribute AppEditDto editDto,
                          Model model, RedirectAttributes redirectAttributes){
        try {
            this.appService.editApp(editDto);
        }catch (RuntimeException exc){
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "medidata-apps-edit";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }
}
