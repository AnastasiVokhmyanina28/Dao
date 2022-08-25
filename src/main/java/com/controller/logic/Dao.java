package com.controller.logic;

import com.services.dao.impl.DictionaryDAOImpl;

import java.util.ArrayList;
import java.util.List;

public class Dao implements ChoiceOfAction {
    private DictionaryDAOImpl dictionaryDAO;


    public Dao(DictionaryDAOImpl dictionaryDAO) {
        this.dictionaryDAO = dictionaryDAO;
    }

    @Override
    public String removeRecord(String key) {
        return null;
    }

    @Override
    public String search(String key) {
        return null;
    }

    @Override
    public String addAnEntry(String key, String value) {
        return null;
    }

    @Override
    public String fileReading() {
        return null;
    }

    @Override

    public List<String> fileReadingList() {
        var cf = dictionaryDAO.getListOfDictionary();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < cf.size(); i++) {
            list.add(cf.get(i).toString());
        }
        return list;
    }


}
