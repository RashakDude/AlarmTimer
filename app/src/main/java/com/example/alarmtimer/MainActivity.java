package com.example.alarmtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    Button button;
    CountDownTimer countDownTimer;
    boolean setTimer = false;

    public void resetTimer() {
        textView.setText("0:30");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        button.setText("GO!");
        setTimer = false;
    }

    public void buttonOnClick(View view) {
        if(setTimer){
            resetTimer();
        }

        else {
            seekBar.setEnabled(false);
            button.setText("STOP");
            setTimer = true;
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 500, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTime((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mediaPlayer.start();
                    resetTimer();
                }
            }.start();
        }
    }


    public void updateTime(int secondsLeft) {
        int min = secondsLeft/60;
        int sec = secondsLeft%60;
        String s1 = Integer.toString(min);
        String s2 = Integer.toString(sec);
        if(sec <= 9) s2 = "0" + s2;
        textView.setText(s1 + ":" + s2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTime(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}