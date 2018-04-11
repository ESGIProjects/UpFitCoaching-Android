package com.mycoaching.mycoaching.Util.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by tensa on 08/03/2018.
 */


public class CallService{

    public static void signIn(String isCoach, String mail, String password, final ServiceResultListener srl){
        ApiUtils.getRetrofitInstance().signIn(isCoach,mail,password).enqueue(new Callback<Void>() {
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
