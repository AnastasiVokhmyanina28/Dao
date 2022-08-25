package com.services.dao;

import com.model.dto.IntermediateTable;

import java.util.List;

public interface WordsDAO {
    List<IntermediateTable> getIntermediateTable();
    Integer checkTheIdForPresenceInTheTableWords(Integer wordId, Integer translationId);
}
