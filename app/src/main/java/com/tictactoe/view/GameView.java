package com.tictactoe.view;

import com.tictactoe.model.Player;

import java.util.List;

public interface GameView {
    void onTapPlayer1(int res);
    void onTapPlayer2(int res);
    void showWinner(Player player);
    void updateGame(List<Player> players);
    void onResetGame();
    void onRestartGame();
    void onLoadGame(List<Player> players);
    void onDraws();
}
