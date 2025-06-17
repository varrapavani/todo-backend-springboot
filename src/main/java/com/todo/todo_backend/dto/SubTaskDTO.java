package com.todo.todo_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubTaskDTO {
    private Long taskId;
    private String task;
    private boolean done;
}