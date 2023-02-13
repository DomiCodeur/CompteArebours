package CompteAr.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private Integer id;
    private String email;
    private String timeUnit;
    private String token;

    public UserInfo(Object o, Object o1, Object o2, Object o3, String emailInconnu) {
    }
}
