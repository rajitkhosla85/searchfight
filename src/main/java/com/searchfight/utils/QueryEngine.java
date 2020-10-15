package com.searchfight.utils;

public enum QueryEngine {
    GOOGLE("Google"), BING("Bing");

    public final String label;

    private QueryEngine(String label) {
        this.label = label;
    }

    public String getValue() {
        return label;
    }
}

