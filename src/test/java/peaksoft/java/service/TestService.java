package peaksoft.java.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
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
import peaksoft.java.repository.TeamRepository;
import peaksoft.java.repository.UserRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class TaskServiceTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TeamMembersRepository teamMembersRepository;
    @Autowired
    private TeamRepository teamRepository;

    private User user;
    private Team team;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("atai@gmail.com");
        user.setUserName("ataiii");
        user.setPassword("Atai123");
        user = userRepository.save(user);

        team = new Team();
        team.setName("Team A");
        team = teamRepository.save(team);

        TeamMembers teamMember = new TeamMembers();
        teamMember.setTeam(team);
        teamMember.setUser(user);
        teamMembersRepository.save(teamMember);

        // Устанавливаем авторизацию в контексте
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user.getEmail(), null, null)
        );
    }

    @Test
    @Commit
    void testAddAndGetTask() {
        TaskRequest request = new TaskRequest(
                "Title",
                "Description",
                Status.NEW,
                3,
                "Study",
                LocalDate.now().plusDays(3)
        );

        SimpleResponse response = taskService.addTask(request);
        assertThat(response.status()).isEqualTo(HttpStatus.OK);
        assertThat(response.message()).isEqualTo("Task saved successfully");

        Task task = taskRepository.findAll().get(0);

        TaskResponse taskResponse = taskService.getTask(task.getId());
        assertThat(taskResponse.title()).isEqualTo("Title");
        assertThat(taskResponse.status()).isEqualTo(Status.NEW);
    }

    @Test
    void testUpdateTask() {
        Task task = new Task();
        task.setTitle("Old Title");
        task.setDescription("Old Desc");
        task.setStatus(Status.NEW);
        task.setCreatedBy(user);
        task.setCreatedAt(LocalDate.now());
        task.setPriority(2);
        task.setCategory("Work");
        task.setDeadline(LocalDate.now().plusDays(5));
        task = taskRepository.save(task);

        TaskRequest request = new TaskRequest(
                "New Title",
                "New Description",
                Status.COMPLETED,
                5,
                "Updated",
                LocalDate.now().plusDays(10)
        );

        TaskResponse updated = taskService.updateTask(task.getId(), request);
        assertThat(updated.title()).isEqualTo("New Title");
        assertThat(updated.status()).isEqualTo(Status.COMPLETED);
    }

    @Test
    void testDeleteTask() {
        Task task = new Task();
        task.setTitle("Delete Me");
        task.setCreatedBy(user);
        task.setCreatedAt(LocalDate.now());
        task = taskRepository.save(task);

        SimpleResponse response = taskService.delete(task.getId());
        assertThat(response.status()).isEqualTo(HttpStatus.OK);
        assertThat(response.message()).isEqualTo("Task deleted successfully");

        assertThat(taskRepository.findById(task.getId())).isEmpty();
    }

    @Test
    void testChangeStatus() {
        Task task = new Task();
        task.setTitle("Status Task");
        task.setStatus(Status.IN_PROGRESS);
        task.setCreatedBy(user);
        task.setCreatedAt(LocalDate.now());
        task = taskRepository.save(task);

        TaskResponse response = taskService.status(task.getId(), Status.CANCELLED);
        assertThat(response.status()).isEqualTo(Status.CANCELLED);
    }

    @Test
    void testAssignTaskToUser() {
        Task task = new Task();
        task.setTitle("Assignment");
        task.setCreatedBy(user);
        task.setCreatedAt(LocalDate.now());
        task = taskRepository.save(task);

        AssignTaskResponse response = taskService.assign(task.getId(), user.getId());

        assertThat(response.userId()).isEqualTo(user.getId());
        assertThat(response.taskId()).isEqualTo(task.getId());
        assertThat(response.email()).isEqualTo("atai@gmail.com");
    }
}
