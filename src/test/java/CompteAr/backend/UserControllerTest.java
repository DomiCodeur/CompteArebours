package CompteAr.backend;

import CompteAr.backend.controller.UserController;
import CompteAr.backend.model.User;
import CompteAr.backend.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(JUnit4.class)
@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser()  throws IOException {
        // given
/*        User user = new User("john.doe@example.com", "Password" );
        given(userRepository.save(user)).willReturn(user);

        // when
        ResponseEntity<User> result = userController.createUser(user);

        // then
        assertThat(result, is(user));*/
    }

}


