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
        // given
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

        // моки
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        when(userRepository.findUserByEmail(email)).thenReturn(user);
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        SimpleResponse response = taskService.addTask(taskRequest);

        // then
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

    // задаём поведение моков
    when(taskRepository.getTaskById(taskId)).thenReturn(task);
    when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // вызов сервиса
    TaskResponse updated = taskService.updateTask(taskId, request);

    // проверки
    assertThat(updated.title()).isEqualTo("New Title");
    assertThat(updated.status()).isEqualTo(Status.COMPLETED);
}

    @Test
    void testDeleteTask() {
        // Создание пользователя и задачи
        User user = new User();
        user.setEmail("atai@gmail.com");
        System.out.println("user = " + user);
        Task task = new Task();
        task.setTitle("Delete Me");
        task.setCreatedBy(user);
        task.setCreatedAt(LocalDate.now());
        System.out.println("task = " + task);

        // Мокаем сохранение задачи и присваиваем ID
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            savedTask.setId(1L);  // Присваиваем ID вручную для мокированного объекта
            System.out.println("savedTask = " + savedTask);
            return savedTask;
        });

        // Сохраняем задачу через сервис
        task = taskRepository.save(task);
        System.out.println("task = " + task);

        // Мокаем метод getTaskById, чтобы вернуть задачу по ID
        when(taskRepository.getTaskById(task.getId())).thenReturn(task);

        // Мокаем удаление задачи
        doNothing().when(taskRepository).deleteById(task.getId());  // Мокаем удаление

        // Вызов метода удаления
        SimpleResponse response = taskService.delete(task.getId());
        System.out.println("response = " + response);

        // Проверки
        assertThat(response.status()).isEqualTo(HttpStatus.OK);
        assertThat(response.message()).isEqualTo("Task deleted successfully");

        // Проверяем, что задача была удалена
        verify(taskRepository, times(1)).deleteById(task.getId());
    }

    @Test
    void testChangeStatus() {
        Long taskId = 1L;

        // Создаем объект задачи и пользователя
        Task task = new Task();
        User user = new User();
        user.setEmail("atai@gmail.com");

        task.setId(taskId);
        task.setTitle("Status Task");
        task.setStatus(Status.IN_PROGRESS);
        task.setCreatedBy(user);
        task.setCreatedAt(LocalDate.now());


        // Мокаем метод findById, чтобы вернуть задачу по ID
        when(taskRepository.getTaskById(taskId)).thenReturn(task);

        // Вызов метода смены статуса
        TaskResponse response = taskService.status(task.getId(), Status.CANCELLED);

        // Проверки
        assertThat(response.status()).isEqualTo(Status.CANCELLED);
        verify(taskRepository).save(task);  // Проверяем, что метод save был вызван
    }

    @Test
    void testAssignTaskToUser() {
//        Long userId = 1L;
//        Long taskId = 2L;
//
//        User user = new User();
//        user.setId(userId);
//        user.setEmail("atai@gmail.com");
//
//        Team team = new Team();
//        team.setName("Dev Team");
//        user.setTeam(team);
//
//        Task task = new Task();
//        task.setId(taskId);
//        task.setTitle("Assignment");
//        task.setCreatedBy(user);
//        task.setCreatedAt(LocalDate.now());
//
//        TeamMembers teamMember = new TeamMembers();
//        teamMember.setUser(user);
//        teamMember.setTeam(team);
//
//        // Мокаем методы
//        when(taskRepository.getTaskById(taskId)).thenReturn(task);
//        when(teamMembersRepository.findByTeamByUser(userId)).thenReturn(team);
//
//        System.out.println("task = " + task);
//        System.out.println("userId = " + userId);
//
//        AssignTaskResponse response = taskService.assign(taskId, userId);
//        System.out.println("response = " + response);
//
//        assertThat(response.userId()).isEqualTo(user.getId());
//        assertThat(response.taskId()).isEqualTo(task.getId());
//        assertThat(response.email()).isEqualTo("atai@gmail.com");
//
//        verify(taskRepository).save(task);
        // Данные
        Long userId = 1L;
        Long taskId = 10L;

        // Создание пользователя
        User user = new User();
        user.setId(userId);
        user.setEmail("atai@gmail.com");
        user.setUserName("Atai");

        // Создание команды
        Team team = new Team();
        team.setId(100L);
        team.setName("Dev Team");

        // Создание задачи
        Task task = new Task();
        task.setId(taskId);
        task.setTitle("Assignment");
        task.setCreatedBy(user);
        task.setCreatedAt(LocalDate.now());

        // Мокаем нужные зависимости
        when(taskRepository.getTaskById(taskId)).thenReturn(task);
        when(userRepository.getUserById(userId)).thenReturn(user);
        when(teamMembersRepository.findByTeamByUser(user.getId())).thenReturn(team);

        // Вызов метода
        AssignTaskResponse response = taskService.assign(taskId, userId);

        // Вывод в консоль (можно убрать после отладки)
        System.out.println("response = " + response);

        // Проверки
        assertThat(response.userId()).isEqualTo(user.getId());
        assertThat(response.taskId()).isEqualTo(task.getId());
        assertThat(response.email()).isEqualTo(user.getEmail());

        // Проверка, что задача была сохранена
        verify(taskRepository).save(task);
    }
}

