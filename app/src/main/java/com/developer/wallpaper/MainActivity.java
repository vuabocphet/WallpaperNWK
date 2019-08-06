package com.developer.wallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.developer.wallpaper.retrofit.APIClient;
import com.developer.wallpaper.retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private DataClient dataClient;
    private ArrayList<String> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterIMG adapterIMG;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataClient = APIClient.getData();
        recyclerView = findViewById(R.id.ry);
        adapterIMG = new AdapterIMG(list, this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setAdapter(adapterIMG);
        dataClient.getDataALL().enqueue(new Callback<List<ABC>>() {
            @Override
            public void onResponse(Call<List<ABC>> call, Response<List<ABC>> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "ERR", Toast.LENGTH_SHORT).show();
                    return;
                }

               for (ABC abc:response.body()){

                   for (WpFeaturedmedium_ wpFeaturedmedium_:abc.getEmbedded().getWpFeaturedmedia()){
                       Log.e("TAG",wpFeaturedmedium_.getSourceUrl());
                   }

               }

            }

            @Override
            public void onFailure(Call<List<ABC>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        dataClient.getDataALL().enqueue(new Callback<List<PostModel>>() {
//            @Override
//            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
//                if (!response.isSuccessful()){
//                    Log.e("CODE",response.code()+"");
//                    return;
//                }else {
//                    List<PostModel> postModels=response.body();
//                    Log.e("SIZE",postModels.get(1).getId()+"");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<PostModel>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

//        dataClient.getCategoryPosition("18").enqueue(new Callback<List<PostModel>>() {
//            @Override
//            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
//                if (!response.isSuccessful()) {
//                    Log.e("CODE", response.code() + "");
//                    return;
//                } else {
//                    list.clear();
//                    List<PostModel> postModels = response.body();
//                    Log.e("SIZE", postModels.size() + "");
//                    for (PostModel x : postModels) {
//
//                        String[] words = x.getContent().getRendered().split("\\s");
//                        for (String w : words) {
////                            if (w.startsWith("src=")) {
////                                Log.e("CONTENT", w.substring(5,w.length()-1));
////                                list.add(w.substring(4));
////
////                            }
////                            if (w.startsWith("srcset=")) {
////                                Log.e("CONTENT", w.substring(8,w.length()));
////                                list.add(w.substring(7));
////                            }4
//
//                            if (w.startsWith("http:") && w.endsWith("jpg")) {
//                                Log.e("CONTENT", w);
//                                list.add(w);
//                            }
//
//
//                        }
//                    }
//                    Log.e("LIST", list.size()+"");
//                    adapterIMG.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<PostModel>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


    }
}
