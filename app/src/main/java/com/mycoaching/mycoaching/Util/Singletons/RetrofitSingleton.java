package com.mycoaching.mycoaching.Util.Singletons;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kevin on 17/03/2018.
 */

public class RetrofitSingleton {

    private RetrofitSingleton(){
    }

    public static Retrofit getInstance(String url) {
        return SingletonHolder.getInstance(url);
    }

    private static class SingletonHolder {
        public final static Retrofit getInstance(String url){
            return new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }
}
