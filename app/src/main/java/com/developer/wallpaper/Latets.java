package com.developer.wallpaper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.developer.wallpaper.adapter.GirdAdapter;
import com.developer.wallpaper.retrofit.APIClient;
import com.developer.wallpaper.retrofit.DataClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Latets extends FragmentData {
      private GirdAdapter girdAdapter;
      private GridView gridView;
      private List<String> strings;
      private DataClient dataClient;
      private FrameLayout frameLayout;
      private  FragmentManager fm;
      private  FragmentTransaction ft_add;
      private Handler handler;
      private boolean isLoading=false;
      private View viewa;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.latets,container,false);
        gridView=view.findViewById(R.id.girdview);
        frameLayout=view.findViewById(R.id.framlayout_img);
        viewa=LayoutInflater.from(getContext()).inflate(R.layout.load_more,null);
        frameLayout.setVisibility(View.GONE);
        dataClient= APIClient.getData();
        strings=new ArrayList<>();
         fm = getActivity().getSupportFragmentManager();
         ft_add = fm.beginTransaction();
        girdAdapter=new GirdAdapter(strings,getContext(),getwidth());

        gridView.setAdapter(girdAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("I",strings.get(i)+"");
                click(strings.get(i));
            }
        });

        loaddata();
        return view;
    }


    private int getwidth(){
        DisplayMetrics metrics = new DisplayMetrics();   //for all android versions
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    private void loaddata(){

        dataClient.getDataALL().enqueue(new Callback<List<ABC>>() {
            @Override
            public void onResponse(Call<List<ABC>> call, Response<List<ABC>> response) {
                if (!response.isSuccessful()){
//                    Toast.makeText(getContext(), "ERR_CODE:"+response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                for (ABC abc:response.body()){
                    String[] words = abc.getContent().getRendered().split("\\s");

                    for (String w : words) {
                        //Log.e("W", w);
//                        if (w.startsWith("http:") && w.endsWith("jpg")) {
//                            Log.e("CONTENT", w);
//                        }
                        if (w.startsWith("srcset=")) {
                            strings.add(w.substring(8,w.length()));
                            Log.e("CONTENT", w.substring(8,w.length()));

                        }
                    }
                }

//                String[] words = response.body().get(0).getContent().getRendered().split("\\s");
//
//                for (String w : words) {
//                    //Log.e("W", w);
////                        if (w.startsWith("http:") && w.endsWith("jpg")) {
////                            Log.e("CONTENT", w);
////                        }
//                    if (w.startsWith("srcset=")) {
//                        strings.add(w.substring(8,w.length()));
//                        Log.e("CONTENT", w.substring(8,w.length()));
//
//                    }
//                }
                view.findViewById(R.id.loading).setVisibility(View.GONE);
                girdAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ABC>> call, Throwable t) {
               Log.e("TAG", t.getMessage());
               loaddata();
            }
        });

    }

    public void click(String urlimg){
        Intent intent=new Intent(getActivity(),ImageActivity.class);
        intent.putExtra("img",urlimg);
        startActivity(intent);
    }



    @Override
    public void onStart() {
        super.onStart();
        Log.e("onStart","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume","onResume");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("onDestroy","onDestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("onPause","onPause");
    }


}
