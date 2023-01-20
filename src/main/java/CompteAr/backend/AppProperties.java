package CompteAr.backend;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Data
@Service
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    @Value("${spring.datasource.username}")
    private String DB_username;

    @Value("${spring.datasource.password}")
    private String DB_password;
}
