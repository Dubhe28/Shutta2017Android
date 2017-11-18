package com.example.kcci6.shuttaproject.gamePackage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.kcci6.shuttaproject.R;
import com.example.kcci6.shuttaproject.fragmentPackage.*;
import com.example.kcci6.shuttaproject.cardPackage.Deck;
import com.example.kcci6.shuttaproject.playerPackage.Player;
import com.example.kcci6.shuttaproject.roundInfoPackage.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.kcci6.shuttaproject.gamePackage.StartMenuActivity.PLAY_OPTION2;

public class MainActivity extends AppCompatActivity {

    public static final int DELAY_TIME_UNIT_FRONT = 60; // LAST CARD SHOWED AT DELAY_TIME_UNIT_FRONT * getDelayTimeFromIndex(3)
    public static final int DELAY_MILLIS_SHOW_ALERT = (int)(DELAY_TIME_UNIT_FRONT * getDelayTimeFromIndex(3)) + 1000;
    public static final int DELAY_MILLIS_DISMISS_ALERT = DELAY_MILLIS_SHOW_ALERT + 2000;

    private List<Player> players;
    private ArrayList<RoundInfo> roundInfoList = new ArrayList<>();
    private ArrayList<PlayerCardFragment> playerCardFragments = new ArrayList<>();
    private ArrayList<PlayerInfoFragment> playerInfoFragments = new ArrayList<>();
    private boolean clickEnabled = false;
    private static final int REQUEST_CODE_SHOW_RESULT = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Deck.getInstance().makeCards(this);
        setPlayers(getIntent().getIntExtra("playerMoney", 0));
        setGameView();
    }

    private void setGameView() {
        setFragmentsId();
        initPlayerInfoTexts(Arrays.asList(players.get(0).getMoney(), players.get(1).getMoney()));
        setPlayerCardsOnClickListener();
    }

    private void setPlayers(int playerInitialMoney) {
        players = Arrays.asList(new Player(playerInitialMoney), new Player(playerInitialMoney));
    }

    private void setFragmentsId() {
        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 3; j++) {
                playerCardFragments.add((PlayerCardFragment) getSupportFragmentManager().findFragmentById(
                        getResources().getIdentifier("frgPlayer" + i + "Card" + j, "id", "com.example.kcci6.shuttaproject")));
            }
            playerInfoFragments.add((PlayerInfoFragment) getSupportFragmentManager().findFragmentById(
                    getResources().getIdentifier("frgPlayerInfo" + i , "id", "com.example.kcci6.shuttaproject")));
            playerInfoFragments.get(i-1).setTxvPlayerName(i-1);
        }
    }

    private void setPlayerCardsOnClickListener() {
        for (int i = 0; i < 4; i++) {
            playerCardFragments.get(i).getImvCardBack().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeStatus();
                    if(clickEnabled) {

                        playRound();

                        if (!Game.getInstance().isRunning(players))
                            goToNextActivity();
                        else {
                            goToNextRound();
                        }
                    }
                }

                private void playRound() {
                    Game.getInstance().playGame(roundInfoList, players);

                    setPlayerCardImages();
                    showCardsFront();

                    Dealer.getInstance().returnCardPairsToDeck(players);
                }

                private void showCardsFront() {
                    for (int i = 0; i < 4; i++) {
                        final int cardIndex = i;
                        final int delayTime;
                        delayTime = (int) (DELAY_TIME_UNIT_FRONT * getDelayTimeFromIndex(i));
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                playerCardFragments.get(cardIndex).rotate();}
                        }, delayTime);
                    }
                }

                private void goToNextRound() {
                    showDialog();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showCardsBack();
                            changeStatus();
                            setPlayerInfoTexts();
                        }

                        private void showCardsBack() {
                            for (int i = 0; i < 4; i++) {
                            playerCardFragments.get(i).rotate();
                        }
                        }
                    }, DELAY_MILLIS_DISMISS_ALERT);
                }
            });
        }
    }

    private static double getDelayTimeFromIndex(int i) {
        return Math.exp(i)*i;
    }

    private void setPlayerInfoTexts() {
        for (int j = 0; j < 2; j++) {
            playerInfoFragments.get(j).setTxvPlayerInfo(roundInfoList.get(roundInfoList.size()-1).getWinner(),
                    roundInfoList.get(roundInfoList.size()-1).getPlayersMoney());
        }
    }

    private void initPlayerInfoTexts(List<Integer> playersMoney){
        for (int j = 0; j < 2; j++) {
            playerInfoFragments.get(j).initTxvPlayerInfo(playersMoney);
        }
    }

    private void setPlayerCardImages() {
        for (int i = 0; i < 4; i++) {
            playerCardFragments.get(i).setImv(players.get(i / 2).getCardPair().getCardImageIds().get(i % 2));
        }
    }

    private void changeStatus()
    {
        clickEnabled = !clickEnabled;
    }

    private void goToNextActivity() {
        Intent intent = new Intent(getApplicationContext(), RoundsResultActivity.class);
        intent.putExtra("gameOption", PLAY_OPTION2);
        intent.putParcelableArrayListExtra("rounds", roundInfoList);
        startActivityForResult(intent, REQUEST_CODE_SHOW_RESULT);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        setAlertDialog(builder);
        final AlertDialog alert = builder.create();
        alert.setTitle("[ Round Result ]");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { alert.show();}
        }, DELAY_MILLIS_SHOW_ALERT);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { alert.dismiss(); }
            }, DELAY_MILLIS_DISMISS_ALERT);
    }

    private void setAlertDialog(AlertDialog.Builder builder) {

        builder.setMessage(roundInfoList.get(roundInfoList.size()-1).getRoundInfoStr())
                .setCancelable(false).setPositiveButton("종료",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        goToNextActivity();
                    }
                });
    }
}