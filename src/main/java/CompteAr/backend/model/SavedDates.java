package CompteAr.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "saved_dates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavedDates {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Id
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "time_unit", nullable = false)
    private String timeUnit;

    @Column(name = "name", nullable = false)
    private String name;
}
