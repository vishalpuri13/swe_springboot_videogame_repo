package Gameatorium.videogames.repositories;

import Gameatorium.videogames.models.Users;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmailId(String emailId);

}
