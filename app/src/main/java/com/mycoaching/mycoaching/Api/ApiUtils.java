package com.mycoaching.mycoaching.Api;

import com.mycoaching.mycoaching.Util.Singletons.RetrofitSingleton;

/**
 * Created by kevin on 08/03/2018.
 */


public class ApiUtils {

    private static final String BASE_URL = "http://212.47.234.147/";

    public static final ApiInterface getApiInstance(){
        return RetrofitSingleton.getInstance(BASE_URL).create(ApiInterface.class);
    }

}
