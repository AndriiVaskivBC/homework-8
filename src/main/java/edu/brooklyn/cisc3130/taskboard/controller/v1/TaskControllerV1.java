package edu.brooklyn.cisc3130.taskboard.controller.v1;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.brooklyn.cisc3130.taskboard.dto.TaskRequest;
import edu.brooklyn.cisc3130.taskboard.dto.TaskResponse;
import edu.brooklyn.cisc3130.taskboard.model.Task;
import edu.brooklyn.cisc3130.taskboard.service.TaskService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskControllerV1 {

    private final TaskService taskService;

    public TaskControllerV1(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks() {
        List<TaskResponse> tasks = taskService.getAllTasks()
                .stream()
                .map(TaskResponse::fromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Integer id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(TaskResponse.fromEntity(task));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest taskRequest) {

        Task task = convertRequestToTask(taskRequest);

        Task createdTask = taskService.createTask(task);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TaskResponse.fromEntity(createdTask));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Integer id,
            @Valid @RequestBody TaskRequest taskRequest) {

        Task task = convertRequestToTask(taskRequest);

        Task updatedTask = taskService.updateTask(id, task);

        return ResponseEntity.ok(TaskResponse.fromEntity(updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<Void> restoreTask(@PathVariable Integer id) {
        taskService.restoreTask(id);
        return ResponseEntity.noContent().build();
    }

    private Task convertRequestToTask(TaskRequest taskRequest) {
        Task task = new Task();

        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());

        task.setCompleted(
                taskRequest.getCompleted() != null
                        ? taskRequest.getCompleted()
                        : false
        );

        task.setPriority(
                Task.Priority.valueOf(
                        taskRequest.getPriority() != null
                                ? taskRequest.getPriority().toUpperCase()
                                : "MEDIUM"
                )
        );

        return task;
    }
}