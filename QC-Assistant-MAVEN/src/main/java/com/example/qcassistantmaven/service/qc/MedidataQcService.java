package com.example.qcassistantmaven.service.qc;

import com.example.qcassistantmaven.domain.dto.orderNotes.MedidataOrderNotesDto;
import com.example.qcassistantmaven.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistantmaven.domain.order.MedidataOrder;
import com.example.qcassistantmaven.service.noteGeneration.MedidataNoteGenerationService;
import com.example.qcassistantmaven.service.orderParse.MedidataOrderParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MedidataQcService {

    private MedidataOrderParseService orderParseService;
    private MedidataNoteGenerationService noteGenerationService;

    private static int requestsCount = 0;
    private static final String FORMAT = "Medidata: %d%n";

    @Autowired
    public MedidataQcService(MedidataOrderParseService orderParseService,
                             MedidataNoteGenerationService noteGenerationService) {
        this.orderParseService = orderParseService;
        this.noteGenerationService = noteGenerationService;
    }

    public MedidataOrderNotesDto generateOrderNotes(RawOrderInputDto inputDto){
        MedidataOrder order = this.orderParseService.parseOrder(inputDto);
        MedidataOrderNotesDto notes = this.noteGenerationService.generateNotes(order);
        incrementOrderCount();
        return notes;
    }

    private synchronized void incrementOrderCount(){
        requestsCount++;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void printRequestsCount(){
        System.out.println(LocalDate.now());
        System.out.printf(String.format(FORMAT, requestsCount));
        requestsCount = 0;
    }
}
