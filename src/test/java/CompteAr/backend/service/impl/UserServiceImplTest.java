package compteAr.backend.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import compteAr.backend.entity.Role;
import compteAr.backend.entity.UserEntity;
import compteAr.backend.repository.UserRepository;
import compteAr.backend.resources.UserResource;
import compteAr.backend.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	public void testGetAllUsersCasNominal() {
		//given
		List<UserEntity> usersEntity = Arrays.asList(
				UserEntity.builder().id(1)
				.username("userName1").email("email1")
				.password("password1").role(Role.ADMIN)
				.signInMethod("email").timeUnit("dodo").build(),
				UserEntity.builder().id(2).username("userName2")
				.email("email2").password("password2")
				.role(Role.USER).signInMethod("email")
				.timeUnit("dodo").build());
		when(userRepository.findAll()).thenReturn(usersEntity);
		
		//when
		List<UserResource> usersResource = userService.getAllUsers();
		
		//then
		assertThat(usersResource).isNotNull().isNotEmpty().hasSize(2);
		assertThat(usersResource).element(0).isInstanceOfSatisfying(UserResource.class, userResource -> {
			assertThat(userResource).extracting("id", "email", "password", "signInMethod").containsExactly(1, "email1", "password1", "email");
		});
		assertThat(usersResource).element(1).isInstanceOfSatisfying(UserResource.class, userResource -> {
			assertThat(userResource).extracting("id", "email", "password", "signInMethod").containsExactly(2, "email2", "password2", "email");
		});
	}

}
