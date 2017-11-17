package com.example.kcci6.shuttaproject.roundInfoPackage;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.kcci6.shuttaproject.mainPackage.Winner;

import java.util.ArrayList;

public class RoundInfoAdapter  extends BaseAdapter{
    private ArrayList<RoundInfo> _roundInfos = new ArrayList<>();

    @Override
    public int getCount() {
        return _roundInfos.size();
    }

    @Override
    public RoundInfo getItem(int position) {
        return _roundInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RoundInfo roundInfo = _roundInfos.get(position);
        RoundInfoView view = new RoundInfoView(parent.getContext());

        for (int i = 0; i < 4; i++)
            view.setImvPlayerCards(i, roundInfo.getPlayersCardImageIds().get(i));

        view.setTextViews(0, String.valueOf(position+1)+"R");

        for (int i = 0; i < 2; i++) {
            view.setTextViews(3*i+1, getPlayerResult(roundInfo, i).toString());
            view.setTextViews(3*i+2, (roundInfo.getPlayerScores().get(i).toString()));
            view.setTextViews(3*i+3, (roundInfo.getPlayersMoney().get(i).toString()));
        }
        return view;
    }

    private RoundResultForPlayer getPlayerResult(RoundInfo roundInfo, int i){

        int winner = roundInfo.getWinner().ordinal();
        if(winner == 2)
            return RoundResultForPlayer.무승부;
        else
            return RoundResultForPlayer.values()[Math.abs(winner-i)];
    }

    public boolean getIsTied() {
        return _roundInfos.size() != 0 && _roundInfos.get(_roundInfos.size() - 1).getWinner() == Winner.None;
    }

    public void add(RoundInfo roundInfo){
        _roundInfos.add(roundInfo);
    }
}

