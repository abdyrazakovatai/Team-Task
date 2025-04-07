package peaksoft.java.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import peaksoft.java.entity.User;
import peaksoft.java.entity.UserInfo;
import peaksoft.java.repository.UserRepository;

import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class JwtService {

    @Value("${app.security.jwt.secret_key}")
    private String secretKey;

    @Value("${app.security.jwt.expiration}")
    private Long expiration;

    @Value("${app.security.jwt.refreshExpiration}")
    private Long refreshExpiration;

    final UserRepository userRepository;

    public String createJwtToken(User user, boolean isRefreshToken) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        Long expirationDate = isRefreshToken ? refreshExpiration : expiration;

        JWTCreator.Builder token = JWT.create()
                .withClaim("id", user.getId())
                .withClaim("username", user.getUserName())
                .withIssuedAt(zonedDateTime.toInstant())
                .withExpiresAt(zonedDateTime.plusSeconds(expirationDate).toInstant());

        if (!isRefreshToken) {
            token
                    .withClaim("email", user.getEmail())
                    .withClaim("role", user.getRole().name());
    }
        return token.sign(getAlgorithm());


//        return new String(token
//                , zonedDateTime, zonedDateTime.plusSeconds(expiration)
//        );
    }

//    public String createRefreshToken(User user) {
//        ZonedDateTime zonedDateTime = ZonedDateTime.now();
//
//        String refreshToken = JWT.create()
//                .withClaim("id", user.getId())
//                .withClaim("email", user.getEmail())
//                .withIssuedAt(zonedDateTime.toInstant())
//                .withExpiresAt(zonedDateTime.plusSeconds(refreshExpiration).toInstant())
//                .sign(getAlgorithm());
//        return refreshToken;
//    }

    public UserInfo verifyJwtToken(String token) {
        Algorithm algorithm = getAlgorithm();
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT verify = verifier.verify(token);
        String email = verify.getClaim("email").asString();
        User user = userRepository.findUserByEmail(email);
        return new UserInfo(user);

    }

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secretKey);
    }
}

