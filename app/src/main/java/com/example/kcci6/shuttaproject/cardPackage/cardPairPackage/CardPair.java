package com.example.kcci6.shuttaproject.cardPackage.cardPairPackage;

import com.example.kcci6.shuttaproject.cardPackage.Card;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public abstract class CardPair implements Comparable<CardPair>{

    private Stack<Card> _cards;
    // region Jokbo _score
    private Jokbo _score;
    public Jokbo getScore() {
        return _score;
    }
    void setScore(Jokbo score) {
        _score = score;
    }
    // endregion

    CardPair(Card card1, Card card2) {
        _cards = new Stack<>();
        _cards.add(card1);
        _cards.add(card2);
        ScoreCalculator.getInstance().setCardScore(this);
    }

    Card getFirstCard() { return _cards.get(0); }
    Card getSecondCard() { return _cards.get(1); }

    public List<Integer> getCardImageIds()
    {
        return Arrays.asList(getFirstCard().getImgId(), getSecondCard().getImgId());
    }

    public Card returnCard()
    {
        return _cards.pop();
    }

    @Override
    public int compareTo(CardPair o) {
        return getScore().compareTo(o.getScore());
    }
}
