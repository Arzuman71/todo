package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

    private int id;
    private Date created_date;
    private String deadline;
    private Status status;
    private User user;

    public Todo( String deadline, Status status) {
        this.deadline = deadline;
        this.status = status;
    }
}
