package peaksoft.java.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import peaksoft.java.entity.User;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    final JwtService jwtService;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer";

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String headerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (headerToken != null && headerToken.startsWith(BEARER)) {
            String token = headerToken.substring(7);
            try {
                User user = jwtService.verifyJwtToken(token);
                if (user != null) {
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    user.getAuthorities()
                            )
                    );
                }
            } catch (JWTVerificationException e) {
                System.err.println("Ошибка проверки токена: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
