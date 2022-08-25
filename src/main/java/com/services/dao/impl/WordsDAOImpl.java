package com.services.dao.impl;

import com.config.JdbcConfig;
import com.model.dto.IntermediateTable;
import com.services.dao.WordsDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WordsDAOImpl implements WordsDAO {
    private JdbcConfig jdbcConfig;

    public List<IntermediateTable> getIntermediateTable() {
        Statement f = jdbcConfig.getStat();
        ArrayList<IntermediateTable> a = new ArrayList();
        try {
            ResultSet resultSet = f.executeQuery("select * from words");
            while (resultSet.next()) {
                a.add(new IntermediateTable(resultSet.getLong("id"), resultSet.getLong("word"), resultSet.getLong("translation")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return a;
    }

    public Integer checkTheIdForPresenceInTheTableWords(Integer wordId, Integer translationId) {
        int stringID;
        try {
            Statement statement = jdbcConfig.getStat();
            PreparedStatement preparedStatement = statement.getConnection().prepareStatement("select id from words where word = ? and translation = ?");
            preparedStatement.setInt(1, wordId);
            preparedStatement.setInt(2, translationId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                stringID = resultSet.getInt("id");
            } else {
                stringID = -1;
            }

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return stringID;
    }

    private Integer addingANewLine(Integer wordId, Integer translationId) {
        Statement statement = jdbcConfig.getStat();

        try {
            PreparedStatement preparedStatement = statement.getConnection().prepareStatement("insert into words(word, translation) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, wordId);
            preparedStatement.setInt(2, translationId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Error");
            }
            ResultSet generateKey = statement.getGeneratedKeys();
            if (generateKey.next()) {
                return generateKey.getInt(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
