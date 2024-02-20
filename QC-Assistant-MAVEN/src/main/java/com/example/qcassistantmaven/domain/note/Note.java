package com.example.qcassistantmaven.domain.note;

import com.example.qcassistantmaven.domain.enums.Severity;

public class Note {

    private Severity severity;
    private String text;

    public Note(Severity severity, String text){
        setSeverity(severity);
        setText(text);
    }

    public Severity getSeverity() {
        return severity;
    }

    public Note setSeverity(Severity severity) {
        this.severity = severity;
        return this;
    }

    public String getText() {
        return text;
    }

    public Note setText(String text) {
        this.text = text;
        return this;
    }

    public boolean isLowSeverity(){
        return this.severity.equals(Severity.LOW);
    }

    public boolean isMediumSeverity(){
        return this.severity.equals(Severity.MEDIUM);
    }

    public boolean isHighSeverity(){
        return this.severity.equals(Severity.HIGH);
    }
}
