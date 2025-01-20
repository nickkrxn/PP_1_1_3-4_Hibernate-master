package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Vasya", "Pupkin", (byte) 25);
        userService.saveUser("Ken", "Adams", (byte) 30);
        userService.saveUser("Michael", "Jackson", (byte) 55);
        userService.saveUser("Ellie", "Lastofus", (byte) 15);

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
