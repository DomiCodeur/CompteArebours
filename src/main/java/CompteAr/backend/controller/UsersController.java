package CompteAr.backend.controller;

import CompteAr.backend.exception.ResourceNotFoundException;
import CompteAr.backend.model.Users;
import CompteAr.backend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Date;
import java.sql.Timestamp;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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

        @GetMapping("/getUserById")
        public ResponseEntity<Users> getUserById(@RequestParam(value = "id") Integer userId)
                throws ResourceNotFoundException {
                Users user = usersRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
                return ResponseEntity.ok().body(user);
        }

        @PutMapping("/updateUser")
        public ResponseEntity<Users> updateUser(@RequestParam(value = "id") Integer userId,
                                                @RequestBody Users userDetails) throws ResourceNotFoundException {
                Users user = usersRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

                user.setUsername(userDetails.getUsername());
                user.setPassword(userDetails.getPassword());
                user.setEmail(userDetails.getEmail());
                final Users updatedUser = usersRepository.save(user);
                return ResponseEntity.ok(updatedUser);
        }

        @DeleteMapping("/deleteUser")
        public ResponseEntity deleteUser(@RequestParam(value = "id") Integer userid) throws ResourceNotFoundException {
                Users user = usersRepository.findById(userid)
                        .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userid));

                usersRepository.delete(user);
                return ResponseEntity.ok().build();
        }

        private String generateToken(Users user) {
                // Générez un token de connexion unique pour cet utilisateur en utilisant leur ID et d'autres informations (comme la date actuelle)
                // Vous pouvez utiliser une bibliothèque de création de token comme JWT (Json Web Token) pour gérer cela
                // Assurez-vous que le token est assez sécurisé et ne peut pas être deviné facilement par quelqu'un d'autre
                return "TOKEN_HERE";
        }
        
        @PostMapping("/login")
        public ResponseEntity<Users> login(@RequestBody Users user) throws ResourceNotFoundException {
                        // Vérifiez si l'utilisateur existe en utilisant son email et son mot de passe
                Users loggedInUser = usersRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
                if (loggedInUser == null) {
                        // Si l'utilisateur n'existe pas, renvoyez une erreur
                        return ResponseEntity.badRequest().build();
                } else {
                        // Si l'utilisateur existe, générez un nouveau token de connexion et mettez à jour la date d'expiration du token
                        String token = generateToken();
                        Date expirationDate = calculateExpirationDate();
                        loggedInUser.setToken(token);
                        loggedInUser.setExpirationDate(expirationDate);
                        // Enregistrez l'utilisateur avec son nouveau token et sa nouvelle date d'expiration
                        usersRepository.save(loggedInUser);
                        return ResponseEntity.ok(loggedInUser);
                }
        }

        private Date calculateExpirationDate() {
        }

        @PostMapping("/logout")
        public ResponseEntity logout(@RequestBody Users user) throws ResourceNotFoundException {
                // Récupérez l'utilisateur en utilisant son email et son token de connexion
                Users loggedInUser = usersRepository.findByEmailAndToken(user.getEmail(), user.getToken());
                if (loggedInUser == null) {
                // Si l'utilisateur n'existe pas, renvoyez une erreur
                        return ResponseEntity.badRequest().build();
                } else {
                // Si l'utilisateur existe, mettez à jour son token de connexion et sa date d'expiration pour les rendre null
                        loggedInUser.setToken(null);
                        loggedInUser.setExpirationDate(null);
                // Enregistrez l'utilisateur avec ses nouvelles valeurs nulles
                        usersRepository.save(loggedInUser);
                        return ResponseEntity.ok().build();
                }
        }


}
