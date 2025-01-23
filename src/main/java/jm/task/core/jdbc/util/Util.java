package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import jm.task.core.jdbc.model.User;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/test2";
    private static final String USER = "root";
    private static final String PASSWORD = "FuN-kYf-Ml51-21";

    private static final SessionFactory sessionFactory = new Configuration()
            .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect")
            .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
            .setProperty("hibernate.connection.url", URL)
            .setProperty("hibernate.connection.username", USER)
            .setProperty("hibernate.connection.password", PASSWORD)
            .setProperty("hibernate.show_sql", "true")
            .setProperty("hibernate.hbm2ddl.auto", "update")
            .addAnnotatedClass(User.class)
            .buildSessionFactory();

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
