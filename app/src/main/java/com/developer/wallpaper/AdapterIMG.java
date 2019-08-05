package com.developer.wallpaper;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;

public class AdapterIMG extends RecyclerView.Adapter<AdapterIMG.ViewHolderIMG> {
private ArrayList<String> list;
private Context context;

    public AdapterIMG(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderIMG onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderIMG(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderIMG holder, int position) {
        Picasso.get().load(list.get(position)).fit().transform(boderIMG(0, 8))
                .centerCrop().error(R.drawable.ic_launcher_background).into(holder.imageView, new Callback() {
            @Override
            public void onSuccess() {
                //code loading
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderIMG extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        public ViewHolderIMG(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img);

        }
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
