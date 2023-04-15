package CompteAr.backend.resources;

import lombok.Builder;
import lombok.Data;

/**
 * Ressource utilisée pour l'authentification.
 *
 */
@Data
@Builder
public class AuthenticationResource {

	private String email;

	private transient String password;
}
