package peaksoft.java.service;

import com.auth0.jwt.JWT;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import peaksoft.java.dto.response.SimpleResponse;
import peaksoft.java.entity.RefreshToken;
import peaksoft.java.entity.User;
import peaksoft.java.jwt.JwtService;
import peaksoft.java.repository.TeamRepository;
import peaksoft.java.repository.TokenRepository;
import peaksoft.java.repository.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TokenServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private TokenService tokenService;

    public TokenServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateRefreshToken_newToken() {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("atai@gmail.com", null, new ArrayList<>());
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        User user = new User();
        user.setId(1L);
        user.setEmail("atai@gmail.com");

        when(userRepository.findUserByEmail("atai@gmail.com")).thenReturn(user);
        when(tokenRepository.findByUser_Id(user.getId())).thenReturn(Optional.empty());

        String fakeToken = createFakeJwtToken();
        when(jwtService.createJwtToken(user, true)).thenReturn(fakeToken);

        SimpleResponse response = tokenService.createRefreshToken();

        assertEquals(HttpStatus.OK, response.status());
        assertEquals(fakeToken, response.message());
        verify(tokenRepository, times(1)).save(any(RefreshToken.class));

    }

    @Test
    void testCreateRefreshToken_updateExistingToken() {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("atai@gmail.com", null, new ArrayList<>());
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        User user = new User();
        user.setId(2L);
        user.setEmail("atai@gmail.com");

        RefreshToken existing = new RefreshToken();
        existing.setUser(user);

        when(userRepository.findUserByEmail("atai@gmail.com")).thenReturn(user);
        when(tokenRepository.findByUser_Id(user.getId())).thenReturn(Optional.of(existing));

        String newToken = createFakeJwtToken();
        when(jwtService.createJwtToken(user, true)).thenReturn(newToken);

        SimpleResponse response = tokenService.createRefreshToken();

        assertEquals(HttpStatus.OK, response.status());
        assertEquals(newToken, response.message());
        verify(tokenRepository, times(1)).save(existing);
    }

    @Test
    void testLogout() {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken("atai@gmail.com", null, new ArrayList<>());
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        User user = new User();
        user.setId(3L);
        user.setEmail("atai@gmail.com");

        when(userRepository.findUserByEmail("atai@gmail.com")).thenReturn(user);

        SimpleResponse response = tokenService.logout();

        assertEquals(HttpStatus.OK, response.status());
        assertEquals("Logged out successfully", response.message());
        verify(teamRepository).deleteUserById(user.getId());
        verify(tokenRepository).deleteByUser_Id(user.getId());
        verify(userRepository).delete(user);
    }

    private String createFakeJwtToken() {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + 86400000); // +1 day
        return JWT.create()
                .withSubject("atai@gmail.com")
                .withExpiresAt(expiresAt)
                .sign(com.auth0.jwt.algorithms.Algorithm.HMAC256("secret"));
    }
}