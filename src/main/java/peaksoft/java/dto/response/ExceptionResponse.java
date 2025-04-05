package peaksoft.java.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ExceptionResponse(
        HttpStatus status,
        String exceptionsClassName,
        String message) {
}
