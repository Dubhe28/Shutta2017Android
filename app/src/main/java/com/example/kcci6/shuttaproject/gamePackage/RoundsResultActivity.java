package com.example.kcci6.shuttaproject.gamePackage;
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

    private ArrayList<RoundInfo> roundInfoList = new ArrayList<>();
    private List<Integer> gameStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rounds_info_list);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();

        if (bundle.getBoolean("playOption")) // 플레이옵션이 자동인 경우
            playGames(intent);
        else
            roundInfoList = getIntent().getParcelableArrayListExtra("rounds");

        showGameResult();
        setAdapterRoundInfo();
    }

    private void playGames(Intent intent) {
        int playerInitialMoney = intent.getIntExtra("playerMoney", 0);

        List<Player> players = Arrays.asList(new Player(playerInitialMoney), new Player(playerInitialMoney));
        Deck.getInstance().makeCards(this);

        while (Game.getInstance().isRunning(players)) {
            Game.getInstance().playGame(roundInfoList, players);
            Dealer.getInstance().returnCardPairsToDeck(players);
        }
    }

    private void showGameResult() {

        gameStatistics = getStatistics();

        ((TextView)findViewById(R.id.txvGameResult)).setText(getResultString());
    }

    private void setAdapterRoundInfo() {

        RoundInfoAdapter adapter = new RoundInfoAdapter();

        for (RoundInfo roundInfo : roundInfoList) {
            adapter.add(roundInfo);
        }
        ((ListView)findViewById(R.id.lsvRounds)).setAdapter(adapter);
    }

    private Winner getFinalWinner() {
        List<Integer> playersMoney = roundInfoList.get(roundInfoList.size() - 1).getPlayersMoney();
        if (playersMoney.get(0) > playersMoney.get(1))
            return Winner.PlayerA;
        else if (playersMoney.get(0) < playersMoney.get(1))
            return Winner.PlayerB;
        else
            return Winner.None;
    }

    private String getResultString(){

        return getFinalWinner() + " 님 이 최종 승리하셨습니다! [ 플레이 수: " + roundInfoList.size() + " ]\nPlayerA 님의 승률: "
                + gameStatistics.get(0) + "% / PlayerB 님의 승률: " + gameStatistics.get(1) + "%\n무승부:" + gameStatistics.get(2) + "%";
    }

    private List<Integer> getStatistics() {
        List<Integer> statisticsList = Arrays.asList(0, 0, 0);
        for (RoundInfo roundInfo :
                roundInfoList) {
            if (roundInfo.getWinner() == Winner.PlayerA)
                statisticsList.set(0, statisticsList.get(0) + 1);
            else if (roundInfo.getWinner() == Winner.PlayerB)
                statisticsList.set(1, statisticsList.get(1) + 1);
        }

        for (int i = 0; i < 2; i++)
            statisticsList.set(i, (int) ((double) statisticsList.get(i) / roundInfoList.size() * 100));

        statisticsList.set(2, 100 - statisticsList.get(0) - statisticsList.get(1));
        return statisticsList;
    }

}
