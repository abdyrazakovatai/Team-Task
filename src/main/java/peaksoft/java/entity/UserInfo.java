//package peaksoft.java.entity;
//
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import peaksoft.java.enums.Role;
//
//import java.util.Collection;
//import java.util.List;
//
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@Builder
//public class UserInfo implements UserDetails {
//    String userName;
//    String email;
//    String password;
//    Role role;
//    User user;
//
//    public UserInfo(User user) {
//        this.userName = user.getUserName();
//        this.email = user.getEmail();
//        this.password = user.getPassword();
//        this.role = user.getRole();
//        this.user = user;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//}
