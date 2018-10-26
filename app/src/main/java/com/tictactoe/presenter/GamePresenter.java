package com.tictactoe.presenter;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.tictactoe.MainActivity;
import com.tictactoe.model.DBHelper;
import com.tictactoe.model.Player;
import com.tictactoe.view.GameView;

import java.sql.SQLException;

import javax.inject.Inject;

import static com.tictactoe.presenter.GamePresenter.PLAYER.P1;
import static com.tictactoe.presenter.GamePresenter.PLAYER.P2;

public class GamePresenter {

    private GameView mView;
    private PLAYER mCurrentPlayer = P1;
    private int mWinner;
    private DBHelper mDBHelper;
    private boolean mIsWinner;
    private Player mPlayer = null;
    private int mTapCount= 0;
    private Dao<Player, Integer> mPlayerEntityDao;
    private short[][] mMatrix = new short[3][3];

    @Inject
    public GamePresenter(GameView gameView){
        this.mView = gameView;
        resetMatrix();
        try {
            mPlayerEntityDao = getHelper().getPlayerEntityDao();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onHandleTapButton(int res, Object tag) throws SQLException {
        mTapCount++;
        String tagStr = (String) tag;
        String pos[] =  tagStr.split("_");
        mMatrix[Integer.valueOf(pos[0])][Integer.valueOf(pos[1])] = (short) mCurrentPlayer.getNumber();

        mIsWinner = mWinner(mCurrentPlayer.getNumber());
        if (mIsWinner){
            mWinner = mCurrentPlayer.getNumber();
        }

        if (mCurrentPlayer == P1){
            if(!mIsWinner) mCurrentPlayer = P2;
            mView.onTapPlayer1(res);
        } else {
            mCurrentPlayer = P1;
            mView.onTapPlayer2(res);
        }

        if(mIsWinner){
            mPlayer = mPlayerEntityDao.queryForId(mWinner);
            mPlayer.addWins();
            mPlayerEntityDao.update(mPlayer);
            mView.updateGame(mPlayerEntityDao.queryForAll());
            mView.showWinner(mPlayer);
            resetMatrix();
        } else {
            if(mTapCount == 9){
                mCurrentPlayer = P1;
                Player mPlayer1 = mPlayerEntityDao.queryForId((1));
                mPlayer1.addDraws();
                mPlayerEntityDao.update(mPlayer1);
                mView.updateGame(mPlayerEntityDao.queryForAll());
                resetMatrix();
                mView.onDraws();
            }
        }
    }

    public void onReset(){
        mTapCount = 0;
        mCurrentPlayer = P1;

        resetMatrix();
        resetDataBase();
        mView.onResetGame();
    }

    public void onRestart(){
        mTapCount = 0;
        mCurrentPlayer = P1;

        resetMatrix();
        mView.onRestartGame();
    }

    private boolean mWinner(int ordinal){

        if(ordinal == mMatrix[0][0] && ordinal == mMatrix[0][1] && ordinal == mMatrix[0][2] ) {
            return true;
        } else if(ordinal == mMatrix[1][0] && ordinal == mMatrix[1][1] && ordinal == mMatrix[1][2] ){
            return true;
        } else if(ordinal == mMatrix[2][0] && ordinal == mMatrix[2][1] && ordinal == mMatrix[2][2] ){
            return true;
        }  else  if(ordinal == mMatrix[0][0] && ordinal == mMatrix[1][0] && ordinal == mMatrix[2][0] ) {
            return true;
        } else if(ordinal == mMatrix[0][1] && ordinal == mMatrix[1][1] && ordinal == mMatrix[2][1] ){
            return true;
        } else if(ordinal == mMatrix[0][2] && ordinal == mMatrix[1][2] && ordinal == mMatrix[2][2] ){
            return true;
        } else if(ordinal == mMatrix[0][0] && ordinal == mMatrix[1][1] && ordinal == mMatrix[2][2] ){
            return true;
        } else if(ordinal == mMatrix[0][2] && ordinal == mMatrix[1][1] && ordinal == mMatrix[2][0] ){
            return true;
        }

        return false;
    }

    private DBHelper getHelper() {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper((MainActivity)this.mView, DBHelper.class);
        }
        return mDBHelper;
    }

    private void resetDataBase(){
        try {
            Player mPlayer1 = mPlayerEntityDao.queryForId(1);
            Player mPlayer2 = mPlayerEntityDao.queryForId(2);
            mPlayer1.setDraws(0);
            mPlayer1.setWins(0);
            mPlayerEntityDao.update(mPlayer1);
            mPlayer2.setDraws(0);
            mPlayer2.setWins(0);
            mPlayerEntityDao.update(mPlayer2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadGame(){
        try {
            mCurrentPlayer = P1;
            mView.onLoadGame(mPlayerEntityDao.queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void resetMatrix(){
        for (int i = 0;i<3;i++){
            mMatrix[i][0] = -1;
            mMatrix[i][1] = -1;
            mMatrix[i][2] = -1;
        }
    }

    public enum PLAYER{ P1(1),P2(2);

        private final int mNumber;

        private PLAYER(int number) {
            this.mNumber = number;
        }

        public int getNumber() {
            return mNumber;
        }
    }

}
