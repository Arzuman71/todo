package manager;

import db.DBConnectionProvider;
import model.Todo;

import java.sql.*;


public class TodoManager {
    private Connection connection;

    public TodoManager() {

        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void addTodo(Todo todo, int userId) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("Insert into todo(deadline,status,user_id) values(?,?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, String.valueOf(todo.getDeadline()));
        preparedStatement.setString(2, String.valueOf(todo.getStatus()));
        preparedStatement.setInt(3, userId);
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            int id = resultSet.getInt(1);
            todo.setId(id);
        }
    }

    public void getAlltodo(int userId) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo WHERE user_id = " + userId);

        while (resultSet.next()) {
            System.out.println("___________________");
            System.out.println("id " + resultSet.getString("id"));
            System.out.println("created_date " + resultSet.getString("created_date"));
            System.out.println("deadline " + resultSet.getString("deadline"));
            System.out.println("status " + resultSet.getString("status"));
        }
    }

    public void myInProgressList(int statusNumber, int userId) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo WHERE `status`=" + statusNumber + " AND user_id="+userId);
        while (resultSet.next()) {
            System.out.println("___________________");
            System.out.println("id " + resultSet.getString("id"));
            System.out.println("created_date " + resultSet.getString("created_date"));
            System.out.println("deadline " + resultSet.getString("deadline"));
            System.out.println("status " + resultSet.getString("status"));
        }


    }


    public void changeTodoStatus(int todoId, int statusNumber) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `todo`.`todo` SET `status` = " +
                statusNumber + " WHERE `id` = " + todoId);
        //  preparedStatement.setInt(1, todoId);
        preparedStatement.executeUpdate();


    }

    public void deleteTodoBYid(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM todo WHERE id =?");
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }
}