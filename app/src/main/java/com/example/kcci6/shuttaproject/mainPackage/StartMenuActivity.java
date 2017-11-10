package com.example.kcci6.shuttaproject.mainPackage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.example.kcci6.shuttaproject.R;
import com.example.kcci6.shuttaproject.roundInfoPackage.RoundsInfoListActivity;

import java.util.Arrays;
import java.util.List;

public class StartMenuActivity extends AppCompatActivity {

    Button btnStartMenu;
    Spinner spinnerPlayerMoney;
    Spinner spinnerBettingMoney;
    RadioButton radioBtnPlayOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        btnStartMenu = findViewById(R.id.btnStartMenu);
        spinnerPlayerMoney = findViewById(R.id.spinnerPlayerMoney);
        spinnerBettingMoney = findViewById(R.id.spinnerBettingMoney);
        radioBtnPlayOption = findViewById(R.id.radioBtnPlayOptionAuto); // 라디오버튼의 "자동"

        List<String> playerMoneyOptions = Arrays.asList("1000", "2000", "3000");
        ArrayAdapter<String> playerMoneyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, playerMoneyOptions);
        spinnerPlayerMoney.setAdapter(playerMoneyAdapter);

        List<String> bettingMoneyOptions = Arrays.asList("100", "200", "500");
        ArrayAdapter<String> bettingMoneyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, bettingMoneyOptions);
        spinnerBettingMoney.setAdapter(bettingMoneyAdapter);

        //버튼이 클릭되면 초기자본금과 판돈을 설정하고 화면을 이동한다.
        btnStartMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean option = radioBtnPlayOption.isChecked();
                BettingMoneyManager.getInstance().setStatBettingMoney(Integer.parseInt(spinnerBettingMoney.getSelectedItem().toString()));

                if(option) { // 옵션이 자동인 경우
                    Intent intent = new Intent(getApplicationContext(), RoundsInfoListActivity.class);
                    intent.putExtra("playerMoney", Integer.parseInt(spinnerPlayerMoney.getSelectedItem().toString()));
                    startActivityForResult(intent, 101);
                }
                else { // 옵션이 수동인 경우
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("playerMoney", Integer.parseInt(spinnerPlayerMoney.getSelectedItem().toString()));
                    startActivityForResult(intent, 102);
                }
            }
        });

    }
}
