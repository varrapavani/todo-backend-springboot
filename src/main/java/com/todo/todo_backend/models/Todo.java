package com.todo.todo_backend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String todo;

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<SubTask> subtask = new ArrayList<>();


    public Todo() {
    }

    public Todo(Long id, String todo, List<SubTask> subtask) {
        this.id = id;
        this.todo = todo;
        this.subtask = subtask;
    }


}
