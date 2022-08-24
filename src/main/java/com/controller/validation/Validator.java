package com.controller.validation;

import java.util.regex.Pattern;

public class Validator implements ValidationValue {
    private final String patternKey;
    private final String patternValue;
    public Validator(String patternKey, String patternValue){
        this.patternKey = patternKey;
        this.patternValue = patternValue;
    }

    public boolean keyCheck(String key){
        return Pattern.matches(patternKey, key);
    }

    public boolean valueCheck(String value) {
        return Pattern.matches(patternValue, value);
    }

    @Override
    public boolean validPair(String key, String value){
        return (keyCheck(key) && valueCheck(value));
    }

}
