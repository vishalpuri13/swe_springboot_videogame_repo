package Gameatorium.videogames.services;

import Gameatorium.videogames.models.Games;
import Gameatorium.videogames.repositories.GamesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

public class GamesServiceTest {

    @InjectMocks
    private GamesService gamesService;

    @Mock
    private GamesRepository gamesRepository;

    private Games testGame1;
    private Games testGame2;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        testGame1 = new Games();
        testGame1.setGameName("Game1");
        testGame1.setVersion("1.0");
        testGame1.setGenre("Action");
        testGame1.setPlatform("PC");
        testGame1.setReleaseDate(LocalDate.of(2023,01,15));

        testGame2 = new Games();
        testGame2.setGameName("Game2");
        testGame2.setVersion("2.0");
        testGame2.setGenre("Adventure");
        testGame2.setPlatform("Console");
        testGame2.setReleaseDate(LocalDate.of(2023,02,20));
    }

    @Test
    @DisplayName("Save Game")
    void saveGame() {
        Mockito.when(gamesRepository.save(any(Games.class))).thenReturn(testGame1);

        Games savedGame = gamesService.save(testGame1);

        assertNotNull(savedGame);
        assertEquals("Game1", savedGame.getGameName());
    }

    @Test
    @DisplayName("Get All Games")
    void getAllGames() {
        List<Games> gameList = new ArrayList<>();
        gameList.add(testGame1);
        gameList.add(testGame2);

        Mockito.when(gamesRepository.findAll()).thenReturn(gameList);

        List<Games> games = gamesService.getAllGames();

        assertNotNull(games);
        assertEquals(2, games.size());
    }

    @Test
    @DisplayName("Get Game by ID")
    void getGameById() {
        Mockito.when(gamesRepository.findById(anyLong())).thenReturn(Optional.of(testGame1));

        Games retrievedGame = gamesService.getGameById(1L);

        assertNotNull(retrievedGame);
        assertEquals("Game1", retrievedGame.getGameName());
    }

    @Test
    @DisplayName("Get Games by Name")
    void getGamesByName() {
        List<Games> gamesByName = new ArrayList<>();
        gamesByName.add(testGame1);

        Mockito.when(gamesRepository.findByGameName(anyString())).thenReturn(gamesByName);

        List<Games> retrievedGames = gamesService.getGamesByName("Game1");

        assertNotNull(retrievedGames);
        assertEquals(1, retrievedGames.size());
    }

    @Test
    @DisplayName("Get Games by Genre")
    void getGamesByGenre() {
        List<Games> gamesByGenre = new ArrayList<>();
        gamesByGenre.add(testGame1);

        Mockito.when(gamesRepository.findByGenre(anyString())).thenReturn(gamesByGenre);

        List<Games> retrievedGames = gamesService.getGamesByGenre("Action");

        assertNotNull(retrievedGames);
        assertEquals(1, retrievedGames.size());
    }

    @Test
    @DisplayName("Get Games by Platform")
    void getGamesByPlatform() {
        List<Games> gamesByPlatform = new ArrayList<>();
        gamesByPlatform.add(testGame1);

        Mockito.when(gamesRepository.findByPlatform(anyString())).thenReturn(gamesByPlatform);

        List<Games> retrievedGames = gamesService.getGamesByPlatform("PC");

        assertNotNull(retrievedGames);
        assertEquals(1, retrievedGames.size());
    }

    @Test
    @DisplayName("Update Game")
    void updateGame() {
        Mockito.when(gamesRepository.findById(anyLong())).thenReturn(Optional.of(testGame1));
        Mockito.when(gamesRepository.save(any(Games.class))).thenReturn(testGame1);

        testGame1.setVersion("1.1");

        Games result = gamesService.updateGame(1L, testGame1);

        assertNotNull(result);
        assertEquals("1.1", result.getVersion());
    }

    @Test
    @DisplayName("Delete Game")
    void deleteGame() {
        Mockito.when(gamesRepository.findById(anyLong())).thenReturn(Optional.of(testGame1));
        Mockito.doNothing().when(gamesRepository).delete(testGame1);

        assertDoesNotThrow(() -> gamesService.deleteGame(1L));
    }
}

