package CompteAr.backend.resources;

import java.util.Date;

import CompteAr.backend.entity.SavedDatesEntity;
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
    private Integer userId;

    /**
     * la {@link Date} associée.
     */
    private Date date;

    /**
     * le nom sauvegardé.
     */
    private String name;
    
    /**
     * Transformation en entité.
     * @return un {@link SavedDatesEntity}.
     */
    public SavedDatesEntity toEntity() {
    	return SavedDatesEntity.builder()
    			.id(id)
    			.userId(userId)
    			.date(date)
    			.name(name)
    			.build();
    }
}
