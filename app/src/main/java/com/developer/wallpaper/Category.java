package com.developer.wallpaper;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.wallpaper.adapter.CategoryAdapter;
import com.developer.wallpaper.model.CategoryModel;
import com.developer.wallpaper.retrofit.APIClient;
import com.developer.wallpaper.retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category extends FragmentData {
    private DataClient dataClient;
    private RecyclerView rc;
    private List<String> img;
    private List<String> title;
    private List<Integer> position;
    private CategoryAdapter categoryAdapter;
    private LinearLayoutManager linearLayoutManager;
    private int i1 = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.category, container, false);
        dataClient = APIClient.getData();
        rc=view.findViewById(R.id.rc);
        linearLayoutManager=new LinearLayoutManager(getContext());
        img=new ArrayList<>();
        title=new ArrayList<>();
        position=new ArrayList<>();
        categoryAdapter=new CategoryAdapter(img,title,Category.this,position);
        rc.setLayoutManager(linearLayoutManager);
        rc.setHasFixedSize(true);
        rc.setAdapter(categoryAdapter);
        loadCategori();
        return view;

    }

    private void loadCategori() {
        img.clear();
        title.clear();
        position.clear();
        dataClient.getDataALL().enqueue(new Callback<List<ABC>>() {
            @Override
            public void onResponse(Call<List<ABC>> call, Response<List<ABC>> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                for (ABC abc : response.body()) {

                    for (WpFeaturedmedium_ wpFeaturedmedium_ : abc.getEmbedded().getWpFeaturedmedia()) {
                        Log.e("TAG", wpFeaturedmedium_.getSourceUrl());
                        img.add(wpFeaturedmedium_.getSourceUrl());
                    }

                    title.add(abc.getTitle().getRendered());

                }
                for (int i=0;i<title.size();i++){
                    position.add(getposition(i,response.body()));
                }
                view.findViewById(R.id.loading).setVisibility(View.GONE);
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ABC>> call, Throwable t) {
                loadCategori();
            }
        });
    }

    public int getposition(final int i,List<ABC> abcs){
        i1=0;
        String[] words = abcs.get(i).getContent().getRendered().split("\\s");
        for (String w : words) {
            if (w.startsWith("src=")) {
                i1++;
                Log.e("III",i1+"");
            }
        }




        return i1;
    }
}
