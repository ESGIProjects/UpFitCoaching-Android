package com.mycoaching.mycoaching.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.mycoaching.mycoaching.Util.Constants.DATE_TIME_FORMATTER;

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
            if (fields[i].equals("") || !(fields[i].trim().length() > 0)) {
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
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMATTER, Locale.getDefault());
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
     */
    public static void performTransition(Activity a, Intent i, int from, int to) {
        a.startActivity(i);
        a.overridePendingTransition(from, to);
    }

    /**
     * @param token the token to check
     * @return true if the token is expired, false if it's not
     */
    public static boolean isTokenExpired(String token) {
        JWT jwt = new JWT(token);
        if(jwt.getExpiresAt().before(new Date())){
            return true;
        }
        return false;
    }

    /**
     * @param message the message in JSON response
     * @return the corresponding error message to display
     */
    public static String getCorrespondingErrorMessage(String message) {
        String toReturn = "";
        switch (message){
            case "appraisal_not_found":
                toReturn = "Aucun bilan correspondant";
                break;
            case "user_already_exists":
                toReturn = "Cet utilisateur existe déjà";
                break;
            case "user_insert_failed":
                toReturn = "Nous avons rencontré un problème lors du traitement du compte utilisateur. Merci de réessayer plus tard";
                break;
            case "prescription_insert_failed":
                toReturn = "Nous avons rencontré un problème lors de l'ajout de la prescription. Merci de réessayer plus tard";
                break;
            case "thread_insert_failed":
                toReturn = "Nous avons rencontré un problème lors de l'ajout du sujet. Merci de réessayer plus tard";
                break;
            case "post_insert_failed":
                toReturn = "Nous avons rencontré un problème lors de l'ajout du message. Merci de réessayer plus tard";
                break;
            case "appraisal_insert_failed":
                toReturn = "Nous avons rencontré un problème lors de l'ajout du bilan. Merci de réessayer plus tard";
                break;
            case "measurements_insert_failed":
                toReturn = "Nous avons recontré un problème lors de l'ajout des mensurations. Merci de réessayer plus tard";
                break;
            case "test_insert_failed":
                toReturn = "Nous avons rencontré un problème lors de l'ajout du test. Merci de réessayer plus tard";
                break;
            case "event_insert_failed":
                toReturn = "Nous avons rencontré un problème lors de l'ajout de l'événement. Merci de réessayer plus tard";
                break;
            case "token_error":
                toReturn = "En raison d'une erreur inconnue, nous ne pouvons plus vous identifier. Merci de vous reconnecer avant de réessayer";
                break;
            case "token_not_valid":
                toReturn = "En raison d'une erreur inconnue, nous ne pouvons plus vous identifier. Merci de vous reconnecer avant de réessayer";
                break;
            case "user_wrong_password":
                toReturn = "Ce mot de passe est incorrect";
                break;
            case "parameter_error":
                toReturn = "Certains paramètres sont manquants ou erronés. Vérifiez avant de réessayer";
                break;
            case "user_not_exist":
                toReturn = "Cet utilisateur n'existe pas";
                break;
            case "event_not_exist":
                toReturn = "Cet événement n'existe pas";
                break;
            case "token_creation_error":
                toReturn = "Nous recontrons un problème réseau. Merci de réessayer plus tard";
                break;
            default:
                toReturn = "Nous recontrons un problème réseau. Merci de réessayer plus tard";
        }
        return toReturn;
    }

    /**
     * @param oldToken the old token to refresh
     */
    public static void refreshToken(String oldToken, final Context context){
        ApiCall.getRefreshedToken("Bearer " + oldToken, new ServiceResultListener() {
            @Override
            public void onResult(ApiResults ar) {
                if(ar.getResponseCode() == 200){
                    Toast.makeText(context,"Le token a été mis à jour !",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, getCorrespondingErrorMessage(ar.getErrorMessage()), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
