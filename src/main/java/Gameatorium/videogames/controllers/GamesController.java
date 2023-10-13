package Gameatorium.videogames.controllers;

import Gameatorium.videogames.models.Games;
import Gameatorium.videogames.services.GamesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GamesController {

    @Autowired
    GamesService gamesService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Games createGame(@RequestBody Games game) {return gamesService.save(game);}


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Games> getAllGames() {return gamesService.getAllGames();}

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Games getGameById(@PathVariable Long id) {return gamesService.getGameById(id);}


    @GetMapping("/name/{gameName}")
    @ResponseStatus(HttpStatus.OK)
    public Object getGameByName(@PathVariable String gameName) {return gamesService.getGameByName(gameName);}

    @GetMapping("/genre/{genre}")
    @ResponseStatus(HttpStatus.OK)
    public Object getGameByGenre(@PathVariable String genre) {return gamesService.getGameByGenre(genre);}

    @GetMapping("/platform/{platform}")
    @ResponseStatus(HttpStatus.OK)
    public Object getGameByPlatform(@PathVariable String platform) {return gamesService.getGameByPlatform(platform);}


    @PutMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Object updateGame(@PathVariable Long id, @RequestBody Games game) {
        return gamesService.updateGame(game,id); }


    @DeleteMapping("/id/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteGame(@PathVariable Long id) {return gamesService.deleteGame(id);}

}
