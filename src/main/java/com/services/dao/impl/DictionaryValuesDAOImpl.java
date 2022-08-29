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
    public boolean delete(String word) {
        Statement statement = config.getStat();
        try {
            PreparedStatement statement1 = statement.getConnection().prepareStatement("delete from dictionary_values where word = ?");
            statement1.setString(1, word);
            statement1.executeUpdate();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public String add(int id_words, String word, String translation) {
        Statement statement = config.getStat();
        try {

            PreparedStatement preparedStatement = statement.getConnection("");

        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public String pattern(String dictionary) {
        String pattern = "";
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

    private boolean wordTemplate(String word, String dictionary) {
        return Pattern.matches(pattern(dictionary), word);
    }

    private boolean translationTemplate(String dictionary, String translation) {
        return Pattern.matches(pattern(dictionary), translation);
    }

    @Override
    public boolean valid(String word, String translation, String dictionary) {
        return (wordTemplate(word, dictionary) && translationTemplate(translation, dictionary));
    }


// главный метод добавления, в котором собрано все
    private void addingALineToDictionaryValues(int id, String word, String translation, String dictionary){
        if (valid(word, translation,dictionary)){
            if (!checkStringsToSeeIfTheyMatch(id, word, translation)){

            }
        }





    }

    //метод который соберет в себе 3 метода сравнения и если все методы вернут true то
    private boolean checkStringsToSeeIfTheyMatch(int id, String word, String translate){
        if (!checkingIfTheIdsMatch(id).isEmpty() && !checkingIfTheWordsMatch(word).isEmpty() && checkingIfTheTranslationMatch(translate).equals(translate)){return true;}
        else {
            return false;
        }
    }

//метод, который сравнивает id и возвращает
    private ArrayList<MeaningsLyingInTheDictionary>  checkingIfTheIdsMatch(int id_words) {
        ArrayList<MeaningsLyingInTheDictionary> lying = new ArrayList<>();
        try {
            Statement statement = config.getStat();
            PreparedStatement preparedStatement = statement.getConnection().prepareStatement("select id_words from dictionary_values  where id_words = ? ");
            preparedStatement.setInt(1, id_words);
            ResultSet resultSet = preparedStatement.executeQuery();
            lying.add(new MeaningsLyingInTheDictionary(resultSet.getLong("id_word")));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lying;
    }

    private ArrayList<MeaningsLyingInTheDictionary> checkingIfTheWordsMatch(String word) {
        ArrayList<MeaningsLyingInTheDictionary> lying = new ArrayList<>();
        try {
            Statement statement = config.getStat();
            PreparedStatement preparedStatement = statement.getConnection().prepareStatement("select word from dictionary_values  where word = ? ");
            preparedStatement.setString(1, word);
            ResultSet resultSet = preparedStatement.executeQuery();
            lying.add(new MeaningsLyingInTheDictionary(resultSet.getString("word")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lying;


    }

    private ArrayList<MeaningsLyingInTheDictionary> checkingIfTheTranslationMatch(String translation) {
        ArrayList<MeaningsLyingInTheDictionary> lying = new ArrayList<>();
        try {
            Statement statement = config.getStat();
            PreparedStatement preparedStatement = statement.getConnection().prepareStatement("select translation from dictionary_values  where translation = ? ");
            preparedStatement.setString(1, translation);
            ResultSet resultSet = preparedStatement.executeQuery();
            lying.add(new MeaningsLyingInTheDictionary(resultSet.getString("translation")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lying;
    }
}