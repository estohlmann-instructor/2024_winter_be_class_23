package edu.mcc.codeschool.tictactoe.controllers;

import edu.mcc.codeschool.tictactoe.models.Game;
import edu.mcc.codeschool.tictactoe.services.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Game> createGame(){
        return ResponseEntity.ok(gameService.createGame());
    }

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames(){
        return ResponseEntity.ok(gameService.getGames());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable String id){
        return gameService.getGame(id);
    }

    @PostMapping("/clear/{id}")
    public ResponseEntity<Game> clearGame(@PathVariable String id){
        return gameService.clearGame(id);
    }

    @PostMapping("/reset/{id}")
    public ResponseEntity<Game> resetGame(@PathVariable String id){
        return gameService.resetGame(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable String id){
        return gameService.deleteGame(id);
    }

}
