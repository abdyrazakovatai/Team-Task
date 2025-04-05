package peaksoft.java.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.java.dto.request.AuthRequest;
import peaksoft.java.dto.request.LoginRequest;
import peaksoft.java.dto.request.UpdateRequest;
import peaksoft.java.dto.response.AuthResponse;
import peaksoft.java.dto.response.SimpleResponse;
import peaksoft.java.dto.response.UserResponse;
import peaksoft.java.dto.response.UsersResponse;
import peaksoft.java.entity.User;
import peaksoft.java.entity.UserInfo;
import peaksoft.java.enums.Role;
import peaksoft.java.exception.BadRequestException;
import peaksoft.java.exception.NotFoundException;
import peaksoft.java.jwt.JwtService;
import peaksoft.java.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final JwtService jwtService;

    @PostConstruct
    public void init() {
        List<UserInfo> users = List.of(
                new UserInfo("admin","admin@gmail.com",Role.ADMIN),
                new UserInfo("manager","manager@gmail.com",Role.MANAGER)
        );
        users.forEach(userInfo -> createUserIfNotExists(userInfo));
    }

    private void createUserIfNotExists(UserInfo userInfo) {
        boolean exists = userRepository.existsUserByEmail(userInfo.getEmail());
        if (!exists) {
            userRepository.save(User.builder()
                    .userName(userInfo.getUsername())
                    .email(userInfo.getEmail())
                    .role(userInfo.getRole())
                    .build());
        }
    }


    public SimpleResponse register(AuthRequest authRequest) {

        if (userRepository.existsUserByEmail(authRequest.email())) {
            throw new NotFoundException("User with email " + authRequest.email() + " already exists");
        }

        userRepository.save(User.builder()
                .createdAt(LocalDate.now())
                .userName(authRequest.username())
                .email(authRequest.email())
                .password(passwordEncoder.encode(authRequest.password()))
                .firstName(authRequest.firstName())
                .lastName(authRequest.lastName())
                .role(Role.USER)
                .build());

        return new SimpleResponse(
                HttpStatus.OK,
                "Register successfully"
        );
    }

    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.getUserByEmail(loginRequest.email());

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new BadRequestException("Wrong password");
        }
        return new AuthResponse(
                jwtService.createJwtToken(user),
                user.getEmail(),
                user.getRole()
        );
    }

    public List<UsersResponse> users() {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> new UsersResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        )).toList();
    }

    public UsersResponse getUser(Long id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            throw new NotFoundException("User with id " + id + " not found");
        }
        return new UsersResponse(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public UserResponse updateUser(Long id, UpdateRequest updateUser) {
        User user = userRepository.getUserById(id);

        user.setUpdatedAt(LocalDate.now());
        user.setUserName(updateUser.username());
        user.setEmail(updateUser.email());
        user.setFirstName(updateUser.firstName());
        user.setLastName(updateUser.lastName());
        user.setRole(updateUser.role());

        User save = userRepository.save(user);
        return new UserResponse(
                save.getId(),
                save.getUsername(),
                save.getEmail(),
                save.getFirstName(),
                save.getLastName(),
                save.getRole()
        );
    }

    public SimpleResponse delete(Long id) {
        User user = userRepository.getUserById(id);
        if (user == null) {
            return new SimpleResponse(
                    HttpStatus.NOT_FOUND,
                    "User with id " + id + " not found"
            );
        }
        userRepository.delete(user);
        return new SimpleResponse(
                HttpStatus.OK,
                "Delete successfully"
        );
    }
}
