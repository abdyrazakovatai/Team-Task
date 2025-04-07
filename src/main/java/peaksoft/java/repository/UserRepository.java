package peaksoft.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.java.entity.User;
import peaksoft.java.entity.UserInfo;
import peaksoft.java.exception.NotFoundException;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    default User findUserByEmail(String email) {
        return findByEmail(email).orElseThrow(
                () -> new NotFoundException("User with email: " + email + " not found"));
    }

    UserInfo getUserByEmail(String email);

    boolean existsUserByEmail(String email);

    default User getUserById(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("User with id: " + id + " not found")
        );
    }
}
