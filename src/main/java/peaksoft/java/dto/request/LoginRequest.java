package peaksoft.java.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
