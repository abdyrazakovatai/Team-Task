package peaksoft.java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.java.entity.User;
import peaksoft.java.exception.NotFoundException;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    default User findUserByEmail(String email) {
        User user = findByEmail(email).orElseThrow(
                () -> new NotFoundException("User with email: " + email + " not found"));
        return user;
    }

    User getUserByEmail(String email);

    boolean existsUserByEmail(String email);

    User getUserById(Long id);
}
