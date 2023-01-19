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
    private String username;
    private String description;
}
