package com.example.chupeng.progressbrademo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity
{
    private int status = 0;
    private ProgressBar progressBar;
    private ProgressBarView progressBarView;
    private Button StartButton;
    private Button PauseButton;
    private Button StopButton;
    private Handler handler;
    private boolean Start = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBarView = (ProgressBarView) findViewById(R.id.CustomProgressBar);
        StartButton = (Button) findViewById(R.id.StartButton);
        PauseButton = (Button) findViewById(R.id.PauseButton);
        StopButton = (Button) findViewById(R.id.StopButton);

        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Start)
                {
                    handler.post(runnable);
                    Start = true;
                }
            }
        });

        PauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Start)
                {
                    handler.removeCallbacks(runnable);
                    Start = false;
                }

            }
        });

        StopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(runnable);
                progressBar.setProgress(0);
                progressBarView.setCurrentProgressBar(0);
                Start = false;
            }
        });
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            status = progressBar.getProgress() + 1;
            progressBar.setProgress(status);
            progressBarView.setCurrentProgressBar(status);
            if(status <= 100)
            {
                handler.postDelayed(runnable, 100);
            }
            else
            {
                progressBar.setProgress(0);
                progressBarView.setCurrentProgressBar(0);
                Start = false;
            }
        }
    };
}
