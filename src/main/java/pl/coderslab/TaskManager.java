package pl.coderslab;

import java.sql.SQLException;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) throws SQLException {

        String data;
        boolean AppRunFlag = true;
        while (AppRunFlag) {
            data = Util.getDataFromUser("Select option by typing option number:\n[1] create user\n[2] get user by id\n[3] remove user by id\n[4] get all users\n[5] exit");
            switch (data) {
                case "1":
                    //insert
                    Util.createUser();
                    break;
                case "2":
                    // get user by id
                    System.out.println("user with id returned");
                    break;
                case "3":
                    // remove user by id
                    System.out.println("user removed");
                    break;
                case "4":
                    // get all users
                    System.out.println("returned all users");
                    break;
                case "5":
                    AppRunFlag = false;
                    System.out.println("App closed!");
                default:

            }
        }
    }
}
