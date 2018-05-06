package com.mycoaching.mycoaching.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by tensa on 17/03/2018.
 */

public class CommonMethods {

    public static boolean isAvailable(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());
    }

    public static boolean checkFields(String ...fields){
        for(int i = 0; i < fields.length; i++) {
            if(fields[i].equals("")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSame(String a, String b){
        if(a.equals(b)){
            return true;
        }
        return false;
    }

    public static void clearFields(EditText ...fields){
        for(int i = 0; i < fields.length; i++){
            fields[i].getText().clear();
        }
    }

    public static boolean checkEmail(String email){
        return email.contains("@");
    }

    public static boolean checkPassword(String pwd){
        return pwd.length() >= 8;
    }

    public static String getDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }
}
