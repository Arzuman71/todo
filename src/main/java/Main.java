
import lombok.SneakyThrows;
import manager.TodoManager;
import manager.UserManager;

import model.Status;
import model.Todo;
import model.User;


import java.sql.SQLException;
import java.util.Scanner;

public class Main implements Command {
    private static User currentUser = null;
    private static Scanner scanner = new Scanner(System.in);
    private static UserManager userManager = new UserManager();
    private static TodoManager todoManager = new TodoManager();

    public static void main(String[] args) throws SQLException {

        boolean isRun = true;
        while (isRun) {
            Command.printCommand1();
            int command1;
            try {
                command1 = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command1 = -1;
            }
            switch (command1) {
                case EXIT:
                    isRun = false;
                    break;
                case LOGIN:
                    loginUser();
                    break;
                case REGISTER:
                    registerUser();
                    break;
                default:
                    System.out.println("Wrong command!");
            }
        }
    }


    private static void loginUser() throws SQLException {
        System.out.println("Please input name,password");
        String loginstr = scanner.nextLine();
        String[] loginArr = loginstr.split(",");
        currentUser = userManager.login(loginArr[0], loginArr[1]);
        System.out.println("welcome" + currentUser.getName());
        loginCommand();


    }

    private static void loginCommand() throws SQLException {

        boolean isRun = true;
        while (isRun) {
            Command.printCommand2();
            int command2;
            try {
                command2 = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                command2 = -1;
            }
            switch (command2) {
                case LOGOUT:
                    isRun = false;
                    break;
                case MY_IN_PROGRESS_LIST:
                    todoManager.myInProgressList(Status.IN_PROGRESS, currentUser.getId());
                    break;
                case MY_FINISHED_LIST:
                    todoManager.myInProgressList(Status.FINISHED, currentUser.getId());
                    break;
                case ADD_TODO:
                    addTodo();
                    break;
                case CHANGE_TODO_STATUS:
                    todoManager.printAllTodo(currentUser.getId());
                    changeTodoStatus();
                    break;
                case DELETE_TODO_BY_ID:
                    todoManager.printAllTodo(currentUser.getId());
                    System.out.println("please input todo id");
                    int todoIdForDelete = Integer.parseInt(scanner.nextLine());
                    todoManager.deleteTodoBYid(todoIdForDelete);
                    break;
                case MY_LIST:
                    todoManager.printAllTodo(currentUser.getId());
                    break;
                case DELETE_USER:
                    userManager.deleteUserBYid(currentUser.getId());
                    break;
                default:
                    System.out.println("Wrong command!");
            }
        }
    }

    @SneakyThrows
    private static void changeTodoStatus() {
        todoManager.printAllTodo(currentUser.getId());
        System.out.println("please input todoId");
        int todoId = Integer.parseInt(scanner.nextLine());
        System.out.println("please input statusNumber");
        System.out.println("FINISHED_LIST, TODO ,IN_PROGRESS");
        Status status = Status.valueOf(scanner.nextLine().toUpperCase());
        todoManager.changeTodoStatus(todoId, status);
    }

    private static void addTodo() {
        try {
            System.out.println("please input todo name");
            String name = scanner.nextLine();
            System.out.println("Status(TODO, FINISHED, IN_PROGRESS)");
            Status status = Status.valueOf(scanner.nextLine().toUpperCase());
            System.out.println("data date `dd.MM.yyyy");
            String date = scanner.nextLine();
            Todo todo = new Todo();
            todo.setStatus(status);
            todo.setDeadline(date);
            todo.setName(name);
            todoManager.addTodo(todo, currentUser.getId());
            System.out.println("your todo added");
        } catch (IllegalArgumentException e) {
            addTodo();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void registerUser() throws SQLException {
        try {


            System.out.println("please input User` name, surname, password,phoneNumber");
            String string = scanner.nextLine();
            String[] userDataArr = string.split(",");
            User user = new User();
            userManager.addUser(User.builder()
                    .name(userDataArr[0])
                    .surname(userDataArr[1])
                    .password(userDataArr[2])
                    .phoneNumber(userDataArr[3])
                    .build()
            );
            System.out.println("User was successfully added");
        } catch (ArrayIndexOutOfBoundsException e) {
            registerUser();
        }
    }

}
