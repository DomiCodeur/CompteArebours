package compteAr.backend.resources;

import compteAr.backend.entity.Role;
import compteAr.backend.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * Resource utilisé pour la gestion des utilisateurs.
 */
@Data
@Builder
public class UserResource {

	private final static String TIME_UNIT = "dodos";

	/**
	 * Id de l'utilisateur.
	 */
	private Integer id;

	/**
	 * Email de l'utilisateur.
	 */
	@NotBlank(message = "L'email ne doit pas être vide")
	@Email
	private String email;

	/**
	 * Mot de passe de l'utilisateur.
	 */
	@NotBlank(message = "Le mot de passe ne doit pas être vide")
	@Size(min = 8, max = 15, message = "Le mot de passe doit être entre 8 et 15 caractères")
	private String password;

	/**
	 * Méthode de connexion utilisée: email ou google.
	 */
	@NotBlank(message = "la méthode d'enregistrement doit être renseignée.")
	private String signInMethod;

	/**
	 * Permet de créer l'entité correspondante.
	 * 
	 * @return une {@link UserEntity}.
	 */
	public UserEntity toEntity() {
		return UserEntity.builder().id(id).email(getEmail()).password(getPassword()).signInMethod(getSignInMethod())
				.timeUnit(TIME_UNIT).role(Role.USER).build();
	}

}
