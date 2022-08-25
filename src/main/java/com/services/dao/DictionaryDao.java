package com.services.dao;

import com.model.dto.ListOfDictionaries;

import java.util.List;

public interface DictionaryDao {
  List<ListOfDictionaries> getListOfDictionary();
  ListOfDictionaries getLineOfDictionary(Long id);
  Integer getIdDictionary(String dictionary);
}
