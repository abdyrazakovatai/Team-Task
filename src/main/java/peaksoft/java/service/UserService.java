//package peaksoft.java.service;
//
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import peaksoft.java.dto.request.AuthRequest;
//import peaksoft.java.dto.request.LoginRequest;
//import peaksoft.java.dto.request.UpdateRequest;
//import peaksoft.java.dto.response.AuthResponse;
//import peaksoft.java.dto.response.SimpleResponse;
//import peaksoft.java.dto.response.user.UserResponse;
//import peaksoft.java.dto.response.user.UsersResponse;
//import peaksoft.java.entity.User;
//import peaksoft.java.enums.Role;
//import peaksoft.java.exception.BadRequestException;
//import peaksoft.java.exception.NotFoundException;
//import peaksoft.java.jwt.JwtService;
//import peaksoft.java.repository.UserRepository;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class UserService {
//    final UserRepository userRepository;
//    final PasswordEncoder passwordEncoder;
//    final JwtService jwtService;
//
//    @PostConstruct
//    public void init() {
//        List<User> users = List.of(
//                new User("admin", "admin@gmail.com", "Admin123", Role.ADMIN),
//                new User("manager", "manager@gmail.com", "Manager123", Role.MANAGER)
//        );
//        users.forEach(this::createUserIfNotExists);
//    }
//
//    private void createUserIfNotExists(User user) {
//        if (!userRepository.existsUserByEmail(user.getEmail())) {
//            userRepository.save(User.builder()
//                    .userName(user.getUserName())
//                    .email(user.getEmail())
//                    .password(passwordEncoder.encode(user.getPassword()))
//                    .role(user.getRole())
//                    .build());
//            log.info("Default user [{}] created", user.getEmail());
//        } else {
//            log.info("User [{}] already exists, skipping", user.getEmail());
//        }
//    }
//
//    public SimpleResponse register(AuthRequest authRequest) {
//        if (userRepository.existsUserByEmail(authRequest.email())) {
//            log.warn("Attempt to register with existing email: {}", authRequest.email());
//            throw new NotFoundException("User with email " + authRequest.email() + " already exists");
//        }
//
//        userRepository.save(User.builder()
//                .createdAt(LocalDate.now())
//                .userName(authRequest.username())
//                .email(authRequest.email())
//                .password(passwordEncoder.encode(authRequest.password()))
//                .firstName(authRequest.firstName())
//                .lastName(authRequest.lastName())
//                .role(Role.USER)
//                .build());
//
//        log.info("User [{}] registered successfully", authRequest.email());
//
//        return new SimpleResponse(
//                HttpStatus.OK,
//                "Register successfully"
//        );
//    }
//
//    public AuthResponse login(LoginRequest loginRequest) {
//        User user = userRepository.findUserByEmail(loginRequest.email());
//
//        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
//            log.warn("Wrong password attempt for email: {}", loginRequest.email());
//            throw new BadRequestException("Wrong password");
//        }
//
//        String accessToken = jwtService.createJwtToken(user, false);
//
//        log.info("User [{}] logged in successfully", loginRequest.email());
//
//        return new AuthResponse(
//                accessToken,
//                user.getEmail(),
//                user.getRole()
//        );
//    }
//
//    public List<UsersResponse> users() {
//        log.info("Fetching all users");
//
//        List<User> users = userRepository.findAll();
//
//        return users.stream().map(user -> new UsersResponse(
//                user.getId(),
//                user.getUserName(),
//                user.getEmail(),
//                user.getFirstName(),
//                user.getLastName()
//        )).toList();
//    }
//
//    public UsersResponse getUser(Long id) {
//        User user = userRepository.getUserById(id);
//        if (user == null) {
//            log.error("User with id {} not found", id);
//            throw new NotFoundException("User with id " + id + " not found");
//        }
//
//        log.info("Fetched user with id {}", id);
//
//        return new UsersResponse(
//                user.getId(),
//                user.getUserName(),
//                user.getEmail(),
//                user.getFirstName(),
//                user.getLastName()
//        );
//    }
//
//    public UserResponse updateUser(Long id, UpdateRequest updateUser) {
//        User user = userRepository.getUserById(id);
//
//        user.setUpdatedAt(LocalDate.now());
//        user.setUserName(updateUser.username());
//        user.setEmail(updateUser.email());
//        user.setFirstName(updateUser.firstName());
//        user.setLastName(updateUser.lastName());
//        user.setRole(updateUser.role());
//
//        User save = userRepository.save(user);
//
//        log.info("User with id {} updated successfully", id);
//
//        return new UserResponse(
//                save.getId(),
//                save.getUserName(),
//                save.getEmail(),
//                save.getFirstName(),
//                save.getLastName(),
//                save.getRole()
//        );
//    }
//
//
//
//    public SimpleResponse delete(Long id) {
//        User user = userRepository.getUserById(id);
//        if (user == null) {
//            log.warn("Attempt to delete non-existent user with id {}", id);
//            return new SimpleResponse(
//                    HttpStatus.NOT_FOUND,
//                    "User with id " + id + " not found"
//            );
//        }
//
//        userRepository.delete(user);
//        log.info("User with id {} deleted successfully", id);
//
//        return new SimpleResponse(
//                HttpStatus.OK,
//                "Delete successfully"
//        );
//    }
//
//    public List<User> findAll() {
//        return userRepository.findAll();
//    }
//}
