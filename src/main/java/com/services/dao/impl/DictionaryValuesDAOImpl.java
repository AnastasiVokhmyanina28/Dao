package com.services.dao.impl;

import com.config.JdbcConfig;
import com.controller.logic.Dictionary;
import com.model.dto.MeaningsLyingInTheDictionary;
import com.services.dao.DictionaryValuesDAO;
import com.view.Console;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
@Repository
public class DictionaryValuesDAOImpl implements DictionaryValuesDAO {
    @Autowired
    private JdbcConfig config;

    private final String aCoupleExists = "This pairing exists";
    private final String p = "No matches with the template were found";

    private WordsDAOImpl dao;



//вывод всех
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
//поиск
    @Override
    public MeaningsLyingInTheDictionary getLineOfDictionary(String key) {
        Statement stat = config.getStat();
        MeaningsLyingInTheDictionary ofDictionaries;
        try {
            PreparedStatement result = stat.getConnection().prepareStatement("select word,translation  from dictionary_values where word = ? ");
            result.setString(1, key);
            ResultSet resultSet = result.executeQuery();
            ofDictionaries = new MeaningsLyingInTheDictionary(resultSet.getString("word"), resultSet.getString("translation"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ofDictionaries;
    }

    @Override
    public String removeRecord(String key) {
        Statement statement = config.getStat();
        try {
            PreparedStatement stat = statement.getConnection().prepareStatement("delete from dictionary_values where word = ?");
            stat.setString(1, key);
            stat.executeUpdate();
        } catch (Exception e) {
            return Dictionary.NO_KEY;
        }
        return Console.KEY_DELETE;
    }


    // главный метод добавления, в котором собрано все
    @Override
    public void addingALineToDictionaryValues(String word, String translation, String dictionary, int wordId, int translationId) {
        if (valid(word, translation, dictionary)) { //вернет true , если слова подходят под шаблон
            if (checkStringsToSeeIfTheyMatch(word, translation)) { // вернет true, если совпадений нет
                addingARowToATable(word, translation, wordId, translationId); // метод записи
                System.out.println("The pair has been successfully added"); // сделала текстом, чтоб было понятнее
            } else {
                System.out.println(aCoupleExists);
            }
        } else {
            System.out.println("No matches with the template were found");
        }

    }

    private String pattern(String dictionary) {
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


    // ------ВАЛИДАЦИЯ СЛОВ НА ПАТТЕРН
    @Override
    public boolean valid(String word, String translation, String dictionary) {
        return (wordTemplate(word, dictionary) && translationTemplate(translation, dictionary));
    }

    private boolean wordTemplate(String word, String dictionary) {
        return Pattern.matches(pattern(dictionary), word);
    }

    private boolean translationTemplate(String dictionary, String translation) {
        return Pattern.matches(pattern(dictionary), translation);
    }
//------------------------------------------------


    // -----------ПРОВЕРКА ЕСТЬ ЛИ ТАКАЯ ЗАПИСЬ(без id)------ вернет true если список пустой
    @Override
    public boolean checkStringsToSeeIfTheyMatch(String word, String translation) {
        return checkingIfTheWordsMatch(word).isEmpty() && checkingIfTheTranslationMatch(translation).isEmpty();
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
    //-----------------------------------------------------

    //-------МЕТОД ДОБАВЛЕНИЯ ПАР
    //добавить в интерфейс
    private void addingARowToATable(String word, String translation, int wordId, int translationId) {
        Statement statement = config.getStat();
        try {
            PreparedStatement preparedStatement = statement.getConnection().prepareStatement("insert into dictionay_values(id_words, word, translation) values (?, ?, ?)");
            preparedStatement.setInt(1, dao.checkTheIdForPresenceInTheTableWords(wordId, translationId));
            preparedStatement.setString(2, word);
            preparedStatement.setString(3, translation);
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}