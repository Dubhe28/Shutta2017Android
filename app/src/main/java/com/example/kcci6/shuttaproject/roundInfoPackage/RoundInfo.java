package com.example.kcci6.shuttaproject.roundInfoPackage;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.kcci6.shuttaproject.cardPackage.cardPairPackage.Jokbo;
import com.example.kcci6.shuttaproject.mainPackage.Winner;
import com.example.kcci6.shuttaproject.playerPackage.Player;

import java.util.ArrayList;
import java.util.List;

public class RoundInfo implements Parcelable {

    public RoundInfo(List<Player> players, Winner winner) {
        setPlayersScore(players);
        setPlayersMoney(players);
        setPlayersCardImageIds(players);
        _winner = winner;
    }

    public RoundInfo(Parcel in) {
        readFromParcel(in);
    }

    // region List<Jokbo> _playersScore
    private List<Jokbo> _playersScore;

    public void setPlayersScore(List<Player> players)
    {
        _playersScore = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            _playersScore.add(players.get(i).getCardPair().getScore());
        }
    }

    public List<Jokbo> getPlayerScores()
    {
        return _playersScore;
    }
    // endregion

    // region List<Integer> _playersMoney
    private List<Integer> _playersMoney;
    public void setPlayersMoney(List<Player> players) {
        _playersMoney = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            _playersMoney.add(players.get(i).getMoney());
        }
    }
    public List<Integer> getPlayersMoney() {
        return _playersMoney;
    }
    // endregion

    // region List<Integer> _playersCardImageIds
    private List<Integer> _playersCardImageIds;

    public void setPlayersCardImageIds(List<Player> players)
    {
        _playersCardImageIds = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                _playersCardImageIds.add(players.get(i).getCardPair().getCardImageIds().get(j));
            }
        }
    }
    public List<Integer> getPlayersCardImageIds()
    {
        return _playersCardImageIds;
    }
    // endregion

    //region Winner _winner
    private Winner _winner; // _winner 변수는 이번 라운드에서 누가 이겼는지를 열거타입으로 저장한다.
    public Winner getWinner() {
        return _winner;
    }   // getter
    //endregion

    private void readFromParcel(Parcel in) {
        _playersScore = new ArrayList<>();
        _playersMoney = new ArrayList<>();
        _playersCardImageIds = new ArrayList<>();

        _winner = (Winner) in.readValue(Winner.class.getClassLoader());

        for (int i = 0; i < 2; i++) {
            _playersScore.add((Jokbo) in.readValue(Jokbo.class.getClassLoader()));
            _playersMoney.add(in.readInt());
        }

        for (int i = 0; i < 4; i++) {
            _playersCardImageIds.add(in.readInt());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(_winner);
        for (int i = 0; i < 2; i++) {
            dest.writeValue(_playersScore.get(i));
            dest.writeInt(_playersMoney.get(i));
        }
        for (int i = 0; i < 4; i++) {
            dest.writeInt(_playersCardImageIds.get(i));
        }
    }

    public static final Creator<RoundInfo> CREATOR = new Creator<RoundInfo>() {
        @Override
        public RoundInfo createFromParcel(Parcel in) {
            return new RoundInfo(in);
        }

        @Override
        public RoundInfo[] newArray(int size) {
            return new RoundInfo[size];
        }
    };

    public String getRoundInfoStr() {
        return "The Winner is "+_winner+", Remains: "
                + _playersMoney.get(0) +"원 , "+_playersMoney.get(1)+"원 "
                + _playersScore.get(0)+" "+_playersScore.get(1);
    }
}
