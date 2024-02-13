package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import java.sql.SQLException;
import java.util.Scanner;

public class Util {

    public static String getDataFromUser(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);

        while (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return null;
    }

    public static int getIntFromUser(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(prompt);

        while (scanner.hasNextInt()) {
            return scanner.nextInt();
        }
        return 0;
    }

    public static void printData(User user) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("user id: ").append(user.getId()).append("\n");
        stringBuilder.append("user name: ").append(user.getUserName()).append("\n");
        stringBuilder.append("user email: ").append(user.getEmail()).append("\n");
        System.out.println(stringBuilder);
    }
    public static void printData(User[] users) {
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : users) {
            stringBuilder.append("id: ").append(user.getId()).append(", ");
            stringBuilder.append("user name: ").append(user.getUserName()).append(", ");
            stringBuilder.append("email: ").append(user.getEmail()).append(", ").append("\n");
        }
        System.out.println(stringBuilder);
    }

    public static User createUser() throws SQLException {
        User user =  new User();
        user.setUserName(getDataFromUser("Please provide user name:"));
        user.setEmail(getDataFromUser("Please provide email:"));
        user.setPassword(getDataFromUser("Please provide password (max 60 chars):"));
        UserDao userDao = new UserDao();

        try {
            user = userDao.create(user);
            System.out.printf("New user with id %s created%n", user.getId());
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static User getUserById() throws SQLException {
        UserDao userDao = new UserDao();
        try {
            return userDao.read(getIntFromUser("Please provide user id:"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void removeUserById() {
        UserDao userDao = new UserDao();
        try {
            userDao.delete(getIntFromUser("Please provide user id:")); ;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static User[] getAllUsers() {
        UserDao userDao = new UserDao();
        try {
            return userDao.findAll();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void updateUser() {
        UserDao userDao = new UserDao();
        try {
            User user = getUserById();
            if (user != null) {
                userDao.update(user);
            } else {
                System.out.println("User not found!");
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
