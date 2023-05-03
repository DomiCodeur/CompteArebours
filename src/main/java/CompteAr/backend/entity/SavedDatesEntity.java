package compteAr.backend.entity;

import java.util.Date;

import compteAr.backend.resources.SavedDatesResource;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entité pour les dates.
 *
 */
@Entity
@Table(name = "saved_dates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SavedDatesEntity {

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
    
    /**
     * Transformation en resource
     * @return un {@link SavedDatesResource}.
     */
    public SavedDatesResource toResource() {
    	return SavedDatesResource.builder()
    			.id(id)
    			.userId(userId)
    			.date(date)
    			.name(name)
    			.build();
    }
}
