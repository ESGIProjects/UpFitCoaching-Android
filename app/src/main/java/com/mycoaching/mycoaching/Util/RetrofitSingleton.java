package com.mycoaching.mycoaching.Util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tensa on 17/03/2018.
 */

public class RetrofitSingleton {

    static final String url = ""
            ;
    private RetrofitSingleton(){

    }

    public static Retrofit getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        public static Retrofit instance = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }
}
