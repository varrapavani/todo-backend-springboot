package com.todo.todo_backend.controller;

import com.todo.todo_backend.dto.CreateSubTaskDTO;
import com.todo.todo_backend.dto.SubTaskDTO;
import com.todo.todo_backend.dto.TodoDTO;
import com.todo.todo_backend.models.SubTask;
import com.todo.todo_backend.models.Todo;
import com.todo.todo_backend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class TodoController {
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

}
