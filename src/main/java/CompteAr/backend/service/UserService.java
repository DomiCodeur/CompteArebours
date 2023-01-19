package CompteAr.backend.service;

import java.util.List;
import java.util.Optional;

import CompteAr.backend.model.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Integer id);
    User createUser(User user);
    User updateUser(Integer id, User userDetails);
    ResponseEntity<Void> deleteUser(Integer id);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Integer id);

    boolean existsById(Integer id);

    void deleteById(Integer id);

    User save(User user);
}
