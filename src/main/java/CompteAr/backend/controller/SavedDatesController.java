package CompteAr.backend.controller;

import java.util.List;

import CompteAr.backend.resources.SavedDatesResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CompteAr.backend.service.SavedDatesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

/**
 * Endpoint utilisé pour la gestion des dates.
 *
 */
@RestController
@RequestMapping("/dates")
public class SavedDatesController {

	@Autowired
	private SavedDatesService savedDatesService;

	/**
	 * Récupération de toutes les dates sauvegardées.
	 * 
	 * @return une {@link ResponseEntity} 200 contenant toutes les
	 *         {@link SavedDatesResource}.
	 */
	@Operation(summary = "Récupération de toutes les dates en base de données.")
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "L'opération s'est correctement déroulée.", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SavedDatesResource.class))) }) })
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public ResponseEntity<List<SavedDatesResource>> getAllSavedDates() {
		return new ResponseEntity<List<SavedDatesResource>>(savedDatesService.findAll(), HttpStatus.OK);
	}

}
