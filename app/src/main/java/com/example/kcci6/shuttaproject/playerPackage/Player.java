package com.example.kcci6.shuttaproject.playerPackage;
import com.example.kcci6.shuttaproject.cardPackage.cardPairPackage.CardPair;

public class Player{

    public Player(int _money) {
        this._money = _money;
    }

    //region CardPair cardPair
    private CardPair cardPair;
    public CardPair getCardPair() {
        return cardPair;
    }
    public void setCardPair(CardPair cardPair) {
        this.cardPair = cardPair;
    }
    //endregion

    //region int _money, 플레이어의 현재 잔고
    private int _money;
    public int getMoney() {
        return _money;
    }
    public void setMoney(int money) {
        _money = money;
    }
    //endregion
}
