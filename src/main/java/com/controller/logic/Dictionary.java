package com.controller.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.controller.validation.Validator;
import com.model.DictionaryType;
import com.model.DictionaryStorage;
import com.model.dto.ListOfDictionaries;
import com.view.Console;

public class Dictionary implements ChoiceOfAction {
    public static final String NO_KEY = "No key found!";
    public static final String SIMILARITY_TO_THE_PATTERN = "No matches with the template were found!";
    public static final String ADD_KEY = "The key has been successfully added!";
    public static final String KEY_DOES_NOT_EXIST = "This key does not exist!";
    private final Map<String, String> localMap;
    private final DictionaryStorage dictionaryStorage;
    private final Validator validator;

    public Dictionary(DictionaryType dictionaryType) {
        this.validator = new Validator(dictionaryType.getPatternKey(), dictionaryType.getPatternValue());
        this.dictionaryStorage = new DictionaryStorage(dictionaryType.getDictionaryPath());
        localMap =  dictionaryStorage.getData();
    }

    public void saveData() {
        dictionaryStorage.saveData();
    }


    @Override
    public String removeRecord(String key)  {
        if (localMap.containsKey(key)) {
            localMap.remove(key);
            saveData();
        } else {
          return NO_KEY;
        }
        return Console.KEY_DELETE;
    }

    @Override
    public String fileReading(){
        StringBuilder dictionaryContent = new StringBuilder();
        for (Map.Entry<String,String> pair : localMap.entrySet()) {
            dictionaryContent.append(pair.getKey()).append(DictionaryType.getSymbol()).append(pair.getValue()).append("\n");
        }
        return dictionaryContent.toString();
    }

    @Override
    public List<String> fileReadingList() {
        List<String> dictionaryContent = new ArrayList<>();
        for (Map.Entry<String,String> pair : localMap.entrySet()) {
            dictionaryContent.add(pair.getKey() + DictionaryType.getSymbol() + pair.getValue()  ) ;
        }
        return dictionaryContent;
    }

    @Override
    public String search(String key) {
        String search = localMap.get(key);
        if (search != null) {
            return  key + DictionaryType.getSymbol() + search;
        } else {
            return KEY_DOES_NOT_EXIST;
        }

    }

    @Override
    public String addAnEntry(String key, String value) {
        if (validator.validPair(key, value)) {
            localMap.put(key, value);
            saveData();
            return ADD_KEY;
        } else {
            return SIMILARITY_TO_THE_PATTERN;
        }
    }




}