package com.developer.wallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class ImageActivity extends AppCompatActivity {
    private FragmentManager fm;
    private FragmentTransaction ft_add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Image Detail");
        fm =getSupportFragmentManager();
        ft_add = fm.beginTransaction();
        Intent intent=getIntent();
        if (intent==null){
            return;
        }else {
            ImageDetais imageDetais=new ImageDetais();
            Bundle bundle=new Bundle();
            bundle.putString("img",intent.getStringExtra("img"));
            imageDetais.setArguments(bundle);
            ft_add.add(R.id.abc, imageDetais);
            ft_add.commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
