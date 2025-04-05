package peaksoft.java.dto.response;

import org.springframework.http.HttpStatus;

public record SimpleResponse(
        HttpStatus status,
        String message
) {
}
