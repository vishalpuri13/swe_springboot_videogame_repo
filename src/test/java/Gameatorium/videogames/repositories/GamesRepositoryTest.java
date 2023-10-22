package Gameatorium.videogames.repositories;

import Gameatorium.videogames.models.Games;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
public class GamesRepositoryTest {

    @Autowired
    private GamesRepository gamesRepository;

    private Games testGame1;
    private Games testGame2;

    @BeforeEach
    void init() {
        testGame1 = new Games();
        testGame1.setGameName("Game1");
        testGame1.setGenre("Action");
        testGame1.setPlatform("PC");
        testGame1.setVersion("1.0");
        testGame1.setReleaseDate(LocalDate.ofEpochDay(2023-10-01));


        testGame2 = new Games();
        testGame2.setGameName("Game2");
        testGame2.setGenre("RPG");
        testGame2.setPlatform("PS4");
        testGame2.setVersion("1.0");
        testGame2.setReleaseDate(LocalDate.ofEpochDay(2023-10-02));
    }

    @Test
    @DisplayName("Save game to test database")
    void save() {
        Games newGame = gamesRepository.save(testGame1);
        assertNotNull(newGame);
        assertEquals("Game1", newGame.getGameName());
    }

    @Test
    @DisplayName("Return list of games by ID")
    void findById() {
        gamesRepository.save(testGame1);
        gamesRepository.save(testGame2);

        Games newGame1 = gamesRepository.findById(testGame1.getGameId()).get();
        Games newGame2 = gamesRepository.findById(testGame2.getGameId()).get();

        assertNotNull(newGame1);
        assertNotNull(newGame2);
        assertEquals("PC", newGame1.getPlatform());
        assertEquals("RPG", newGame2.getGenre());
    }

    @Test
    @DisplayName("Return list of games by genre")
    void findByGenre() {
        gamesRepository.save(testGame1);
        gamesRepository.save(testGame2);

        List<Games> gamesList = gamesRepository.findByGenre("Action");

        assertNotNull(gamesList);
        assertEquals(1, gamesList.size());
        assertEquals("Action", gamesList.get(0).getGenre());
    }

    @Test
    @DisplayName("Return list of games by platform")
    void findByPlatform() {
        gamesRepository.save(testGame1);
        gamesRepository.save(testGame2);

        List<Games> gamesList = gamesRepository.findByPlatform("PS4");

        assertNotNull(gamesList);
        assertEquals(1, gamesList.size());
        assertEquals("PS4", gamesList.get(0).getPlatform());
    }

    @Test
    @DisplayName("Return game by its name")
    void findByGameName() {
        gamesRepository.save(testGame1);
        gamesRepository.save(testGame2);

        Games foundGame = gamesRepository.findByGameName("Game2").get(0);

        assertNotNull(foundGame);
        assertEquals("Game2", foundGame.getGameName());
    }

    @Test
    @DisplayName("Return list of all games")
    void findAllGames() {
        gamesRepository.save(testGame1);
        gamesRepository.save(testGame2);

        List<Games> gamesList = gamesRepository.findAll();

        assertNotNull(gamesList);
        assertEquals(2, gamesList.size());
    }

    @Test
    @DisplayName("Update game")
    void updateGame() {
        Games savedGame = gamesRepository.save(testGame1);

        // Modify an attribute of the saved game
        savedGame.setGenre("Adventure");

        Games updatedGame = gamesRepository.save(savedGame);

        assertNotNull(updatedGame);
        assertEquals("Adventure", updatedGame.getGenre());
    }

    @Test
    @DisplayName("Delete game")
    void deleteGame() {
        Games savedGame = gamesRepository.save(testGame1);

        Long gameId = savedGame.getGameId();
        gamesRepository.delete(savedGame);

        Optional<Games> deletedGame = gamesRepository.findById(gameId);

        assertNotNull(deletedGame);
        assertThat(deletedGame).isEmpty();
    }
}
