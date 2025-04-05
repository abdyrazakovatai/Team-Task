package peaksoft.java.api.task;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.java.dto.request.TaskRequest;
import peaksoft.java.dto.response.SimpleResponse;
import peaksoft.java.dto.response.TaskResponse;
import peaksoft.java.dto.response.TasksResponse;
import peaksoft.java.service.TaskService;
import peaksoft.java.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskApi {
    final UserService userService;
    final TaskService taskService;

//    todo   POST /api/tasks - Жаңы тапшырма түзүү
//    todo GET /api/tasks - Тапшырмалардын тизмесин алуу (фильтрация жана пагинация колдоо менен)
//    todo GET /api/tasks/{id} - Тапшырма жөнүндө толук маалымат алуу
//    PUT /api/tasks/{id} - Тапшырманы жаңыртуу
//    DELETE /api/tasks/{id} - Тапшырманы өчүрүү
//    PATCH /api/tasks/{id}/status - Тапшырманын статусун өзгөртүү
//    PATCH /api/tasks/{id}/assign - Тапшырманы башка колдонуучуга дайындоо

    @PostMapping
    public SimpleResponse addTask(@RequestBody TaskRequest taskRequest){
        return taskService.addTask(taskRequest);
    }

    @GetMapping
    public List<TasksResponse> tasks (@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "0") int size){
        return taskService.tasks(page,size);
    }
    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable Long id){
        return taskService.getTask(id);
    }
    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id,
                                   @RequestBody TaskRequest taskRequest){
        return taskService.updateTask(id,taskRequest);
    }

}
