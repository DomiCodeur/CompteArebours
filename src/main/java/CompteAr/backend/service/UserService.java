package compteAr.backend.service;

import java.util.List;
import java.util.Optional;

import compteAr.backend.entity.UserEntity;
import compteAr.backend.resources.UserResource;

/**
 * Service de gestion des utilisateurs.
 */
public interface UserService {
	
	/**
	 * Récupération de tous les utilisateurs.
	 * 
	 * @return une {@link List} d'{@link UserResource}.
	 */
    List<UserResource> getAllUsers();
    
    /**
     * Récupération d'un utilisateur à partir de son id.
     * 
     * @param id l'id de l'utilisateur.
     * 
     * @return un {@link Optional} contenant l'{@link UserEntity} trouvé sinon un {@link Optional} vide.
     */
    Optional<UserResource> getUserById(Integer id);
    
    /**
     * Création d'un utilisateur.
     * 
     * @param user une {@link UserResource} representant l'utilisateur à creer.
     * 
     * @return un {@link Optional} contenant l'{@link UserResource} créé, un {@link Optional} vide sinon.
     */
    Optional<UserResource> createUser(UserResource user);
    
    /**
     * Mise a jour d'un utilisateur.
     * 
     * @param id l'id de l'utilisateur.
     * @param userResource les données de la ressource à mettre à jour.
     * 
     * @return un {@link Optional} contenant la ressource mise à jour, un {@link Optional} sinon.
     */
    Optional<UserResource> updateUser(Integer id, UserResource userResource);
    
    /**
     * Suppression d'un utilisateur.
     * 
     * @param id l'id de l'utilisateur à supprimer.
     */
    void deleteUser(Integer id);
    
    /**
     * Récupération d'un utilisateur à partir de son email.
     * 
     * @param email l'email sur lequel faire la recherche.
     * 
     * @return un {@link UserResource} correspondant à la recherche, un {@link Optional} vide sinon.
     */
    Optional<UserResource> findByEmail(String email);
    
    /**
     * Vérifie si un utilisateur existe.
     * 
     * @param id l'id de l'utilisateur sur lequel faire la recherche.
     * 
     * @return true si l'utilisateur existe en base, false sinon.
     */
    boolean existsById(Integer id);
    
}
