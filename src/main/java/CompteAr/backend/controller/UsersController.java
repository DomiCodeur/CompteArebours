package CompteAr.backend.controller;

import CompteAr.backend.exception.ResourceNotFoundException;
import CompteAr.backend.model.Users;
import CompteAr.backend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UsersController {
        @Autowired
        private UsersRepository usersRepository;

        @GetMapping("/getAllUsers")
        public List<Users> getAllUsers() {
                return usersRepository.findAll();
        }

        @GetMapping("/getUser/{id}")
        public ResponseEntity<Users> getUserById(@PathVariable(value = "id") Long userId)
                throws ResourceNotFoundException {
                Users user = usersRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
                return ResponseEntity.ok().body(user);
        }

        @PutMapping("/updateUser/{id}")
        public ResponseEntity<Users> updateUser(@PathVariable(value = "id") Long userId,
                                                @RequestBody Users userDetails) throws ResourceNotFoundException {
                Users user = usersRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

                user.setUsername(userDetails.getUsername());
                user.setPassword(userDetails.getPassword());
                user.setEmail(userDetails.getEmail());
                final Users updatedUser = usersRepository.save(user);
                return ResponseEntity.ok(updatedUser);
        }

        @DeleteMapping("/deleteUser/{id}")
        public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
                throws ResourceNotFoundException {
                Users user = usersRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

                usersRepository.delete(user);
                Map<String, Boolean> response = new HashMap<>();
                response.put("deleted", Boolean.TRUE);
                return response;
        }
}
