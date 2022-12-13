package CompteAr.backend.controller;

import CompteAr.backend.model.User;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;


@RestController
public class Controller {

        @GetMapping("/hello")
        public String hello() {
            return "Hello World";
        }



        /**
         * enregistre un nouvel utilisateur avec le nom d'utilisateur et le mot de passe spécifiés.
         * @param user
         * @throws SQLException
         */

        // recrèe la méthode ci dessus mais avec un objet User en paramètre
        @PostMapping("/registerUser")
        public String registerUser(@RequestBody User user) throws SQLException {
            // Vérifier que les données d'entrée sont valides
            if (!user.validate()) {
                // Retourner une erreur si les données ne sont pas valides
                return "Données invalides";
            }

            // Récupérer les données de l'utilisateur depuis l'objet RequestBody
            String username = user.getUsername();
            String password = user.getPassword();

            // Exécuter la méthode registerUser() pour enregistrer l'utilisateur dans la base de données
            User.registerUser(username, password);
            return "Utilisateur enregistré avec succès";
        }
        // changer la méthode ci dessus


        /**
         * authentifie l'utilisateur avec le nom d'utilisateur et le mot de passe spécifiés, et retourne un objet utilisateur si l'authentification réussit, ou **`null`** si elle échoue.
         * @param username
         * @param password
         * @return user
         * @throws SQLException
         */

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
