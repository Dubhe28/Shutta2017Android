package com.example.kcci6.shuttaproject.gamePackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.kcci6.shuttaproject.R;

import java.util.Arrays;

public class StartMenuActivity extends AppCompatActivity {

    private Spinner spinnerPlayerMoney;
    private Spinner spinnerBettingMoney;

    private static final int REQUEST_CODE_OPTION1 = 101;
    private static final int REQUEST_CODE_OPTION2 = 102;
    private static final boolean PLAY_OPTION1 = true;
    static final boolean PLAY_OPTION2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        setSpinnerOptions();
        setStartButtonOnClickListener();
    }

    private void setStartButtonOnClickListener() {
        findViewById(R.id.btnStartMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BettingMoneyManager.getInstance().setStaticBettingMoney(Integer.parseInt(spinnerBettingMoney.getSelectedItem().toString()));

                if(((RadioButton)findViewById(R.id.radioBtnPlayOptionAuto)).isChecked()) // 옵션이 자동인 경우
                    startRoundsResultActivity();
                else // 옵션이 수동인 경우
                    startMainActivity();
            }
        });
    }

    private void startRoundsResultActivity() {
        Intent intent = new Intent(getApplicationContext(), RoundsResultActivity.class);
        intent.putExtra("playOption", PLAY_OPTION1);
        intent.putExtra("playerMoney", Integer.parseInt(spinnerPlayerMoney.getSelectedItem().toString()));
        startActivityForResult(intent, REQUEST_CODE_OPTION1);
    }

    private void startMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("playerMoney", Integer.parseInt(spinnerPlayerMoney.getSelectedItem().toString()));
        startActivityForResult(intent, REQUEST_CODE_OPTION2);
    }

    private void setSpinnerOptions() {

        spinnerPlayerMoney = findViewById(R.id.spinnerPlayerMoney);
        spinnerBettingMoney = findViewById(R.id.spinnerBettingMoney);

        spinnerPlayerMoney.setAdapter( new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList("1000", "2000", "3000")));
        spinnerBettingMoney.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Arrays.asList("100", "200", "500")));
    }
}
