package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            String sql = """
                    CREATE TABLE IF NOT EXISTS users (  
                    id SERIAL PRIMARY KEY,             
                    name VARCHAR (15) NOT NULL,       
                    lastName VARCHAR(15) NOT NULL,    
                    age INTEGER NOT NULL)             
                    """;
            statement.executeUpdate(sql);
        }
    }

    public void dropUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "DROP TABLE IF EXISTS users"; //удаляем таблицу пользователей если она существует
            statement.executeUpdate(sql);
        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (Connection connection = Util.getConnection()) {
            String sql = "INSERT INTO users (name, lastName, age) VALUES(?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);// Устанавливается значение параметра с индексом 1 значение параметра name будет подставлено на место первого символа ? в SQL-запросе.
                statement.setString(2, lastName);
                statement.setByte(3, age);
                statement.executeUpdate();
            }
        }

    }

    public void removeUserById(long id) throws SQLException {
        try (Connection connection = Util.getConnection()) {
            String sql = "DELETE FROM users WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            String sql = "SELECT * FROM users";  //получаем всю инфу из таблицы
            try (Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String lastName = resultSet.getString("lastName");
                    byte age = resultSet.getByte("age");
                    User user = new User(name, lastName, age);
                    user.setId(id);
                    users.add(user);
                }
            }
        }
        return users;
    }

    public void cleanUsersTable() throws SQLException {
        try (Connection connection = Util.getConnection()) {
            String sql = "TRUNCATE TABLE users";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
            }
        }

    }
}
