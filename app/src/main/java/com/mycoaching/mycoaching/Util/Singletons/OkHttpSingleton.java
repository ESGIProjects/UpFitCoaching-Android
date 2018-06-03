package com.mycoaching.mycoaching.Util.Singletons;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by kevin on 01/05/2018.
 */

public class OkHttpSingleton {

    private OkHttpSingleton() {

    }

    public static OkHttpClient getInstance() {
        return SingletonHolder.getInstance();
    }

    private static class SingletonHolder {
        public final static OkHttpClient getInstance() {
            return new OkHttpClient.Builder().readTimeout(3, TimeUnit.SECONDS).
                    retryOnConnectionFailure(true).build();
        }
    }
}
