package com.todo.todo_backend.controller;


import com.todo.todo_backend.dto.CreateSubTaskDTO;
import com.todo.todo_backend.models.SubTask;
import com.todo.todo_backend.models.Todo;
import com.todo.todo_backend.repository.SubtaskRepository;
import com.todo.todo_backend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class SubTaskController {
    @Autowired
    private SubtaskRepository subtaskRepository;

    @Autowired
    private TodoRepository todoRepository;

    @PostMapping("/addSubTask")
    public Todo createSubTask(@RequestBody CreateSubTaskDTO dto) {
        System.out.println("Received request to create SubTask for todoId: " + dto.getTodoId());
        System.out.println("Received request to create SubTask : " + dto.getTask());

        Todo todo = todoRepository.findById(dto.getTodoId())
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + dto.getTodoId()));

        SubTask subTask = new SubTask();
        subTask.setTask(dto.getTask());
        subTask.setDone(false);
        subTask.setTodo(todo);  // link subtask to parent

        // Optional: if cascade is set on Todo -> SubTask
        todo.getSubtask().add(subTask);

        Todo saved = todoRepository.save(todo);
        System.out.println("Saved subtask to DB under Todo: " + saved.getId());
        return saved;
    }

    @PutMapping("/toggleSubTask")
    public List<Todo> toggleSubTask(@RequestBody Map<String, Long> payload) {
        Long taskId = payload.get("taskId");
        System.out.println("task id ===> " + taskId);
        SubTask subTask = subtaskRepository.findById(taskId).orElseThrow();
        subTask.setDone(!subTask.isDone());
        subtaskRepository.save(subTask);
        return todoRepository.findAll();
    }
}
