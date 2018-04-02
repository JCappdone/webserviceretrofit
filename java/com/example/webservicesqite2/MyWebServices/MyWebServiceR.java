package com.example.webservicesqite2.MyWebServices;

import com.example.webservicesqite2.Models.UserModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by shriji on 30/3/18.
 */

public interface MyWebServiceR {

    String BASE_URL = "http://192.168.43.190/users/";
    String FEED = "dbcrudoprations.php";

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
   //         .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    @POST(FEED)
    @FormUrlEncoded
    Call<String> saveUser(@Field("type") int flag,
                             @Field("name") String name,
                             @Field("phone") String phone,
                             @Field("image") String image);

    @GET(FEED)
    Call<List<UserModel>> getUsers(@Query("type") int flag);

    @GET(FEED)
    Call<String> deleteUsers(@Query("type") int flag,
                                      @Query("id") int id);

    @POST(FEED)
    @FormUrlEncoded
    Call<String> updateUser(@Field("type") int flag,
                          @Field("id") int id,
                          @Field("name") String name,
                          @Field("phone") String phone,
                          @Field("image") String image);

    @GET(FEED)
    Call<String> undoAllUsers(@Query("type") int flag);
}
