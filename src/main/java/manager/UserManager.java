package manager;

import db.DBConnectionProvider;
import model.User;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserManager {

    private Connection connection;

    public UserManager() {

        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void addUser(User user) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("Insert into user(name,surname,password,phone_number) values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setString(4, user.getPhoneNumber());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            user.setId(id);
        }
    }

    public User login(String name, String password) throws SQLException {


        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT *FROM `user`");
        while (resultSet.next()) {

            if (name.equals(resultSet.getString("name"))  && password.equals( resultSet.getString("password"))) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setPassword(resultSet.getString("password"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                return user;

            }
        }
        return null;
    }

    public void deleteUserBYid(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

}
