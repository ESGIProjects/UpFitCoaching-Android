package com.mycoaching.mycoaching.Util.Api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by tensa on 07/03/2018.
 */

public interface ApiInterface {
    @POST("signin/")
    @FormUrlEncoded
    Call<Void> signIn(@Field("isCoach") String isCoach, @Field("mail") String mail, @Field("password") String password);

    @POST("signup/")
    @FormUrlEncoded
    Call<Void> singUp(@Field("tyoe") String type,@Field("mail") String mail,@Field("password") String password,
                      @Field("firstName") String firstName,@Field("lastName") String lastName,
                      @Field("birthDate") String birthDate, @Field("city") String city,
                      @Field("phoneNumber") String phoneNumber);
}
