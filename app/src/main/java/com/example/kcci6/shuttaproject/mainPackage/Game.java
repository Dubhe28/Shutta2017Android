package com.example.kcci6.shuttaproject.mainPackage;

import com.example.kcci6.shuttaproject.playerPackage.Player;
import com.example.kcci6.shuttaproject.roundInfoPackage.RoundInfo;
import java.util.List;

class Game {

    private static Game instance = new Game();

    private Game() {}

    static Game getInstance() {
        return instance;
    }

    void playGame(List<RoundInfo> roundInfos, List<Player> players) {
        boolean isLastGameTied = judgeTie(roundInfos);
        Dealer.getInstance().giveCardPairs(isLastGameTied, players); // 딜러가 카드를 나눠준다.

        // 배팅한다.
        BettingMoneyManager.getInstance().betMoney(players, isLastGameTied);

        // 승자를 확인하고, 플레이어에 배팅액을 분배한다.
        BettingMoneyManager.getInstance().attributeMoney(players);

        // 한 라운드의 결과를 형성하고 리스트에 추가한다.
        roundInfos.add(new RoundInfo(players, BettingMoneyManager.getInstance().getWinner()));
    }

    private boolean judgeTie(List<RoundInfo> roundInfos) {
        return roundInfos.size() != 0 && (roundInfos.get(roundInfos.size() - 1)).getWinner() == Winner.None;
    }

    boolean isRunning(List<Player> players) {
        return (players.get(0).getMoney() > 0) && (players.get(1).getMoney() > 0);
    }
}
