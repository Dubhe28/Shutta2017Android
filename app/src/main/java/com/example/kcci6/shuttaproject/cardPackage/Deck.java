package com.example.kcci6.shuttaproject.cardPackage;

import android.content.Context;
import java.util.Collections;
import java.util.Stack;

public class Deck {

    // region class Deck defined as a singleton
    private static Deck instance = new Deck();
    private Deck() {
        _deck = new Stack<>();
    }
    public static Deck getInstance() {
        return instance;
    }
    //endregion

    private static Context _context;

    private Stack<Card> _deck;

    public void makeCards(Context context) {
        _context = context;
        for (int index=0; index<20 ;index++)
            _deck.add(convertIntToCard(index));
    }

    public void shuffleCards()
    {
        Collections.shuffle(_deck);
    }

    public Card getCardFromDeck()
    {
        return _deck.pop();
    }

    public void recoverCard(Card card) {
        _deck.push(card);
    }

    private Card convertIntToCard(int index) {
        return new Card(getCardNum(index), getCardGwang(index), getCardImageResId(_context, index));
    }

    private int getCardNum(int index) {
        return index%10+1;    // 0~9까지는 1을 더해 1~10으로 반환되고, 10~19는 1~10으로 반환된다.
    }

    private boolean getCardGwang(int index) {
        index++;
        return (index == 1)||(index == 3)||(index == 8); // 카드번호가 1, 3, 8인 카드 하나씩만 광을 가지고 있다.
    }

    private int getCardImageResId(Context context, int index) {
        return context.getResources().getIdentifier(getCardImageIdStr(index), "drawable", "com.example.kcci6.shuttaproject");
    }

    private String getCardImageIdStr(int index) {
        String cardImageIdStr;
        if(getCardGwang(index))
            cardImageIdStr = "@drawable/c" +(getCardNum(index))+"k";
        else
            cardImageIdStr = "@drawable/c" +(getCardNum(index));

        return cardImageIdStr;
    }
}