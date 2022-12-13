package CompteAr.backend.service;

import CompteAr.backend.model.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();
}
