package peaksoft.java.api.auth;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.*;
import peaksoft.java.dto.request.AuthRequest;
import peaksoft.java.dto.request.EmailRequest;
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

    //todo POST /api/auth/register - Жаңы колдонуучуну каттоо
    @PostMapping("/register")
    public SimpleResponse signUp (@RequestBody AuthRequest authRequest){
        return userService.register(authRequest);
    }
    //todo POST /api/auth/login - Системага кирүү жана JWT токенди алуу
    @PostMapping("/login")
    public AuthResponse signIn (@RequestBody LoginRequest loginRequest){
        return userService.login(loginRequest);
    }

    //todo POST /api/auth/refresh - Refresh токен аркылуу жаңы JWT токенди алуу
    @PostMapping("/refreshToken")
    public SimpleResponse refreshToken (){
        return tokenService.createRefreshToken();
    }

    //todo POST /api/auth/logout - Системадан чыгуу
    @DeleteMapping("/logout")
    public SimpleResponse logout (){
        return tokenService.logout();
    }
}
