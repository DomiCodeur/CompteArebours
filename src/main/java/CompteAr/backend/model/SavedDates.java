package CompteAr.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "saved_dates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavedDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "date", nullable = false)
    private Date date;

    @NotBlank(message = "Le nom ne doit pas être vide")
    @Length(max = 50, message = "Le nom doit être au maximum de 50 caractères")
    @Column(name = "name", nullable = false)
    private String name;
}
