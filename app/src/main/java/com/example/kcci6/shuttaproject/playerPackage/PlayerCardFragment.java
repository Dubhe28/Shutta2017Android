package com.example.kcci6.shuttaproject.playerPackage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.example.kcci6.shuttaproject.R;
import com.example.kcci6.shuttaproject.mainPackage.cardFlipPackage.CardFlipAnimation;
import com.example.kcci6.shuttaproject.mainPackage.cardFlipPackage.DisplayNextView;

public class PlayerCardFragment extends Fragment {

    private ImageView imvCardBack;
    private ImageView imvCardFront;

    public PlayerCardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewGroup = inflater.inflate(R.layout.fragment_player_card, container, true);


        imvCardBack = viewGroup.findViewById(R.id.imvCardBack);
        imvCardFront = viewGroup.findViewById(R.id.imvCardFront);
        imvCardFront.setVisibility(View.GONE);

        /*imvCardBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstImage) {
                    applyRotation(0, 90);
                    isFirstImage = !isFirstImage;

                } else {
                    applyRotation(0, -90);
                    isFirstImage = !isFirstImage;
                }
            }
        });
*/
        return viewGroup;
    }

    private boolean isFirstImage = true;

    private void applyRotation(float start, float end) {
// Find the center of image
        final float centerX = imvCardBack.getWidth() / 2.0f;
        final float centerY = imvCardBack.getHeight() / 2.0f;

// Create a new 3D rotation with the supplied parameter
// The animation listener is used to trigger the next animation
        final CardFlipAnimation rotation =
                new CardFlipAnimation(start, end, centerX, centerY);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(isFirstImage, imvCardBack, imvCardFront));

        if (isFirstImage)
        {
            imvCardBack.startAnimation(rotation);
        } else {
            imvCardFront.startAnimation(rotation);
        }

    }

    public void setImv(int resId){
        imvCardBack.setImageResource(resId);
    }

}
