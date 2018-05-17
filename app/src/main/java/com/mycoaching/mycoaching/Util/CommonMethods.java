package com.mycoaching.mycoaching.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.EditText;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kevin on 17/03/2018.
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
        return a.equals(b);
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

    public static String getSHAPassword(String password){
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            for (int i = 0; i < encodedHash.length; i++) {
                String hex = Integer.toHexString(0xff & encodedHash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return hexString.toString();
    }

    public static JSONObject getJSONFromString(String s) throws Exception{
        return new JSONObject(s);
    }
}
