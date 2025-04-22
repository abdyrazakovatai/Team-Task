//package peaksoft.java.api.auth;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import peaksoft.java.dto.request.AuthRequest;
//import peaksoft.java.dto.request.LoginRequest;
//import peaksoft.java.dto.response.AuthResponse;
//import peaksoft.java.dto.response.SimpleResponse;
//import peaksoft.java.service.TokenService;
//import peaksoft.java.service.UserService;
//
//@RestController
//@RequestMapping("/api/auth")
//@RequiredArgsConstructor
//public class AuthApi {
//    final UserService userService;
//    final TokenService tokenService;
//
//    @PostMapping("/register")
//    public SimpleResponse signUp(@Valid @RequestBody AuthRequest authRequest) {
//        return userService.register(authRequest);
//    }
//
//    @PostMapping("/login")
//    public AuthResponse signIn(@Valid @RequestBody LoginRequest loginRequest) {
//        return userService.login(loginRequest);
//    }
//
//    @PostMapping("/refreshToken")
//    public SimpleResponse refreshToken() {
//        return tokenService.createRefreshToken();
//    }
//
//    @DeleteMapping("/logout")
//    public SimpleResponse logout() {
//        return tokenService.logout();
//    }
//}
