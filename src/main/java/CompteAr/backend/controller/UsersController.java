package CompteAr.backend.controller;

import CompteAr.backend.model.Users;
import CompteAr.backend.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {
        private final UsersRepository usersRepository;

        @GetMapping
        public List<Users> getAllUsers() {
                return usersRepository.findAll();
        }

        @PostMapping
        public ResponseEntity<Users> createUser(@RequestBody Users user) {
                Optional<Users> existingUser = Optional.ofNullable(usersRepository.findByEmail(user.getEmail()));
                if (existingUser.isPresent()) {
                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
                Users savedUser = usersRepository.save(user);
                return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Users> getUserById(@PathVariable Integer id) {
                return usersRepository.findById(id)
                        .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @PutMapping("/{id}")
        public ResponseEntity<Users> updateUser(@PathVariable Integer id, @RequestBody Users userDetails) {
                return usersRepository.findById(id)
                        .map(user -> {
                                user.setUsername(userDetails.getUsername());
                                user.setPassword(userDetails.getPassword());
                                user.setEmail(userDetails.getEmail());
                                final Users updatedUser = usersRepository.save(user);
                                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
                        })
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity deleteUser(@PathVariable Integer id) {
                if (usersRepository.existsById(id)) {
                        usersRepository.deleteById(id);
                        return new ResponseEntity<>(HttpStatus.OK);
                } else {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
        }



}
