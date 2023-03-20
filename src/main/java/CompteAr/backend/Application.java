package CompteAr.backend;

import CompteAr.backend.config.OpenAPIConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(OpenAPIConfig.class)
@EnableConfigurationProperties(AppProperties.class)
public class Application {

	private final AppProperties appProperties;

	public Application(AppProperties appProperties) {
		this.appProperties = appProperties;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
