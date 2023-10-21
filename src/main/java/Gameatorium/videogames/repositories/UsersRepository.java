package Gameatorium.videogames.repositories;

import Gameatorium.videogames.models.Users;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmailId(String emailId);

    Optional<Users> findByUsername(String username);

    @Query("SELECT u FROM Users u WHERE u.emailId = :usernameOrEmail OR u.username = :usernameOrEmail")
    Optional<Users> findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);


}
