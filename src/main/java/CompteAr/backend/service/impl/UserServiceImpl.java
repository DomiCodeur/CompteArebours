package CompteAr.backend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import CompteAr.backend.entity.UserEntity;
import CompteAr.backend.repository.UserRepository;
import CompteAr.backend.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import CompteAr.backend.service.UserService;

/**
 * Implémentation du service {@link UserService}.
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public List<UserResource> getAllUsers() {
        return userRepository.findAll().stream()
        		.map(UserEntity::toResource)
        		.collect(Collectors.toList());
    }

    @Override
    public Optional<UserResource> getUserById(Integer id) {
        return userRepository.findById(id)
        		.map(UserEntity::toResource);
    }

    @Override
    public Optional<UserResource> createUser(UserResource userResource) {
    	// Encodage du mot de passe.
    	String passwordEncoded = passwordEncoder.encode(userResource.getPassword());
    	userResource.setPassword(passwordEncoded);
    	
    	try {
    		//sauvegarde en base.
    		return Optional.of(userRepository.save(userResource.toEntity()).toResource());
    	} catch (Exception e) {
    		return Optional.empty();
    	}
    }
    
    @Override
    public Optional<UserResource> updateUser(Integer id, UserResource userResource) {
        Optional<UserEntity> userOpt = userRepository.findById(id);
        
        if(userOpt.isEmpty()) {
        	return Optional.empty();
        }
        
        UserEntity user = userOpt.get();
        user.setPassword(userResource.getPassword());
        user.setEmail(userResource.getEmail());
        
        return Optional.ofNullable(userRepository.save(user).toResource());
    }

    @Override
    public boolean deleteUser(Integer id) {
    	if(existsById(id)) {
    		userRepository.deleteById(id);
    		return true;
    	}

    	return false;
    }
    
    @Override
    public Optional<UserResource> findByEmail(String email) {
        return userRepository.findByEmail(email)
        		.map(userEntity -> UserResource.builder()
        				.id(userEntity.getId())
        				.email(userEntity.getEmail())
        				.password(userEntity.getPassword())
        				.signInMethod(userEntity.getSignInMethod())
        				.build());
    }

    @Override
    public boolean existsById(Integer id) {
        return userRepository.existsById(id);
    }


}
