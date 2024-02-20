package com.example.qcassistantmaven.web.transfer;

import com.example.qcassistantmaven.domain.dto.transfer.ClinicalEntitiesTransferDTO;
import com.example.qcassistantmaven.service.transfer.MedidataTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MedidataTransferController {

    private MedidataTransferService transferService;

    @Autowired
    public MedidataTransferController(MedidataTransferService transferService) {
        this.transferService = transferService;
    }

    @GetMapping("/medidata/export")
    public String getExportEntities(Model model){
        ClinicalEntitiesTransferDTO dto = this.transferService.exportEntities();
        model.addAttribute("json", dto);
        return "medidata-export";
    }

    @GetMapping("/medidata/import")
    public String getImportEntities(Model model){
        model.addAttribute("json", new ClinicalEntitiesTransferDTO());
        return "medidata-import";
    }

    @PostMapping("/medidata/import")
    public String importEntities(Model model,
                                 @ModelAttribute ClinicalEntitiesTransferDTO entitiesJSON,
                                 RedirectAttributes redirectAttributes){
        try {
            this.transferService.importEntities(entitiesJSON);
        }catch (RuntimeException exc){
            model.addAttribute("json", entitiesJSON);
            model.addAttribute("error", true);
            model.addAttribute("message", exc.getMessage());
            return "medidata-import";
        }

        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/";
    }
}
