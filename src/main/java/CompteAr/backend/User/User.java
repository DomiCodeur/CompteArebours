package CompteAr.backend.User;
import java.util.List;
import java.util.Date;


public class User {
    // créer la classe User
    private int id;
    private String username;
    private String password;
    private List<Date> savedDates;

    // `User(username, password)`** : construit un nouvel utilisateur avec le nom d'utilisateur et le mot de passe spécifiés.
    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }


    public User() {
    }

    public static void addUser(User user) {
        User.addUser(user);
    }

    public static List<User> getUsers() {
        return User.getUsers();
    }

    // `getId()`** : retourne l'identifiant de l'utilisateur.
    public int getId() {
        return id;
    }

    // `setId(id)`** : définit l'identifiant de l'utilisateur.
    public void setId(int id) {
        this.id = id;
    }

    // getUser()
    public static User getUser(int id) {
        return null;
    }

    // `getUsername()`** : retourne le nom d'utilisateur de l'utilisateur.
    public String getUsername() {
        return username;
    }

    // `setUsername(username)`** : change le nom d'utilisateur de l'utilisateur.
    public void setUsername(String username) {
        this.username = username;
    }

    // `getPassword()`** : retourne le mot de passe de l'utilisateur.
    public String getPassword() {
        return password;
    }

    // `setPassword(password)`** : change le mot de passe de l'utilisateur.
    public void setPassword(String password) {
        this.password = password;
    }

    // `getSavedDates()`** : retourne la liste des dates suvegardées par l'utilisateur.
    public List<Date> getSavedDates() {
        return savedDates;
    }

    // `setSavedDates(savedDates)`** : change la liste des dates sauvegardées par l'utilisateur.
    public void setSavedDates(List<Date> savedDates) {
        this.savedDates = savedDates;
    }


}
