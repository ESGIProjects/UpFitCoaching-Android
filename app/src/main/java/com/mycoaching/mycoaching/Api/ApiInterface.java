package com.mycoaching.mycoaching.Api;

import com.mycoaching.mycoaching.Models.Realm.Message;
import com.mycoaching.mycoaching.Models.Retrofit.Appraisal;
import com.mycoaching.mycoaching.Models.Retrofit.Event;
import com.mycoaching.mycoaching.Models.Retrofit.Measurement;
import com.mycoaching.mycoaching.Models.Retrofit.Post;
import com.mycoaching.mycoaching.Models.Retrofit.Prescription;
import com.mycoaching.mycoaching.Models.Retrofit.Test;
import com.mycoaching.mycoaching.Models.Retrofit.Thread;
import com.mycoaching.mycoaching.Models.Retrofit.UserRetrofit;
import com.mycoaching.mycoaching.Models.Retrofit.UserToken;

import java.util.List;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @POST("login/signin/")
    @FormUrlEncoded
    Call<UserToken> signIn(@Field("mail") String mail, @Field("password") String password);

    @POST("signup/")
    @FormUrlEncoded
    Call<UserRetrofit> signUp(@Field("type") String type, @Field("mail") String mail, @Field("password") String password,
                              @Field("firstName") String firstName, @Field("lastName") String lastName,
                              @Field("sex") String sex, @Field("birthDate") String birthDate,
                              @Field("city") String city, @Field("address") String address,
                              @Field("phoneNumber") String phoneNumber);

    @POST("checkmail/")
    @FormUrlEncoded
    Call<Void> checkMail(@Field("mail") String mail);

    /**
     * Endpoint for message
     */

    @GET("messages/")
    Call<List<Message>> getConversation(@Header("Authorization") String token,@Query("userId") int id);

    /**
     * Endpoints for threads and posts
     */

    @POST("thread/")
    @FormUrlEncoded
    Call<Void> createThread(@Header("Authorization") String token,@Field("title") String title, @Field("date") String date, @Field("content") String content,
                            @Field("forumId") String forumId, @Field("userId") String userId);

    @POST("post/")
    @FormUrlEncoded
    Call<Void> sendPost(@Header("Authorization") String token,@Field("threadId") String threadId, @Field("date") String date, @Field("content") String content,
                        @Field("userId") String userId);


    @GET("threads/")
    Call<List<Thread>> getThreads(@Header("Authorization") String token, @Query("forumId") int id);

    @GET("thread/")
    Call<List<Post>> getPosts(@Header("Authorization") String token, @Query("threadId") int id);

    /**
     * Endpoints for events
     */

    @GET("events/")
    Call<List<Event>> getEvents(@Header("Authorization") String token, @Query("userId") int id);

    @POST("events/")
    @FormUrlEncoded
    Call<Void> addEvent(@Header("Authorization") String token, @Field("name") String name, @Field("type") String type, @Field("firstUser") String firstUser,
                        @Field("secondUser") String secondUser, @Field("start") String start, @Field("end") String end,
                        @Field("created") String created, @Field("createdBy") String createdBy);

    @PUT("events/")
    @FormUrlEncoded
    Call<Void> updateEvent(@Header("Authorization") String token, @Field("eventId") String eventId, @Field("name") String name, @Field("type") String type,
                           @Field("start") String start, @Field("end") String end, @Field("updated") String updated, @Field("updateBy") String updateBy);


    @DELETE("events/")
    Call<Void> deleteEvent(@Header("Authorization") String token, @Query("eventId") String eventId, @Query("userId") String userId);

    /**
     * Endpoint for firebase token
     */

    @PUT("token/")
    @FormUrlEncoded
    Call<Void> putToken(@Field("userId") String userId,@Field("token") String token,
                        @Nullable @Field("oldToken") String oldToken);

    /**
     * Endpoint for profile
     */

    @PUT("users/")
    @FormUrlEncoded
    Call<UserRetrofit> updateUser(@Field("userId") String userId, @Field("mail") String mail,
                                  @Field("password") String password, @Field("firstName") String firstName,
                                  @Field("lastName") String lastName, @Field("city") String city,
                                  @Field("phoneNumber") String phoneNumber, @Field("address") String address);

    /**
     * Endpoint for followUp
     */

    @GET("appraisals/")
    Call<Appraisal> getLastAppraisal(@Query("userId") int id);

    @POST("appraisals/")
    @FormUrlEncoded
    Call<Void> postAppraisal(@Field("userId") String userId, @Field("date") String date,
                                     @Field("goal") String goal, @Field("sessionsByWeek") String sessionsByWeek,
                                     @Field("contraindication") String contraindication,
                                     @Field("sportAntecedents") String sportAntecedents,
                                     @Field("helpNeeded") String helpNeeded,
                                     @Field("hasNutritionist") String hasNutritionist, @Field("comments") String comments);

    @GET("tests/")
    Call<List<Test>> getTests(@Query("userId") int id);

    @POST("tests/")
    @FormUrlEncoded
    Call<Void> postTest(@Field("userId") String userId,@Field("date") String date,
                        @Field("warmUp") String warmUp,@Field("startSpeed") String startSpeed,
                        @Field("increase") String increase,@Field("frequency") String frequency,
                        @Field("kneeFlexibility") String kneeFlexibility,@Field("shinFlexibility") String shinFlexibility,
                        @Field("hitFootFlexibility") String hitFootFlexibility,@Field("closedFistGroundFlexibility") String closedFistGroundFlexibility,
                        @Field("handFlatGroundFlexibility") String handFlatGroundFlexibility);

    @GET("measurements/")
    Call<List<Measurement>> getMeasurements(@Query("userId") int id);

    @POST("measurements/")
    @FormUrlEncoded
    Call<Void> postMeasurements(@Field("userId") String userId,@Field("date") String date,
                                @Field("weight") String weight,@Field("height") String height,
                                @Field("hipCircumference") String hipCircumference,@Field("waistCircumference") String waistCircumference,
                                @Field("thighCircumference") String thighCircumference,@Field("armCircumference") String armCircumference);

    /**
     * Endpoints for prescription
     */

    @GET("prescriptions/")
    Call<List<Prescription>> getPrescriptions(@Query("userId") int id);

    @POST("prescriptions/")
    @FormUrlEncoded
    Call<Void> postPrescription(@Field("userId") String userId,@Field("date") String date,
                                @Field("exercises") String exercices);

}
