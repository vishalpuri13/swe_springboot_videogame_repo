package Gameatorium.videogames.repositories;

import Gameatorium.videogames.models.Users;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findByEmailId(String emailId);

}
