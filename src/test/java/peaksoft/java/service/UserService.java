//package peaksoft.java.service;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import peaksoft.java.dto.request.AuthRequest;
//import peaksoft.java.dto.request.LoginRequest;
//import peaksoft.java.dto.response.AuthResponse;
//import peaksoft.java.dto.response.SimpleResponse;
//import peaksoft.java.dto.response.user.UsersResponse;
//import peaksoft.java.entity.User;
//import peaksoft.java.enums.Role;
//import peaksoft.java.exception.BadRequestException;
//import peaksoft.java.exception.NotFoundException;
//import peaksoft.java.jwt.JwtService;
//import peaksoft.java.repository.UserRepository;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//class UserServiceTest {
//
//    @InjectMocks
//    private UserService userService;
//
//    @Mock
//    private UserRepository userRepository;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//    @Mock
//    private JwtService jwtService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void register_shouldSaveUser() {
//        AuthRequest request = new AuthRequest("john", "john@gmail.com", "1234", "John", "Doe");
//        when(userRepository.existsUserByEmail(request.email())).thenReturn(false);
//        when(passwordEncoder.encode("1234")).thenReturn("encoded123");
//
//        SimpleResponse response = userService.register(request);
//
//        assertEquals("Register successfully", response.message());
//        assertEquals(HttpStatus.OK, response.status());
//
//        verify(userRepository).save(any(User.class));
//    }
//
//    @Test
//    void register_shouldThrowIfUserExists() {
//        AuthRequest request = new AuthRequest("john", "john@gmail.com", "1234", "John", "Doe");
//        when(userRepository.existsUserByEmail(request.email())).thenReturn(true);
//
//        assertThrows(NotFoundException.class, () -> userService.register(request));
//    }
//
//    @Test
//    void login_shouldReturnToken() {
//        LoginRequest loginRequest = new LoginRequest("user@example.com", "password");
//        User user = new User("user", "user@example.com", "encodedPass", Role.USER);
//        when(userRepository.findUserByEmail("user@example.com")).thenReturn(user);
//        when(passwordEncoder.matches("password", "encodedPass")).thenReturn(true);
//        when(jwtService.createJwtToken(user, false)).thenReturn("token");
//
//        AuthResponse response = userService.login(loginRequest);
//
//        assertEquals("token", response.jwtTokenResponse());
//        assertEquals("user@example.com", response.email());
//        assertEquals(Role.USER, response.role());
//    }
//
//    @Test
//    void login_shouldThrowIfPasswordWrong() {
//        LoginRequest loginRequest = new LoginRequest("user@example.com", "wrongPass");
//        User user = new User("user", "user@example.com", "encodedPass", Role.USER);
//        when(userRepository.findUserByEmail("user@example.com")).thenReturn(user);
//        when(passwordEncoder.matches("wrongPass", "encodedPass")).thenReturn(false);
//
//        assertThrows(BadRequestException.class, () -> userService.login(loginRequest));
//    }
//
//    @Test
//    void getUser_shouldReturnUser() {
//        User user = new User("john", "john@gmail.com", "pass", Role.USER);
//        user.setId(1L);
//        when(userRepository.getUserById(1L)).thenReturn(user);
//
//        UsersResponse response = userService.getUser(1L);
//
//        assertEquals(1L, response.userId());
//        assertEquals("john", response.username());
//    }
//
//    @Test
//    void getUser_shouldThrowIfNotFound() {
//        when(userRepository.getUserById(1L)).thenReturn(null);
//        assertThrows(NotFoundException.class, () -> userService.getUser(1L));
//    }
//
//    @Test
//    void delete_shouldRemoveUser() {
//        User user = new User("john", "john@gmail.com", "pass", Role.USER);
//        when(userRepository.getUserById(1L)).thenReturn(user);
//
//        SimpleResponse response = userService.delete(1L);
//
//        assertEquals(HttpStatus.OK, response.status());
//        verify(userRepository).delete(user);
//    }
//
//    @Test
//    void delete_shouldReturnNotFoundIfMissing() {
//        when(userRepository.getUserById(1L)).thenReturn(null);
//
//        SimpleResponse response = userService.delete(1L);
//
//        assertEquals(HttpStatus.NOT_FOUND, response.status());
//        assertEquals("User with id 1 not found", response.message());
//    }
//}