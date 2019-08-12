package com.developer.wallpaper.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.developer.wallpaper.Category;
import com.developer.wallpaper.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHodel> {
       private List<String> urlimg;
       private List<String> title;
       private Category context;
       private List<Integer> positiona;

    public CategoryAdapter(List<String> urlimg, List<String> title, Category context, List<Integer> position) {
        this.urlimg = urlimg;
        this.title = title;
        this.context = context;
        this.positiona = position;
    }

    @NonNull
    @Override
    public CategoryHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHodel(LayoutInflater.from(context.getContext()).inflate(R.layout.view_category,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHodel holder, int position) {

        holder.textView.setText(title.get(position)+"("+positiona.get(position)+")");
        Picasso.get().load(urlimg.get(position)).fit().transform(boderIMG(0, 5))
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
        if (title.isEmpty())return 0;
         return title.size();
    }

    public class CategoryHodel extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public CategoryHodel(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.img_category);
            textView=itemView.findViewById(R.id.txt_category);
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
