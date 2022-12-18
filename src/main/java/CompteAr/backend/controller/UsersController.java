package CompteAr.backend.controller;

import CompteAr.backend.exception.ResourceNotFoundException;
import CompteAr.backend.model.Users;
import CompteAr.backend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
        @Autowired
        private UsersRepository usersRepository;


        @GetMapping("/getAllUsers")
        public List<Users> getAllUsers() {
                return usersRepository.findAll();
        }

        @PostMapping("/createUser")
        public Users createUser(@RequestBody Users user) throws ResourceNotFoundException {
                return usersRepository.save(user);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Users> getUserById(@PathVariable(value = "userId") long userId)
                throws ResourceNotFoundException {
                Users user = usersRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
                return ResponseEntity.ok().body(user);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Users> updateUser(@PathVariable(value = "id") long userId,
                                                @RequestBody Users userDetails) throws ResourceNotFoundException {
                Users user = usersRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

                user.setUsername(userDetails.getUsername());
                user.setPassword(userDetails.getPassword());
                user.setEmail(userDetails.getEmail());
                final Users updatedUser = usersRepository.save(user);
                return ResponseEntity.ok(updatedUser);
        }

        @DeleteMapping("/users/{id}")
        public ResponseEntity deleteUser(@PathVariable(value = "id") long userid) throws ResourceNotFoundException {
                Users user = usersRepository.findById(userid)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userid));

                usersRepository.delete(user);
                return ResponseEntity.ok().build();
        }
}
