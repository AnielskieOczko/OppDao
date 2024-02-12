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
}
