package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    // реализуйте настройку соеденения с БД
    public Util() {}
    private static final Logger logger = Logger.getLogger(Util.class.getName());
    private SessionFactory sessionFactory;
    public SessionFactory getSessionFactory() {
        String drv = "db.Drv";
        String url = "db.url";
        String username = "db.username";
        String password = "db.password";
        String dial = "db.dial";
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER, PropertiesUtil.get(drv));
                settings.put(Environment.URL, PropertiesUtil.get(url));
                settings.put(Environment.USER, PropertiesUtil.get(username));
                settings.put(Environment.PASS, PropertiesUtil.get(password));
                settings.put(Environment.DIALECT, PropertiesUtil.get(dial));
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "none");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                logger.info("Session factory created successfully.");
            } catch (Exception e) {
                logger.severe("Problem creating session factory: " + e.getMessage());
                throw new RuntimeException("Problem creating session factory", e);
            }
        }
        return sessionFactory;
    }

    static {
        loadDriver();
    }
    private static void loadDriver() {
        String drvKey = "db.url";
        try {
            Class.forName(PropertiesUtil.get(drvKey));
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE,"ERROR: driver NOT FOUND!!");
        }
    }

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
