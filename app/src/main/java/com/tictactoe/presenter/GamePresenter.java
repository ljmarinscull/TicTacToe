package com.tictactoe.presenter;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.tictactoe.MainActivity;
import com.tictactoe.model.DBHelper;
import com.tictactoe.model.Player;
import com.tictactoe.view.GameView;

import java.sql.SQLException;

import static com.tictactoe.presenter.GamePresenter.PLAYER.P1;
import static com.tictactoe.presenter.GamePresenter.PLAYER.P2;
import static java.lang.Enum.valueOf;



public class GamePresenter {

    private GameView view;
    private PLAYER currentPlayer = P1;
    private int winner;
    private DBHelper mDBHelper;
    private boolean isWinner;
    private Player player = null;
    private int tapCount= 0;
    private Dao<Player, Integer> mPlayerEntityDao;
    private short[][] matrix = new short[3][3];

    public GamePresenter(GameView gameView){
        this.view = gameView;
        resetMatrix();
        try {
            mPlayerEntityDao = getHelper().getPlayerEntityDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* public void onHandleTapButton(int res, Object tag){
        tapCount++;
        String tagStr = (String) tag;
        String pos[] =  tagStr.split("_");
        matrix[Integer.valueOf(pos[0])][Integer.valueOf(pos[1])] = (short) currentPlayer.getNumber();

        isWinner = winner(currentPlayer.getNumber());

        try {
            if(isWinner){
                player = mPlayerEntityDao.queryForId(currentPlayer.getNumber());
                player.addWins();
                mPlayerEntityDao.update(player);
                resetMatrix();

                if (currentPlayer == P1){
                    view.onTapPlayer1(res,isWinner);
                } else {
                    player = mPlayerEntityDao.queryForId((currentPlayer.getNumber()));
                    currentPlayer = P1;
                    view.onTapPlayer2(res,isWinner);
                }
            } else {
                if(currentPlayer == P1)
                    currentPlayer = P2;
                currentPlayer = P1;
                if(tapCount == 9){
                    Player player1 = mPlayerEntityDao.queryForId((1));
                    player1.addDraws();
                    mPlayerEntityDao.update(player1);
                    view.onDraws();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public void onHandleTapButton(int res, Object tag) throws SQLException {
        tapCount++;
        String tagStr = (String) tag;
        String pos[] =  tagStr.split("_");
        matrix[Integer.valueOf(pos[0])][Integer.valueOf(pos[1])] = (short) currentPlayer.getNumber();

        isWinner = winner(currentPlayer.getNumber());
        if (isWinner){
            winner = currentPlayer.getNumber();
        }

        if (currentPlayer == P1){
            if(!isWinner) currentPlayer = P2;
            view.onTapPlayer1(res);
        } else {
            currentPlayer = P1;
            view.onTapPlayer2(res);
        }

        if(isWinner){
            player = mPlayerEntityDao.queryForId(winner);
            player.addWins();
            mPlayerEntityDao.update(player);
            view.updateGame(mPlayerEntityDao.queryForAll());
            view.showWinner(player);
            resetMatrix();
        } else {
            if(tapCount == 9){
                currentPlayer = P1;
                Player player1 = mPlayerEntityDao.queryForId((1));
                player1.addDraws();
                mPlayerEntityDao.update(player1);
                view.updateGame(mPlayerEntityDao.queryForAll());
                resetMatrix();
                view.onDraws();
            }
        }
    }

    public void onReset(){
        tapCount = 0;
        currentPlayer = P1;

        resetMatrix();
        resetDataBase();
        view.onResetGame();
    }

    public void onRestart(){
        tapCount = 0;
        currentPlayer = P1;

        resetMatrix();
        view.onRestartGame();
    }

    private boolean winner(int ordinal){

        if(ordinal == matrix[0][0] && ordinal == matrix[0][1] && ordinal == matrix[0][2] ) {
            return true;
        } else if(ordinal == matrix[1][0] && ordinal == matrix[1][1] && ordinal == matrix[1][2] ){
            return true;
        } else if(ordinal == matrix[2][0] && ordinal == matrix[2][1] && ordinal == matrix[2][2] ){
            return true;
        }  else  if(ordinal == matrix[0][0] && ordinal == matrix[1][0] && ordinal == matrix[2][0] ) {
            return true;
        } else if(ordinal == matrix[0][1] && ordinal == matrix[1][1] && ordinal == matrix[2][1] ){
            return true;
        } else if(ordinal == matrix[0][2] && ordinal == matrix[1][2] && ordinal == matrix[2][2] ){
            return true;
        } else if(ordinal == matrix[0][0] && ordinal == matrix[1][1] && ordinal == matrix[2][2] ){
            return true;
        } else if(ordinal == matrix[0][2] && ordinal == matrix[1][1] && ordinal == matrix[2][0] ){
            return true;
        }

        return false;
    }

    private DBHelper getHelper() {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper((MainActivity)this.view, DBHelper.class);
        }
        return mDBHelper;
    }

    private void resetDataBase(){
        try {
            Player player1 = mPlayerEntityDao.queryForId(1);
            Player player2 = mPlayerEntityDao.queryForId(2);
            player1.setDraws(0);
            player1.setWins(0);
            mPlayerEntityDao.update(player1);
            player2.setDraws(0);
            player2.setWins(0);
            mPlayerEntityDao.update(player2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadGame(){
        try {
            currentPlayer = P1;
            view.onLoadGame(mPlayerEntityDao.queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void resetMatrix(){
        for (int i = 0;i<3;i++){
            matrix[i][0] = -1;
            matrix[i][1] = -1;
            matrix[i][2] = -1;
        }
    }

    public enum PLAYER{ P1(1),P2(2);

        private final int number;

        private PLAYER(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
        }
    }

}
