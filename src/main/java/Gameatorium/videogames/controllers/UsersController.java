package Gameatorium.videogames.controllers;

import Gameatorium.videogames.exceptions.RoleNotFoundException;
import Gameatorium.videogames.exceptions.UserNotFoundException;
import Gameatorium.videogames.models.Users;
import Gameatorium.videogames.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createUser(@RequestBody Users user) {
        try {
            Users createdUser = usersService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create user: " + e.getMessage());
        }
    }

    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<Users> users = usersService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve users: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        try {
            Users user = usersService.getUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve user: " + e.getMessage());
        }
    }

    @GetMapping("/email/{email}")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getUserByEmail(@PathVariable String email) {
        try {
            Users user = usersService.getUserByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve user: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody Users user) {
        try {
            Users updatedUser = usersService.updateUser(id, user);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            usersService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user: " + e.getMessage());
        }
    }

    @PostMapping("/{userId}/assignRole/{roleName}")
    public ResponseEntity<Object> assignRole(@PathVariable Long userId, @PathVariable String roleName) {
        try {
            Users user = usersService.assignRole(userId, roleName);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (UserNotFoundException | RoleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to assign role: " + e.getMessage());
        }
    }
}
