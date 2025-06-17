package com.todo.todo_backend.repository;

import com.todo.todo_backend.models.Todo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    @EntityGraph(attributePaths = "subtask")
    List<Todo> findAll();
}
