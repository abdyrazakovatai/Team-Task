//package peaksoft.java.dto.request;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import peaksoft.java.enums.Role;
//
//public record UpdateRequest(
//        @NotBlank(message = "Username cannot be blank.")
//        String username,
//
//        @NotBlank(message = "Email cannot be blank.")
//        @Email(message = "Invalid email format.")
//        String email,
//
//        @NotBlank(message = "First name cannot be blank.")
//        String firstName,
//
//        @NotBlank(message = "Last name cannot be blank.")
//        String lastName,
//
//        @NotNull(message = "Role cannot be null.")
//        Role role
//) {
//}
