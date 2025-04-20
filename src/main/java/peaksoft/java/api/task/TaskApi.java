//package peaksoft.java.api.task;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.web.bind.annotation.*;
//import peaksoft.java.dto.request.TaskRequest;
//import peaksoft.java.dto.response.task.AssignTaskResponse;
//import peaksoft.java.dto.response.SimpleResponse;
//import peaksoft.java.dto.response.task.TaskResponse;
//import peaksoft.java.dto.response.task.TasksResponse;
//import peaksoft.java.enums.Status;
//import peaksoft.java.service.TaskService;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/tasks")
//@RequiredArgsConstructor
//public class TaskApi {
//    final TaskService taskService;
//
//    @Secured({"ADMIN","MANAGER","USER"})
//    @PostMapping
//    public SimpleResponse addTask(@Valid @RequestBody TaskRequest taskRequest) {
//        return taskService.addTask(taskRequest);
//    }
//
//    @Secured({"ADMIN","MANAGER","USER"})
//    @GetMapping
//    public List<TasksResponse> tasks(@RequestParam int page,
//                                     @RequestParam int size) {
//        return taskService.tasks(page, size);
//    }
//
//    @Secured({"ADMIN","MANAGER","USER"})
//    @GetMapping("/{id}")
//    public TaskResponse getTask(@PathVariable Long id) {
//        return taskService.getTask(id);
//    }
//
//    @Secured({"ADMIN","MANAGER","USER"})
//    @PutMapping("/{id}")
//    public TaskResponse updateTask(@PathVariable Long id,
//                                   @Valid @RequestBody TaskRequest taskRequest) {
//        return taskService.updateTask(id, taskRequest);
//    }
//
//    @Secured({"ADMIN","MANAGER","USER"})
//    @DeleteMapping("/{id}")
//    public SimpleResponse deleteTask(@PathVariable Long id) {
//        return taskService.delete(id);
//    }
//
//    @Secured({"ADMIN","MANAGER","USER"})
//    @PatchMapping("/{id}/status")
//    public TaskResponse changeSt(@PathVariable Long id,
//                                 @RequestParam Status status) {
//        return taskService.status(id, status);
//    }
//
//    @Secured({"ADMIN","MANAGER","USER"})
//    @PatchMapping("/{taskId}/assign")
//    public AssignTaskResponse assign(@PathVariable Long taskId,
//                                     @RequestParam Long userId) {
//        return taskService.assign(taskId, userId);
//    }
//}