package peaksoft.java.service;

import com.auth0.jwt.JWT;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import peaksoft.java.dto.response.SimpleResponse;
import peaksoft.java.entity.RefreshToken;
import peaksoft.java.entity.User;
import peaksoft.java.jwt.JwtService;
import peaksoft.java.repository.TeamRepository;
import peaksoft.java.repository.TokenRepository;
import peaksoft.java.repository.UserRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
    final TokenRepository tokenRepository;
    final TeamRepository teamRepository;
    final UserRepository userRepository;
    final JwtService jwtService;

    @Transactional
    public SimpleResponse createRefreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return new SimpleResponse(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        String username = authentication.getName();
        User user = userRepository.findUserByEmail(username);

        Optional<RefreshToken> existingToken = tokenRepository.findByUser_Id(user.getId());

        if (existingToken.isPresent()) {

            RefreshToken token = existingToken.get();
            String refreshToken = jwtService.createJwtToken(user, true);

            Date expiresDate = JWT.decode(refreshToken).getExpiresAt();
            LocalDate expiresAt = expiresDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            token.setToken(refreshToken);
            token.setExpiryDate(expiresAt);
            tokenRepository.save(token);

            return new SimpleResponse(HttpStatus.OK, refreshToken);
        } else {

            String refreshToken = jwtService.createJwtToken(user, true);

            Date expiresDate = JWT.decode(refreshToken).getExpiresAt();
            LocalDate expiresAt = expiresDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            tokenRepository.save(RefreshToken.builder()
                    .token(refreshToken)
                    .user(user)
                    .isRevoked(false)
                    .expiryDate(expiresAt)
                    .build());
            return new SimpleResponse(HttpStatus.OK, refreshToken);
        }

    }

    @Transactional
    public SimpleResponse logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return new SimpleResponse(HttpStatus.UNAUTHORIZED, "User is not authenticated");
        }

        String username = authentication.getName();
        User user = userRepository.findUserByEmail(username);

        teamRepository.deleteUserById(user.getId());
        tokenRepository.deleteByUser_Id(user.getId());
        userRepository.delete(user);

        SecurityContextHolder.clearContext();
        return new SimpleResponse(HttpStatus.OK,
                "Logged out successfully");
    }
}
