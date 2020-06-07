
import manager.TodoManager;
import manager.UserManager;

import model.ToDoStatus;
import model.Todo;
import model.User;


import java.sql.Array;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Scanner;

public class Main implements Command {
    private static User currentUser = null;
    private static Scanner scanner = new Scanner(System.in);
    private static UserManager userManager = new UserManager();
    private static TodoManager todoManager = new TodoManager();
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd-HH-mm-ss");

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
        System.out.println("Please input email,password");
        String loginStr = scanner.nextLine();
        String[] loginArr = loginStr.split(",");
        User user = userManager.getByEmailAndPassword(loginArr[0], loginArr[1]);
        if (user != null) {
            currentUser = user;
            System.out.println("welcome" + currentUser.getName());
            loginCommand();
        }

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
                    todoManager.myToDoList(ToDoStatus.IN_PROGRESS, currentUser.getId());
                    break;
                case MY_FINISHED_LIST:
                    todoManager.myToDoList(ToDoStatus.FINISHED, currentUser.getId());
                    break;
                case MY_TODO_LIST:
                    todoManager.myToDoList(ToDoStatus.TODO, currentUser.getId());
                    break;
                case ADD_TODO:
                    addTodo();
                    break;
                case DELETE_TODO_BY_ID:
                    deleteTodoById();
                    break;
                case MY_LIST:
                    todoManager.getAllToDosByUser(currentUser.getId());
                    break;
                case DELETE_USER:
                    userManager.deleteUserBYid(currentUser.getId());
                    break;
                default:
                    System.out.println("Wrong command!");
            }
        }
    }

    private static void deleteTodoById() {
        todoManager.getAllToDosByUser(currentUser.getId());
        System.out.println("please input todo id");
        long id = Long.parseLong(scanner.nextLine());
        Todo byId = todoManager.getById(id);
        if (byId.getUser().getId()==currentUser.getId()){
            todoManager.deleteTodoBYid(id);
        }else {
            System.out.println("Wrong id");
        }
    }


    private static void addTodo() {

        System.out.println("please input todo title,deadline(yyy-MM-dd-HH-mm-ss");
        String toDoDataStr = scanner.nextLine();
        String[] split = toDoDataStr.split(",");
        Todo todo = new Todo();
        try {
            String title = split[0];
            todo.setTitle(title);
            try {
                if (split[1] != null) {
                    todo.setDeadline(sdf.parse(split[1]));
                }
            } catch (IndexOutOfBoundsException e) {

            } catch (ParseException e) {
                System.out.println("pleas input gate by this (yyyy-MM-dd-HH-mm-ss");
            }
            todo.setStatus(ToDoStatus.TODO);
            todo.setUser(currentUser);
            if (todoManager.create(todo)) {
                System.out.println(" your todo added");
            }
        } catch (IllegalArgumentException e) {
        }
    }

    private static void registerUser() throws SQLException {
        System.out.println(" please input User` name, surname, email,password");

        try {
            String string = scanner.nextLine();
            String[] userDataArr = string.split(",");
            User userFromStorage = userManager.getByEmail(userDataArr[2]);
            if (userFromStorage == null) {
                User user = new User();
                userManager.register(User.builder()
                        .name(userDataArr[0])
                        .surname(userDataArr[1])
                        .email(userDataArr[2])
                        .password(userDataArr[3])
                        .build()
                );
                System.out.println(" User was successfully added");
            } else {
                registerUser();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            registerUser();
        }
    }

}
