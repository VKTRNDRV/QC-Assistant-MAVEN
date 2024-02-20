package com.example.qcassistantmaven.web.transfer;

import com.example.qcassistantmaven.domain.dto.transfer.ClinicalEntitiesTransferDTO;
import com.example.qcassistantmaven.service.transfer.IqviaTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class IqviaTransferController {

    private IqviaTransferService transferService;

    @Autowired
    public IqviaTransferController(IqviaTransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("/iqvia/export")
    public String getExportEntities(Model model){
        ClinicalEntitiesTransferDTO dto = this.transferService.exportEntities();
        model.addAttribute("json", dto);
        return "iqvia-export";
    }

    @GetMapping("/iqvia/import")
    public String getImportEntities(Model model){
        model.addAttribute("json", new ClinicalEntitiesTransferDTO());
        return "iqvia-import";
    }

    @PostMapping("/iqvia/import")
    public String importEntities(Model model,
                                 @ModelAttribute ClinicalEntitiesTransferDTO entitiesJSON,
                                 RedirectAttributes redirectAttributes){
        try {
            this.transferService.importEntities(entitiesJSON);
        }catch (RuntimeException exc){
            model.addAttribute("json", entitiesJSON);
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "iqvia-import";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }
}
