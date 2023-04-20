package compteAr.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import compteAr.backend.resources.SavedDatesResource;
import compteAr.backend.service.SavedDatesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

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

	/**
	 * Suppression d'une date.
	 * 
	 * @param dateId l'id de la date sauvegardée.
	 * @return une {@link ResponseEntity} 200 en cas de succes, une
	 *         {@link ResponseEntity} 404 si l'utilisateur n'a pas été trouvé.
	 */
	@Operation(summary = "Suppression d'une date à partir de son id.")
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "La date a bien été supprimée.", content = @Content),
			@ApiResponse(responseCode = "404", description = "La date n'existe pas en base de donnée.", content = @Content) })
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/{dateId}")
	public ResponseEntity<Void> deleteSavedDate(
			@Parameter(description = "L'id de la date à supprimer.") @Valid @NotBlank @PathVariable Integer dateId) {
		if (savedDatesService.deleteDate(dateId)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
