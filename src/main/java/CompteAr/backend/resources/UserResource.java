package compteAr.backend.resources;

import compteAr.backend.entity.Role;
import compteAr.backend.entity.UserEntity;
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
	private String email;
	
	/**
	 * Mot de passe de l'utilisateur.
	 */
	private String password;
	
	/**
	 * Méthode de connexion utilisée: email ou google.
	 */
	private String signInMethod;
    
	/**
	 * Permet de créer l'entité correspondante.
	 * 
	 * @return une {@link UserEntity}.
	 */
	public UserEntity toEntity() {
		return UserEntity.builder()
				.id(id)
				.email(getEmail())
				.password(getPassword())
				.signInMethod(getSignInMethod())
				.timeUnit(TIME_UNIT)
				.role(Role.USER)
				.build();
	}
	
}
