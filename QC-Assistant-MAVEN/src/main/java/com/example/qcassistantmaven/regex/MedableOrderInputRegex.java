package com.example.qcassistantmaven.regex;

public class MedableOrderInputRegex {

    public static final String CLIENT_VALIDATION_REGEX = "Medable";

    public static final String SIM_SHORTNAME = "Simon-IOT SIM Card";
    public static final String SIM_REGEX = "Medable\\s*Simon\\s*TMO\\s*SIM\\s*Card\\s*(?<serial>[0-9]{19}F{0,1})";

    public static final String SIM_SERIAL_GROUP_NAME = "serial";

}
