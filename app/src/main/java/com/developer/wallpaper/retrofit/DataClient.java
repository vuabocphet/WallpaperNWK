package com.developer.wallpaper.retrofit;

import com.developer.wallpaper.ABC;
import com.developer.wallpaper.model.CategoryModel;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DataClient {

      //Code GET ALL DATA
    @GET("/wp-json/wp/v2/posts?_embed")
    Call<List<ABC>> getDataALL();

    @FormUrlEncoded
    @GET("wp-json/wp/v2/media/{id}")
        Call<List<CategoryModel>> getDataCategory(@Path("id")int id);


      //Code GET CATEGORY POSITION


//    @GET("/wp-json/wp/v2/posts")
//    Call<List<PostModel>> getCategoryPosition(@Query("category") String category);



}
