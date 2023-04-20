package compteAr.backend.service;

import java.util.List;
import java.util.Optional;

import compteAr.backend.entity.SavedDatesEntity;
import compteAr.backend.resources.SavedDatesResource;

/**
 * Service gérant les dates.
 *
 */
public interface SavedDatesService {

	/**
	 * Récupération de toutes les dates.
	 * 
	 * @return une {@link List} de {@link SavedDatesEntity}.
	 */
	List<SavedDatesResource> findAll();

	/**
	 * Récupération des dates pour un utilisateur donné.
	 * 
	 * @param userId l'id de l'utilisateur sur lequel faire la recherche.
	 * 
	 * @return une {@link List} de {@link SavedDatesEntity} pour cet utilisateur.
	 */
	List<SavedDatesResource> findByUserId(Integer userId);
	
	/**
	 * Fonction de sauvegarde d'une date.
	 * 
	 * @param savedDates la date à sauvegarder au format {@link SavedDatesEntity}.
	 * 
	 * @return un {@link Optional} contenant la date sauvegardée, un {@link Optional} vide si problème.
	 */
	Optional<SavedDatesResource> saveDate(SavedDatesResource savedDates);
	
	/**
	 * Suppression d'un date donnée.
	 * 
	 * @param dateId l'id de la date à supprimer.
	 * @return true si l'opération a réussie, false si la date ne peut etre trouvée.
	 */
	boolean deleteDate(Integer dateId);
	
}
