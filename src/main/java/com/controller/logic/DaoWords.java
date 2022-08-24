package com.controller.logic;

import com.config.JdbcConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DaoWords {

    private JdbcConfig jdbcConfig;
    private final String QUERY = "SELECT t1.id, t1.dictionary as word, dictionaries.dictionary as translation " +
            "from (SELECT  words.id,  dictionaries.dictionary, words.translation " +
            "from words " +
            "join dictionaries on words.word = dictionaries.id) as t1 " +
            "join dictionaries  on t1.translation = dictionaries.id";

    public DaoWords(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }



    public List<TableWords> gybh() {
        Statement statement = jdbcConfig.getStat();
        ArrayList<TableWords> dictionary = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(QUERY);
            while (resultSet.next()) {
                dictionary.add(new TableWords(resultSet.getLong("id"), resultSet.getString("word"), resultSet.getString("translation")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dictionary;
    }

    class TableWords {
        private Long id;
        private String word;
        private String translation;

        public TableWords(Long id, String word, String translation) {
            this.id = id;
            this.word = word;
            this.translation = translation;
        }
    }
}
