package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
            userService.createUsersTable();
            userService.saveUser("Yuriy", "Korobaev", (byte) 58);
            userService.saveUser("Anatoliy", "Volkov", (byte) 30);
            userService.saveUser("Alina", "Chayka", (byte) 28);
            userService.saveUser("Dinara", "Akjigitova", (byte) 34);
            List<User> userList = userService.getAllUsers();
            printTableOut();
            printTableBody(userList);
            userService.cleanUsersTable();
            userService.dropUsersTable();
    }

    public static void printTableOut() {
        System.out.println(" ID |   Name   |  Last Name  | Age");
        System.out.println(" ---------------------------------");
    }

    public static void printTableBody(List<User> users) {
        for (User user : users) {
            System.out.println(user);
        }
    }
}
