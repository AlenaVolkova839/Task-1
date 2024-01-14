package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static String dbURL = "jdbc:postgresql://localhost:5432/postgres";
    private static String dbUserName = "postgres";
    private static String dbPassword = "0313";

    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(dbURL, dbUserName, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Настройки подключения к базе данных
                configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres");
                configuration.setProperty("hibernate.connection.username", "postgres");
                configuration.setProperty("hibernate.connection.password", "0313");
                configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");

                // Настройки Hibernate
                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                configuration.setProperty("hibernate.show_sql", "true");
                configuration.setProperty("hibernate.hbm2ddl.auto", "update");

                // Добавление классов сущностей
                configuration.addAnnotatedClass(User.class);
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
