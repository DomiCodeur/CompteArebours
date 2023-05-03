package compteAr.backend.entity;

import compteAr.backend.resources.UserResource;
import org.hibernate.validator.constraints.Length;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entité pour les utilisateurs.
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class UserEntity {

	@Id
    @GeneratedValue
    private Integer id;

    @Length(min = 3, max = 20, message = "Si complété, le nom d'utilisateur doit être entre 3 et 20 caractères")
    @Nullable
    private String username;

    @NotBlank(message = "L'email ne doit pas être vide")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le mot de passe ne doit pas être vide")
    @Length(min = 8, max = 70, message = "Le mot de passe doit être entre 8 et 70 caractères")
    private String password;

    private String signInMethod;

    private String timeUnit;

    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Permet de créer la resource correspondante.
     * 
     * @return un {@link UserResource}.
     */
	public UserResource toResource() {
		return UserResource.builder()
				.id(getId())
				.email(getEmail())
				.password(getPassword())
				.signInMethod(getSignInMethod())
				.build();
	}

}
