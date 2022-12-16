package CompteAr.backend;

import CompteAr.backend.controller.UsersController;
import CompteAr.backend.exception.ResourceNotFoundException;
import CompteAr.backend.model.Users;
import CompteAr.backend.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersController usersController;

    @Test
    void testCreateUser() {
        // given
        Users user = new Users("John", "Doe", "john.doe@example.com");
        given(usersRepository.save(user)).willReturn(user);

        // when
        Users result = usersController.createUser(user);

        // then
        assertThat(result, is(user));
    }

    @Test
    void testGetUser() throws ResourceNotFoundException {
        // given
        long id = 1L;
        Users user = new Users("John", "Doe", "john.doe@example.com");
        user.setId(id);
        given(usersRepository.findById(id)).willReturn(Optional.of(user));

        // when
        ResponseEntity<Users> result = usersController.getUserById(id);

        // then
        assertThat(result.getStatusCodeValue(), is(200));
        assertThat(result.getBody(), is(user));
    }

    @Test
    void testGetUser_NotFound() {
        // given
        long id = 1L;
        given(usersRepository.findById(id)).willReturn(Optional.empty());

        // when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> usersController.getUserById(id));

        // then
        assertThat(exception.getMessage(), is("User not found for this id :: " + id));
    }

    @Test
    void testUpdateUser() throws ResourceNotFoundException {
        // given
        long id = 1L;
        Users user = new Users("John", "Doe", "john.doe@example.com");
        user.setId(id);
        given(usersRepository.findById    (id)).willReturn(Optional.of(user));
        given(usersRepository.save(user)).willReturn(user);

        // when
        ResponseEntity<Users> result = usersController.updateUser(id, user);

        // then
        assertThat(result.getStatusCodeValue(), is(200));
        assertThat(result.getBody(), is(user));
    }

    @Test
    void testUpdateUser_NotFound() {
        // given
        long id = 1L;
        Users user = new Users("John", "Doe", "john.doe@example.com");
        given(usersRepository.findById(id)).willReturn(Optional.empty());

        // when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> usersController.updateUser(id, user));

        // then
        assertThat(exception.getMessage(), is("User not found for this id :: " + id));
    }

    @Test
    void testDeleteUser() throws ResourceNotFoundException {
        // given
        long id = 1L;
        Users user = new Users("John", "Doe", "john.doe@example.com");
        user.setId(id);
        given(usersRepository.findById(id)).willReturn(Optional.of(user));

        // when
        ResponseEntity result = usersController.deleteUser(id);

        // then
        assertThat(result.getStatusCodeValue(), is(200));
        verify(usersRepository).delete(user);
    }

    @Test
    void testDeleteUser_NotFound() {
        // given
        long id = 1L;
        given(usersRepository.findById(id)).willReturn(Optional.empty());

        // when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> usersController.deleteUser(id));

        // then
        assertThat(exception.getMessage(), is("User not found for this id :: " + id));
    }
}

