package peaksoft.java.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import peaksoft.java.dto.request.UpdateRequest;
import peaksoft.java.dto.response.SimpleResponse;
import peaksoft.java.dto.response.UserResponse;
import peaksoft.java.dto.response.UsersResponse;
import peaksoft.java.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApi {
    private final UserService userService;

//    GET /api/users - Бардык колдонуучулардын тизмесин алуу (АДМИН, ЖЕТЕКЧИ гана)
//    GET /api/users/{id} - Колдонуучу жөнүндө маалымат алуу
//    PUT /api/users/{id} - Колдонуучу маалыматын жаңыртуу
//    DELETE /api/users/{id} - Колдонуучуну өчүрүү (АДМИН гана)

    @Secured("{ADMIN,MANAGER}")
    @GetMapping()
    public List<UsersResponse> getAll() {
        return userService.users();
    }

    @GetMapping("/{id}")
    public UsersResponse getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UpdateRequest updateRequest) {
        return userService.updateUser(id,updateRequest);
    }

    @Secured("ADMIN")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteUser(@PathVariable Long id) {
        return userService.delete(id);
    }

}
