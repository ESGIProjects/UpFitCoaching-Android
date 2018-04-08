package com.mycoaching.mycoaching.Util.Api;

import com.mycoaching.mycoaching.Util.RetrofitSingleton;

public class ApiUtils {

    private static final String BASE_URL = "http://212.47.234.147/";

    public static final ApiInterface getRetrofitInstance(){
        return RetrofitSingleton.getInstance(BASE_URL).create(ApiInterface.class);
    }

}
