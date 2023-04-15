package CompteAr.backend.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import CompteAr.backend.service.SavedDatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CompteAr.backend.resources.SavedDatesResource;
import CompteAr.backend.resources.UserResource;
import CompteAr.backend.service.UserService;

/**
 * Endpoint utilisé pour la gestion des utilisateurs.
 */
@RestController
@RequestMapping("/users")
@Validated
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private SavedDatesService savedDatesService;
	
	/**
	 * Création d'un utilisateur.
	 * 
	 * @param userResource dto contenant les informations de l'utilisateur.
	 * 
	 * @return une {@link ResponseEntity} 201 contenant un {@link UserResource} 
	 * ou une {@link ResponseEntity} 309 si l'utilisateur existe deja 
	 * ou une {@link ResponseEntity} 500 en cas de problème lors de la sauvegarde en base de donnée.
	 */
	@PostMapping
	public ResponseEntity<UserResource> createUser(@Valid @RequestBody UserResource userResource) {
		//Vérification de la présence d'un utilisateur avec l'email fourni, si oui on renvoi une erreur conflict.
		Optional<UserResource> existingUser = userService.findByEmail(userResource.getEmail());
		if (existingUser.isPresent()) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		//Sauvegarde de l'utilisateur.
		Optional<UserResource> savedUser = userService.createUser(userResource);
		if(savedUser.isPresent()) {
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
	@PreAuthorize("isAuthenticated()")
	@GetMapping
	public List<UserResource> getAllUsers() {
		return userService.getAllUsers();
	}

	/**
	 * Réupération d'un utilisateur à partir de son id.
	 * 
	 * @param id l'id de l'utilisateur.
	 * 
	 * @return une {@link ResponseEntity} 200 contenant l'{@link UserResource} correspondante à la recherche
	 * ou une {@link ResponseEntity} 404 si l'utilisateur n'a pas été trouvé.
	 */
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{id}")
	public ResponseEntity<UserResource> getUserById(@PathVariable Integer id) {
		return userService.getUserById(id)
				.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * Mise à jour d'un utilisateur.
	 * 
	 * @param id l'id de l'utilisateur à mettre à jour.
	 * @param userResource les données sous forme d'{@link UserResource}.
	 * 
	 * @return une {@link ResponseEntity} 200 en cas de réussite 
	 * ou une {@link ResponseEntity} 404 si l'utilisateur n'a pas été trouvé.
	 */
	@PreAuthorize("isAuthenticated()")
	@PutMapping("/{id}")
	public ResponseEntity<UserResource> updateUser(@PathVariable Integer id, @Valid @RequestBody UserResource userResource) {
		return userService.updateUser(id, userResource)
				.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
				.orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * Suppression d'un utilisateur.
	 * 
	 * @param id l'id de l'utilisateur à supprimer.
	 * 
	 * @return une {@link ResponseEntity} 204.
	 */
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Récupération des dates pour un utilisateur donné.
	 * 
	 * @param userId l'id de l'utilisateur.
	 * 
	 * @return une {@link ResponseEntity} 200 contenant les dates 
	 * ou une {@link ResponseEntity} 404 si l'utilisateur n'a pas de dates.
	 */
	@PreAuthorize("isAuthenticated()")
    @GetMapping("/{userId}/dates")
    public ResponseEntity<List<SavedDatesResource>> getSavedDatesByUserId(@PathVariable Integer userId) {
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
	 * @param userId l'id de l'utilisateur.
	 * @param savedDates la date à sauvegarder.O
	 * 
	 * @return une {@link ResponseEntity} 201 contenant la date sauvegardée 
	 * ou une {@link ResponseEntity} 500 en cas de problème.
	 */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{userId}/dates")
    public ResponseEntity<SavedDatesResource> createSavedDates(@PathVariable Integer userId, @RequestBody SavedDatesResource savedDates) {
        savedDates.setUserId(userId);
        Optional<SavedDatesResource> savedDate = savedDatesService.saveDate(savedDates);
        if(savedDate.isEmpty()) {
        	return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<SavedDatesResource>(savedDate.get(), HttpStatus.CREATED);
    }
    
}
