package peaksoft.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.java.dto.request.TaskRequest;
import peaksoft.java.dto.response.SimpleResponse;
import peaksoft.java.dto.response.TaskResponse;
import peaksoft.java.dto.response.TasksResponse;
import peaksoft.java.entity.Task;
import peaksoft.java.entity.User;
import peaksoft.java.exception.NotFoundException;
import peaksoft.java.repository.TaskRepository;
import peaksoft.java.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {
    final TaskRepository taskRepository;
    final UserRepository userRepository;

    public SimpleResponse addTask(TaskRequest taskRequest) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.getUserByEmail(currentUserEmail);
        taskRepository.save(Task.builder()
                .title(taskRequest.title())
                .description(taskRequest.description())
                .status(taskRequest.status())
                .createdAt(LocalDate.now())
                .priority(taskRequest.priority())
                .deadline(taskRequest.deadline())
                .createdAt(LocalDate.now())
                .createdBy(currentUser)
                .build());
        return new SimpleResponse(
                HttpStatus.OK,
                "Task saved successfully"
        );
    }

    public List<TasksResponse> tasks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Task> taskPage = taskRepository.findAll(pageable);
        List<TasksResponse> tasksResponses = taskPage.getContent().stream()
                .map(task -> new TasksResponse(
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getPriority(),
                        task.getCategory(),
                        task.getDeadline(),
                        task.getCreatedAt()
                )).collect(Collectors.toList());
        return tasksResponses;
    }

    public TaskResponse getTask(Long id) {
        Task task = taskRepository.getById(id);
        if (task == null) {
            throw new NotFoundException("Task with id: " + id + "not found");
        }
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getCategory(),
                task.getDeadline(),
                task.getCreatedAt()
        );
    }

    public TaskResponse updateTask(Long id, TaskRequest taskRequest) {
        Task task = taskRepository.getById(id);
        if (task == null) {
            throw new NotFoundException("Task with id: " + id + "not found");
        }
        task.setTitle(taskRequest.title());
        task.setDescription(taskRequest.description());
        task.setStatus(taskRequest.status());
        task.setDeadline(taskRequest.deadline());
        task.setCreatedAt(LocalDate.now());
        task.setPriority(taskRequest.priority());
        task.setCategory(taskRequest.category());
        task.setDeadline(taskRequest.deadline());
        Task save = taskRepository.save(task);
        return new TaskResponse(
                save.getId(),
                save.getTitle(),
                save.getDescription(),
                save.getStatus(),
                save.getPriority(),
                save.getCategory(),
                save.getDeadline(),
                save.getCreatedAt()
        );
    }
}
