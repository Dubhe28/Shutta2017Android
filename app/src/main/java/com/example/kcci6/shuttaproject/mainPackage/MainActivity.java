package com.example.kcci6.shuttaproject.mainPackage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.kcci6.shuttaproject.playerPackage.PlayerCardFragment;
import com.example.kcci6.shuttaproject.R;
import com.example.kcci6.shuttaproject.cardPackage.Deck;
import com.example.kcci6.shuttaproject.playerPackage.*;
import com.example.kcci6.shuttaproject.roundInfoPackage.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.kcci6.shuttaproject.mainPackage.StartMenuActivity.PLAY_OPTION2;

public class MainActivity extends AppCompatActivity {
    private List<Player> players;
    private ArrayList<RoundInfo> roundInfoList = new ArrayList<>();
    private ArrayList<PlayerCardFragment> playerCardFragments = new ArrayList<>();
    private ArrayList<PlayerInfoFragment> playerInfoFragments = new ArrayList<>();
    private Intent intent;
    private boolean clickEnabled = false;

    private static final int REQUEST_CODE_SHOW_RESULT = 201;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 옵션 화면으로부터 정보를 받아온다.
        Intent intent = getIntent();
        int playerInitialMoney = intent.getIntExtra("playerMoney", 0);

        // 플레이어와 카드들을 생성한다.
        players = Arrays.asList(new Player(playerInitialMoney), new Player(playerInitialMoney));
        Deck.getInstance().makeCards(this);

        setFragmentsId();

        setPlayerCardsOnClickListener();
    }

    private void setPlayerCardsOnClickListener() {
        for (int i = 0; i < 4; i++) {
            playerCardFragments.get(i).getImvCardBack().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeStatus();
                    if(clickEnabled) {
                        Game.getInstance().playGame(roundInfoList, players);

                        rotateCards();

                        Dealer.getInstance().returnCardPairsToDeck(players);
                        if (!Game.getInstance().isRunning(players))
                            goToNextActivity();
                        else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 4; i++) {
                                        playerCardFragments.get(i).rotate();
                                    }
                                }
                            }, 3000);

                            setPlayerInfoTxts();
                            showDialog();
                            changeStatus();
                        }
                    }
                }
            });
        }
    }

    private void setPlayerInfoTxts() {
        for (int j = 0; j < 2; j++) {
            playerInfoFragments.get(j).setTxvPlayerInfo(roundInfoList.get(roundInfoList.size()-1).getWinner(),
                    roundInfoList.get(roundInfoList.size()-1).getPlayersMoney());
        }
    }

    private void rotateCards() {
        for (int i = 0; i < 4; i++) {
            playerCardFragments.get(i).setImv(players.get(i / 2).getCardPair().getCardImageIds().get(i % 2));
            playerCardFragments.get(i).rotate();
        }
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

    private void changeStatus()
    {
        clickEnabled = !clickEnabled;
    }

    private void goToNextActivity() {
        intent = new Intent(getApplicationContext(), RoundsResultActivity.class);
        intent.putExtra("playOption", PLAY_OPTION2);
        intent.putParcelableArrayListExtra("rounds", roundInfoList);
        startActivityForResult(intent, REQUEST_CODE_SHOW_RESULT);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(roundInfoList.get(roundInfoList.size()-1).getRoundInfoStr())
                .setCancelable(false).setPositiveButton("계속",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton("종료",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        intent = new Intent(getApplicationContext(), RoundsResultActivity.class);
                        intent.putExtra("gameOption", PLAY_OPTION2);
                        intent.putParcelableArrayListExtra("rounds", roundInfoList);
                        startActivityForResult(intent, REQUEST_CODE_SHOW_RESULT);
                    }
                });
        AlertDialog alert = builder.create();
        // Title for AlertDialog
        alert.setTitle("[ Round Result ]");
        alert.show();
    }
}