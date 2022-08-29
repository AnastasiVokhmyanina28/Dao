package com.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class JdbcConfig {
    public Statement getStat() {
        return stat;
    }
    private final Statement stat = connect();

    private Statement connect() {
        String url = "jdbc:postgresql://localhost:5432/dictionarydatabase";
        String user = "postgres";
        String password = "TZU3UMmo";
        Statement stat = null;
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            stat = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stat;
    }
}