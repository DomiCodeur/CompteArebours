package CompteAr.backend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import java.util.Optional;

import CompteAr.backend.service.UserService;
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

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() {

    }

    @Test
    public void testCreateUserOk() {
        // given
        User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole(Role.USER);

        when(userService.findByEmail(eq("email"))).thenReturn(Optional.empty());
        given(userService.save(user)).willReturn(user);


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
    public void testCreateUserConflict() {
        // given
        User user = new User();
        user.setEmail("email");
        user.setPassword("password");
        user.setRole(Role.USER);

        when(userService.findByEmail(eq("email"))).thenReturn(Optional.of(user));

        // when
        ResponseEntity<User> result = userController.createUser(user);

        // then
        assertThat(result).isNotNull().isInstanceOfSatisfying(ResponseEntity.class, r -> {
            assertThat(r.getStatusCode()).isNotNull().isEqualTo(HttpStatus.CONFLICT);
            assertThat(r.getBody()).isNull();
        });

    }

    @Test
    public void testGetUserByIdOk() {
        // given
        User user = new User();
        user.setId(1);
        user.setEmail("email");
        user.setPassword("password");
        user.setRole(Role.USER);
        when(userService.findById(1)).thenReturn(Optional.of(user));

        // when
        ResponseEntity<User> result = userController.getUserById(1);

        // then
        assertThat(result).isNotNull().isInstanceOfSatisfying(ResponseEntity.class, r -> {
            assertThat(r.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
            assertThat(r.getBody()).isNotNull().isInstanceOfSatisfying(User.class, u -> {
                assertThat(u.getId()).isNotNull().isEqualTo(1);
                assertThat(u.getEmail()).isNotNull().isEqualTo("email");
                assertThat(u.getPassword()).isNotNull().isEqualTo("password");
                assertThat(u.getRole()).isNotNull().isEqualTo(Role.USER);
            });
        });
    }

    @Test
    public void testGetUserByIdNotFound() {
        // given
        when(userService.findById
        (1)).thenReturn(Optional.empty());

        // when
        ResponseEntity<User> result = userController.getUserById(1);

        // then
        assertThat(result).isNotNull().isInstanceOfSatisfying(ResponseEntity.class, r -> {
            assertThat(r.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(r.getBody()).isNull();
        });
    }

    @Test
    public void testUpdateUserOk() {
        // given
        User user = new User();
        user.setId(1);
        user.setEmail("email");
        user.setPassword("password");
        user.setRole(Role.USER);
        when(userService.findById(1)).thenReturn(Optional.of(user));
        when(userService.save(user)).thenReturn(user);

        // when
        ResponseEntity<User> result = userController.updateUser(1, user);

        // then
        assertThat(result).isNotNull().isInstanceOfSatisfying(ResponseEntity.class, r -> {
            assertThat(r.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
            assertThat(r.getBody()).isNotNull().isInstanceOfSatisfying(User.class, u -> {
                assertThat(u.getId()).isNotNull().isEqualTo(1);
                assertThat(u.getEmail()).isNotNull().isEqualTo("email");
                assertThat(u.getPassword()).isNotNull().isEqualTo("password");
                assertThat(u.getRole()).isNotNull().isEqualTo(Role.USER);
            });
        });
    }

    @Test
    public void testUpdateUserNotFound() {
        // given
        User user = new User();
        user.setId(1);
        user.setEmail("email");
        user.setPassword("password");
        user.setRole(Role.USER);
        when(userService.findById(1)).thenReturn(Optional.empty());

        // when
        ResponseEntity<User> result = userController.updateUser(1, user);

        // then
        assertThat(result).isNotNull().isInstanceOfSatisfying(ResponseEntity.class, r -> {
            assertThat(r.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(r.getBody()).isNull();
        });
    }


    @Test
    public void testDeleteUserOk() {
        // given
        when(userService.findById(1)).thenReturn(Optional.of(new User()));

        // when
        ResponseEntity<Void> result = userController.deleteUser(1);

        // then
        assertThat(result).isNotNull().isInstanceOfSatisfying(ResponseEntity.class, r -> {
            assertThat(r.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NO_CONTENT);
            assertThat(r.getBody()).isNull();
        });
    }

    @Test
    public void testDeleteUserNotFound() {
        // given
        when(userService.findById(1)).thenReturn(Optional.empty());

        // when
        ResponseEntity<Void> result = userController.deleteUser(1);

        // then
        assertThat(result).isNotNull().isInstanceOfSatisfying(ResponseEntity.class, r -> {
            assertThat(r.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(r.getBody()).isNull();
        });
    }
}
