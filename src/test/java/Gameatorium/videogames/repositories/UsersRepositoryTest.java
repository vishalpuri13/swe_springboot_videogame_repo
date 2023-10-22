package Gameatorium.videogames.repositories;

import Gameatorium.videogames.models.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsersRepositoryTest {

    @Autowired
    private UsersRepository usersRepository;

    private Users testUser1;
    private Users testUser2;

    @BeforeEach
    void init() {
        testUser1 = new Users();
        testUser1.setFirstName("Test1");
        testUser1.setLastName("User1");
        testUser1.setAge(25);
        testUser1.setUsername("testuser1");
        testUser1.setPassword("testpassword1");
        testUser1.setEmailId("testuser1@email.com");

        testUser2 = new Users();
        testUser2.setFirstName("Test2");
        testUser2.setLastName("User2");
        testUser2.setAge(30);
        testUser2.setUsername("testuser2");
        testUser2.setPassword("testpassword2");
        testUser2.setEmailId("testuser2@email.com");
    }

    @Test
    @DisplayName("Save user to test database")
    void save() {
        Users newUser = usersRepository.save(testUser1);
        assertNotNull(newUser);
        assertThat(newUser.getUserId()).isNotEqualTo(null);
        assertEquals("testuser1", newUser.getUsername());
    }

    @Test
    @DisplayName("Return list of all users. i.e 2 users")
    void getAllUsers() {
        usersRepository.save(testUser1);
        usersRepository.save(testUser2);

        List<Users> list = usersRepository.findAll();

        assertNotNull(list);
        assertThat(list).isNotNull();
        assertEquals(2, list.size());
    }


    @Test
    @DisplayName("Return user by it's ID")
    void getUserById() {
        usersRepository.save(testUser1);
        usersRepository.save(testUser2);

        Users newUser1 = usersRepository.findById(testUser1.getUserId()).get();
        Users newUser2 = usersRepository.findById(testUser2.getUserId()).get();

        assertNotNull(newUser1);
        assertNotNull(newUser2);
        assertEquals("Test1", newUser1.getFirstName());
        assertEquals("User2", newUser2.getLastName());
    }

    @Test
    @DisplayName("Return user by it's email")
    void getUserByEmail() {
        usersRepository.save(testUser1);
        usersRepository.save(testUser2);

        Users newUser1 = usersRepository.findByEmailId(testUser1.getEmailId()).get();
        Users newUser2 = usersRepository.findByEmailId(testUser2.getEmailId()).get();

        assertNotNull(newUser1);
        assertNotNull(newUser2);
        assertEquals("Test1", newUser1.getFirstName());
        assertEquals("User2", newUser2.getLastName());
    }


    @Test
    @DisplayName("Return user by it's username")
    void getUserByUsername() {
        usersRepository.save(testUser1);
        usersRepository.save(testUser2);

        Users newUser1 = usersRepository.findByUsername(testUser1.getUsername()).get();
        Users newUser2 = usersRepository.findByUsername(testUser2.getUsername()).get();

        assertNotNull(newUser1);
        assertNotNull(newUser2);
        assertEquals("Test1", newUser1.getFirstName());
        assertEquals("User2", newUser2.getLastName());
    }

    @Test
    @DisplayName("Return user by it's username or email")
    void getUserByUsernameOrEmail() {
        usersRepository.save(testUser1);
        usersRepository.save(testUser2);

        Users newUser1 = usersRepository.findByUsernameOrEmail(testUser1.getUsername()).get();
        Users newUser2 = usersRepository.findByUsernameOrEmail(testUser2.getEmailId()).get();

        assertNotNull(newUser1);
        assertNotNull(newUser2);
        assertEquals("Test1", newUser1.getFirstName());
        assertEquals("User2", newUser2.getLastName());
    }

    @Test
    @DisplayName("Update user password to newpassword")
    void updateUser() {
        usersRepository.save(testUser1);

        Users existingUser = usersRepository.findById(testUser1.getUserId()).get();
        existingUser.setPassword("newPassword");
        Users updatedUser = usersRepository.save(existingUser);

        assertNotNull(updatedUser);
        assertEquals("newPassword", updatedUser.getPassword());
    }

    @Test
    @DisplayName("Delete user password")
    void deleteUser() {
        usersRepository.save(testUser1);
        usersRepository.save(testUser2);

        Long id1 = testUser1.getUserId();
        Long id2 = testUser2.getUserId();

        usersRepository.delete(testUser2);

        List<Users> list = usersRepository.findAll();

        Optional<Users> existingMovie1 = usersRepository.findById(id1);
        Optional<Users> existingMovie2 = usersRepository.findById(id2);

        assertNotNull(list);
        assertEquals(1, list.size());
        assertThat(existingMovie2).isEmpty();
    }
}