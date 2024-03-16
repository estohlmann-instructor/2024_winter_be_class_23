package edu.mcc.codeschool.tictactoe.services;

import edu.mcc.codeschool.tictactoe.models.Game;
import edu.mcc.codeschool.tictactoe.models.Move;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameService {

    List<Game> games = new ArrayList<>();
    public Game createGame(){
        Game game = new Game();
        game.setId(UUID.randomUUID());
        games.add(game);
        return game;
    }

    public List<Game> getGames(){
        return games;
    }

    public ResponseEntity<Game> getGame(String id){
        Optional<Game> game = games.stream().filter(myGame -> myGame.getId().toString().equals(id)).findFirst();
        if(game.isPresent()){
            return ResponseEntity.ok(game.get());
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Game> clearGame(String id){
        return clearOrReset(id, false);
    }

    public ResponseEntity<Game> resetGame(String id){
        return clearOrReset(id, true);
    }

    private ResponseEntity<Game> clearOrReset(String id, boolean reset){
        Optional<Game> game = games.stream().filter(myGame -> myGame.getId().toString().equals(id)).findFirst();
        if(game.isPresent()){
            Game currentGame = game.get();
            games.remove(currentGame);

            String[][] freshBoard = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};
            currentGame.setBoard(freshBoard);
            currentGame.setWinner(null);
            currentGame.setStatus("In-Progress");
            if(reset) {
                currentGame.setoWins(0);
                currentGame.setxWins(0);
                currentGame.setTies(0);
            }
            games.add(currentGame);

            return ResponseEntity.ok(currentGame);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> deleteGame(String id){
        Optional<Game> game = games.stream().filter(myGame -> myGame.getId().toString().equals(id)).findFirst();
        if(game.isPresent()){
            Game currentGame = game.get();
            games.remove(currentGame);

            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Game> makeMove(Move request){
        Optional<Game> game = games.stream().filter(myGame -> myGame.getId().toString().equals(request.getGameId().toString())).findFirst();
        if(game.isPresent()){
            Game currentGame = game.get();
            if(currentGame.getWinner() != null){
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            games.remove(currentGame);

            String[][] currentBoard = currentGame.getBoard();
            currentBoard[Integer.parseInt(request.getLocation().split(",")[0])][Integer.parseInt(request.getLocation().split(",")[1])] = request.getPlayer();
            currentGame.setBoard(currentBoard);

            String winner = checkWinner(currentGame.getBoard());

            if (winner.equalsIgnoreCase("Player X wins!")){
                currentGame.setxWins(currentGame.getxWins()+1);
                currentGame.setWinner("X");
            } else if(winner.equalsIgnoreCase("Player O wins!")){
                currentGame.setxWins(currentGame.getoWins()+1);
                currentGame.setWinner("O");
            } else if(winner.equalsIgnoreCase("It's a tie!")){
                currentGame.setxWins(currentGame.getTies()+1);
                currentGame.setWinner("TIE");
            }

            games.add(currentGame);
            return ResponseEntity.ok(currentGame);

        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    private String checkWinner(String[][] board) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].equals(" ")) {
                return "Player " + board[i][0] + " wins!";
            }
            if (board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]) && !board[0][i].equals(" ")) {
                return "Player " + board[0][i] + " wins!";
            }
        }

        // Check diagonals
        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].equals(" ")) {
            return "Player " + board[0][0] + " wins!";
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].equals(" ")) {
            return "Player " + board[0][2] + " wins!";
        }

        // Check if the game is finished or still ongoing
        boolean full = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j].equals(" ")) {
                    full = false;
                    break;
                }
            }
            if (!full) {
                break;
            }
        }
        if (full) {
            return "It's a tie!";
        }

        // No winner yet
        return "No winner yet";
    }
}
