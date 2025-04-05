package peaksoft.java.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import peaksoft.java.enums.Role;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserInfo {
    String username;
    String email;
    Role role;
}
