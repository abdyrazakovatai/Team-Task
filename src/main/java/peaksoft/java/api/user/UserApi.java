package peaksoft.java.api.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import peaksoft.java.dto.request.UpdateRequest;
import peaksoft.java.dto.response.SimpleResponse;
import peaksoft.java.dto.response.user.UserResponse;
import peaksoft.java.dto.response.user.UsersResponse;
import peaksoft.java.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApi {
    private final UserService userService;

    @Secured({"ADMIN","MANAGER"})
    @GetMapping()
    public List<UsersResponse> getAll() {
        return userService.users();
    }

    @Secured({"ADMIN","MANAGER","USER"})
    @GetMapping("/{id}")
    public UsersResponse getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @Secured({"ADMIN","MANAGER","USER"})
    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id,
                                   @Valid @RequestBody UpdateRequest updateRequest) {
        return userService.updateUser(id,updateRequest);
    }

    @Secured("ADMIN")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteUser(@PathVariable Long id) {
        return userService.delete(id);
    }
}