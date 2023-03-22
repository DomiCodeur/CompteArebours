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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Validated
public class UserController {

        @Autowired
        private  UserService userService;

        @Autowired
        private AuthenticationService service;

        @PostMapping("/createUser")
        public ResponseEntity<UserInfo> createUser(@Valid @RequestBody User user) {
                Optional<User> existingUser = userService.findByEmail(user.getEmail());
                if (existingUser.isPresent()) {
                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                }
                User savedUser = userService.save(user);
                savedUser.setRole(Role.USER);
                userService.save(savedUser);
                RegisterRequest request = new RegisterRequest();
                request.setEmail(savedUser.getEmail());
                request.setPassword(savedUser.getPassword());
                request.setSignInMethod(savedUser.getSignInMethod());
                request.setTimeUnit("dodos");
                UserInfo userInfo = service.register(request);
                return new ResponseEntity<>(userInfo, HttpStatus.CREATED);
        }


        @PostMapping("/login")
        public ResponseEntity<UserInfo> login(@Valid @RequestBody AuthenticationRequest request) {
                User user = userService.findByEmail(request.getEmail())
                        .orElse(null);
                if (user == null) {
                        return new ResponseEntity<>(new UserInfo(null, null, null, null, "Email inconnu"), HttpStatus.NOT_FOUND);
                }

                try {
                        AuthenticationResponse response = service.authenticate(request);
                        UserInfo userInfo = new UserInfo(user.getId(), user.getEmail(), user.getTimeUnit(), response.getToken(), null);
                        return new ResponseEntity<>(userInfo, HttpStatus.OK);
                } catch (AuthenticationException ex) {
                        return new ResponseEntity<>(new UserInfo(null, null, null, null, "Erreur de mot de passe"), HttpStatus.UNAUTHORIZED);
                }
        }

        @PreAuthorize("isAuthenticated()")
        @GetMapping
        public List<User> getAllUsers() {
                return userService.getAllUsers();
        }


        @PreAuthorize("isAuthenticated()")
        @GetMapping("/{id}")
        public ResponseEntity<User> getUserById(@PathVariable Integer id) {
                return userService.findById(id)
                        .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @PreAuthorize("isAuthenticated()")
        @PutMapping("/{id}")
        public ResponseEntity<User> updateUser(@PathVariable Integer id,@Valid  @RequestBody User userDetails) {
                return userService.findById(id)
                        .map(user -> {
                                user.setPassword(userDetails.getPassword());
                                user.setEmail(userDetails.getEmail());
                                final User updatedUser = userService.save(user);
                                return new ResponseEntity<>(updatedUser, HttpStatus.OK);
                        })
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        @PreAuthorize("isAuthenticated()")
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
