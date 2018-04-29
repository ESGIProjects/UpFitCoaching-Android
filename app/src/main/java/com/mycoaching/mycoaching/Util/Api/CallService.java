package com.mycoaching.mycoaching.Util.Api;

import com.mycoaching.mycoaching.Util.Model.Retrofit.UserRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tensa on 08/03/2018.
 */


public class CallService{

    public static void signIn(String mail, String password, final ServiceResultListener srl){
        ApiUtils.getRetrofitInstance().signIn(mail,password).enqueue(new Callback<UserRetrofit>() {
            @Override
            public void onResponse(Call<UserRetrofit> call, Response<UserRetrofit> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                sir.setUr(response.body());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<UserRetrofit> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    public static void signUp(String type, String mail, String password, String firstName, String lastName,
                              String birthDate, String city, String address, String phoneNumber, final ServiceResultListener srl){
        ApiUtils.getRetrofitInstance().signUp(type,mail,password,firstName,lastName,birthDate,city, address, phoneNumber)
                .enqueue(new Callback<UserRetrofit>() {
                    @Override
                    public void onResponse(Call<UserRetrofit> call, Response<UserRetrofit> response) {
                        ApiResults sir = new ApiResults();
                        sir.setResponseCode(response.code());
                        sir.setUr(response.body());
                        srl.onResult(sir);
                    }

                    @Override
                    public void onFailure(Call<UserRetrofit> call, Throwable t) {
                        ApiResults sir = new ApiResults();
                        sir.setException(t);
                        srl.onResult(sir);
                    }
                });
    }

    public static void checkMail(String mail, final ServiceResultListener srl){
        ApiUtils.getRetrofitInstance().checkMail(mail).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }
}
