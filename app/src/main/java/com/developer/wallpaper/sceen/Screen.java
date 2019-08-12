package com.developer.wallpaper.sceen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.developer.wallpaper.Home;
import com.developer.wallpaper.R;

import java.util.Timer;
import java.util.TimerTask;

public class Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_screen);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(Screen.this, Home.class));
                finish();
            }
        },2000);
    }
}
