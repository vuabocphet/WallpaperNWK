package com.developer.wallpaper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.developer.wallpaper.adapter.GirdAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MyLove extends FragmentData {
    private GirdAdapter girdAdapter;
    private GridView gridView;
    private List<String> list;
    private SharedPreferences love;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mylove, container, false);
        gridView = view.findViewById(R.id.girdview);
        love = getActivity().getSharedPreferences("LOVE", Context.MODE_PRIVATE);
        list = new ArrayList<>();

        getData();
        return view;
    }

    private void getData() {
        String httpParamJSONLista = love.getString("list", "");
        if (!httpParamJSONLista.isEmpty()) {
            list.clear();
            list = new Gson().fromJson(httpParamJSONLista, new TypeToken<List<String>>() {
                    }.getType());

            if (!list.isEmpty()){
                girdAdapter = new GirdAdapter(list, getContext(), getwidth());
                gridView.setAdapter(girdAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        click(list.get(i));
                    }
                });
                view.findViewById(R.id.loading).setVisibility(View.GONE);
            }
        }
    }

    private int getwidth() {
        DisplayMetrics metrics = new DisplayMetrics();   //for all android versions
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public void click(String urlimg){
        Intent intent=new Intent(getActivity(),ImageActivity.class);
        intent.putExtra("img",urlimg);
        startActivity(intent);
    }

}
