package Gameatorium.videogames.controllers;

import Gameatorium.videogames.exceptions.GameNotFoundException;
import Gameatorium.videogames.models.Games;
import Gameatorium.videogames.services.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GamesController {

    @Autowired
    GamesService gamesService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createGame(@RequestBody Games game) {
        try {
            Games createdGame = gamesService.save(game);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdGame);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: Failed to create the game. Please check the details provided" + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Object> getAllGames() {
        try {
            List<Games> games = gamesService.getAllGames();
            return ResponseEntity.status(HttpStatus.OK).body(games);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve games: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getGameById(@PathVariable Long id) {
        try {
            Games game = gamesService.getGameById(id);
            return ResponseEntity.status(HttpStatus.OK).body(game);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve the game: " + e.getMessage());
        }
    }

    @GetMapping("/name/{gameName}")
    public ResponseEntity<Object> getGamesByName(@PathVariable String gameName) {
        try {
            List<Games> games = gamesService.getGamesByName(gameName);
            if (games.isEmpty()) {
                throw new GameNotFoundException("No games found with the name: " + gameName);
            }
            return ResponseEntity.status(HttpStatus.OK).body(games);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve games: " + e.getMessage());
        }
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<Object> getGamesByGenre(@PathVariable String genre) {
        try {
            List<Games> games = gamesService.getGamesByGenre(genre);
            if (games.isEmpty()) {
                throw new GameNotFoundException("No games found with the genre: " + genre);
            }
            return ResponseEntity.status(HttpStatus.OK).body(games);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve games: " + e.getMessage());
        }
    }

    @GetMapping("/platform/{platform}")
    public ResponseEntity<Object> getGamesByPlatform(@PathVariable String platform) {
        try {
            List<Games> games = gamesService.getGamesByPlatform(platform);
            if (games.isEmpty()) {
                throw new GameNotFoundException("No games found with the platform: " + platform);
            }
            return ResponseEntity.status(HttpStatus.OK).body(games);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to retrieve games: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> updateGame(@PathVariable Long id, @RequestBody Games game) {
        try {
            Games updatedGame = gamesService.updateGame(id, game);
            return ResponseEntity.status(HttpStatus.OK).body(updatedGame);
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update the game: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteGame(@PathVariable Long id) {
        try {
            gamesService.deleteGame(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (GameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete the game: " + e.getMessage());
        }
    }
}