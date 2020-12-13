package com.mrsoftit.apsaa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class Welcome extends AppCompatActivity {

    public int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new CountDownTimer(2000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                counter++;
            }
            @Override
            public void onFinish() {

                startActivity(new Intent(Welcome.this,signIn_activity.class));
                finish();
            }
        }.start();

    }
}