package com.disl.commons.payloads;

public class LanguageWiseTextRequest {

    private String text;
    private long languageId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(long languageId) {
        this.languageId = languageId;
    }
}
