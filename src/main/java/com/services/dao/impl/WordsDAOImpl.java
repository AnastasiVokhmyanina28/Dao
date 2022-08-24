package com.services.dao.impl;

import com.config.JdbcConfig;
import com.model.dto.IntermediateTable;
import com.services.dao.WordsDAO;

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
}
