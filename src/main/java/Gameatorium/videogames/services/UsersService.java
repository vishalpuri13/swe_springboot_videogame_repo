package Gameatorium.videogames.services;

import Gameatorium.videogames.exceptions.RoleNotFoundException;
import Gameatorium.videogames.exceptions.UserNotFoundException;
import Gameatorium.videogames.models.Users;
import Gameatorium.videogames.models.UsersRoles;
import Gameatorium.videogames.repositories.UsersRepository;
import Gameatorium.videogames.repositories.UsersRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepo;
    @Autowired
    private UsersRolesRepository roleRepo;

    public Users save(Users user) {
        return usersRepo.save(user);
    }

    public List<Users> getAllUsers() {
        return usersRepo.findAll();
    }

    public Users getUserById(Long userId) {
        return usersRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
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
        existingUser.setPassword(user.getPassword());
        return usersRepo.save(existingUser);
    }

    public void deleteUser(Long userId) {
        Users existingUser = usersRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        usersRepo.delete(existingUser);
    }

    public Users assignRole(Long userId, String roleName) {
        Users user = usersRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        UsersRoles role = roleRepo.findByRoleName(roleName).orElseThrow(() -> new RoleNotFoundException(roleName));
        user.getRoles().add(role);
        return usersRepo.save(user);
    }
}

