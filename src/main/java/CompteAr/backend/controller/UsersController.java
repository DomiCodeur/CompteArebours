package CompteAr.backend.controller;

import CompteAr.backend.model.Users;
import CompteAr.backend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UsersController {
        @Autowired
        private UsersRepository usersRepository;

        @GetMapping("/getUsers")
        public List<Users> getUsers() {
            return usersRepository.findAll();
        }

        @PostMapping("/createUser")
        public Users createUser( @RequestBody Users user) {
                return usersRepository.save(user);
        }

        @DeleteMapping("/deleteUser")
        public void deleteUser(@RequestBody Users user) {
                usersRepository.delete(user);
        }

        @PutMapping("/updateUser")
        public Users updateUser(@RequestBody Users user) {
                return usersRepository.save(user);
        }

}
