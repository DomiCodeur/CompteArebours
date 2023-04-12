package compteAr.backend.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import compteAr.backend.exception.AuthenticationException;
import compteAr.backend.resources.AuthenticationResource;
import compteAr.backend.resources.AuthenticationResponse;
import compteAr.backend.resources.UserResource;
import compteAr.backend.service.AuthenticationService;
import compteAr.backend.service.UserService;

/**
 * Endpoint d'authentification.
 *
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService service;

	@Autowired
	private UserService userService;
	
	/**
	 * Authentification.
	 * 
	 * @param authenticationResource la ressource d'authentification: {@link AuthenticationResource}.
	 * 
	 * @return une {@link ResponseEntity} 200 contenant le jeton jwt
	 * ou une {@link ResponseEntity} 404 si l'utilisateur n'est pas présent dans la base de donnée.
	 */
	@PostMapping("/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationResource authenticationResource) {
		Optional<UserResource> user = userService.findByEmail(authenticationResource.getEmail());
		if (user.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		try {
			return ResponseEntity.ok(service.authenticate(authenticationResource));
		} catch (AuthenticationException e) {
			return ResponseEntity.status(401).build();
		}
	}
}
