package com.example.kcci6.shuttaproject.fragmentPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kcci6.shuttaproject.R;
import com.example.kcci6.shuttaproject.gamePackage.Winner;

import java.util.Arrays;
import java.util.List;

public class PlayerInfoFragment extends Fragment {

    private TextView txvPlayerInfo;
    private TextView txvPlayerName;

    private int playerType;
    private List<Integer> resultCounts;

    public PlayerInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewGroup = inflater.inflate(R.layout.fragment_player_info, container, true);
        txvPlayerName = viewGroup.findViewById(R.id.txvPlayerName);
        txvPlayerInfo = viewGroup.findViewById(R.id.txvPlayerInfo);
        resultCounts = Arrays.asList(0, 0, 0);
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
        txvPlayerInfo.setText(getPlayerInfoStr(playersMoney));
    }

    public void initTxvPlayerInfo(List<Integer> playersMoney){

        txvPlayerInfo.setText(getPlayerInfoStr(playersMoney));
    }

    private void setCounts(Winner winner){

        if(playerType == convertWinnerToInt(winner))
            resultCounts.set(0, resultCounts.get(0)+1); // when player wins
        else if (convertWinnerToInt(winner) == 2)
            resultCounts.set(2, resultCounts.get(2)+1); // when players ties
        else
            resultCounts.set(1, resultCounts.get(1)+1); // when player loses

    }

    private int convertWinnerToInt(Winner winner){
        if(winner == Winner.PlayerA)
            return 0;
        else if(winner == Winner.PlayerB)
            return 1;
        else
            return 2;
    }

    private String getPlayerInfoStr(List<Integer> playersMoney){

        return resultCounts.get(0) + " 승 " +  resultCounts.get(1) +" 패 / "+ resultCounts.get(2) +" 무\n남은 금액: "
                + playersMoney.get(playerType) + "원";
    }
}
