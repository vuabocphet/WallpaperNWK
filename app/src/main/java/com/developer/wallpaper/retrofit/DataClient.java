package com.developer.wallpaper.retrofit;

import com.developer.wallpaper.PostModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataClient {

      //Code GET ALL DATA
    @GET("/wp-json/wp/v2/posts?embed")
    Call<List<PostModel>> getDataALL();


      //Code GET CATEGORY POSITION


    @GET("/wp-json/wp/v2/posts")
    Call<List<PostModel>> getCategoryPosition(@Query("category") String category);



}
