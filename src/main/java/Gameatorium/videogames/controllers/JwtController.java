package Gameatorium.videogames.controllers;

import Gameatorium.videogames.exceptions.UserNotFoundException;
import Gameatorium.videogames.models.JwtRequest;
import Gameatorium.videogames.models.JwtResponse;
import Gameatorium.videogames.services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {

    @Autowired
    private JwtService jwtService;


    @PostMapping("/users/login")
    public JwtResponse createAuthenticationToken(@RequestBody JwtRequest jwtRequest) throws UserNotFoundException,Exception {
        return jwtService.createJwtToken(jwtRequest);
    }
}