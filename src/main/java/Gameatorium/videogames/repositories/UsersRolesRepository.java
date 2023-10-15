package Gameatorium.videogames.repositories;


import Gameatorium.videogames.models.UsersRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsersRolesRepository extends JpaRepository<UsersRoles, Long> {
    Optional<UsersRoles> findByRoleName(String roleName);
}