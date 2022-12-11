package CompteAr.backend.User;

import CompteAr.backend.User.User;
import CompteAr.backend.User.UserManagerDAO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.SQLException;
import java.util.List;
import java.util.Date;

public class UserManager {

    private User user;
    private List<User> users;

    public UserManager() {
    }

    /**
     * enregistre un nouvel utilisateur avec le nom d'utilisateur et le mot de passe spécifiés.
     * @param user
     * @throws SQLException
     */
    @PostMapping("/registerUser")
    public void registerUser(@RequestBody User user) throws SQLException {
        // Récupérer les données de l'utilisateur depuis l'objet RequestBody
        String username = user.getUsername();
        String password = user.getPassword();

        // Exécuter la méthode registerUser() pour enregistrer l'utilisateur dans la base de données
        UserManagerDAO.registerUser(username, password);
    }

    /**
     * authentifie l'utilisateur avec le nom d'utilisateur et le mot de passe spécifiés, et retourne un objet utilisateur si l'authentification réussit, ou **`null`** si elle échoue.
     * @param username
     * @param password
     * @return user
     * @throws SQLException
     */
    @PostMapping("/authenticateUser")
    public User authenticateUser(String username, String password) throws SQLException {
        // Exécuter la méthode authenticateUser() pour authentifier l'utilisateur dans la base de données
        User user = UserManagerDAO.authenticateUser(username, password);
        return user;
    }

    /**
     * retourne la liste des dates sauvegardées par l'utilisateur spécifié.
     * @param user
     * @return dates
     * @throws SQLException
     */
    public List<Date> getSavedDates(User user) {
        return user.getSavedDates();
    }

    /**
     * enregistre une nouvelle date pour l'utilisateur spécifié.
     * @param user
     * @param date
     * @throws SQLException
     */
    public void saveDate(User user, Date date) {
        user.getSavedDates().add(date);
    }

    /**
     * supprime la date spécifiée de la liste des dates sauvegardées pour l'utilisateur spécifié.
     * @param user
     * @param date
     * @throws SQLException
     */
    public void deleteDate(User user, Date date) {
        user.getSavedDates().remove(date);
    }


    /**
     * retourne l'utilisateur correspondant à l'identifiant spécifié.
     * @param id
     * @return
     */
    public static User getUser(int id) {
        return User.getUser(id);
    }

    /**
     * retourne la liste de tous les utilisateurs.
     * @return users
     */
    public static List<User> getUsers() {
        return User.getUsers();
    }

    /**
     * ajoute l'utilisateur spécifié à la liste des utilisateurs.
     * @param user
     */
    public static void addUser(User user) {
        User.addUser(user);
    }

    /**
     * supprime l'utilisateur spécifié de la liste des utilisateurs.
     * @param user
     */
    public static void removeUser(User user) {
    }

    /**
     * met à jour les informations de l'utilisateur spécifié.
     * @param user
     */
    public static void updateUser(User user) {

    }

}
