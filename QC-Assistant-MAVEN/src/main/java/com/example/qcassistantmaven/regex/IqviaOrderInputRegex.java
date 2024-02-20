package com.example.qcassistantmaven.regex;

public class IqviaOrderInputRegex {

    public static final String CLIENT_VALIDATION_REGEX = "IQVIA";

    public static final String SIM_SHORTNAME = "Simon-IOT SIM Card";
    public static final String SIM_REGEX = "IQVIA\\s*Simon\\s*[IOTMO]{3}\\s*SIM\\s*Card\\s*(?<serial>[0-9]{19}F{0,1})";
    public static final String SIM_SERIAL_GROUP_NAME = "serial";


    public static final String DOCUMENT_REGEX = "IQVIA\\s*Custom\\s*Study\\s*Welcome.{0,32}55934-DNI\\s*(?<copiesCount>[0-9]{1,3})";
    public static final String DOC_COPIES_COUNT_GROUP = "copiesCount";
    public static final String DOC_SHORTNAME = "Document";
}
