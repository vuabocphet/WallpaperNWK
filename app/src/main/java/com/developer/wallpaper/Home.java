package com.developer.wallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.developer.wallpaper.retrofit.APIClient;
import com.developer.wallpaper.retrofit.DataClient;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public View header;
    private Toolbar toolbar;
    public ActionBarDrawerToggle toggle;
    private DataClient dataClient;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        dataClient = APIClient.getData();
        fm = getSupportFragmentManager();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dataClient = APIClient.getData();

        drawerLayout = findViewById(R.id.layoutDrawer);
        navigationView = findViewById(R.id.nav_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setTitle(getString(R.string.app_name));
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawers();
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });


        //loaddata();
        addfragment(R.id.latest);


    }

    private void loaddata() {
        dataClient.getDataALL().enqueue(new Callback<List<ABC>>() {
            @Override
            public void onResponse(Call<List<ABC>> call, Response<List<ABC>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Home.this, "ERR_CODE:" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                for (ABC abc : response.body()) {
                    String[] words = abc.getContent().getRendered().split("\\s");

                    for (String w : words) {
                        //Log.e("W", w);
//                        if (w.startsWith("http:") && w.endsWith("jpg")) {
//                            Log.e("CONTENT", w);
//                        }
                        if (w.startsWith("srcset=")) {
                            Log.e("CONTENT", w);
                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<List<ABC>> call, Throwable t) {
                Toast.makeText(Home.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                loaddata();
            }
        });

    }

    private void addfragment(int id) {

        FragmentTransaction ft_add = fm.beginTransaction();
        switch (id) {
            case R.id.latest:
                getSupportActionBar().setTitle("Latest");
                ft_add.replace(R.id.framlayout, new Latets());
                ft_add.commit();
                break;
            case R.id.category:
                getSupportActionBar().setTitle("Category");
                ft_add.replace(R.id.framlayout, new Category());
                ft_add.commit();
                break;
            case R.id.myfavprites:
                getSupportActionBar().setTitle("My Favorites");
                ft_add.replace(R.id.framlayout, new MyLove());
                ft_add.commit();
                break;
            case R.id.aboutus:
                getSupportActionBar().setTitle("About Us");
                ft_add.replace(R.id.framlayout, new AboutUS());
                ft_add.commit();
                break;

                default:
                getSupportActionBar().setTitle("Latest");
                ft_add.replace(R.id.framlayout, new Latets());
                ft_add.commit();

                break;
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.latest:
                addfragment(R.id.latest);
                break;

            case R.id.category:
                addfragment(R.id.category);
                break;
            case R.id.myfavprites:
                addfragment(R.id.myfavprites);
                break;

            case R.id.aboutus:
                addfragment(R.id.aboutus);

                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void start(int i) {

    }

}
