package CompteAr.backend.controller;

import CompteAr.backend.model.User;
import CompteAr.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
        private final UserRepository userRepository;

        @GetMapping
        public List<User> getAllUsers() {
                return userRepository.findAll();
        }

        @PostMapping
        public ResponseEntity<User> createUser(@RequestBody User user) {
                Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
                if (existingUser.isPresent()) {
                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
                User savedUser = userRepository.save(user);
                return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        public ResponseEntity<User> getUserById(@PathVariable Integer id) {
                return userRepository.findById(id)
                        .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @PutMapping("/{id}")
        public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails) {
                return userRepository.findById(id)
                        .map(user -> {
                                user.setPassword(userDetails.getPassword());
                                user.setEmail(userDetails.getEmail());
                                final User updatedUser = userRepository.save(user);
                                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
                        })
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity deleteUser(@PathVariable Integer id) {
                if (userRepository.existsById(id)) {
                        userRepository.deleteById(id);
                        return new ResponseEntity<>(HttpStatus.OK);
                } else {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
        }



}
