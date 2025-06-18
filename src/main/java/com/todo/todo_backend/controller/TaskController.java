package com.todo.todo_backend.controller;

import com.todo.todo_backend.dto.SubTaskDTO;
import com.todo.todo_backend.dto.TodoDTO;
import com.todo.todo_backend.models.Todo;
import com.todo.todo_backend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {
    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/todos")
    public List<TodoDTO> getAllTodos() {
        List<Todo> all = todoRepository.findAll();

        System.out.println("🔸 After Fetching from DB:");
        all.forEach(todo -> {
            System.out.println("Todo: " + todo.getTodo());
            todo.getSubtask().forEach(sub ->
                    System.out.println("  - SubTask: " + sub.getTask() + ", Done: " + sub.isDone())
            );
        });

        List<TodoDTO> result = all.stream().map(todo -> {
            List<SubTaskDTO> subtasks = todo.getSubtask().stream().map(sub ->
                    new SubTaskDTO(sub.getTaskId(), sub.getTask(), sub.isDone())
            ).toList();

            return new TodoDTO(todo.getId(), todo.getTodo(), subtasks, todo.getSubtask().size());
        }).toList();

        System.out.println("🟢 Before Sending API Response:");
        result.forEach(dto -> {
            System.out.println("TodoDTO: " + dto.getTodo());
            dto.getSubtask().forEach(sub ->
                    System.out.println("  - SubTaskDTO: " + sub.getTask() + ", Done: " + sub.isDone())
            );
        });

        return result;
    }

    @PostMapping("/addTask")
    public Todo createTodo(@RequestBody TodoDTO dto) {
        System.out.println("Received request to create Todo: " + dto.getTodo());

        Todo todo = new Todo();
        todo.setTodo(dto.getTodo());

        Todo savedTodo = todoRepository.save(todo);

        System.out.println("Saved Todo to DB: " + savedTodo);

        return savedTodo;
    }

    @DeleteMapping("/deleteTodo/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        if (!todoRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo not found");
        }

        todoRepository.deleteById(id);  // JPA will handle cascade delete
        return ResponseEntity.ok("Todo and its subtasks deleted successfully");
    }
    // write code for delete subtask


}
