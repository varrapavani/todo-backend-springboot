package com.todo.todo_backend.dto;

import lombok.Data;

@Data
public class CreateSubTaskDTO {
    private Long todoId;
    private String task;
}
