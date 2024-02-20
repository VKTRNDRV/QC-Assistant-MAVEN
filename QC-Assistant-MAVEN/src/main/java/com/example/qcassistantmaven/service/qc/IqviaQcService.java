package com.example.qcassistantmaven.service.qc;

import com.example.qcassistantmaven.domain.dto.orderNotes.IqviaOrderNotesDto;
import com.example.qcassistantmaven.domain.dto.raw.RawOrderInputDto;
import com.example.qcassistantmaven.domain.order.IqviaOrder;
import com.example.qcassistantmaven.service.noteGeneration.IqviaNoteGenerationService;
import com.example.qcassistantmaven.service.orderParse.IqviaOrderParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IqviaQcService {

    private IqviaOrderParseService orderParseService;

    private IqviaNoteGenerationService noteGenerationService;

    private static int requestsCount = 0;
    private static final String FORMAT = "IQVIA: %d%n";

    @Autowired
    public IqviaQcService(IqviaOrderParseService orderParseService,
                          IqviaNoteGenerationService noteGenerationService) {
        this.orderParseService = orderParseService;
        this.noteGenerationService = noteGenerationService;
    }

    public IqviaOrderNotesDto generateOrderNotes(RawOrderInputDto inputDto){
        IqviaOrder order = this.orderParseService.parseOrder(inputDto);
        IqviaOrderNotesDto notes = this.noteGenerationService.generateNotes(order);
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
