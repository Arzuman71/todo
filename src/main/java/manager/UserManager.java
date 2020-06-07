package manager;

import db.DBConnectionProvider;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserManager {

    private Connection connection = DBConnectionProvider.getInstance().getConnection();


    public User getByEmail(String email) {
        try {
            String sql = "SELECT * FROM `user` WHERE `email` = ? ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getUserFromResultSet(resultSet);

            }

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private User getUserFromResultSet(ResultSet resultSet) {
        try {
            return User.builder()
                    .id(resultSet.getInt("id"))
                    .name(resultSet.getString("name"))
                    .surname(resultSet.getString("surname"))
                    .password(resultSet.getString("password"))
                    .email(resultSet.getString("email"))
                    .build();
        } catch (SQLException e) {
            return null;
        }

    }

    public boolean register(User user) {
        try {
            String sql = "INSERT INTO user(`name`,surname,email,password) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void deleteUserBYid(long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?");
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }

    public User getByEmailAndPassword(String email, String password) {
        try {
            String sql = "SELECT * FROM `user` WHERE `email` = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getUserFromResultSet(resultSet);
            }

        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM `user`";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(getUserFromResultSet(resultSet));
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getById(long id) {
        User user = new User();
        String sql = "SELECT * FROM `user` WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return getUserFromResultSet(resultSet);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
