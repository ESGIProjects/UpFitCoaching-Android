package com.mycoaching.mycoaching.Views.Activities.Common;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Activities.CoachActivity.CoachMainActivity;
import com.mycoaching.mycoaching.Views.Activities.UserActivity.UserMainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.checkEmail;
import static com.mycoaching.mycoaching.Util.CommonMethods.checkPassword;
import static com.mycoaching.mycoaching.Util.CommonMethods.clearFields;
import static com.mycoaching.mycoaching.Util.CommonMethods.getSHAPassword;
import static com.mycoaching.mycoaching.Util.CommonMethods.isNetworkAvailable;
import static com.mycoaching.mycoaching.Util.CommonMethods.performTransition;

/**
 * Created by kevin on 01/03/2018.
 */

public class LoginActivity extends AppCompatActivity {

    private Intent i = null;
    private ProgressDialog pd = null;
    private Realm realm = null;
    private SharedPreferences sp;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password)
    EditText password;

    @OnClick(R.id.signin)
    void signIn() {

        //we check if the network is available
        if (isNetworkAvailable(getApplicationContext())) {

            //we check if email and password are set
            if (checkEmail(email.getText().toString()) && checkPassword(password.getText().toString())) {
                pd = new ProgressDialog(this, R.style.StyledDialog);
                pd.setMessage("Connection en cours...");
                pd.show();
                ApiCall.signIn(email.getText().toString(), getSHAPassword(password.getText().toString()), new ServiceResultListener() {
                    @Override
                    public void onResult(ApiResults ar) {
                        pd.dismiss();
                        if (ar.getResponseCode() == 200) {
                            //we save
                            if (realm.isEmpty()) {
                                executeTransaction(realm, ar);
                            }
                            sp = getApplicationContext().getSharedPreferences("user_prefs",MODE_PRIVATE);
                            if((sp.getString("firebase_token", null) == null) || (!sp.getString("firebase_token", null).equals(FirebaseInstanceId.getInstance().getToken()))){
                                ApiCall.putToken(ar.getUr().getId(), FirebaseInstanceId.getInstance().getToken(), null, new ServiceResultListener() {
                                    @Override
                                    public void onResult(ApiResults ar) {
                                        Log.i("RESPONSE : ", ""+ar.getResponseCode());
                                        sp = getApplicationContext().getSharedPreferences("user_prefs",MODE_PRIVATE);
                                        sp.edit().putString("firebase_token", FirebaseInstanceId.getInstance().getToken()).apply();
                                    }
                                });
                            }
                            //we check if the user is a coach or a regular
                            if (ar.getUr().getType() == 2) {
                                i = new Intent(LoginActivity.this, CoachMainActivity.class);
                                performTransition(LoginActivity.this,i, R.animator.slide_from_right, R.animator.slide_to_left);
                            } else {
                                i = new Intent(LoginActivity.this, UserMainActivity.class);
                                performTransition(LoginActivity.this,i, R.animator.slide_from_right, R.animator.slide_to_left);
                            }
                            Toast.makeText(getApplicationContext(), "Connexion réussie !", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("ERROR : ", "" + ar.getResponseCode());
                            if (ar.getResponseCode() != 0) {
                                Toast.makeText(getApplicationContext(), "Veuillez réessayer ultérieurement", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Pas de réponse du serveur", Toast.LENGTH_SHORT).show();
                            }
                            clearFields(email, password);
                        }
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), R.string.missing_fields, Toast.LENGTH_LONG).show();
                clearFields(email, password);
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.signup)
    void signUp() {
        i = new Intent(this, RegisterActivity.class);
        performTransition(this,i, R.animator.slide_from_right, R.animator.slide_to_left);
    }

    @OnClick(R.id.forgot)
    void forgot() {
        i = new Intent(LoginActivity.this, UserMainActivity.class);
        performTransition(this,i, R.animator.slide_from_right, R.animator.slide_to_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Realm.init(getApplicationContext());
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        realm = Realm.getDefaultInstance();
    }

    //if the back button is pressed, we kill the application
    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    //we save in database all informations about the user/coach
    public void executeTransaction(Realm r, final ApiResults ar) {
        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserRealm ur = realm.createObject(UserRealm.class, ar.getUr().getId());
                ur.setMail(ar.getUr().getMail());
                ur.setFirstName(ar.getUr().getFirstName());
                ur.setLastName(ar.getUr().getLastName());
                ur.setCity(ar.getUr().getCity());
                ur.setPhoneNumber(ar.getUr().getPhoneNumber());
                ur.setType(ar.getUr().getType());
                if (ar.getUr().getCoach() == null) {
                    ur.setBirthDate(ar.getUr().getBirthDate());
                } else {
                    ur.setAddress(ar.getUr().getAddress());
                    ur.setIdCoach(ar.getUr().getCoach().getId());
                    ur.setMailCoach(ar.getUr().getCoach().getMail());
                    ur.setFirstNameCoach(ar.getUr().getCoach().getFirstName());
                    ur.setLastNameCoach(ar.getUr().getCoach().getLastName());
                    ur.setCityCoach(ar.getUr().getCoach().getCity());
                    ur.setPhoneNumberCoach(ar.getUr().getCoach().getPhoneNumber());
                    ur.setTypeCoach(ar.getUr().getCoach().getType());
                    ur.setAddressCoach(ar.getUr().getCoach().getAddress());
                }
            }
        });
    }
}