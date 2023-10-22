package Gameatorium.videogames.services;

import Gameatorium.videogames.models.Users;
import Gameatorium.videogames.models.UsersRoles;
import Gameatorium.videogames.repositories.UsersRepository;
import Gameatorium.videogames.repositories.UsersRolesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Optional;

import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



public class UsersServiceTest {

    @InjectMocks
    private UsersService usersService;

    @Mock
    private UsersRepository usersRepo;

    @Mock
    private UsersRolesRepository roleRepo;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    private Users testUser1;
    private UsersRoles testRole1;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        testUser1 = new Users();
        testUser1.setFirstName("Test1");
        testUser1.setLastName("User1");
        testUser1.setAge(25);
        testUser1.setUsername("testuser1");
        testUser1.setPassword("testpassword1");
        testUser1.setEmailId("testuser1@email.com");

        testRole1 = new UsersRoles();
        testRole1.setRoleName("USER");
    }

    @Test
    @DisplayName("Save User")
    void saveUser() {

        Mockito.when(roleRepo.findByRoleName("USER")).thenReturn(Optional.of(testRole1));
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        Mockito.when(usersRepo.save(any(Users.class))).thenReturn(testUser1);

        Users savedUser = usersService.save(testUser1);

        assertNotNull(savedUser);
        assertEquals("testuser1", savedUser.getUsername());
        assertEquals("encodedPassword", savedUser.getPassword());
    }

    @Test
    @DisplayName("Init Roles and Users")
    void initRolesAndUsers() {
        UsersRoles adminRole = new UsersRoles();
        adminRole.setRoleName("ADMIN");

        UsersRoles userRole = new UsersRoles();
        userRole.setRoleName("USER");

        Users adminUser = new Users();
        adminUser.setUsername("admin123");
        adminUser.setPassword("admin@54321");

        Set<UsersRoles> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminRoles.add(userRole);
        adminUser.setRoles(adminRoles);

        Mockito.when(roleRepo.save(adminRole)).thenReturn(adminRole);
        Mockito.when(roleRepo.save(userRole)).thenReturn(userRole);
        Mockito.when(passwordEncoder.encode("admin@54321")).thenReturn("encodedPassword");
        Mockito.when(usersRepo.save(adminUser)).thenReturn(adminUser);

        // Call the initRolesAndUsers method
        assertDoesNotThrow(() -> usersService.initRolesAndUsers());
    }

    @Test
    @DisplayName("Get All Users")
    void getAllUsers() {
        Users testUser2 = new Users();
        testUser2.setFirstName("Test2");
        testUser2.setLastName("User2");
        testUser2.setAge(30);
        testUser2.setUsername("testuser2");
        testUser2.setPassword("testpassword2");
        testUser2.setEmailId("testuser2@email.com");

        List<Users> userList = new ArrayList<>();
        userList.add(testUser1);
        userList.add(testUser2);

        Mockito.when(usersRepo.save(any(Users.class))).thenReturn(testUser1);
        Mockito.when(usersRepo.findAll()).thenReturn(userList);

        List<Users> users = usersService.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    @DisplayName("Get User by ID")
    void getUserById() {

        Mockito.when(usersRepo.findById(anyLong())).thenReturn(Optional.of(testUser1));

        Users retrievedUser = usersService.getUserById(testUser1.getUserId());

        assertNotNull(retrievedUser);
        assertEquals("testuser1@email.com", retrievedUser.getEmailId());
    }

    @Test
    @DisplayName("Get User by Username")
    void getUserByUsername() {

        Mockito.when(usersRepo.findByUsername(anyString())).thenReturn(Optional.of(testUser1));

        Users retrievedUser = usersService.getUserByUsername("testuser1");

        assertNotNull(retrievedUser);
        assertEquals("testuser1@email.com", retrievedUser.getEmailId());
    }

    @Test
    @DisplayName("Get User by Email")
    void getUserByEmail() {

        Mockito.when(usersRepo.findByEmailId("testuser1@example.com")).thenReturn(Optional.of(testUser1));

        Users retrievedUser = usersService.getUserByEmail("testuser1@example.com");

        assertNotNull(retrievedUser);
        assertEquals("testuser1", retrievedUser.getUsername());
    }

    @Test
    @DisplayName("Update User")
    void updateUser() {

        Mockito.when(usersRepo.findById(anyLong())).thenReturn(Optional.of(testUser1));
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        Mockito.when(usersRepo.save(any(Users.class))).thenReturn(testUser1);

        testUser1.setEmailId("updatedEmail@email.com");

        Users result = usersService.updateUser(testUser1.getUserId(), testUser1);

        assertNotNull(result);
        assertEquals("updatedEmail@email.com", result.getEmailId());
    }

    @Test
    @DisplayName("Delete User")
    void deleteUser() {

        Long id = testUser1.getUserId();

        Mockito.when(usersRepo.findById(anyLong())).thenReturn(Optional.of(testUser1));
        Mockito.doNothing().when(usersRepo).delete(any(Users.class));

        assertDoesNotThrow(() -> usersService.deleteUser(id));

    }

    @Test
    @DisplayName("Save Role")
    void saveRole() {

        Mockito.when(roleRepo.save(any(UsersRoles.class))).thenReturn(testRole1);

        UsersRoles savedRole = usersService.save(testRole1);

        assertNotNull(savedRole);
        assertEquals("USER", savedRole.getRoleName());
    }

    @Test
    @DisplayName("Assign Role to User")
    void assignRoleToUser() {

        // Mock the behavior of usersRepo and roleRepo
        Mockito.when(usersRepo.findById(anyLong())).thenReturn(Optional.of(testUser1));
        Mockito.when(roleRepo.findByRoleName(anyString())).thenReturn(Optional.of(testRole1));
        Mockito.when(usersRepo.save(any(Users.class))).thenReturn(testUser1);

        Users updatedUser = usersService.assignRole(testUser1.getUserId(), testRole1.getRoleName());

        assertNotNull(updatedUser);
        assertTrue(updatedUser.getRoles().contains(testRole1));
    }
}
