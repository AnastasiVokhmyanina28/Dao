package com.services.dao;

import com.model.dto.MeaningsLyingInTheDictionary;

import java.util.List;

public interface DictionaryValuesDAO {
    List<MeaningsLyingInTheDictionary> getMeaningsLyingInTheDictionaries();
    MeaningsLyingInTheDictionary getLineOfDictionary(String word);
    boolean delete(String words);
    String pattern (String dictionary);
    boolean valid(String word,String translation, String dictionary);
    boolean checkStringsToSeeIfTheyMatch(int id, String word, String translate);



}
