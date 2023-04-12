package compteAr.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import compteAr.backend.entity.SavedDatesEntity;

import java.util.List;
import java.util.Optional;

/**
 * {@link Repository} pour la gestion des dates.
 *
 */
@Repository
public interface SavedDatesRepository extends JpaRepository<SavedDatesEntity, Integer> {

//    SavedDatesEntity findByUserIdAndName(Integer userId, String name);

	/**
	 * Récupération des dates d'un utilisateur.
	 * 
	 * @param userId l'id de l'utilisateur sur lequel faire la recherche.
	 * 
	 * @return une {@link List} de {@link SavedDatesEntity}.
	 */
    List<SavedDatesEntity> findByUserId(Integer userId);

//    void delete(SavedDatesEntity savedDates);
//
//    Optional<SavedDatesEntity> findByIdAndUserId(Integer dateId, Integer userId);
}