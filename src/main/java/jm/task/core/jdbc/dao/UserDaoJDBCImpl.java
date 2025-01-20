package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {}

    @Override
    public void createUsersTable() {
        executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50), " +
                "lastName VARCHAR(50), " +
                "age TINYINT)");
    }

    @Override
    public void dropUsersTable() {
        executeUpdate("DROP TABLE IF EXISTS users");
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        executePreparedStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)", ps -> {
            try {
                ps.setString(1, name);
                ps.setString(2, lastName);
                ps.setByte(3, age);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        System.out.println("User с именем " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        executePreparedStatement("DELETE FROM users WHERE id = ?", ps -> {
            try {
                ps.setLong(1, id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        executeQuery("SELECT * FROM users", rs -> {
            try {
                while (rs.next()) {
                    users.add(new User(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getString("lastName"),
                            rs.getByte("age")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        return users;
    }

    @Override
    public void cleanUsersTable() {
        executeUpdate("TRUNCATE TABLE users");
    }

    private void executeUpdate(String sql) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executePreparedStatement(String sql, Consumer<PreparedStatement> statementConsumer) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            statementConsumer.accept(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void executeQuery(String sql, Consumer<ResultSet> resultSetConsumer) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            resultSetConsumer.accept(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}