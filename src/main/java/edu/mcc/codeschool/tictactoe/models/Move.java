package edu.mcc.codeschool.tictactoe.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class Move {
    @NotNull
    UUID gameId;
    @NotNull
    @Size(min = 3, max = 3)
    String location;
    @NotNull
    @Size(min = 1, max = 1)
    String player;

    public UUID getGameId() {
        return gameId;
    }

    public Move setGameId(UUID gameId) {
        this.gameId = gameId;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Move setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getPlayer() {
        return player;
    }

    public Move setPlayer(String player) {
        this.player = player;
        return this;
    }
}
