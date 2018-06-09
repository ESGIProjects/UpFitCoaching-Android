package com.mycoaching.mycoaching.Api;

import com.mycoaching.mycoaching.Models.Event;
import com.mycoaching.mycoaching.Models.Message;
import com.mycoaching.mycoaching.Models.Post;
import com.mycoaching.mycoaching.Models.Retrofit.UserRetrofit;
import com.mycoaching.mycoaching.Models.Thread;

import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by kevin on 07/03/2018.
 */

public interface ApiInterface {

    /**
     * Endpoints for authentication
     */

    @POST("signin/")
    @FormUrlEncoded
    Call<UserRetrofit> signIn(@Field("mail") String mail, @Field("password") String password);

    @POST("signup/")
    @FormUrlEncoded
    Call<UserRetrofit> signUp(@Field("type") String type, @Field("mail") String mail, @Field("password") String password,
                              @Field("firstName") String firstName, @Field("lastName") String lastName,
                              @Field("birthDate") String birthDate, @Field("city") String city, @Field("address") String address,
                              @Field("phoneNumber") String phoneNumber);

    @POST("checkmail/")
    @FormUrlEncoded
    Call<Void> checkMail(@Field("mail") String mail);

    /**
     * Endpoint for message
     */

    @GET("messages/")
    Call<List<Message>> getConversation(@Query("userId") int id);

    /**
     * Endpoints for threads and posts
     */

    @POST("thread/")
    @FormUrlEncoded
    Call<Void> createThread(@Field("title") String title, @Field("date") String date, @Field("content") String content,
                            @Field("forumId") String forumId, @Field("userId") String userId);

    @POST("post/")
    @FormUrlEncoded
    Call<Void> sendPost(@Field("threadId") String threadId, @Field("date") String date, @Field("content") String content,
                        @Field("userId") String userId);


    @GET("threads/")
    Call<List<Thread>> getThreads(@Query("forumId") int id);

    @GET("thread/")
    Call<List<Post>> getPosts(@Query("threadId") int id);

    /**
     * Endpoints for events
     */

    @GET("events/")
    Call<List<Event>> getEvents(@Query("userId") int id);

    @POST("events/")
    @FormUrlEncoded
    Call<Void> addEvent(@Field("name") String name, @Field("type") String type, @Field("firstUser") String firstUser,
                        @Field("secondUser") String secondUser, @Field("start") String start, @Field("end") String end,
                        @Field("created") String created, @Field("createdBy") String createdBy);

    @PUT("events/")
    @FormUrlEncoded
    Call<Void> updateEvent(@Field("eventId") String eventId, @Field("name") String name, @Field("start") String start,
                           @Field("end") String end, @Field("updated") String updated, @Field("updateBy") String updateBy);

    /**
     * Endpoint for firebase token
     */

    @PUT("token/")
    @FormUrlEncoded
    Call<Void> putToken(@Field("userId") String userId,@Field("token") String token,
                           @Nullable @Field("oldToken") String oldToken);
}
