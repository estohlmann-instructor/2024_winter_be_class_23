package edu.mcc.codeschool.tictactoe.services;

import static org.assertj.core.api.Assertions.assertThat;

import edu.mcc.codeschool.tictactoe.models.Game;
import edu.mcc.codeschool.tictactoe.models.Move;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("Game Service Test")
public class GameServiceTest {

    @InjectMocks
    private GameService subject;
    private final String[][] default_board = {{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}};

    @Test
    @DisplayName("Create Game")
    void test_createGame(){
        // when
        Game game = subject.createGame();

        // then
        assertThat(game.getId()).isNotNull();
        assertThat(game.getStatus()).isEqualTo("In-Progress");
        assertThat(game.getBoard()).isEqualTo(default_board);
    }

    @Test
    @DisplayName("Reset Game")
    void test_reset_game(){
        // given
        Game game = subject.createGame();
        Move move = new Move();
        move.setGameId(game.getId());
        move.setPlayer("X");
        move.setLocation("1,1");
        ResponseEntity<Game> response = subject.makeMove(move);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getWinner()).isNull();
        assertThat(response.getBody().getBoard()).isNotEqualTo(default_board);
        move.setLocation("0,1");
        response =subject.makeMove(move);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getWinner()).isNull();
        assertThat(response.getBody().getBoard()).isNotEqualTo(default_board);
        move.setLocation("2,1");
        response =subject.makeMove(move);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody().getWinner()).isNotNull();
        assertThat(response.getBody().getWinner()).isEqualTo("X");
        assertThat(response.getBody().getBoard()).isNotEqualTo(default_board);
        assertThat(response.getBody().getxWins()).isEqualTo(1);


        // when
        ResponseEntity<Game> resetResponse = subject.resetGame(response.getBody().getId().toString());

        // then
        assertThat(resetResponse.getStatusCode().value()).isEqualTo(200);
        assertThat(resetResponse.getBody().getId()).isNotNull();
        assertThat(resetResponse.getBody().getStatus()).isEqualTo("In-Progress");
        assertThat(resetResponse.getBody().getBoard()).isEqualTo(default_board);
        assertThat(resetResponse.getBody().getxWins()).isEqualTo(0);
    }

    @Test
    @DisplayName("Delete Game - Not Found")
    void test_deleteGame_notFound(){
        // when
        ResponseEntity<Void> deleteResponse = subject.deleteGame("123");

        // then
        assertThat(deleteResponse.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    @DisplayName("Delete Game")
    void test_deleteGame(){
        // when
        Game game = subject.createGame();

        // when
        ResponseEntity<Void> deleteResponse = subject.deleteGame(game.getId().toString());

        // then
        assertThat(deleteResponse.getStatusCode().value()).isEqualTo(204);
    }

    @Test
    @DisplayName("Reset Game - NO GO")
    void test_reset_game_bad(){
        // given
        Game game = subject.createGame();

        // when
        ResponseEntity<Game> resetResponse = subject.resetGame(game.getId().toString());

        // then
        assertThat(resetResponse.getStatusCode().value()).isEqualTo(200);
        assertThat(resetResponse.getBody().getStatus()).isEqualTo("In-Progress");
        assertThat(resetResponse.getBody().getBoard()).isEqualTo(default_board);
        assertThat(resetResponse.getBody().getxWins()).isEqualTo(0);
    }

}
