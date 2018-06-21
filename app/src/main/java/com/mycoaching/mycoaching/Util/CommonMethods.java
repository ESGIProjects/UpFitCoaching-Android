package com.mycoaching.mycoaching.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

    private CommonMethods() {

    }


    /**
     * @param context The context attached to the current environment
     * @return true if there is network is available and false if it's unavailable
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return (activeNetwork != null && activeNetwork.isConnected());
    }

    /**
     * @param fields a String array
     * @return true if all elements in fields are filled, false if they are not
     */
    public static boolean checkFields(String... fields) {
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].equals("")) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param a
     * @param b
     * @return true if a is equal to b, false if it's not
     */
    public static boolean isSame(String a, String b) {
        return a.equals(b);
    }

    /**
     * @param fields an EditText array to clear
     */
    public static void clearFields(EditText... fields) {
        for (int i = 0; i < fields.length; i++) {
            fields[i].getText().clear();
        }
    }

    /**
     * @param email
     * @return true if email is valid, false if it's not
     */
    public static boolean checkEmail(String email) {
        return email.contains("@");
    }

    /**
     * @param pwd
     * @return true if pwd is valid (length > 8) , false if it's not
     */
    public static boolean checkPassword(String pwd) {
        return pwd.length() >= 8;
    }

    /**
     * @return the current date with a specific format (yyyy-MM-dd HH:mm:ss)
     */
    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return sdf.format(date);
    }

    /**
     * @param password the password to encrypt
     * @return the password in SHA-256 format
     */
    public static String getSHAPassword(String password) {
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            for (int i = 0; i < encodedHash.length; i++) {
                String hex = Integer.toHexString(0xff & encodedHash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }

    /**
     * @param s
     * @return the string s to convert in JSON format
     * @throws Exception
     */
    public static JSONObject getJSONFromString(String s) throws Exception {
        return new JSONObject(s);
    }


    /**
     * @param a the calling activity
     * @param i the intent to perform
     * @param from the starting point
     * @param to the destination
     *
     */
    public static void performTransition(Activity a, Intent i, int from, int to) {
        a.startActivity(i);
        a.overridePendingTransition(from, to);
    }
}
