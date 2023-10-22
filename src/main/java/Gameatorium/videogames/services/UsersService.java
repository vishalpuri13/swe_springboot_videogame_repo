package Gameatorium.videogames.services;

import Gameatorium.videogames.exceptions.RoleNotFoundException;
import Gameatorium.videogames.exceptions.UserNotFoundException;
import Gameatorium.videogames.models.Users;
import Gameatorium.videogames.models.UsersRoles;
import Gameatorium.videogames.repositories.UsersRepository;
import Gameatorium.videogames.repositories.UsersRolesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepo;
    @Autowired
    private UsersRolesRepository roleRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public Users save(Users user) {
//        Setting the default role of new user
        UsersRoles role = roleRepo.findByRoleName("USER").get();
        Set<UsersRoles> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
//        Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepo.save(user);
    }

/*    To pre-populate your database with initial data during application startup, set default user and Admin roles
    Another way is through data.sql file in resources*/
    @Transactional
    public void initRolesAndUsers() {
    UsersRoles adminRole = new UsersRoles();
    adminRole.setRoleName("ADMIN");
    adminRole = roleRepo.save(adminRole);

    UsersRoles userRole = new UsersRoles();
    userRole.setRoleName("USER");
    userRole = roleRepo.save(userRole);

    // Initialize an admin user
    Users adminUser = new Users();
    adminUser.setUsername("admin123");
    adminUser.setFirstName("admin");
    adminUser.setLastName("admin");
    adminUser.setAge(25);
    adminUser.setEmailId("admin.123@gmail.com");
    adminUser.setPassword(passwordEncoder.encode("admin@54321"));

    Set<UsersRoles> adminRoles = new HashSet<>();
    adminRoles.add(adminRole);
    adminRoles.add(userRole);
    adminUser.setRoles(adminRoles);

    adminUser = usersRepo.save(adminUser);
    }

    public List<Users> getAllUsers() {
        return usersRepo.findAll();
    }

    public Users getUserById(Long userId) {
        return usersRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public Users getUserByUsername(String username) {
        return usersRepo.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public Users getUserByEmail(String emailId) {
        return usersRepo.findByEmailId(emailId).orElseThrow(() -> new UserNotFoundException(emailId));
    }

    public Users updateUser(Long userId, Users user) {
        Users existingUser = usersRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmailId(user.getEmailId());
        existingUser.setAge(user.getAge());
        existingUser.setUsername(user.getUsername());

        // Encode the new password before updating
        existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepo.save(existingUser);
    }

    public void deleteUser(Long userId) {
        Users existingUser = usersRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        usersRepo.delete(existingUser);
    }

    public UsersRoles save(UsersRoles role) {
        return roleRepo.save(role);
    }

    public Users assignRole(Long userId, String roleName) {
        Users user = usersRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        UsersRoles role = roleRepo.findByRoleName(roleName).orElseThrow(() -> new RoleNotFoundException(roleName));
        user.getRoles().add(role);
        return usersRepo.save(user);
    }

}

