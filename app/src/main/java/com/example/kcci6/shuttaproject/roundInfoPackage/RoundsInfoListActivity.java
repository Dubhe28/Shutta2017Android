package com.example.kcci6.shuttaproject.roundInfoPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.kcci6.shuttaproject.R;
import com.example.kcci6.shuttaproject.mainPackage.Dealer;
import com.example.kcci6.shuttaproject.mainPackage.Game;
import com.example.kcci6.shuttaproject.mainPackage.Winner;

import java.util.ArrayList;

public class RoundsInfoListActivity extends AppCompatActivity {

    TextView txvGameResult;
    ArrayList<RoundInfo> roundInfos;
    RoundInfoAdapter adapter = new RoundInfoAdapter();
    ListView lsvRounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rounds_info_list);

/*
        if(playOption) // 플레이옵션이 자동인 경우
        {
            while(Game.getInstance().isRunning(players))
            {
                Game.getInstance().playGame(roundInfos, players);
                // 카드를 돌려보낸다.
                Dealer.getInstance().returnCardPairsToDeck(players);
            }

            goToNextActivity();
        }

*/

        roundInfos = getIntent().getParcelableArrayListExtra("rounds");

        for (RoundInfo roundInfo : roundInfos) {
            adapter.add(roundInfo);
        }

        lsvRounds = findViewById(R.id.lsvRounds);
        lsvRounds.setAdapter(adapter);

        txvGameResult = findViewById(R.id.txvGameResult);
        //txvGameResult.setText(string);
    }
}
