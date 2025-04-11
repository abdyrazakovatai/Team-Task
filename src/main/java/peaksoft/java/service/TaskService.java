package peaksoft.java.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.java.dto.request.TaskRequest;
import peaksoft.java.dto.response.task.AssignTaskResponse;
import peaksoft.java.dto.response.SimpleResponse;
import peaksoft.java.dto.response.task.TaskResponse;
import peaksoft.java.dto.response.task.TasksResponse;
import peaksoft.java.entity.Task;
import peaksoft.java.entity.Team;
import peaksoft.java.entity.User;
import peaksoft.java.enums.Status;
import peaksoft.java.repository.TaskRepository;
import peaksoft.java.repository.TeamMembersRepository;
import peaksoft.java.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskService {
    final TaskRepository taskRepository;
    final UserRepository userRepository;
    final TeamMembersRepository teamMembersRepository;

    public SimpleResponse addTask(TaskRequest taskRequest) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(currentUserEmail);

        log.info("User [{}] is creating a new task: {}", currentUserEmail, taskRequest.title());

        taskRepository.save(Task.builder()
                .title(taskRequest.title())
                .description(taskRequest.description())
                .status(taskRequest.status())
                .createdAt(LocalDate.now())
                .category(taskRequest.category())
                .priority(taskRequest.priority())
                .deadline(taskRequest.deadline())
                .createdAt(LocalDate.now())
                .createdBy(user)
                .build());

        log.info("Task [{}] created successfully", taskRequest.title());

        return new SimpleResponse(
                HttpStatus.OK,
                "Task saved successfully"
        );
    }

    public List<TasksResponse> tasks(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        log.info("Fetching tasks page: {}, size: {}", page, size);

        Page<Task> taskPage = taskRepository.findAll(pageable);
        return taskPage.getContent().stream()
                .map(task -> new TasksResponse(
                        task.getTitle(),
                        task.getDescription(),
                        task.getStatus(),
                        task.getPriority(),
                        task.getCategory(),
                        task.getDeadline(),
                        task.getCreatedAt()
                )).collect(Collectors.toList());
    }

    public TaskResponse getTask(Long id) {
        Task task = taskRepository.getTaskById(id);
        log.info("Getting task with ID: {}", id);

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
        Task task = taskRepository.getTaskById(id);
        log.info("Updating task with ID: {}", id);

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

    public SimpleResponse delete(Long id) {
        Task task = taskRepository.getTaskById(id);
        log.warn("Deleting task with ID: {}", id);

        taskRepository.deleteById(task.getId());
        return new SimpleResponse(
                HttpStatus.OK,
                "Task deleted successfully"
        );
    }

    public TaskResponse status(Long id, Status status) {
        Task task = taskRepository.getTaskById(id);
        log.info("Updating status of task [{}] to {}", id, status);

        task.setStatus(status);
        taskRepository.save(task);
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

    public AssignTaskResponse assign(Long taskId, Long userId) {
        Task task = taskRepository.getTaskById(taskId);
        User user = userRepository.getUserById(userId);
        Team team = teamMembersRepository.findByTeamByUser(user.getId());
        log.info("Assigning task [{} - {}] to user [{} - {}]", taskId, task.getTitle(), userId, user.getUserName());

        task.setTeam(team);
        task.setAssignedTo(user);
        taskRepository.save(task);

        return new AssignTaskResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                task.getId(),
                task.getTitle(),
                task.getCategory(),
                task.getStatus(),
                task.getDeadline()
        );
    }
}
