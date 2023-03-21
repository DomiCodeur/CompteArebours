package CompteAr.backend.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  @NotBlank(message = "L'email ne doit pas être vide")
  @Email(message = "L'email doit être valide")
  private String email;

  @NotBlank(message = "Le mot de passe ne doit pas être vide")
  @Length(min = 8, max = 50, message = "Le mot de passe doit être entre 8 et 50 caractères")
  private String password;

  private String signInMethod;

  private String timeUnit;
}
