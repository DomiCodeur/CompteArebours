package CompteAr.backend.resources;

import java.util.Date;

import CompteAr.backend.entity.SavedDatesEntity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * Ressource utilisée pour la gestion des dates.
 * 
 */
@Data
@Builder
public class SavedDatesResource {

	/**
	 * Id de la date.
	 */
	private Integer id;

	/**
	 * Id de l'utilisateur.
	 */
	@NotBlank(message = "l'id de l'utilisateur doit être renseigné.")
	private Integer userId;

	/**
	 * la {@link Date} associée.
	 */
	@NotNull(message = "la date ne doit pas être nulle.")
	@Future(message = "impossible de saisir une date passée.")
	private Date date;

	/**
	 * le nom sauvegardé.
	 */
	@NotBlank(message = "le nom de la date doit être renseigné.")
	private String name;

	/**
	 * Transformation en entité.
	 * 
	 * @return un {@link SavedDatesEntity}.
	 */
	public SavedDatesEntity toEntity() {
		return SavedDatesEntity.builder().id(id).userId(userId).date(date).name(name).build();
	}
}
