package com.services.dao.impl;

import com.config.JdbcConfig;
import com.model.dto.MeaningsLyingInTheDictionary;
import com.services.dao.DictionaryValuesDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DictionaryValuesDAOImpl implements DictionaryValuesDAO {
    private JdbcConfig config;

    @Override
    public List<MeaningsLyingInTheDictionary> getMeaningsLyingInTheDictionaries() {
        Statement statement = config.getStat();
        ArrayList<MeaningsLyingInTheDictionary> lying = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("select word, translation from dictionary_values");
            while (resultSet.next()) {
                lying.add(new MeaningsLyingInTheDictionary(resultSet.getString("word"), resultSet.getString("translation")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lying;
    }

    @Override
    public MeaningsLyingInTheDictionary getLineOfDictionary(String word) {
        Statement stat = config.getStat();
        MeaningsLyingInTheDictionary ofDictionaries;
        try {
            PreparedStatement result = stat.getConnection().prepareStatement("select word,translation  from dictionary_values where word = ? ");
            result.setString(1, word);
            ResultSet resultSet = result.executeQuery();
            ofDictionaries = new MeaningsLyingInTheDictionary(resultSet.getString("word"), resultSet.getString("translation"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ofDictionaries;
    }

    @Override
    public boolean delete(String words) {
        Statement statement = config.getStat();
        try {
            PreparedStatement statement1 = statement.getConnection().prepareStatement("delete from dictionary_values where word = ?");
            statement1.setString(1, words);
            statement1.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }





//    public String add(String word, String translation, String dictionary) {
//
//    if (valid( word,  translation, dictionary)){
//        Statement statement = config.getStat();
//        PreparedStatement preparedStatement = statement.getConnection("");
//
//    }
//
//    }

    @Override
    public String pattern (String dictionary) {
        String pattern= "";
        try {
            Statement statement = config.getStat();
            PreparedStatement preparedStatement = statement.getConnection().prepareStatement("select template from dictionaries  where dictionary = ? ");
            preparedStatement.setString(1, dictionary);
            ResultSet resultSet = preparedStatement.executeQuery();
            pattern = resultSet.getString("template");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pattern;
    }

    private boolean wordTemplate(String word, String dictionary){
        return Pattern.matches(pattern(dictionary), word);
    }

    private boolean translationTemplate(String dictionary, String translation){
        return Pattern.matches(pattern(dictionary), translation);
    }

    @Override
    public boolean valid(String word,String translation, String dictionary){
        return (wordTemplate(word,dictionary) && translationTemplate(translation, dictionary));
    }



    private void addingALineToDictionaryValues(int id, String word, String translation, String dictionary){
        if (valid(word, translation,dictionary))
    }

}