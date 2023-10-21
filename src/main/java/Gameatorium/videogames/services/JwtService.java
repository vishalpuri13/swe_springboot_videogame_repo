package Gameatorium.videogames.services;

import Gameatorium.videogames.configurations.JwtUtil;
import Gameatorium.videogames.exceptions.UserNotFoundException;
import Gameatorium.videogames.models.JwtRequest;
import Gameatorium.videogames.models.JwtResponse;
import Gameatorium.videogames.models.Users;
import Gameatorium.videogames.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByUsernameInternal(username);
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        return loadUserByUsernameInternal(email);
    }


    public UserDetails loadUserByUsernameInternal(String usernameOrEmail) throws UsernameNotFoundException {
        Users user = userRepository.findByUsernameOrEmail(usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(usernameOrEmail));

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toList());

        return new User(user.getEmailId(), user.getPassword(), authorities);
    }
    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws UserNotFoundException,Exception{
        String usernameOrEmail = jwtRequest.getUsername();
        String password = jwtRequest.getPassword();
        authenticate(usernameOrEmail, password);
        final UserDetails userDetails = loadUserByUsernameInternal(usernameOrEmail);
        String newGeneratedToken = jwtUtil.generateToken(userDetails);
        Users user = userRepository.findByUsernameOrEmail(usernameOrEmail).get();
        return new JwtResponse(newGeneratedToken);
    }

    private void authenticate(String username, String password) throws UserNotFoundException,Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("User is disabled");
        } catch(UsernameNotFoundException e) {
            throw new UserNotFoundException("Bad credentials from user");
        }
    }
}