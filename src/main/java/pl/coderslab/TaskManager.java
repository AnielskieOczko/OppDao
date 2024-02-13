package pl.coderslab;

import pl.coderslab.entity.User;

import java.sql.SQLException;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) throws SQLException {
        User user;
        String data;
        boolean AppRunFlag = true;
        while (AppRunFlag) {
            data = Util.getDataFromUser("Select option by typing option number:\n[1] create user\n[2] get user by id\n[3] remove user by id\n[4] get all users\n[5] update user by id\n[6] exit");
            switch (data) {
                case "1":
                    //insert
                    user = Util.createUser();
                    break;
                case "2":
                    // get user by id
                    user = Util.getUserById();
                    if (user != null) {
                        Util.printData(user);
                    }
                    break;
                case "3":
                    // remove user by id
                    Util.removeUserById();
                    break;
                case "4":
                    // get all users
                    User[] users = Util.getAllUsers();
                    if (users != null) {
                        Util.printData(users);
                    }

                    break;
                case "5":
                    Util.updateUser();
                    break;
                case "6":
                    AppRunFlag = false;
                    System.out.println("App closed!");
                    break;
                default:

            }
        }
    }
}
