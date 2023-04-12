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
	 * @return une {@link ResponseEntity} 200 contenant toutes les {@link SavedDatesResource}. 
	 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<SavedDatesResource>> getAllSavedDates() {
        return new ResponseEntity<List<SavedDatesResource>>(savedDatesService.findAll(), HttpStatus.OK);
    }
    
    /**
     * Suppression d'une date.
     * 
     * @param dateId l'id de la date sauvegardée.
     * @return
     */
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{dateId}")
    public ResponseEntity<Void> deleteSavedDate(@PathVariable Integer dateId) {
    	if(savedDatesService.deleteDate(dateId)) {
    		return new ResponseEntity<>(HttpStatus.OK);
    	} else {
    		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    	}
    }
}

