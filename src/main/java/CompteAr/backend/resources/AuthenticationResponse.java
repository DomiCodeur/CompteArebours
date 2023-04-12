package compteAr.backend.resources;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Réponse pour l'authentification.
 */
@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse {

	/**
	 * Le token jwt.
	 */
	private String token;
	
	/**
	 * L'id de l'utilisateur.
	 */
	private Integer userId;
	
	/**
	 * Message d'erreur.
	 */
	private String error;

}
