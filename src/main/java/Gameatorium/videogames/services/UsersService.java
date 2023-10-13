package Gameatorium.videogames.services;

import Gameatorium.videogames.models.Users;
import Gameatorium.videogames.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepo;

    public Users save(Users user) { return usersRepo.save(user);   }

    public List<Users> getAllUsers() { return usersRepo.findAll(); }

    public Users getUserById(Long userId) throws RuntimeException {
        return usersRepo.findById(userId).orElseThrow(
                                            () -> new RuntimeException("User not found with id: "+ userId));}

    public Object getUserByEmail(String emailId) throws RuntimeException {
        try { return usersRepo.findByEmailId(emailId);}
        catch (RuntimeException e) {
            return ("User not found with the email: "+ emailId);}
    }

    public Object updateUser(Users user, Long userId) throws RuntimeException {
        try {
            Users existingUser = usersRepo.findById(userId).get();
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmailId(user.getEmailId());
            existingUser.setAge(user.getAge());
            existingUser.setPassword(user.getPassword());
            existingUser.setUsersRoles(user.getUsersRoles());
            return usersRepo.save(existingUser);
        }
        catch (RuntimeException e) {
            return ("User not found with the id: "+ userId);}
    }

    public String deleteUser(Long userId) throws RuntimeException {
        try {
            Users existingUser = usersRepo.findById(userId).get();
            usersRepo.delete(existingUser);
            return ("User successfully deleted with id: " + userId);
        }
        catch (RuntimeException e) {
            return ("User not found with the id: "+ userId);}
    }

}
