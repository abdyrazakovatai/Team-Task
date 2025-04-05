package peaksoft.java.dto.response.jwt;

import java.time.ZonedDateTime;

public record JwtTokenResponse(
        String token,
        ZonedDateTime issueAt,
        ZonedDateTime expiresAt
) {

}
