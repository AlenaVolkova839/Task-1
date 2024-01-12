package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static String dbURL = "jdbc:postgresql://localhost:5432/postgres";
    private static String dbUserName = "postgres";
    private static String dbPassword = "0313";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbURL, dbUserName, dbPassword);
    }
}
