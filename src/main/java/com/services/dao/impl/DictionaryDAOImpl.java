package com.services.dao.impl;

import com.config.JdbcConfig;
import com.model.dto.ListOfDictionaries;
import com.services.dao.DictionaryDao;
import org.springframework.beans.factory.annotation.Autowired;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DictionaryDAOImpl implements DictionaryDao {

    private   JdbcConfig jdbcConfig;
    public DictionaryDAOImpl(JdbcConfig jdbcConfig) {
        this.jdbcConfig = jdbcConfig;
    }



    @Override
    public List<ListOfDictionaries> getListOfDictionary() {
    Statement stat= jdbcConfig.getStat();
    ArrayList<ListOfDictionaries> dictionary = new ArrayList<>();
        try {
            ResultSet resultSet = stat.executeQuery("select * from dictionaries");
            while (resultSet.next()){
            dictionary.add(new ListOfDictionaries(resultSet.getLong("id"), resultSet.getString("dictionary"),resultSet.getString("template") ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dictionary ;
    }

    @Override
    public ListOfDictionaries getLineOfDictionary(Long id) {
        Statement stat = jdbcConfig.getStat();
        ListOfDictionaries ofDictionaries;
        try {
            PreparedStatement result = stat.getConnection().prepareStatement("select * from dictionaries where id = ?");
            result.setLong(1, id);
            ResultSet  resultSet = result.executeQuery();
             ofDictionaries = new ListOfDictionaries(resultSet.getLong("id"), resultSet.getString("dictionary"), resultSet.getString("template"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return ofDictionaries;
    }
}
