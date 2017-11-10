package com.example.kcci6.shuttaproject.cardPackage.cardPairPackage;

class ScoreCalculator {

    //region singleton
    private static ScoreCalculator _instance = new ScoreCalculator();

    static ScoreCalculator getInstance()
    {
        return _instance;
    }

    private ScoreCalculator() {}
    //endregion

    private CardPair _cardPair;

    void setCardScore(CardPair cardPair){
        _cardPair = cardPair;
        if(getIsGwang()){ // 광땡일때
            cardPair.setScore(Jokbo.values()[20]);
        }else if(getIsJang()){ // 장땡일때
            cardPair.setScore(Jokbo.values()[19]);
        }else if(getIsDdeng()){ // 땡 일때
            cardPair.setScore(Jokbo.values()[(calculateDdeng(cardPair.getFirstCard().getNum()))]);
        }else{ //끗일때
            cardPair.setScore(Jokbo.values()[((cardPair.getFirstCard().getNum()+cardPair.getSecondCard().getNum())%10)]);
        }
    }

    private boolean getIsGwang(){ // 광땡일 때 true return
        return _cardPair.getFirstCard().getGwang() && _cardPair.getSecondCard().getGwang();
    }
    private boolean getIsJang(){ // 장땡일때 true return
        return _cardPair.getFirstCard().getNum() == 10 && _cardPair.getSecondCard().getNum() == 10;
    }
    private boolean getIsDdeng(){ // 땡일때 true return
        return _cardPair.getFirstCard().getNum() == _cardPair.getSecondCard().getNum();
    }
    private int calculateDdeng(int cardNumber){ //땡일 때 점수 계산
        return cardNumber+9;
    }

}
