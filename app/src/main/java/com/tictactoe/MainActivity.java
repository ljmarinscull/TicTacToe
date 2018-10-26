package com.tictactoe;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tictactoe.dagger.DaggerComponentApp;
import com.tictactoe.dagger.ModuleApp;
import com.tictactoe.model.Player;
import com.tictactoe.presenter.GamePresenter;
import com.tictactoe.view.GameView;

import java.sql.SQLException;
import java.util.List;


import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GameView {

    private Button b11,b12,b13,b21,b22,b23,b31,b32,b33,restart,reset;
    private TextView player1Txt,player2Txt,p1WinsValue,p2WinsValue,drawsValue;

    @Inject GamePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerComponentApp.builder()
                .moduleApp(new ModuleApp(this))
                .build()
                .inject(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE },10);
            } else {
                init();
            }
        } else {
            init();
        }
    }

    private void init(){

        player1Txt = findViewById(R.id.player1Txt);
        player2Txt = findViewById(R.id.player2Txt);
        player2Txt.setVisibility(View.INVISIBLE);
        p1WinsValue = findViewById(R.id.p1_wins_value);
        p2WinsValue = findViewById(R.id.p2_wins_value);
        drawsValue = findViewById(R.id.draws_value);

        b11 = findViewById(R.id.b11);
        b12 = findViewById(R.id.b12);
        b13 = findViewById(R.id.b13);
        b21 = findViewById(R.id.b21);
        b22 = findViewById(R.id.b22);
        b23 = findViewById(R.id.b23);
        b31 = findViewById(R.id.b31);
        b32 = findViewById(R.id.b32);
        b33 = findViewById(R.id.b33);

        restart = findViewById(R.id.restart);
        restart.setOnClickListener(this);
        reset = findViewById(R.id.reset);
        reset.setOnClickListener(this);

        b11.setOnClickListener(this);
        b12.setOnClickListener(this);
        b13.setOnClickListener(this);
        b21.setOnClickListener(this);
        b22.setOnClickListener(this);
        b23.setOnClickListener(this);
        b31.setOnClickListener(this);
        b32.setOnClickListener(this);
        b33.setOnClickListener(this);

        mPresenter.loadGame();
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.reset){
            mPresenter.onReset();
            return;
        } else if(v.getId() == R.id.restart){
            mPresenter.onRestart();
            return;
        }
        try {
            mPresenter.onHandleTapButton(v.getId(),v.getTag());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTapPlayer1(int res) {
        player1Txt.setVisibility(View.INVISIBLE);
        player2Txt.setVisibility(View.VISIBLE);

        ((TextView)findViewById(res)).setText(getString(R.string.x));
        findViewById(res).setEnabled(false);
    }

    @Override
    public void onTapPlayer2(int res) {
        player1Txt.setVisibility(View.VISIBLE);
        player2Txt.setVisibility(View.INVISIBLE);

        ((TextView)findViewById(res)).setText(getString(R.string.o));
        findViewById(res).setEnabled(false);
    }

    @Override
    public void showWinner(Player player) {
        Toast toast = Toast.makeText(MainActivity.this,player.getName() +" wins!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        disableAll();
    }

    @Override
    public void updateGame(List<Player> players) {
        if(players != null){
            Player p1 = players.get(0);
            Player p2 = players.get(1);
            p1WinsValue.setText(String.valueOf(p1.getWins()));
            drawsValue.setText(String.valueOf(p1.getDraws()));
            p2WinsValue.setText(String.valueOf(p2.getWins()));
        }
    }

    @Override
    public void onResetGame() {
        resetCell();
        player1Txt.setVisibility(View.VISIBLE);
        player2Txt.setVisibility(View.INVISIBLE);
        mPresenter.loadGame();
    }

    @Override
    public void onRestartGame() {
        restartGame();
    }

    @Override
    public void onLoadGame(List<Player> players) {
        if(players != null){
            Player p1 = players.get(0);
            Player p2 = players.get(1);
            p1WinsValue.setText(String.valueOf(p1.getWins()));
            drawsValue.setText(String.valueOf(p1.getDraws()));
            p2WinsValue.setText(String.valueOf(p2.getWins()));
        }
    }

    @Override
    public void onDraws() {
        Toast toast = Toast.makeText(MainActivity.this,"Draws!", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        disableAll();
    }

    private void restartGame(){
        resetCell();
        player1Txt.setVisibility(View.VISIBLE);
        player2Txt.setVisibility(View.INVISIBLE);
    }

    private void resetCell(){
        b11.setText(getString(R.string.empty_space));
        b12.setText(getString(R.string.empty_space));
        b13.setText(getString(R.string.empty_space));
        b21.setText(getString(R.string.empty_space));
        b22.setText(getString(R.string.empty_space));
        b23.setText(getString(R.string.empty_space));
        b31.setText(getString(R.string.empty_space));
        b32.setText(getString(R.string.empty_space));
        b33.setText(getString(R.string.empty_space));

        b11.setEnabled(true);
        b12.setEnabled(true);
        b13.setEnabled(true);
        b21.setEnabled(true);
        b22.setEnabled(true);
        b23.setEnabled(true);
        b31.setEnabled(true);
        b32.setEnabled(true);
        b33.setEnabled(true);
    }

    private void disableAll(){
        b11.setEnabled(false);
        b12.setEnabled(false);
        b13.setEnabled(false);
        b21.setEnabled(false);
        b22.setEnabled(false);
        b23.setEnabled(false);
        b31.setEnabled(false);
        b32.setEnabled(false);
        b33.setEnabled(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    init();
                } else {
                    finish();
                }
                return;
            }
        }
    }
}
