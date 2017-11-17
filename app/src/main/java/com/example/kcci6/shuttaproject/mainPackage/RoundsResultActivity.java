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

    private TextView txvGameResult;
    private ArrayList<RoundInfo> roundInfos = new ArrayList<>();
    private RoundInfoAdapter adapter = new RoundInfoAdapter();
    private ListView lsvRounds;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rounds_info_list);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        boolean playOption = bundle.getBoolean("playOption"); // 자동이면 true, 수동이면 false

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

        List<Integer> gameStatistics = getStatistics();

        for (RoundInfo roundInfo : roundInfos) {
            adapter.add(roundInfo);
        }

        lsvRounds = findViewById(R.id.lsvRounds);
        lsvRounds.setAdapter(adapter);

        txvGameResult = findViewById(R.id.txvGameResult);
        txvGameResult.setText("" + getFinalWinner() + " 님 이 최종 승리하셨습니다! [ 플레이 수: " + roundInfos.size() + " ]\nPlayerA 님의 승률: "
                + gameStatistics.get(0) + "% / PlayerB 님의 승률: " +gameStatistics.get(1) + "%\n무승부:" + gameStatistics.get(2) + "%");
    }

    private Winner getFinalWinner(){
        List<Integer> playersMoney = roundInfos.get(roundInfos.size()-1).getPlayersMoney();
        if(playersMoney.get(0) > playersMoney.get(1))
            return Winner.PlayerA;
        else if (playersMoney.get(0) < playersMoney.get(1))
            return Winner.PlayerB;
        else
            return Winner.None;
    }

    private List<Integer> getStatistics(){
        List<Integer> statisticsList = Arrays.asList(0, 0, 0);
        for (RoundInfo roundInfo :
                roundInfos) {
            if (roundInfo.getWinner() == Winner.PlayerA)
                statisticsList.set(0, statisticsList.get(0) + 1);
            else if (roundInfo.getWinner() == Winner.PlayerB)
                statisticsList.set(1, statisticsList.get(1) + 1);
        }

        for(int i = 0;i < 2;i++)
            statisticsList.set(i, (int)((double)statisticsList.get(i)/roundInfos.size()*100));

        statisticsList.set(2, 100-statisticsList.get(0)-statisticsList.get(1));
        return statisticsList;
    }
}
