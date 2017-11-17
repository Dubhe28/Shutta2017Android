package com.example.kcci6.shuttaproject.playerPackage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kcci6.shuttaproject.R;
import com.example.kcci6.shuttaproject.mainPackage.Winner;

import java.util.List;

public class PlayerInfoFragment extends Fragment {

    private TextView txvPlayerInfo;
    private TextView txvPlayerName;

    private int playerType;
    private int winCounts = 0;
    private int loseCounts = 0;
    private int tieCounts = 0;

    public PlayerInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewGroup = inflater.inflate(R.layout.fragment_player_info, container, true);
        txvPlayerName = viewGroup.findViewById(R.id.txvPlayerName);
        txvPlayerInfo = viewGroup.findViewById(R.id.txvPlayerInfo);
        return viewGroup;
    }

    public void setTxvPlayerName(int i){

        playerType = i;
        if(i == 0)
            txvPlayerName.setText("Player A:");
        else if(i == 1)
            txvPlayerName.setText("Player B:");
    }

    public void setTxvPlayerInfo(Winner winner, List<Integer> playersMoney){

        setCounts(winner);
        String str = winCounts + " 승 " +  loseCounts +" 패 / "+ tieCounts +" 무\n남은 금액: "
                + playersMoney.get(playerType) + "원";
        txvPlayerInfo.setText(str);
    }

    private void setCounts(Winner winner){

        if(playerType == convertWinnerToInt(winner))
            winCounts++;
        else if (convertWinnerToInt(winner) == 2)
            tieCounts++;
        else
            loseCounts++;

    }

    private int convertWinnerToInt(Winner winner){
        if(winner == Winner.PlayerA)
            return 0;
        else if(winner == Winner.PlayerB)
            return 1;
        else
            return 2;
    }
}
