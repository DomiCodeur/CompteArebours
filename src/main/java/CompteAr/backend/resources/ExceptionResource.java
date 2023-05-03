package compteAr.backend.resources;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

/**
 * Ressource utilisée pour gérer les exceptions qui doivent être envoyées dans
 * la réponse d'un appel Rest.
 *
 */
@Data
@Builder
public class ExceptionResource {

	private Date date;

	private String description;

	private String message;

}
