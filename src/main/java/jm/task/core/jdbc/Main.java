package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDao userDao = new UserDaoJDBCImpl(new Util());
        User user = new User();
        userDao.createUsersTable();
        userDao.saveUser("Freddy", "Krueger", (byte) 40);
        userDao.saveUser("Dexter", "Morgan", (byte) 33);
        userDao.saveUser("Norman", "Bates", (byte) 27);
        userDao.saveUser("Lizzy", "Borden", (byte) 19);
        user.toString();
        userDao.removeUserById(1);
        System.out.println(userDao.getAllUsers());
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
