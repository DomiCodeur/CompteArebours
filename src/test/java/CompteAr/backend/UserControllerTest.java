package CompteAr.backend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import CompteAr.backend.controller.UserController;
import CompteAr.backend.model.Role;
import CompteAr.backend.model.User;
import CompteAr.backend.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() {

    }

    @Test
    public void testCreateUserOk()  throws IOException {
        // given
        User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole(Role.USER);

        when(userRepository.findByEmail(eq("email"))).thenReturn(Optional.empty());
        given(userRepository.save(user)).willReturn(user);


        // when
        ResponseEntity<User> result = userController.createUser(user);

        // then
        assertThat(result).isNotNull().isInstanceOfSatisfying(ResponseEntity.class, r -> {
            assertThat(r.getStatusCode()).isNotNull().isEqualTo(HttpStatus.CREATED);
            assertThat(r.getBody()).isNotNull().isInstanceOfSatisfying(User.class, u -> {
                assertThat(u.getEmail()).isNotNull().isEqualTo("email");
                assertThat(u.getPassword()).isNotNull().isEqualTo("password");
                assertThat(u.getRole()).isNotNull().isEqualTo(Role.USER);
            });
        });

    }

    @Test
    public void testCreateUserConflict()  throws IOException {
        // given
        User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole(Role.USER);

        when(userRepository.findByEmail(eq("email"))).thenReturn(Optional.of(user));

        // when
        ResponseEntity<User> result = userController.createUser(user);

        // then
        assertThat(result).isNotNull().isInstanceOfSatisfying(ResponseEntity.class, r -> {
            assertThat(r.getStatusCode()).isNotNull().isEqualTo(HttpStatus.CONFLICT);
            assertThat(r.getBody()).isNull();
        });

    }
}