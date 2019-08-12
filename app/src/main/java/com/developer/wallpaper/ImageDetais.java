package com.developer.wallpaper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageDetais extends FragmentData {
    private FloatingActionMenu menuGreen;
    private Handler mUiHandler = new Handler();
    private boolean is = true;
    private ImageView imageView;
    private Bitmap result;
    private String url;
    private SharedPreferences love;
    private SharedPreferences.Editor editor;
    private List<String> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.imagedetail, container, false);
        menuGreen = view.findViewById(R.id.menu_green);
        imageView = view.findViewById(R.id.imgdetais);
        love = getActivity().getSharedPreferences("LOVE", Context.MODE_PRIVATE);
        list = new ArrayList<>();
        list.clear();
        Bundle bundle = getArguments();
        if (bundle != null) {
            url = bundle.getString("img");
            Log.e("URL", url);
            if (!url.isEmpty()) {
                Picasso.get().load(url).fit().transform(boderIMG(0, 3))
                        .centerCrop().error(R.drawable.ic_launcher_background).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        //code loading
                        view.findViewById(R.id.progressBar).setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        }
        return view;
    }

    private Transformation boderIMG(int boderW, int boderConer) {
        return new RoundedTransformationBuilder()
                .borderColor(Color.BLACK)
                .borderWidthDp(boderW)
                .cornerRadiusDp(boderConer)
                .oval(false)
                .build();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        menuGreen.hideMenuButton(false);

        String httpParamJSONList = love.getString("list", "");


        final FloatingActionButton programFab4 = new FloatingActionButton(getActivity());
        programFab4.setButtonSize(FloatingActionButton.SIZE_MINI);
        programFab4.setColorNormal(getResources().getColor(R.color.grey));
        programFab4.setColorPressed(getResources().getColor(R.color.colorPrimary));

        if (!httpParamJSONList.isEmpty()) {
            List<String> httpParamList =
                    new Gson().fromJson(httpParamJSONList, new TypeToken<List<String>>() {
                    }.getType());
            if (!httpParamList.isEmpty()) {

                for (String i : httpParamList) {
                    if (i.equals(url)) {
                        is = false;
                        break;
                    }
                }
                if (!is){
                    programFab4.setImageResource(R.drawable.my_fa_red);
                    list.add(url);
                }else {
                    programFab4.setImageResource(R.drawable.my_fa_white);
                    list.clear();
                }

            } else {
                programFab4.setImageResource(R.drawable.my_fa_white);
                is = true;
            }
        } else {
            programFab4.setImageResource(R.drawable.my_fa_white);
            is = true;
        }
        menuGreen.addMenuButton(programFab4);
        programFab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is) {
                    list.clear();
                    list.add(url);
                    Log.e("SIZE", list.size() + "");
                    programFab4.setImageResource(R.drawable.my_fa_red);
                    is = false;
                } else {
                    String httpParamJSONList = love.getString("list", "");
                    List<String> httpParamList =
                            new Gson().fromJson(httpParamJSONList, new TypeToken<List<String>>() {
                            }.getType());
                    boolean ia = false;
                    for (int i = 0; i < httpParamList.size(); i++) {
                        if (httpParamList.get(i).equals(url)) {
                            httpParamList.remove(i);
                            ia = true;
                            break;
                        }

                    }
                    if (ia) {
                        editor = love.edit();
                        String listabc = new Gson().toJson(httpParamList);
                        editor.putString("list", listabc);
                        editor.apply();
                    }

                    programFab4.setImageResource(R.drawable.my_fa_white);
                    list.remove(0);
                    is = true;
                }
            }
        });

        final FloatingActionButton programFab3 = new FloatingActionButton(getActivity());
        programFab3.setButtonSize(FloatingActionButton.SIZE_MINI);
        programFab3.setColorNormal(getResources().getColor(R.color.grey));
        programFab3.setColorPressed(getResources().getColor(R.color.colorPrimary));
        programFab3.setLabelText("Share");
        programFab3.setImageResource(R.drawable.share);
        menuGreen.addMenuButton(programFab3);
        programFab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                programFab3.setLabelColors(ContextCompat.getColor(getActivity(), R.color.grey),
                        ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                        ContextCompat.getColor(getActivity(), R.color.white_transparent));
                programFab3.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            }
        });


        final FloatingActionButton programFab2 = new FloatingActionButton(getActivity());
        programFab2.setButtonSize(FloatingActionButton.SIZE_MINI);
        programFab2.setColorNormal(getResources().getColor(R.color.grey));
        programFab2.setColorPressed(getResources().getColor(R.color.colorPrimary));
        programFab2.setLabelText("Save Image");
        programFab2.setImageResource(R.drawable.save_a);
        menuGreen.addMenuButton(programFab2);
        programFab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                programFab2.setLabelColors(ContextCompat.getColor(getActivity(), R.color.grey),
                        ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                        ContextCompat.getColor(getActivity(), R.color.white_transparent));
                programFab2.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.white));
            }
        });


        final FloatingActionButton programFab1 = new FloatingActionButton(getActivity());
        programFab1.setButtonSize(FloatingActionButton.SIZE_MINI);
        programFab1.setColorNormal(getResources().getColor(R.color.grey));
        programFab1.setColorPressed(getResources().getColor(R.color.grey));
        programFab1.setColorPressed(getResources().getColor(R.color.colorPrimary));
        programFab1.setLabelText("Set as wallpaper");
        programFab1.setImageResource(R.drawable.save);
        menuGreen.addMenuButton(programFab1);
        programFab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                programFab1.setLabelColors(ContextCompat.getColor(getActivity(), R.color.grey),
                        ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                        ContextCompat.getColor(getActivity(), R.color.white_transparent));
                programFab1.setLabelTextColor(ContextCompat.getColor(getActivity(), R.color.white));


                Picasso.get().load(url).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getContext());
                        try {
                            wallpaperManager.setBitmap(bitmap);
                            Toast.makeText(getContext(), "Đã đặt làm hình nền", Toast.LENGTH_SHORT).show();
                            menuGreen.toggle(false);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });


            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int delay = 400;

        mUiHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                menuGreen.showMenuButton(true);
            }
        }, delay);


        menuGreen.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuGreen.isOpened()) {

                }

                menuGreen.toggle(true);
            }
        });
        createCustomAnimation();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        editor = love.edit();
        String httpParamJSONLista = love.getString("list", "");
        if (!httpParamJSONLista.isEmpty()) {
            List<String> httpParamList =
                    new Gson().fromJson(httpParamJSONLista, new TypeToken<List<String>>() {
                    }.getType());

            if (!httpParamList.isEmpty() && !list.isEmpty()) {
                boolean a=false;
                for (String i:httpParamList){
                    if (i.equals(list.get(0))){
                        a=true;
                        break;
                    }
                }
                if (!a){
                    Log.e("TAG", "THANH CONG 1");
                    httpParamList.add(list.get(0));
                    String httpParamJSONList = new Gson().toJson(httpParamList);
                    editor.putString("list", httpParamJSONList);
                    editor.apply();
                }
            }
            if (!list.isEmpty() && list.size() == 1 && httpParamList.isEmpty()) {
                Log.e("TAG", "THANH CONG 2");
                String httpParamJSONList = new Gson().toJson(list);
                editor.putString("list", httpParamJSONList);
                editor.apply();
            }

        } else {
            if (!list.isEmpty() && list.size() == 1) {
                Log.e("TAG", "THANH CONG 3");
                String httpParamJSONList = new Gson().toJson(list);
                editor.putString("list", httpParamJSONList);
                editor.apply();
            }
        }
        ////
//        Intent intent=new Intent();
//        String httpIntent=new Gson().toJson(intent);
//
//        List<String> httpParamList =
//                new Gson().fromJson(httpParamJSONLista, new TypeToken<List<String>>() {}.getType());
//
//        Intent intent1=new Gson().fromJson(httpParamJSONLista,new TypeToken<Intent>(){}.getType());

    }

    private void createCustomAnimation() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(menuGreen.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(menuGreen.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(menuGreen.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(menuGreen.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                menuGreen.getMenuIconView().setImageResource(menuGreen.isOpened()
                        ? R.drawable.close : R.drawable.add);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        menuGreen.setIconToggleAnimatorSet(set);
    }
}
