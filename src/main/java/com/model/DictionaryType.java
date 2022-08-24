package com.model;

public class DictionaryType {
    private static final String splitChar = ":";
    private final String patternKey;
    private final String patternValue;
    private final String dictionaryPath;

    public DictionaryType (String patternKey, String patternValue, String dictionaryPath) {
        this.patternKey = patternKey;
        this.patternValue = patternValue;
        this.dictionaryPath = dictionaryPath;
    }

    public static String getSymbol() {
        return splitChar;
    }

    public String getPatternKey() {
        return patternKey;
    }

    public String getPatternValue() {
        return patternValue;
    }

    public String getDictionaryPath() {
        return dictionaryPath;
    }
}