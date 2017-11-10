package com.example.kcci6.shuttaproject.roundInfoPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kcci6.shuttaproject.R;

import java.util.ArrayList;

public class RoundInfoView extends LinearLayout{

    private ArrayList<ImageView> imvPlayerCards = new ArrayList<>();
    private ArrayList<TextView> textViews = new ArrayList<>();

    public RoundInfoView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.round_info_item, this, true);

        textViews.add((TextView)findViewById(R.id.txvRoundNum));
        for (int i = 1; i < 3; i++) {
            textViews.add((TextView)findViewById(getResources().getIdentifier("txvPlayer"+i+"RoundResult", "id", "com.example.kcci6.shuttaproject")));
            textViews.add((TextView)findViewById(getResources().getIdentifier("txvPlayer"+i+"Score", "id", "com.example.kcci6.shuttaproject")));
            textViews.add((TextView)findViewById(getResources().getIdentifier("txvPlayer"+i+"Money", "id", "com.example.kcci6.shuttaproject")));
            }
        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 3; j++) {
                imvPlayerCards.add((ImageView)findViewById(getResources().getIdentifier("imvPlayer"+i+"Card"+j, "id", "com.example.kcci6.shuttaproject")));
            }
        }
    }

    public void setImvPlayerCards(int i, int resId){
        imvPlayerCards.get(i).setImageResource(resId);
    }

    public void setTextViews(int i, String string)
    {
        textViews.get(i).setText(string);
    }
}
