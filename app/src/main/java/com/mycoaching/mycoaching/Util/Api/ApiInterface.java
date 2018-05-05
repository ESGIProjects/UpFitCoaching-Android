package com.mycoaching.mycoaching.Util.Api;

import com.mycoaching.mycoaching.Util.Model.Retrofit.UserRetrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by tensa on 07/03/2018.
 */

public interface ApiInterface {

    @POST("signin/")
    @FormUrlEncoded
    Call<UserRetrofit> signIn(@Field("mail") String mail, @Field("password") String password);

    @POST("signup/")
    @FormUrlEncoded
    Call<UserRetrofit> signUp(@Field("type") String type,@Field("mail") String mail,@Field("password") String password,
                      @Field("firstName") String firstName,@Field("lastName") String lastName,
                      @Field("birthDate") String birthDate, @Field("city") String city, @Field("address") String address,
                      @Field("phoneNumber") String phoneNumber);

    @POST("checkmail/")
    @FormUrlEncoded
    Call<Void> checkMail(@Field("mail") String mail);

    @GET("messages/")
    Call<Void> getConversation(@Query("userId") String id);
}
