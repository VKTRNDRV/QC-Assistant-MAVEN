package com.example.qcassistantmaven.service.orderParse;

import com.example.qcassistantmaven.exception.OrderParsingException;
import com.example.qcassistantmaven.regex.OrderInputRegex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SegmentedOrderInput {

    private String basicInfo;

    private String shippingInstructions;

    private String orderTermComments;

    private String itemList;

    private String rawInput;

    public SegmentedOrderInput(String rawText){
        setRawInput(rawText);
        parseBasicInfo(rawText);
        parseShippingInstructions(rawText);
        parseOrderTermComments(rawText);
        parseItemList(rawText);
    }

    private void parseItemList(String rawText) {
        Pattern pattern = Pattern.compile(
                OrderInputRegex.ITEMS_LIST_REGEX);
        Matcher matcher = pattern.matcher(rawText);

        if(matcher.find()){
            setItemList(matcher.group(
                    OrderInputRegex.ITEMS_LIST_GROUP));
        }else{
            throw new OrderParsingException("Items List not detected");
        }
    }

    private void parseOrderTermComments(String rawText) {
        Pattern pattern = Pattern.compile(OrderInputRegex
                .ORDER_TERM_COMMENTS_REGEX);
        Matcher matcher = pattern.matcher(rawText);

        if(matcher.find()){
            setOrderTermComments(matcher.group(OrderInputRegex
                    .ORDER_TERM_COMMENTS_GROUP));
        }else{
            throw new OrderParsingException(
                    "Order Term Comments not detected");
        }
    }

    private void parseShippingInstructions(String rawText) {
        Pattern pattern = Pattern.compile(OrderInputRegex
                .SHIPPING_INSTRUCTIONS_REGEX);
        Matcher matcher = pattern.matcher(rawText);

        if (matcher.find()) {
            setShippingInstructions(matcher.group(OrderInputRegex
                    .SHIPPING_INSTRUCTIONS_GROUP));
        } else {
            throw new OrderParsingException(
                    "Shipping Instructions not detected.");
        }
    }

    private void parseBasicInfo(String rawText) {
        Pattern pattern = Pattern.compile(
                OrderInputRegex.BASIC_INFO_REGEX);
        Matcher matcher = pattern.matcher(rawText);
        if(matcher.find()){
            setBasicInfo(matcher.group(
                    OrderInputRegex.BASIC_INFO_GROUP));
        }else{
            throw new OrderParsingException(
                    "Order's Basic Info Section not detected");
        }
    }

    public String getBasicInfo() {
        return basicInfo;
    }

    public SegmentedOrderInput setBasicInfo(String basicInfo) {
        this.basicInfo = basicInfo;
        return this;
    }

    public String getShippingInstructions() {
        return shippingInstructions;
    }

    public SegmentedOrderInput setShippingInstructions(String shippingInstructions) {
        this.shippingInstructions = shippingInstructions;
        return this;
    }

    public String getOrderTermComments() {
        return orderTermComments;
    }

    public SegmentedOrderInput setOrderTermComments(String orderTermComments) {
        this.orderTermComments = orderTermComments;
        return this;
    }

    public String getItemList() {
        return itemList;
    }

    public SegmentedOrderInput setItemList(String itemList) {
        this.itemList = itemList;
        return this;
    }

    public String getRawInput() {
        return rawInput;
    }

    public SegmentedOrderInput setRawInput(String rawInput) {
        this.rawInput = rawInput;
        return this;
    }
}
