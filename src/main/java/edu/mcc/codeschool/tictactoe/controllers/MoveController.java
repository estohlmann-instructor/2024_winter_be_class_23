package edu.mcc.codeschool.tictactoe.controllers;

import edu.mcc.codeschool.tictactoe.models.Game;
import edu.mcc.codeschool.tictactoe.models.Move;
import edu.mcc.codeschool.tictactoe.services.GameService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/moves")
public class MoveController {

    private final GameService gameService;

    public MoveController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Game> makeMove(@RequestBody @Valid Move request){
        return gameService.makeMove(request);
    }
}
