package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final Logger logger = Logger.getLogger(Util.class.getName());
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static final String DRV_KEY = "db.Drv";
    private static Connection connection;

    static {
        loadDriver();
    }
    private static void loadDriver() {
        try {
            Class.forName(PropertiesUtil.get(DRV_KEY));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public Util() {}
    public static Connection connOpen () {
        try {
            connection = DriverManager.getConnection(PropertiesUtil.get(URL_KEY),PropertiesUtil.get(USERNAME_KEY),PropertiesUtil.get(PASSWORD_KEY));
            logger.log(Level.INFO,"Connection successful");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
