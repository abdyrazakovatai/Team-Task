package peaksoft.java.api.task;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import peaksoft.java.dto.request.TaskRequest;
import peaksoft.java.dto.response.task.AssignTaskResponse;
import peaksoft.java.dto.response.SimpleResponse;
import peaksoft.java.dto.response.task.TaskResponse;
import peaksoft.java.dto.response.task.TasksResponse;
import peaksoft.java.enums.Status;
import peaksoft.java.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskApi {
    final TaskService taskService;

    //todo POST /api/tasks - Жаңы тапшырма түзүү
    @Secured({"ADMIN","MANAGER","USER"})
    @PostMapping
    public SimpleResponse addTask(@RequestBody TaskRequest taskRequest) {
        return taskService.addTask(taskRequest);
    }

    //todo GET /api/tasks - Тапшырмалардын тизмесин алуу (фильтрация жана пагинация колдоо менен)
    @Secured({"ADMIN","MANAGER","USER"})
    @GetMapping
    public List<TasksResponse> tasks(@RequestParam int page,
                                     @RequestParam int size) {
        return taskService.tasks(page, size);
    }

    //todo GET /api/tasks/{id} - Тапшырма жөнүндө толук маалымат алуу
    @Secured({"ADMIN","MANAGER","USER"})
    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    //todo PUT /api/tasks/{id} - Тапшырманы жаңыртуу
    @Secured({"ADMIN","MANAGER","USER"})
    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id,
                                   @RequestBody TaskRequest taskRequest) {
        return taskService.updateTask(id, taskRequest);
    }

    //todo DELETE /api/tasks/{id} - Тапшырманы өчүрүү
    @Secured({"ADMIN","MANAGER","USER"})
    @DeleteMapping("/{id}")
    public SimpleResponse deleteTask(@PathVariable Long id) {
        return taskService.delete(id);
    }

    //todo PATCH /api/tasks/{id}/status - Тапшырманын статусун өзгөртүү
    @Secured({"ADMIN","MANAGER","USER"})
    @PatchMapping("/{id}/status")
    public TaskResponse changeSt(@PathVariable Long id,
                                 @RequestParam Status status) {
        return taskService.status(id, status);
    }

    //todo PATCH /api/tasks/{id}/assign - Тапшырманы башка колдонуучуга дайындоо
    @Secured({"ADMIN","MANAGER","USER"})
    @PatchMapping("/{taskId}/assign")
    public AssignTaskResponse assign(@PathVariable Long taskId,
                                     @RequestParam Long userId) {
        return taskService.assign(taskId, userId);
    }
}