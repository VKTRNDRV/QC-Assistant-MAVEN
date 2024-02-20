package com.example.qcassistantmaven.regex;

public class OrderInputRegex {

    public static final String NEW_HIRE_REGEX = "Horizon\\s*Order\\s*Type\\s*New\\s*Hire";
    public static final String ADVANCE_SEND_REGEX = "Horizon\\s*Order\\s*Type\\s*Advance\\s*Send";


    public static final String BASIC_INFO_REGEX = "(?<basicInfo>.+)Shipping\\s*Instructions:";
    public static final String BASIC_INFO_GROUP = "basicInfo";


    public static final String SHIPPING_INSTRUCTIONS_REGEX = "Shipping\\s*Instructions:(?<shippingInstructions>.+)Order Term Comments";
    public static final String SHIPPING_INSTRUCTIONS_GROUP = "shippingInstructions";


    public static final String ORDER_TERM_COMMENTS_REGEX = "Order\\s*Term\\s*Comments:(?<orderTermComments>.+)OLine";
    public static final String ORDER_TERM_COMMENTS_GROUP = "orderTermComments";
    public static final String SPECIAL_INSTRUCTIONS_REGEX = "[Ss]pecial\\s*[Ii]nstructions";

    public static final String ITEMS_LIST_REGEX = "OLine(?<itemsList>.+)";
    public static final String ITEMS_LIST_GROUP = "itemsList";

    public static final String STUDY_REGEX = "Study\\s*(?<studyName>.+)\\s*Site";
    public static final String STUDY_GROUP = "studyName";
    public static final String NOTE_STRING = "NOTE";

    public static final String LANGUAGES_CSV =
            "English,Spanish,French,German,Italian,Portuguese,Dutch,Sesotho,Xhosa,Zulu,Afrikaans,Hebrew,Chinese,Arabic,Malay,Thai,Korean,Slovenian,Slovakian,Ukrainian,Russian";
}
