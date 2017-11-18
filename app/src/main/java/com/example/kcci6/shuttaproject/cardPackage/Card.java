package com.example.kcci6.shuttaproject.cardPackage;

public class Card{

    Card(int num, boolean gwang, int imgId) {
        _num = num;
        _gwang = gwang;
        _imgId = imgId;
    }

    //region int _num
    private int _num;
    public int getNum() {
        return _num;
    }
    //endregion

    //region boolean _gwang
    private boolean _gwang;
    public boolean getGwang() {
        return _gwang;
    }
    //endregion

    // region int _imgId
    private int _imgId;
    public int getImgId()
    {
        return _imgId;
    }
    // endregion

}