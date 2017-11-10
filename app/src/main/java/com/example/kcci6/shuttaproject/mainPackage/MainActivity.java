package com.example.kcci6.shuttaproject.mainPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kcci6.shuttaproject.R;
import com.example.kcci6.shuttaproject.cardPackage.Deck;
import com.example.kcci6.shuttaproject.playerPackage.*;
import com.example.kcci6.shuttaproject.roundInfoPackage.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public List<Player> players;
    private ArrayList<RoundInfo> roundInfos = new ArrayList<>();
    private ArrayList<PlayerCardFragment> playerCardFragments = new ArrayList<>();
    private Intent intentNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 옵션 화면으로부터 정보를 받아온다.
        Intent intent = this.getIntent();
        int playerInitialMoney = intent.getIntExtra("playerMoney", 0);
        final boolean playOption = intent.getBooleanExtra("playOption", false); // 자동이면 true, 수동이면 false

        // 플레이어와 카드들을 생성한다.
        players = Arrays.asList(new Player(playerInitialMoney), new Player(playerInitialMoney));
        Deck.getInstance().makeCards(this);

        for (int i = 1; i < 3; i++)
            for (int j = 1; j < 3; j++) {
                playerCardFragments.add((PlayerCardFragment) getSupportFragmentManager().findFragmentById(
                        getResources().getIdentifier("frgPlayer" + i + "Card" + j, "id", "com.example.kcci6.shuttaproject")));
            }


            ImageView imageViewBack = findViewById(R.id.imvCardBack);
            imageViewBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Game.getInstance().playGame(roundInfos, players);
                    for (int i = 0; i < 4; i++) {
                        playerCardFragments.get(i).setImv(players.get(i / 2).getCardPair().getCardImageIds().get(i % 2));
                    }

                    // 카드를 되돌린다.
                    Dealer.getInstance().returnCardPairsToDeck(players);

                    if(!Game.getInstance().isRunning(players))
                    {
                        goToNextActivity();
                    }
                    else
                    {
                        // 한 라운드의 결과가 화면에 Toast로 출력된다.(이후 팝업으로 바꾸기)
                        Toast.makeText(getApplicationContext(), roundInfos.get(roundInfos.size() - 1).getRoundInfoStr(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        // 3초 후에 토스트가 뜬다.


    private void goToNextActivity() {
        intentNext = new Intent(getApplicationContext(), RoundsInfoListActivity.class);
        intentNext.putParcelableArrayListExtra("rounds", roundInfos);
        startActivity(intentNext);
    }
}
