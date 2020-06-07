public interface Command {
    int EXIT = 0;
    int REGISTER = 1;
    int LOGIN = 2;

    int LOGOUT = 0;
    int ADD_TODO = 1;
    int MY_LIST = 2;
    int MY_IN_PROGRESS_LIST = 3;
    int MY_FINISHED_LIST = 4;
    int MY_TODO_LIST = 5;
    int CHANGE_TODO_STATUS = 5;
    int DELETE_TODO_BY_ID = 6;

    int DELETE_USER = 7;

    static void printCommand1() {
        System.out.println(EXIT + " FOR EXIT");
        System.out.println(REGISTER + " FOR REGISTER");
        System.out.println(LOGIN + " FOR LOGIN");

    }

    static void printCommand2() {
        System.out.println(LOGOUT + " FOR LOGOUT");
        System.out.println(MY_IN_PROGRESS_LIST + " FOR MY_IN_PROGRESS_LIST");
        System.out.println(MY_FINISHED_LIST + " FOR MY_FINISHED_LIST");
        System.out.println(MY_TODO_LIST + " MY_TODO_LIST");
        System.out.println(ADD_TODO + " FOR ADD_TODO");
        System.out.println(CHANGE_TODO_STATUS + " FOR CHANGE_TODO_STATUS");
        System.out.println(DELETE_TODO_BY_ID + " FOR DELETE_TODO_BY_ID");
        System.out.println(MY_LIST + " FOR MY_LIST");
        System.out.println(DELETE_USER + " FOR DELETE_USER");
    }
}
