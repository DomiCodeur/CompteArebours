package compteAr.backend.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import compteAr.backend.entity.Role;
import compteAr.backend.entity.UserEntity;
import compteAr.backend.repository.UserRepository;
import compteAr.backend.resources.UserResource;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	// On utilise un Mock de l'interface PasswordEncoder pour pouvoir tester le hashing des mots de passe
	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	public void testGetAllUsersCasNominal() {
		// given -- on renseigne la userEntity // ce sont les données d'entrée pour le test
		List<UserEntity> usersEntity = Arrays.asList(
				UserEntity.builder().id(1)
						.username("userName1").email("email1")
						.password("password1").role(Role.ADMIN)
						.signInMethod("email").timeUnit("dodo").build(),
				UserEntity.builder().id(2).username("userName2")
						.email("email2").password("password2")
						.role(Role.USER).signInMethod("email")
						.timeUnit("dodo").build());

		// pour que le repository nous retourne les users on mock les données.
		// quand dans le test on tombe sur UserRepository.findAll retourner usersEntity
		when(userRepository.findAll()).thenReturn(usersEntity);

		// when
		List<UserResource> usersResource = userService.getAllUsers();

		// then
		assertThat(usersResource).isNotNull().isNotEmpty().hasSize(2);
		assertThat(usersResource).element(0).isInstanceOfSatisfying(UserResource.class, userResource -> {
			assertThat(userResource).extracting("id", "email", "password", "signInMethod").containsExactly(1, "email1", "password1", "email");
		});
		assertThat(usersResource).element(1).isInstanceOfSatisfying(UserResource.class, userResource -> {
			assertThat(userResource).extracting("id", "email", "password", "signInMethod").containsExactly(2, "email2", "password2", "email");
		});
	}

	@Test
	public void testGetUserByIdNotFound() {
		// given
		when(userRepository.findById(1)).thenReturn(Optional.empty());

		// when
		Optional<UserResource> userResourceOpt = userService.getUserById(1);

		// then
		assertThat(userResourceOpt).isNotPresent();
	}

	@Test
	public void testCreateUserCasNominal() {
		// Given
		String rawPassword = "password123";
		String encodedPassword = passwordEncoder.encode(rawPassword);

		UserResource userResource = UserResource.builder()
				.email("john.doe@example.com")
				.password(rawPassword)
				.build();

		UserEntity savedUserEntity = UserEntity.builder()
				.id(1)
				.email(userResource.getEmail())
				.password(encodedPassword)
				.build();

		// When
		when(userRepository.save(any(UserEntity.class))).thenReturn(savedUserEntity);
		when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
		Optional<UserResource> createdUser = userService.createUser(userResource);


		// Then
		assertThat(createdUser).isPresent();
		assertThat(createdUser.get().getId()).isEqualTo(1);
		assertThat(createdUser.get().getEmail()).isEqualTo(userResource.getEmail());
	}





	@Test
	public void testUpdateUserNotFound() {
		//given
		Integer id = 1;
		UserResource userResource = UserResource.builder()
				.email("email_updated").password("password_updated").build();

		when(userRepository.findById(id)).thenReturn(Optional.empty());

		//when
		Optional<UserResource> updatedUserResource = userService.updateUser(id, userResource);

		//then
		assertThat(updatedUserResource).isNotPresent();
	}

	@Test
	public void testCreateUserWithExistingEmail() {
		//given
		String email = "email1";
		UserResource userResource = UserResource.builder()
				.email(email)
				.password("password1")
				.signInMethod("email").build();

		UserEntity userEntity = UserEntity.builder()
				.id(1).username("userName1").email(email)
				.password("password1_encoded").role(Role.ADMIN)
				.signInMethod("email").timeUnit("dodo").build();

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

		//when
		Optional<UserResource> createdUser = userService.createUser(userResource);

		//then
		assertThat(createdUser).isNotPresent();
	}

}
