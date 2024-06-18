package CompteAr.backend.controller;

import java.util.List;
import java.util.Optional;

import CompteAr.backend.resources.SavedDatesResource;
import CompteAr.backend.resources.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CompteAr.backend.service.SavedDatesService;
import CompteAr.backend.service.UserService;
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
 * Endpoint utilisé pour la gestion des utilisateurs.
 */
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private SavedDatesService savedDatesService;

	/**
	 * Création d'un utilisateur.
	 * 
	 * @param userResource resource contenant les informations de l'utilisateur.
	 * 
	 * @return une {@link ResponseEntity} 201 contenant un {@link UserResource} ou
	 *         une {@link ResponseEntity} 309 si l'utilisateur existe deja ou une
	 *         {@link ResponseEntity} 500 en cas de problème lors de la sauvegarde
	 *         en base de donnée.
	 */
	@Operation(summary = "Création d'un utilisateur.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "L'utilisateur a correctement été créé.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserResource.class)) }),
			@ApiResponse(responseCode = "409", description = "L'utilisateur existe deja en base de données.", content = @Content),
			@ApiResponse(responseCode = "500", description = "Erreur lors de la création de l'utilisateur.", content = @Content) })
	@PostMapping
	public ResponseEntity<UserResource> createUser(
			@Parameter(description = "La ressource users.") @Valid @RequestBody UserResource userResource) {
		// Vérification de la présence d'un utilisateur avec l'email fourni, si oui on
		// renvoi une erreur conflict.
		Optional<UserResource> existingUser = userService.findByEmail(userResource.getEmail());
		if (existingUser.isPresent()) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}

		// Sauvegarde de l'utilisateur.
		Optional<UserResource> savedUser = userService.createUser(userResource);
		if (savedUser.isPresent()) {
			return new ResponseEntity<>(savedUser.get(), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Récupération de tous les utilisateurs.
	 * 
	 * @return une {@link List} contenant tous les {@link UserResource} présents.
	 */
	@Operation(summary = "Récupération de tous les utilisateurs.")
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "L'opration s'est correctement déroulée.", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResource.class))) }) })
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public ResponseEntity<List<UserResource>> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	/**
	 * Réupération d'un utilisateur à partir de son id.
	 * 
	 * @param id l'id de l'utilisateur.
	 * 
	 * @return une {@link ResponseEntity} 200 contenant l'{@link UserResource}
	 *         correspondante à la recherche ou une {@link ResponseEntity} 404 si
	 *         l'utilisateur n'a pas été trouvé.
	 */
	@Operation(summary = "Récupération d'un utilisateur à partir de son id.")
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Récupération de l'utilisateur.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserResource.class)) }),
			@ApiResponse(responseCode = "404", description = "L'utilisateur n'existe pas en base de données.", content = @Content) })
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{id}")
	public ResponseEntity<UserResource> getUserById(
			@Parameter(description = "L'id de l'utilisateur.") @Valid @NotBlank @PathVariable Integer id) {
		return userService.getUserById(id).map(user -> new ResponseEntity<>(user, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * Mise à jour d'un utilisateur.
	 * 
	 * @param id           l'id de l'utilisateur à mettre à jour.
	 * @param userResource les données sous forme d'{@link UserResource}.
	 * 
	 * @return une {@link ResponseEntity} 200 en cas de réussite ou une
	 *         {@link ResponseEntity} 404 si l'utilisateur n'a pas été trouvé.
	 */
	@Operation(summary = "Mise à jour d'un utilisateur.")
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "L'utilisateur a bien été mis a jour.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserResource.class)) }),
			@ApiResponse(responseCode = "404", description = "L'utilisateur n'existe pas en base de données.", content = @Content) })
	@PreAuthorize("isAuthenticated()")
	@PutMapping("/{id}")
	public ResponseEntity<UserResource> updateUser(
			@Parameter(description = "L'id de l'utilisateur.") @Valid @NotBlank @PathVariable Integer id,
			@Parameter(description = "La ressource users.") @Valid @RequestBody UserResource userResource) {
		return userService.updateUser(id, userResource).map(user -> new ResponseEntity<>(user, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * Suppression d'un utilisateur.
	 * 
	 * @param id l'id de l'utilisateur à supprimer.
	 * 
	 * @return une {@link ResponseEntity} 204 si l'utilisateur a bien été supprimé,
	 *         une {@link ResponseEntity} 404 si l'utilisateur n'existe pas en base
	 *         de données.
	 */
	@Operation(summary = "Suppression d'un utilisateur à partir de son id.")
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "L'utilisateur a bien été supprimé en base de données.", content = @Content),
			@ApiResponse(responseCode = "404", description = "L'utilisateur n'existe pas en base de données.", content = @Content) })
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(
			@Parameter(description = "L'id de l'utilisateur.") @Valid @NotBlank @PathVariable Integer id) {
		if (userService.deleteUser(id)) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Récupération des dates pour un utilisateur donné.
	 * 
	 * @param userId l'id de l'utilisateur.
	 * 
	 * @return une {@link ResponseEntity} 200 contenant les dates ou une
	 *         {@link ResponseEntity} 404 si l'utilisateur n'a pas de dates.
	 */
	@Operation(summary = "Récupération des dates d'un utilisateur à partir de son id.")
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "L'opération s'est corretement déroulée.", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = SavedDatesResource.class))) }),
			@ApiResponse(responseCode = "404", description = "Aucune date trouvée pour l'utilisateur.", content = @Content) })
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{userId}/dates")
	public ResponseEntity<List<SavedDatesResource>> getSavedDatesByUserId(
			@Parameter(description = "L'id de l'utilisateur.") @Valid @NotBlank @PathVariable Integer userId) {
		List<SavedDatesResource> savedDates = savedDatesService.findByUserId(userId);
		if (!savedDates.isEmpty()) {
			return new ResponseEntity<>(savedDates, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Sauvegarde d'une date pour un utilisateur donné.
	 * 
	 * @param userId     l'id de l'utilisateur.
	 * @param savedDates la date à sauvegarder.O
	 * 
	 * @return une {@link ResponseEntity} 201 contenant la date sauvegardée ou une
	 *         {@link ResponseEntity} 500 en cas de problème.
	 */
	@Operation(summary = "Sauvegarde d'une date pour un utilisateur.")
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "La date a bien été sauvegardée.", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = SavedDatesResource.class)) }),
			@ApiResponse(responseCode = "500", description = "Erreur lors de la sauvegarde de la date.", content = @Content) })
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/{userId}/dates")
	public ResponseEntity<SavedDatesResource> createSavedDates(
			@Parameter(description = "L'id de l'utilisateur.") @Valid @NotBlank @PathVariable Integer userId,
			@Parameter(description = "La ressource date.") @Valid @RequestBody SavedDatesResource savedDates) {
		savedDates.setUserId(userId);
		Optional<SavedDatesResource> savedDate = savedDatesService.saveDate(savedDates);
		if (savedDate.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<SavedDatesResource>(savedDate.get(), HttpStatus.CREATED);
	}


	/**
	 * Suppression d'une date pour un utilisateur donné.
	 *
	 * @param userId  l'id de l'utilisateur.
	 * @param dateId  l'id de la date à supprimer.
	 *
	 * @return une {@link ResponseEntity} indiquant le résultat de l'opération.
	 */
	@Operation(summary = "Suppression d'une date pour un utilisateur donné.")
	@SecurityRequirement(name = "Bearer Authentication")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "La date a bien été supprimée.", content = @Content),
			@ApiResponse(responseCode = "404", description = "La date ou l'utilisateur n'existe pas.", content = @Content),
			@ApiResponse(responseCode = "403", description = "Opération non autorisée.", content = @Content)
	})
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/{userId}/dates/{dateId}")
	public ResponseEntity<Void> deleteSavedDate(
			@Parameter(description = "L'id de l'utilisateur.") @PathVariable Integer userId,
			@Parameter(description = "L'id de la date à supprimer.") @PathVariable Integer dateId) {
		boolean isDeleted = savedDatesService.deleteDate(userId, dateId);
		if (isDeleted) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
