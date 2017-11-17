package com.example.kcci6.shuttaproject.mainPackage;

import com.example.kcci6.shuttaproject.playerPackage.Player;

import java.util.List;

class BettingMoneyManager {

    // region class BettingMoneyManager declared as a Singleton
    private static BettingMoneyManager instance = new BettingMoneyManager();
    private BettingMoneyManager() {}
    static BettingMoneyManager getInstance() {
        return instance;
    }
    //endregion

    private int _bettingMoney = STAT_BETTING_MONEY;

    // region static int STAT_BETTING_MONEY
    private static int STAT_BETTING_MONEY;
    void setStaticBettingMoney(int statBettingMoney) {
        STAT_BETTING_MONEY = 2 * statBettingMoney;
    }
    // endregion

    // region Winner _winner
    private Winner _winner;
    private void setWinner(List<Player> players) {
        int comparison = players.get(0).getCardPair().compareTo(players.get(1).getCardPair());
        if (comparison > 0)
            _winner = Winner.PlayerA;
        else if (comparison < 0)
            _winner = Winner.PlayerB;
        else
            _winner = Winner.None;
    }
    Winner getWinner(){ return _winner;}
    // endregion

    // 각각의 플레이어의 소지금에서 배팅 금액 빼기
    void betMoney(List<Player> players, boolean isTied){
        judgeBettingMoney(isTied);
        for (int i = 0; i < 2; i++) {
            Player player = players.get(i);
            player.setMoney(player.getMoney()-_bettingMoney/2);
        }
    }

    // 전 판이 무승부인 경우
    private void judgeBettingMoney(boolean tie){
        if(tie)
            _bettingMoney = _bettingMoney * 2;
        else
            _bettingMoney = STAT_BETTING_MONEY;
    }

    //  게임이 끝난 후 이긴 플레이어에게 배팅액 분배
    void attributeMoney(List<Player> players){
        setWinner(players);
        Player p1 = players.get(0);
        Player p2 = players.get(1);
        if(_winner == Winner.PlayerA)
            p1.setMoney(p1.getMoney() + _bettingMoney);
        else if (_winner == Winner.PlayerB)
            p2.setMoney(p2.getMoney() + _bettingMoney);
        else{
            p1.setMoney(p1.getMoney()+_bettingMoney/2);
            p2.setMoney(p2.getMoney()+_bettingMoney/2);
        }
    }
}
