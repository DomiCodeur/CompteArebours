package compteAr.backend.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * Ressource utilisée pour l'authentification.
 *
 */
@Data
@Builder
public class AuthenticationResource {

	@NotBlank(message = "L'email ne doit pas être vide")
	private String email;

	@NotBlank(message = "Le mot de passe ne doit pas être vide")
	@Size(min = 8, max = 15, message = "Le mot de passe doit être entre 8 et 15 caractères")
	private String password;
}
