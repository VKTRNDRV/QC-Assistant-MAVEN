package com.example.qcassistantmaven.web.qc;

import com.example.qcassistantmaven.domain.dto.orderNotes.MedableOrderNotesDto;
import com.example.qcassistantmaven.domain.dto.orderNotes.MedidataOrderNotesDto;
import com.example.qcassistantmaven.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistantmaven.exception.OrderParsingException;
import com.example.qcassistantmaven.service.qc.MedableQcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medable/qc")
public class MedableQcController {

    private MedableQcService qcService;

    @Autowired
    public MedableQcController(MedableQcService qcService) {
        this.qcService = qcService;
    }

    @GetMapping
    public String getGenerateQcNotes(Model model){
        model.addAttribute("rawOrderInput",
                new RawOrderInputDto());
        return "medable-qc";
    }

    @PostMapping
    public String generateQcNotes(@ModelAttribute RawOrderInputDto rawOrderInputDto,
                                  Model model){
        try {
            MedableOrderNotesDto notes = this.qcService.generateOrderNotes(rawOrderInputDto);
            model.addAttribute("notes", notes);
        }catch (OrderParsingException exception){
            model.addAttribute("rawOrderInput", rawOrderInputDto);
            model.addAttribute("error", true);
            model.addAttribute("message", exception.getMessage());
            return "medable-qc";
        }


        return "medable-qc-notes";
    }
}
