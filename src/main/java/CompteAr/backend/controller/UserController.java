package CompteAr.backend.controller;

import CompteAr.backend.model.*;
import CompteAr.backend.repository.UserRepository;
import CompteAr.backend.service.AuthenticationService;
import CompteAr.backend.service.JwtService;
import CompteAr.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

        @Autowired
        private  UserService userService;

        @Autowired
        private AuthenticationService service;

        @GetMapping
        public List<User> getAllUsers() {
                return userService.getAllUsers();
        }

        @PostMapping
        public ResponseEntity<CreateUser> createUser(@RequestBody User user) {
                Optional<User> existingUser = userService.findByEmail(user.getEmail());
                if (existingUser.isPresent()) {
                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
                User savedUser = userService.save(user);
                RegisterRequest request = new RegisterRequest();
                request.setEmail(savedUser.getEmail());
                request.setPassword(savedUser.getPassword());
                String token = service.register(request);
                CreateUser createUser = new CreateUser(user, token);
                return new ResponseEntity<>(createUser, HttpStatus.CREATED);
        }

        @PostMapping("/login")
        public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
                AuthenticationResponse response = service.authenticate(request);
                User user = userService.findByEmail(request.getEmail())
                        .orElse(null);
                if (user == null) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                response.setUserId(user.getId());
                return ResponseEntity.ok(response);
        }

        @GetMapping("/{id}")
        public ResponseEntity<User> getUserById(@PathVariable Integer id) {
                return userService.findById(id)
                        .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @PutMapping("/{id}")
        public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User userDetails) {
                return userService.findById(id)
                        .map(user -> {
                                user.setPassword(userDetails.getPassword());
                                user.setEmail(userDetails.getEmail());
                                final User updatedUser = userService.save(user);
                                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
                        })
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity deleteUser(@PathVariable Integer id) {
                if (userService.findById(id).isPresent()) {
                        userService.deleteById(id);
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
        }



}
