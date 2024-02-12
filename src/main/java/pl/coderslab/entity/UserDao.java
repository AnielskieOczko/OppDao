package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Arrays;

import static pl.coderslab.DbUtil.getConnection;

public class UserDao {

    private static final String CREATE_USER_QUERY = """
            INSERT INTO users (email, username, password) VALUES (?, ?, ?);
            """;

    private static final String GET_USER_BY_ID = """
            SELECT *
            FROM users
            WHERE id = ?;
            """;

    private static final String UPDATE_USER_BY_ID = """
            UPDATE users
            SET username = ?,
                email = ?,
                password = ?
            WHERE id = ?;
            """;
    private static final String DELETE_USER_BY_ID = """
            UPDATE users
            SET username = ?,
                email = ?,
                password = ?
            WHERE id = ?;
            """;
    private static final String GET_ALL_USERS = """
            SELECT *
            FROM users;
            """;

    public User create(User user) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public User read(int userId) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_USER_BY_ID);
            statement.setInt(1, userId);
            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();
            User user = new User();
            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
            }
            return user;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void update(User user) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_BY_ID);
            statement.setString(1,user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3,hashPassword(user.getPassword()));
            statement.setInt(4,user.getId());
            statement.executeUpdate();
            System.out.printf("User with id %s successfully updated%n", user.getId());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void delete(int userId) throws SQLException {
        try(Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_BY_ID);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public User[] findAll() throws  SQLException {
        User[] users = new User[0];

        try (Connection conn = getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_ALL_USERS);
            statement.executeQuery();
            ResultSet resultSet = statement.getResultSet();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));

                users = addToArray(user, users);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

        return users;
    }

    private User[] addToArray(User user, User[] users) {
        User[] tempArr = Arrays.copyOf(users, users.length + 1);
        tempArr[users.length] = user;
        return tempArr;
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
