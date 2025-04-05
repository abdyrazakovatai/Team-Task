package peaksoft.java.api.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;
import peaksoft.java.dto.request.AuthRequest;
import peaksoft.java.dto.request.LoginRequest;
import peaksoft.java.dto.response.AuthResponse;
import peaksoft.java.dto.response.SimpleResponse;
import peaksoft.java.service.TokenService;
import peaksoft.java.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {
    final UserService userService;
    final TokenService tokenService;

//    POST /api/auth/register - Жаңы колдонуучуну каттоо
//    POST /api/auth/login - Системага кирүү жана JWT токенди алуу
//    POST /api/auth/refresh - Refresh токен аркылуу жаңы JWT токенди алуу
//    POST /api/auth/logout - Системадан чыгуу

    @PostMapping("/register")
    public SimpleResponse signUp (@RequestBody AuthRequest authRequest){
        return userService.register(authRequest);
    }
    @PostMapping("/login")
    public AuthResponse signIn (@RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest);
    }

    @PostMapping("/refreshToken")
    public String refreshToken (@RequestBody LoginRequest loginRequest){
        return tokenService.createRefreshToken(loginRequest);
    }

    @DeleteMapping("/logout")
    public SimpleResponse deleteUser (@RequestBody LoginRequest loginRequest){
        return tokenService.logout(loginRequest);
    }
}
