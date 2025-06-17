package com.todo.todo_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoDTO {
    private Long id;
    private String todo;
    private List<SubTaskDTO> subtask;
    private int incrementor;
}
