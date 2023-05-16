package compteAr.backend.service;

import java.util.Optional;


import compteAr.backend.repository.UserRepository;
import compteAr.backend.resources.AuthenticationResource;
import compteAr.backend.resources.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import compteAr.backend.entity.UserEntity;
import compteAr.backend.exception.AuthenticationException;
import compteAr.backend.model.UserDetail;

/**
 * Service gérant l'authentification.
 */
@Service
public class AuthenticationService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * 
	 * @param request
	 * @return
	 * @throws AuthenticationException
	 */
	public AuthenticationResponse authenticate(AuthenticationResource request) throws AuthenticationException {
		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("Email ou mot de passe incorrect");
		}

		Optional<UserEntity> user = repository.findByEmail(request.getEmail());
		UserDetail userDetail = user.map(userEntity -> UserDetail.builder().userName(userEntity.getEmail())
				.password(userEntity.getPassword()).build()).get();
		String jwtToken = jwtService.generateToken(userDetail);
		return new AuthenticationResponse(jwtToken, user.get().getId(), "");
	}

}

