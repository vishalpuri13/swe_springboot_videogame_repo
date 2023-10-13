package Gameatorium.videogames.controllers;

import Gameatorium.videogames.models.Users;
import Gameatorium.videogames.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Users createUser(@RequestBody Users user) {return usersService.save(user);}


    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Users> getAllUsers() {return usersService.getAllUsers();}


    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Users getUserById(@PathVariable Long id) {return usersService.getUserById(id);}


    @GetMapping("/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public Object getUserByEmail(@PathVariable String email) {return usersService.getUserByEmail(email);}


    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object updateUser(@PathVariable Long id,@RequestBody Users user) {
        return usersService.updateUser(user, id);    }


    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteUser(@PathVariable Long id) {return usersService.deleteUser(id);}


}
