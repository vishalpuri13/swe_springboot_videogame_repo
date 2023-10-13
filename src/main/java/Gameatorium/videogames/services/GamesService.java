package Gameatorium.videogames.services;

import Gameatorium.videogames.repositories.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import Gameatorium.videogames.models.Games;


import java.util.List;

@Service
public class GamesService {

    @Autowired
    GamesRepository gamesRepository;

    public Games save(Games game) { return gamesRepository.save(game);   }

    public List<Games> getAllGames() { return gamesRepository.findAll(); }

    public Games getGameById(Long gameId) throws RuntimeException {
        return gamesRepository.findById(gameId).orElseThrow(
                () -> new RuntimeException("Game not found with id: "+ gameId));}

    public Object getGameByName(String gameName) throws RuntimeException {
        try { return gamesRepository.findByGameName(gameName);}
        catch (RuntimeException e) {
            return ("Games not found with the name: "+ gameName);}
    }

    public Object getGameByGenre(String genre) throws RuntimeException {
        try { return gamesRepository.findByGenre(genre);}
        catch (RuntimeException e) {
            return ("Games not found with the genre: "+ genre);}
    }

    public Object getGameByPlatform(String platform) throws RuntimeException {
        try { return gamesRepository.findByPlatform(platform);}
        catch (RuntimeException e) {
            return ("Games not found with the platform: "+ platform);}
    }

    public Object updateGame(Games game, Long gameId) throws RuntimeException {
        try {
            Games existingGame = gamesRepository.findById(gameId).get();
            existingGame.setGameName(game.getGameName());
            existingGame.setVersion(game.getVersion());
            existingGame.setGenre(game.getGenre());
            existingGame.setPlatform(game.getPlatform());
            existingGame.setReleaseDate(game.getReleaseDate());
            return gamesRepository.save(existingGame);
        }
        catch (RuntimeException e) {
            return ("Games not found with the id: "+ gameId);}
    }

    public String deleteGame(Long gameId) throws RuntimeException {
        try {
            Games existingGame = gamesRepository.findById(gameId).get();
            gamesRepository.delete(existingGame);
            return ("User successfully deleted with id: " + gameId);
        }
        catch (RuntimeException e) {
            return ("Games not found with the id: " + gameId);}
    }
}
