package peaksoft.java.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import peaksoft.java.dto.request.TaskRequest;
import peaksoft.java.dto.response.AssignTaskResponse;
import peaksoft.java.dto.response.SimpleResponse;
import peaksoft.java.dto.response.TaskResponse;
import peaksoft.java.entity.Task;
import peaksoft.java.entity.Team;
import peaksoft.java.entity.TeamMembers;
import peaksoft.java.entity.User;
import peaksoft.java.enums.Status;
import peaksoft.java.repository.TaskRepository;
import peaksoft.java.repository.TeamMembersRepository;
import peaksoft.java.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private TeamMembersRepository teamMembersRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void addTask_shouldSaveTaskAndReturnSuccessResponse() {
        String email = "atai@gmail.com";
        User user = new User();
        user.setEmail(email);

        TaskRequest taskRequest = new TaskRequest(
                "Test Task",
                "Description",
                Status.NEW,
                4,
                "Good",
                LocalDate.of(2025, 4, 20)
        );

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        when(userRepository.findUserByEmail(email)).thenReturn(user);
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SimpleResponse response = taskService.addTask(taskRequest);

        assertEquals(HttpStatus.OK, response.status());
        assertEquals("Task saved successfully", response.message());

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskRepository).save(taskCaptor.capture());

        Task savedTask = taskCaptor.getValue();
        assertEquals(taskRequest.title(), savedTask.getTitle());
        assertEquals(taskRequest.description(), savedTask.getDescription());
        assertEquals(user, savedTask.getCreatedBy());
        assertEquals(taskRequest.priority(), savedTask.getPriority());
        assertEquals(taskRequest.status(), savedTask.getStatus());
        assertEquals(taskRequest.deadline(), savedTask.getDeadline());
        assertEquals(taskRequest.category(), savedTask.getCategory());
    }

@Test
void testUpdateTask() {
    Long taskId = 1L;

    User user = new User();
    user.setEmail("atai@gmail.com");

    Task task = new Task();
    task.setId(taskId);
    task.setTitle("Old Title");
    task.setDescription("Old Desc");
    task.setStatus(Status.NEW);
    task.setCreatedBy(user);
    task.setCreatedAt(LocalDate.now());
    task.setPriority(2);
    task.setCategory("Work");
    task.setDeadline(LocalDate.now().plusDays(5));

    TaskRequest request = new TaskRequest(
            "New Title",
            "New Description",
            Status.COMPLETED,
            5,
            "Updated",
            LocalDate.now().plusDays(10)
    );

    when(taskRepository.getTaskById(taskId)).thenReturn(task);
    when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

    TaskResponse updated = taskService.updateTask(taskId, request);

    assertThat(updated.title()).isEqualTo("New Title");
    assertThat(updated.status()).isEqualTo(Status.COMPLETED);
}

    @Test
    void testDeleteTask() {
        User user = new User();
        user.setEmail("atai@gmail.com");
        System.out.println("user = " + user);
        Task task = new Task();
        task.setTitle("Delete Me");
        task.setCreatedBy(user);
        task.setCreatedAt(LocalDate.now());
        System.out.println("task = " + task);

        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            savedTask.setId(1L);
            System.out.println("savedTask = " + savedTask);
            return savedTask;
        });

        task = taskRepository.save(task);
        System.out.println("task = " + task);

        when(taskRepository.getTaskById(task.getId())).thenReturn(task);

        doNothing().when(taskRepository).deleteById(task.getId());  // Мокаем удаление

        SimpleResponse response = taskService.delete(task.getId());
        System.out.println("response = " + response);

        assertThat(response.status()).isEqualTo(HttpStatus.OK);
        assertThat(response.message()).isEqualTo("Task deleted successfully");

        verify(taskRepository, times(1)).deleteById(task.getId());
    }

    @Test
    void testChangeStatus() {
        Long taskId = 1L;

        Task task = new Task();
        User user = new User();
        user.setEmail("atai@gmail.com");

        task.setId(taskId);
        task.setTitle("Status Task");
        task.setStatus(Status.IN_PROGRESS);
        task.setCreatedBy(user);
        task.setCreatedAt(LocalDate.now());

        when(taskRepository.getTaskById(taskId)).thenReturn(task);

        TaskResponse response = taskService.status(task.getId(), Status.CANCELLED);

        assertThat(response.status()).isEqualTo(Status.CANCELLED);
        verify(taskRepository).save(task);
    }

    @Test
    void testAssignTaskToUser() {
        Long userId = 1L;
        Long taskId = 10L;

        User user = new User();
        user.setId(userId);
        user.setEmail("atai@gmail.com");
        user.setUserName("Atai");

        Team team = new Team();
        team.setId(100L);
        team.setName("Dev Team");

        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Assignment");
        task.setCreatedBy(user);
        task.setCreatedAt(LocalDate.now());

        when(taskRepository.getTaskById(taskId)).thenReturn(task);
        when(userRepository.getUserById(userId)).thenReturn(user);
        when(teamMembersRepository.findByTeamByUser(user.getId())).thenReturn(team);

        AssignTaskResponse response = taskService.assign(taskId, userId);

        System.out.println("response = " + response);

        assertThat(response.userId()).isEqualTo(user.getId());
        assertThat(response.taskId()).isEqualTo(task.getId());
        assertThat(response.email()).isEqualTo(user.getEmail());

        verify(taskRepository).save(task);
    }
}

