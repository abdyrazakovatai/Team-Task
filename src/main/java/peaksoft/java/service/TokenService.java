package peaksoft.java.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.java.dto.request.AuthRequest;
import peaksoft.java.dto.request.LoginRequest;
import peaksoft.java.dto.response.SimpleResponse;
import peaksoft.java.entity.RefreshToken;
import peaksoft.java.entity.User;
import peaksoft.java.jwt.JwtService;
import peaksoft.java.repository.TokenRepository;
import peaksoft.java.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TokenService {
    final TokenRepository tokenRepository;
    final UserRepository userRepository;
    final JwtService jwtService;

    public String createRefreshToken(LoginRequest loginRequest) {
        User user = userRepository.getUserByEmail(loginRequest.email());

        String refreshToken = jwtService.createRefreshToken(user);
        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(refreshToken);

        tokenRepository.save(token);
        return refreshToken;
    }

    public SimpleResponse logout(LoginRequest loginRequest) {
        User user = userRepository.getUserByEmail(loginRequest.email());
        if (user == null) {
            return new SimpleResponse(
                    HttpStatus.NOT_FOUND,
                    "User not found");
        }

        userRepository.delete(user);

        return new SimpleResponse(HttpStatus.OK,
                "Logged out successfully");
    }
}
