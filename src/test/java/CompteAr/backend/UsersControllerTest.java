/*
package CompteAr.backend;

import CompteAr.backend.controller.UsersController;
import CompteAr.backend.exception.ResourceNotFoundException;
import CompteAr.backend.model.Users;
import CompteAr.backend.repository.UsersRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
@SpringBootTest
public class UsersControllerTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersController usersController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() throws ResourceNotFoundException {
        // given
        Users user = new Users("john.doe@example.com", "Doe" );
        given(usersRepository.save(user)).willReturn(user);

        // when
        Users result = usersController.createUser(user);

        // then
        assertThat(result, is(user));
    }

    @Test
    public void testGetUser() throws ResourceNotFoundException {
        // given
        Integer id = 1;
        Users user = new Users("john.doe@example.com", "Doe" );
        user.setId(id);
        given(usersRepository.findById(id)).willReturn(Optional.of(user));

        // when
        ResponseEntity<Users> result = usersController.getUserById(id);

        // then
        assertThat(result.getStatusCodeValue(), is(200));
        assertThat(result.getBody(), is(user));
    }

    @Test
    public void testGetUser_NotFound() {
        // given
        Integer id = 1;
        given(usersRepository.findById(id)).willReturn(Optional.empty());

        // when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> usersController.getUserById(id));

        // then
        assertThat(exception.getMessage(), is("User not found for this id :: " + id));
    }

    @Test
    public void testUpdateUser() throws ResourceNotFoundException {
        // given
        Integer id = 1;
        Users user =new Users("john.doe@example.com", "Doe" );
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
    public void testUpdateUser_NotFound() {
        // given
        Integer id = 1;
        Users user = new Users("john.doe@example.com", "Doe" );
        given(usersRepository.findById(id)).willReturn(Optional.empty());

        // when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> usersController.updateUser(id, user));

        // then
        assertThat(exception.getMessage(), is("User not found for this id :: " + id));
    }

    @Test
    public void testDeleteUser() throws ResourceNotFoundException {
        // given
        Integer id = 1;
        Users user = new Users("john.doe@example.com", "Doe" );
        user.setId(id);
        given(usersRepository.findById(id)).willReturn(Optional.of(user));

        // when
        ResponseEntity result = usersController.deleteUser(id);

        // then
        assertThat(result.getStatusCodeValue(), is(200));
        verify(usersRepository).delete(user);
    }

    @Test
    public void testDeleteUser_NotFound() {
        // given
        Integer id = 1;
        given(usersRepository.findById(id)).willReturn(Optional.empty());

        // when
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> usersController.deleteUser(id));

        // then
        assertThat(exception.getMessage(), is("User not found for this id :: " + id));
    }
}

*/
