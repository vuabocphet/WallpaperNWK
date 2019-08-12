package com.developer.wallpaper.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.developer.wallpaper.Home;
import com.developer.wallpaper.ImageDetais;
import com.developer.wallpaper.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.Pulse;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class GirdAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;
    private int wid;
    private Home home;
    boolean isLoading =true;
    public static final int LOADING = 2;
    public static final int VIEW = 1;

    public GirdAdapter(List<String> list, Context context, int wid) {
        this.list = list;
        this.context = context;
        this.wid = wid;
    }

    @Override
    public int getCount() {

        if (list.isEmpty()){
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View view1;

       if (view==null){
           view= LayoutInflater.from(context).inflate(R.layout.item_girdview,viewGroup,false);
           view1=view;
       }else {
           view1=view;
       }

        ImageView imageView=view1.findViewById(R.id.img);
        RelativeLayout relativeLayout=view1.findViewById(R.id.layout);
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        layoutParams.width = wid/2-12;
        layoutParams.height = wid/2-12;
        view.setLayoutParams(layoutParams);

        final ProgressBar progressBar = view1.findViewById(R.id.spin_kit);



       Picasso.get().load(list.get(i)).fit().transform(boderIMG(0, 3))
                .centerCrop().placeholder(R.drawable.img).error(R.drawable.ic_launcher_background).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                //code loading
               progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });


        return view;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoading){
            if (position == list.size() - 1)

                return LOADING;

            else return VIEW;
        }else
            return VIEW;
    }

    private Transformation boderIMG(int boderW, int boderConer) {
        return new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(boderW)
                .cornerRadiusDp(boderConer)
                .oval(false)
                .build();

    }


}
