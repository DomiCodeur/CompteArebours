package CompteAr.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
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
