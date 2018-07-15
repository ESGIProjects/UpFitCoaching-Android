package com.mycoaching.mycoaching.Views.Activities.Common;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
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
import static com.mycoaching.mycoaching.Util.CommonMethods.getCorrespondingErrorMessage;
import static com.mycoaching.mycoaching.Util.CommonMethods.getSHAPassword;
import static com.mycoaching.mycoaching.Util.CommonMethods.isNetworkAvailable;
import static com.mycoaching.mycoaching.Util.CommonMethods.performTransition;

/**
 * Created by kevin on 01/03/2018.
 */

public class LoginActivity extends AppCompatActivity {

    private Intent i = null;
    private ProgressDialog pd;
    private Realm realm = null;
    private SharedPreferences sp;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password)
    EditText password;

    @OnClick(R.id.signin)
    void signIn() {

        //we check if the network is available
        if(isNetworkAvailable(getApplicationContext())) {
            //we check if email and password are set
            if (checkEmail(email.getText().toString()) && checkPassword(password.getText().toString())) {
                pd = new ProgressDialog(LoginActivity.this, R.style.StyledDialog);
                pd.setCancelable(false);
                pd.setMessage("Connection en cours...");
                pd.show();
                ApiCall.signIn(email.getText().toString(), getSHAPassword(password.getText().toString()), new ServiceResultListener() {
                    @Override
                    public void onResult(ApiResults ar) {
                        if (ar.getResponseCode() == 200) {
                            //we save
                            if (realm.isEmpty()) {
                                executeTransaction(realm, ar);
                            }
                            else{
                                realm.beginTransaction();
                                realm.deleteAll();
                                realm.commitTransaction();
                                executeTransaction(realm, ar);
                            }
                            sp = getApplicationContext().getSharedPreferences("user_prefs",MODE_PRIVATE);
                            if((sp.getString("firebase_token", null) == null) || (!sp.getString("firebase_token", null).equals(FirebaseInstanceId.getInstance().getToken()))){
                                ApiCall.putToken("Bearer " + ar.getUt().getToken(),ar.getUt().getUr().getId(), FirebaseInstanceId.getInstance().getToken(), null, new ServiceResultListener() {
                                    @Override
                                    public void onResult(ApiResults ar) {
                                        Log.i("RESPONSE : ", ""+ar.getResponseCode());
                                        sp = getApplicationContext().getSharedPreferences("user_prefs",MODE_PRIVATE);
                                        sp.edit().putString("firebase_token", FirebaseInstanceId.getInstance().getToken()).apply();
                                    }
                                });
                            }
                            //we check if the user is a coach or a regular
                            if (ar.getUt().getUr().getType() == 2) {
                                i = new Intent(LoginActivity.this, CoachMainActivity.class);
                                performTransition(LoginActivity.this,i, R.animator.slide_from_right, R.animator.slide_to_left);
                            }
                            else {
                                i = new Intent(LoginActivity.this, UserMainActivity.class);
                                performTransition(LoginActivity.this,i, R.animator.slide_from_right, R.animator.slide_to_left);
                            }
                            Toast.makeText(getApplicationContext(), "Connexion réussie !", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),getCorrespondingErrorMessage(ar.getErrorMessage()),
                                    Toast.LENGTH_LONG).show();
                            clearFields(email,password);
                        }
                    }
                });
                pd.dismiss();
            }
            else {
                Toast.makeText(getApplicationContext(), R.string.missing_fields, Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.signup)
    void signUp() {
        i = new Intent(this, RegisterActivity.class);
        performTransition(this,i, R.animator.slide_from_right, R.animator.slide_to_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(savedInstanceState != null){
            Intent i = new Intent(this, SplashScreenActivity.class);
            performTransition(this,i, R.animator.slide_from_left, R.animator.slide_to_right);
            finish();
            return;
        }

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
                UserRealm ur = realm.createObject(UserRealm.class, ar.getUt().getUr().getId());
                ur.setMail(ar.getUt().getUr().getMail());
                ur.setSex(ar.getUt().getUr().getSex());
                ur.setFirstName(ar.getUt().getUr().getFirstName());
                ur.setLastName(ar.getUt().getUr().getLastName());
                ur.setCity(ar.getUt().getUr().getCity());
                ur.setPhoneNumber(ar.getUt().getUr().getPhoneNumber());
                ur.setType(ar.getUt().getUr().getType());
                ur.setToken(ar.getUt().getToken());
                if (ar.getUt().getUr().getCoach() != null) {
                    ur.setBirthDate(ar.getUt().getUr().getBirthDate());
                    ur.setAddress(ar.getUt().getUr().getAddress());
                    ur.setIdCoach(ar.getUt().getUr().getCoach().getId());
                    ur.setMailCoach(ar.getUt().getUr().getCoach().getMail());
                    ur.setFirstNameCoach(ar.getUt().getUr().getCoach().getFirstName());
                    ur.setLastNameCoach(ar.getUt().getUr().getCoach().getLastName());
                    ur.setSexCoach(ar.getUt().getUr().getCoach().getSex());
                    ur.setCityCoach(ar.getUt().getUr().getCoach().getCity());
                    ur.setPhoneNumberCoach(ar.getUt().getUr().getCoach().getPhoneNumber());
                    ur.setTypeCoach(ar.getUt().getUr().getCoach().getType());
                    ur.setAddressCoach(ar.getUt().getUr().getCoach().getAddress());
                }
                else{
                    ur.setAddress(ar.getUt().getUr().getAddress());
                }
            }
        });
    }
}