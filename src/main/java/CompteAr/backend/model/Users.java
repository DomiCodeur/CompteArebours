package CompteAr.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "login_token")
    private String loginToken;

    @Column(name = "login_token_expiration")
    private Timestamp loginTokenExpiration;

    public Users(int i, String user1, String password1, String s) {
    }
}
