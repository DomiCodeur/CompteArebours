package compteAr.backend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import compteAr.backend.entity.UserEntity;

/**
 * {@link Repository} utilisé pour la gestion des utilisateurs.
 * 
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

	/**
	 * Récupération d'un utilisateur à partir de son email.
	 * 
	 * @param email l'email sur lequel faire la recherche.
	 * @return un {@link Optional} contenant un {@link UserEntity}, un optional vide
	 *         si l'utilisateur n'est pas présent en base.
	 */
	Optional<UserEntity> findByEmail(String email);
}
