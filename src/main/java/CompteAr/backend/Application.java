package CompteAr.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class Application {

	@Autowired
	private AppProperties appProperties;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
