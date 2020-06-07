package manager;

import db.DBConnectionProvider;
import model.ToDoStatus;
import model.Todo;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class TodoManager {
    private Connection connection = DBConnectionProvider.getInstance().getConnection();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    private UserManager userManager = new UserManager();

    public boolean create(Todo todo) {
        try {
            String sql = "Insert into todo(title,deadline,status,user_id) values(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, String.valueOf(todo.getTitle()));
            if (todo.getDeadline() != null) {
                preparedStatement.setString(2, sdf.format(todo.getDeadline()));
            } else {
                preparedStatement.setString(2, null);

            }
            preparedStatement.setString(3, todo.getStatus().name());
            preparedStatement.setLong(4, todo.getUser().getId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                todo.setId(id);
            }
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    public Todo getById(long id) {
        try {
            String sql = "SELECT * FROM `todo` WHERE `id` =" + id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return getTodoFromResultSet(resultSet);
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Todo getTodoFromResultSet(ResultSet resultSet) {
        try {
            return Todo.builder()
                    .id(resultSet.getInt(1))
                    .title(resultSet.getString(2))
                    .deadline(resultSet.getString(3) == null ? null : sdf.parse(resultSet.getString(3)))
                    .status(ToDoStatus.valueOf(resultSet.getString(4)))
                    .user(userManager.getById(resultSet.getLong(5)))
                    .created_date(sdf.parse(resultSet.getString(6)))
                    .build();
        } catch (SQLException e) {
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }


    public void myToDoList(ToDoStatus status, long userId) throws SQLException {

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM todo WHERE `status`='" + status + "' AND user_id=" + userId);
        while (resultSet.next()) {
            System.out.println("___________________");
            System.out.println("id " + resultSet.getString("id"));
            System.out.println("title " + resultSet.getString("title"));
            System.out.println("created_date " + resultSet.getString("created_date"));
            System.out.println("deadline " + resultSet.getString("deadline"));
            System.out.println("status " + resultSet.getString("status"));
        }
    }

    public boolean update(long Id, ToDoStatus status) {
        try {
            String sql = "UPDATE `todo` SET `status` = ? WHERE `id` = ?";
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, Id);
            preparedStatement.setString(1, status.name());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public void deleteTodoBYid(long id) {
        try {
            String sql = "DELETE FROM todo WHERE id =" + id;
            Statement preparedStatement = connection.createStatement();
            preparedStatement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAllToDosByUser(long userId) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM todo WHERE user_id=" + userId);
            while (resultSet.next()) {
                System.out.println("___________________");
                System.out.println("id " + resultSet.getString(1));
                System.out.println("title " + resultSet.getString(2));
                System.out.println(" deadline" + resultSet.getString(3));
                System.out.println("status  " + resultSet.getString(4));
                System.out.println("user_id" + resultSet.getString(5));
                System.out.println("created_date" + resultSet.getString(6));
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
