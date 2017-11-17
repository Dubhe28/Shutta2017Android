package com.example.kcci6.shuttaproject.playerPackage;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.example.kcci6.shuttaproject.R;
import com.example.kcci6.shuttaproject.cardFlipPackage.ImageFlipAnimation;
import com.example.kcci6.shuttaproject.cardFlipPackage.DisplayNextView;

public class PlayerCardFragment extends Fragment {

    private ImageView imvCardBack;
    private ImageView imvCardFront;
    private boolean isFirstImage = true;

    public PlayerCardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewGroup = inflater.inflate(R.layout.fragment_player_card, container, true);
        imvCardBack = viewGroup.findViewById(R.id.imvCardBack);
        imvCardFront = viewGroup.findViewById(R.id.imvCardFront);
        imvCardFront.setVisibility(View.GONE);
        return viewGroup;
    }

    public void rotate() {
        if (isFirstImage)
            applyRotation(0, 90);
        else
            applyRotation(0, -90);
        isFirstImage = !isFirstImage;
    }

    public ImageView getImvCardBack(){
        return imvCardBack;
    }

    private void applyRotation(float start, float end) {
        // Find the center of image
        final float centerX = imvCardBack.getWidth() / 2.0f;
        final float centerY = imvCardBack.getHeight() / 2.0f;

        // Create a new 3D rotation with the supplied parameter
        // The animation listener is used to trigger the next animation
        final ImageFlipAnimation rotation = new ImageFlipAnimation(start, end, centerX, centerY);
        rotation.setDuration(200);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new DisplayNextView(isFirstImage, imvCardBack, imvCardFront));

        if (isFirstImage)
            imvCardBack.startAnimation(rotation);
        else
            imvCardFront.startAnimation(rotation);
    }

    public void setImv(int resId){
        imvCardFront.setImageResource(resId);
    }

}
