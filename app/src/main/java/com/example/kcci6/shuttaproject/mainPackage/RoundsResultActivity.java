package com.example.kcci6.shuttaproject.mainPackage;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kcci6.shuttaproject.R;
import com.example.kcci6.shuttaproject.cardPackage.Deck;
import com.example.kcci6.shuttaproject.playerPackage.Player;
import com.example.kcci6.shuttaproject.roundInfoPackage.RoundInfo;
import com.example.kcci6.shuttaproject.roundInfoPackage.RoundInfoAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoundsResultActivity extends AppCompatActivity {

    TextView txvGameResult;
    ArrayList<RoundInfo> roundInfos = new ArrayList<>();
    RoundInfoAdapter adapter = new RoundInfoAdapter();
    ListView lsvRounds;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rounds_info_list);

        Intent intent = getIntent();
        boolean playOption = intent.getBooleanExtra("playOption", true); // 자동이면 true, 수동이면 false

        if(playOption) // 플레이옵션이 자동인 경우
        {
            int playerInitialMoney = intent.getIntExtra("playerMoney", 0);

            // 플레이어와 카드들을 생성한다.
            List<Player> players = Arrays.asList(new Player(playerInitialMoney), new Player(playerInitialMoney));
            Deck.getInstance().makeCards(this);

            while(Game.getInstance().isRunning(players))
            {
                Game.getInstance().playGame(roundInfos, players);
                // 카드를 돌려보낸다.
                Dealer.getInstance().returnCardPairsToDeck(players);
            }
        }
        else
        {
            roundInfos = getIntent().getParcelableArrayListExtra("rounds");
        }

        for (RoundInfo roundInfo : roundInfos) {
            adapter.add(roundInfo);
        }

        lsvRounds = findViewById(R.id.lsvRounds);
        lsvRounds.setAdapter(adapter);

        txvGameResult = findViewById(R.id.txvGameResult);
        txvGameResult.setText("Final Winner: " + roundInfos.get(roundInfos.size()-1).getWinner());
    }
}