package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Todo {

    private long id;
    private String title;
    private Date deadline;
    private ToDoStatus status;
    private Date created_date;
    private User user;


}
