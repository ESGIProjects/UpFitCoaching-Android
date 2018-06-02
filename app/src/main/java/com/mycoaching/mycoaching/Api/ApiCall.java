package com.mycoaching.mycoaching.Api;

import com.mycoaching.mycoaching.Models.Message;
import com.mycoaching.mycoaching.Models.Post;
import com.mycoaching.mycoaching.Models.Retrofit.UserRetrofit;
import com.mycoaching.mycoaching.Models.Thread;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kevin on 08/03/2018.
 */


public class ApiCall {

    public static void signIn(String mail, String password, final ServiceResultListener srl){
        ApiUtils.getApiInstance().signIn(mail,password).enqueue(new Callback<UserRetrofit>() {
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
        ApiUtils.getApiInstance().signUp(type,mail,password,firstName,lastName,birthDate,city, address, phoneNumber)
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
        ApiUtils.getApiInstance().checkMail(mail).enqueue(new Callback<Void>() {
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

    public static void getConversation(String id, final ServiceResultListener srl){
        ApiUtils.getApiInstance().getConversation(id).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                sir.setListMessage(response.body());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    public static void getThreads(String id, final ServiceResultListener srl){
        ApiUtils.getApiInstance().getThreads(id).enqueue(new Callback<List<Thread>>() {
            @Override
            public void onResponse(Call<List<Thread>> call, Response<List<Thread>> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                sir.setListThread(response.body());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<List<Thread>> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }

    public static void getPosts(String id, final ServiceResultListener srl){
        ApiUtils.getApiInstance().getPosts(id).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                ApiResults sir = new ApiResults();
                sir.setResponseCode(response.code());
                sir.setListPost(response.body());
                srl.onResult(sir);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                ApiResults sir = new ApiResults();
                sir.setException(t);
                srl.onResult(sir);
            }
        });
    }
}
