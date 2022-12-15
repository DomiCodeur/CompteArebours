package CompteAr.backend.controller;

import CompteAr.backend.model.Users;
import CompteAr.backend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class UsersController {

        @GetMapping("/hello")
        public String hello() {
            return "Hello World";
        }

        @Autowired
        private UsersRepository usersRepository;

        @GetMapping("/getUsers")
        public List<Users> getUsers() {
            return usersRepository.findAll();
        }

        /**
         * Enregistre un nouvel utilisateur
         * @param user
         * @throws SQLException
         */
        @PostMapping("/createUser")
        public Users createUser( @RequestBody Users user) {
                return usersRepository.save(user);
        }

        /**
         * Supprime un utilisateur
         * @param user
         * @throws SQLException
         */
        @DeleteMapping("/deleteUser")
        public void deleteUser(@RequestBody Users user) {
                usersRepository.delete(user);
        }

        /**
         * Met à jour un utilisateur
         * @param user
         * @throws SQLException
         */
        @PutMapping("/updateUser")
        public Users updateUser(@RequestBody Users user) {
                return usersRepository.save(user);
        }

}
