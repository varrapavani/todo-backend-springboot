package com.todo.todo_backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class SubTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    private String task;
    private boolean done;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    @JsonBackReference
    private Todo todo;

    public SubTask() {
    }

    public SubTask(Long taskId, String task, boolean done) {
        this.taskId = taskId;
        this.task = task;
        this.done = done;
    }
}
