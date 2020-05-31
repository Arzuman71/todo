package manager;

import db.DBConnectionProvider;
import model.User;

import java.sql.*;


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

    public User login(String name, String password) {
        try {
            User user = new User();
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM `user` WHERE `name` = '" + name + "' AND `password` = '" + password + "'");

            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setSurname(resultSet.getString("surname"));
                user.setPassword(resultSet.getString("password"));
                user.setPhoneNumber(resultSet.getString("phone_number"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteUserBYid(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

}
