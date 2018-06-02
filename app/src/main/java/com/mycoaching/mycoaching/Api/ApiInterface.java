package com.mycoaching.mycoaching.Api;

import com.mycoaching.mycoaching.Models.Message;
import com.mycoaching.mycoaching.Models.Post;
import com.mycoaching.mycoaching.Models.Retrofit.UserRetrofit;
import com.mycoaching.mycoaching.Models.Thread;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by kevin on 07/03/2018.
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
    Call<List<Message>> getConversation(@Query("userId") String id);

    @GET("threads/")
    Call<List<Thread>> getThreads(@Query("forumId") String id);

    @GET("thread/")
    Call<List<Post>> getPosts(@Query("threadId") String id);
}
