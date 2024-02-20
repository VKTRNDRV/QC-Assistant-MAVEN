package com.example.qcassistantmaven.service.orderParse;

import com.example.qcassistantmaven.domain.entity.destination.Destination;
import com.example.qcassistantmaven.domain.entity.destination.Language;
import com.example.qcassistantmaven.domain.enums.OrderType;
import com.example.qcassistantmaven.exception.OrderParsingException;
import com.example.qcassistantmaven.regex.OrderInputRegex;
import com.example.qcassistantmaven.service.DestinationService;
import com.example.qcassistantmaven.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public abstract class ClinicalOrderParseService {

    protected DestinationService destinationService;
    protected LanguageService languageService;

    @Autowired
    protected ClinicalOrderParseService(DestinationService destinationService, LanguageService languageService) {
        this.destinationService = destinationService;
        this.languageService = languageService;
    }

    protected Destination getDestination(SegmentedOrderInput segmentedInput){
        Pattern namePattern;
        Matcher matcher;
        List<Destination> destinations = this
                .destinationService.getEntities();
        for(Destination destination : destinations){
            namePattern = Pattern.compile(destination.getName());
            matcher = namePattern.matcher(segmentedInput
                    .getShippingInstructions());
            if(matcher.find()){
                return destination;
            }
        }

        // check order term comments just in case
        for(Destination destination : destinations){
            namePattern = Pattern.compile(destination.getName());
            matcher = namePattern.matcher(segmentedInput.getOrderTermComments());
            if(matcher.find()){
                return destination;
            }
        }

        // return unknown destination
        return this.destinationService.getUnknownDestinationEntity();
    }

    protected Collection<Language> getRequestedLanguages(SegmentedOrderInput segmentedInput){
        Pattern namePattern;
        Matcher matcher;
        Collection<Language> languages = new ArrayList<>();
        for(Language language : this.languageService.getEntities()){
            namePattern = Pattern.compile(language.getName());
            matcher = namePattern.matcher(segmentedInput.getOrderTermComments());
            if(matcher.find()){
                languages.add(language);
            }
        }

        return languages;
    }

    protected OrderType getOrderType(SegmentedOrderInput segmentedInput) {
        Pattern pattern = Pattern.compile(OrderType.UAT.name());
        Matcher matcher = pattern.matcher(segmentedInput.getBasicInfo());
        if(matcher.find()){
            return OrderType.UAT;
        }

        matcher = pattern.matcher(segmentedInput.getOrderTermComments());
        if(matcher.find()){
            return OrderType.UAT;
        }

        return OrderType.PROD;
    }

    protected void validateOrderType(SegmentedOrderInput segmentedOrderInput) {
        Pattern pattern = Pattern.compile(
                OrderInputRegex.NEW_HIRE_REGEX);
        Matcher matcher = pattern.matcher(
                segmentedOrderInput.getBasicInfo());
        if(matcher.find()){
            return;
        }

        pattern = Pattern.compile(
                OrderInputRegex.ADVANCE_SEND_REGEX);
        matcher = pattern.matcher(
                segmentedOrderInput.getBasicInfo());
        if(!matcher.find()){
            throw new OrderParsingException(
                    "Order Type NOT 'New Hire'/'Advance Send'");
        }
    }

    protected String getStudyRangeString(SegmentedOrderInput segmentedInput) {

        Pattern studyPattern = Pattern.compile(
                OrderInputRegex.STUDY_REGEX);
        Matcher matcher = studyPattern.matcher(segmentedInput.getBasicInfo());
        String studyName;
        if(matcher.find()){
            studyName = matcher.group(
                            OrderInputRegex.STUDY_GROUP)
                    .trim();

            return studyName;
        }else{
            throw new OrderParsingException(
                    "Study Name could not be detected");
        }
    }
}
