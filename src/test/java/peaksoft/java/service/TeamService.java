//package peaksoft.java.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import peaksoft.java.dto.request.TeamRequest;
//import peaksoft.java.dto.response.team.AssignTeamResponse;
//import peaksoft.java.dto.response.SimpleResponse;
//import peaksoft.java.dto.response.team.TeamResponse;
//import peaksoft.java.entity.Team;
//import peaksoft.java.entity.TeamMembers;
//import peaksoft.java.entity.User;
//import peaksoft.java.repository.TeamMembersRepository;
//import peaksoft.java.repository.TeamRepository;
//import peaksoft.java.repository.UserRepository;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class TeamServiceTest {
//
//    @Mock
//    TeamRepository teamRepository;
//    @Mock
//    UserRepository userRepository;
//    @Mock
//    TeamMembersRepository teamMembersRepository;
//    @Mock
//    Authentication authentication;
//    @Mock
//    SecurityContext securityContext;
//
//    @InjectMocks
//    TeamService teamService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void newTeam_shouldCreateTeamSuccessfully() {
//        TeamRequest teamRequest = new TeamRequest("Dev Team", "Development team");
//        User user = new User();
//        user.setEmail("user@example.com");
//
//        when(securityContext.getAuthentication()).thenReturn(authentication);
//        when(authentication.getName()).thenReturn(user.getEmail());
//        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);
//        SecurityContextHolder.setContext(securityContext);         // Установка контекста безопасности
//
//        Team team = new Team();
//        team.setName(teamRequest.name());
//        team.setDescription(teamRequest.description());
//        when(teamRepository.save(any(Team.class))).thenReturn(team);
//
//        SimpleResponse response = teamService.newTeam(teamRequest);
//
//        assertEquals(HttpStatus.OK, response.status());
//        assertEquals("New team created", response.message());
//        verify(teamRepository).save(any(Team.class));
//
//    }
//
//    @Test
//    void teams_shouldReturnListOfTeams() {
//        Team team = new Team();
//        team.setId(1L);
//        team.setName("Dev");
//        team.setDescription("Team description");
//
//        when(teamRepository.findAll()).thenReturn(List.of(team));
//
//        var teams = teamService.teams();
//
//        assertEquals(1, teams.size());
//        assertEquals("Dev", teams.get(0).name());
//    }
//
//    @Test
//    void team_shouldReturnTeamById() {
//        Team team = new Team();
//        team.setId(1L);
//        team.setName("Team A");
//        team.setDescription("Desc");
//
//        when(teamRepository.getTeamById(1L)).thenReturn(team);
//
//        TeamResponse response = teamService.team(1L);
//
//        assertEquals("Team A", response.name());
//    }
//
//    @Test
//    void update_shouldUpdateTeam() {
//        Team team = new Team();
//        team.setId(1L);
//        TeamRequest request = new TeamRequest("Updated", "Updated Desc");
//
//        when(teamRepository.getTeamById(1L)).thenReturn(team);
//        when(teamRepository.save(any(Team.class))).thenReturn(team);
//
//        TeamResponse response = teamService.update(1L, request);
//
//        assertEquals("Updated", response.name());
//    }
//
//    @Test
//    void delete_shouldDeleteTeamSuccessfully() {
//        SimpleResponse response = teamService.delete(1L);
//
//        assertEquals(HttpStatus.OK, response.status());
//        assertEquals("Team deleted", response.message());
//        verify(teamRepository).deleteTeamMembersByTeamId(1L);
//        verify(teamRepository).delete(1L);
//    }
//
//    @Test
//    void assign_shouldAssignUserToTeam() {
//        Team team = new Team();
//        team.setId(1L);
//        team.setName("Alpha");
//        User user = new User();
//        user.setId(2L);
//        user.setUserName("John");
//
//        when(teamRepository.getTeamById(1L)).thenReturn(team);
//        when(userRepository.getUserById(2L)).thenReturn(user);
//        when(teamMembersRepository.existsByTeamIdAndUser_Id(1L, 2L)).thenReturn(false);
//
//        AssignTeamResponse response = teamService.assign(1L, 2L);
//
//        assertEquals("Alpha", response.teamName());
//        assertEquals("John", response.username());
//        verify(teamMembersRepository).save(any(TeamMembers.class));
//    }
//
//    @Test
//    void deleteMember_shouldRemoveUserFromTeam() {
//        Team team = new Team();
//        team.setId(1L);
//        TeamMembers member = new TeamMembers();
//
//        when(teamRepository.getTeamById(1L)).thenReturn(team);
//        when(teamMembersRepository.findByTeamIdAndUserId(1L, 2L)).thenReturn(member);
//
//        SimpleResponse response = teamService.deleteMember(1L, 2L);
//
//        assertEquals(HttpStatus.OK, response.status());
//        assertEquals("Team members deleted", response.message());
//        verify(teamMembersRepository).delete(member);
//    }
//}
