package Gameatorium.videogames.repositories;

import Gameatorium.videogames.models.UsersRoles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UsersRolesRepositoryTest {

    @Autowired
    private UsersRolesRepository usersRolesRepository;

    private UsersRoles testRole1;
    private UsersRoles testRole2;

    @BeforeEach
    void init() {
        testRole1 = new UsersRoles();
        testRole1.setRoleName("ROLE_USER");

        testRole2 = new UsersRoles();
        testRole2.setRoleName("ROLE_ADMIN");
    }

    @Test
    @DisplayName("Save role to test database")
    void save() {
        UsersRoles newRole = usersRolesRepository.save(testRole1);
        assertNotNull(newRole);
        assertNotNull(newRole.getRoleId());
        assertEquals("ROLE_USER", newRole.getRoleName());
    }

    @Test
    @DisplayName("Return role by role name")
    void findByRoleName() {
        usersRolesRepository.save(testRole1);
        usersRolesRepository.save(testRole2);

        Optional<UsersRoles> foundRole = usersRolesRepository.findByRoleName("ROLE_ADMIN");

        assertNotNull(foundRole);
        assertThat(foundRole).isPresent();
        assertEquals("ROLE_ADMIN", foundRole.get().getRoleName());
    }
}
