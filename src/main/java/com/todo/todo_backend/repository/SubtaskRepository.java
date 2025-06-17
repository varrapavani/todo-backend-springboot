package com.todo.todo_backend.repository;

import com.todo.todo_backend.models.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubtaskRepository extends JpaRepository<SubTask, Long> {
}
