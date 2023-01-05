package CompteAr.backend.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String loginToken;
    private Timestamp loginTokenExpiration;

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Users() {
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "login_token")
    public String getLoginToken() {
        return loginToken;
    }
    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    @Column(name = "login_token_expiration")
    public Timestamp getLoginTokenExpiration() {
        return loginTokenExpiration;
    }
    public void setLoginTokenExpiration(Timestamp loginTokenExpiration) {
        this.loginTokenExpiration = loginTokenExpiration;
    }
}
