package Gameatorium.videogames.services;

import Gameatorium.videogames.exceptions.GameNotFoundException;
import Gameatorium.videogames.repositories.GamesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import Gameatorium.videogames.models.Games;


import java.util.List;
@Service
public class GamesService {

    @Autowired
    GamesRepository gamesRepository;

    public Games save(Games game) {
        return gamesRepository.save(game);
    }

    public List<Games> getAllGames() {
        return gamesRepository.findAll();
    }

    public Games getGameById(Long gameId) {
        return gamesRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }

    public List<Games> getGamesByName(String gameName) {
        return gamesRepository.findByGameName(gameName);
    }

    public List<Games> getGamesByGenre(String genre) {
        return gamesRepository.findByGenre(genre);
    }

    public List<Games> getGamesByPlatform(String platform) {
        return gamesRepository.findByPlatform(platform);
    }

    public Games updateGame(Long gameId, Games game) {
        Games existingGame = gamesRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
        existingGame.setGameName(game.getGameName());
        existingGame.setVersion(game.getVersion());
        existingGame.setGenre(game.getGenre());
        existingGame.setPlatform(game.getPlatform());
        existingGame.setReleaseDate(game.getReleaseDate());
        return gamesRepository.save(existingGame);
    }

    public void deleteGame(Long gameId) {
        Games existingGame = gamesRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
        gamesRepository.delete(existingGame);
    }
}
