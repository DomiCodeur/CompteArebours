package CompteAr.backend.service;

import CompteAr.backend.model.User;
import CompteAr.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  implements  IUserService {
    @Autowired
    private UserRepository repository;

    @Override
    public List<User> findAll() {

        var users = (List<User>) repository.findAll();

        return users;
    }
}

