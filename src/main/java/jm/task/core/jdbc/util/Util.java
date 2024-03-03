package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final Logger logger = Logger.getLogger(Util.class.getName());

    static {
        loadDriver();
    }
    private static void loadDriver() {
        String drvKey = "db.Drv";
        try {
            Class.forName(PropertiesUtil.get(drvKey));
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"ERROR: driver NOT FOUND!!");
        }
    }
    public Util() {}
    public static Connection connOpen () {
        String urlKey = "db.url";
        String userNameKey = "db.username";
        String passkey = "db.password";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(PropertiesUtil.get(urlKey),PropertiesUtil.get(userNameKey),PropertiesUtil.get(passkey));
            logger.log(Level.INFO,"Connection successful");
        } catch (SQLException e) {
            logger.log(Level.SEVERE,"ERROR: Connection has lost!!");
        }
        return connection;
    }
}
