package com.example.kcci6.shuttaproject.mainPackage;

import com.example.kcci6.shuttaproject.cardPackage.Deck;
import com.example.kcci6.shuttaproject.cardPackage.cardPairPackage.CardPair;
import com.example.kcci6.shuttaproject.cardPackage.cardPairPackage.OriginalCardPair;
import com.example.kcci6.shuttaproject.cardPackage.cardPairPackage.TieCardPair;
import com.example.kcci6.shuttaproject.playerPackage.Player;

import java.util.List;

class Dealer {

    // region class mainPackage.Dealer declared as a Singleton
    private static Dealer instance = new Dealer();
    private Dealer() {}
    static Dealer getInstance() {
        return instance;
    }
    //endregion

    private boolean _isTied;

    void giveCardPairs(boolean isTied, List<Player> players) {
        _isTied = isTied;
        Deck.getInstance().shuffleCards();
        for (int i = 0; i < 2; i++)
            setPlayerCards(players.get(i));
    }

    private void setPlayerCards(Player player)
    {
        player.setCardPair(getRandCardPair());
    }

    private CardPair getRandCardPair(){
        if(_isTied)
            return new TieCardPair(Deck.getInstance().getCardFromDeck(), Deck.getInstance().getCardFromDeck());
        else
            return new OriginalCardPair(Deck.getInstance().getCardFromDeck(), Deck.getInstance().getCardFromDeck());
    }

    private void returnCardPair(Player player) {
        for (int i = 0; i < 2; i++)
            Deck.getInstance().recoverCard(player.getCardPair().returnCard());
    }

    void returnCardPairsToDeck(List<Player> players) {
        for (int i = 0; i < 2; i++)
            returnCardPair(players.get(i));
    }

}
