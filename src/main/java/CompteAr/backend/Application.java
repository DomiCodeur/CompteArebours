package CompteAr.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.util.Collections;

import CompteAr.backend.config.OpenAPIConfig;

@SpringBootApplication
@Import(OpenAPIConfig.class)
public class Application {


	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Application.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", getPort()));
		app.run(args);
	}

	private static String getPort() {
		String port = System.getenv("PORT");
		return port != null ? port : "8080";
	}
}
