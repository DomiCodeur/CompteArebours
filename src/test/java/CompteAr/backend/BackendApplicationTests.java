package CompteAr.backend;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import CompteAr.backend.model.Users;
import CompteAr.backend.repository.UsersRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads() {
	}


	@Test
	void createUser() {
		Users user = new Users("username", "email@example.com", "password");

		// Enregistrement de l'utilisateur en base de données
		UsersRepository usersRepository = new UsersRepository();
		usersRepository.save(user);

		// Vérification que l'utilisateur a été enregistré
		assertNotNull(user.getId());

		// Vérification que l'utilisateur n'existe pas déjà en base de données
		Users existingUser = usersRepository.findByUsername("username");
		assertNull(existingUser);

		existingUser = usersRepository.findByEmail("email@example.com");
		assertNull(existingUser);
	}


}
