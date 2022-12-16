package CompteAr.backend;

import CompteAr.backend.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@SpringBootTest
@ExtendWith(SpringExtension.class)
class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void testSaveUser() {
/*        Users user = new Users("username", "password", "email@gmail.com");
        usersRepository.save(user);
        Users existingUser = usersRepository.findByUsername("username");
        assert existingUser != null;
        existingUser = usersRepository.findByEmail("email@gmail.com");
        assert existingUser != null;*/
    }
}


