package Gameatorium.videogames.repositories;


import Gameatorium.videogames.models.Games;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GamesRepository extends JpaRepository<Games, Long> {

    List<Games> findByGenre(String genre);

    List<Games> findByPlatform(String platform);

    List<Games> findByGameName(String gameName);
}


