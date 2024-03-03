package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Freddy", "Krueger", (byte) 40);
        userService.saveUser("Dexter", "Morgan", (byte) 33);
        userService.saveUser("Norman", "Bates", (byte) 27);
        userService.saveUser("Lizzy", "Borden", (byte) 19);
        userService.toString();
        userService.removeUserById(1);
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
