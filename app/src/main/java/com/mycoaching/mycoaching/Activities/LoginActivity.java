package com.mycoaching.mycoaching.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mycoaching.mycoaching.Activities.CoachActivity.CoachMainActivity;
import com.mycoaching.mycoaching.Activities.UserActivity.UserMainActivity;
import com.mycoaching.mycoaching.Activities.UserActivity.UserRegisterActivity;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Util.Api.ApiResults;
import com.mycoaching.mycoaching.Util.Api.CallService;
import com.mycoaching.mycoaching.Util.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Util.CommonMethods;
import com.mycoaching.mycoaching.Util.Model.Realm.UserRealm;
import com.mycoaching.mycoaching.Util.Model.Retrofit.UserRetrofit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by tensa on 01/03/2018.
 */

public class LoginActivity extends AppCompatActivity {

    private Intent i = null;
    private ProgressDialog pd = null;
    Realm realm = null;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password)
    EditText password;

    @OnClick(R.id.signin) void signIn(){
        if(CommonMethods.isAvailable(getApplicationContext())){
            if(CommonMethods.checkEmail(email.getText().toString())
                    && CommonMethods.checkPassword(password.getText().toString())){
                pd = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
                pd.setMessage("Connection en cours...");
                pd.show();
                CallService.signIn(email.getText().toString(),password.getText().toString(),new ServiceResultListener(){
                    @Override
                    public void onResult(ApiResults ar){
                        pd.dismiss();
                        if(ar.getResponseCode() == 200){
                            realm = Realm.getDefaultInstance();
                            if(realm.isEmpty()){
                                executeTransaction(realm,ar);
                            }
                            if(ar.getUr().getType() == null){
                                i = new Intent(LoginActivity.this,CoachMainActivity.class);
                                performTransition(i,R.animator.slide_from_right,R.animator.slide_to_left);
                            }
                            else{
                                i = new Intent(LoginActivity.this,UserMainActivity.class);
                                performTransition(i,R.animator.slide_from_right,R.animator.slide_to_left);
                            }
                            Toast.makeText(getApplicationContext(),"Connection réussie !",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.i("ERROR : ", "" + ar.getResponseCode());
                            if(ar.getResponseCode() != 0){
                                Toast.makeText(getApplicationContext(),"Veuillez réessayer ultérieurement",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Pas de réponse du serveur",Toast.LENGTH_SHORT).show();
                            }
                            CommonMethods.clearFields(email,password);
                        }
                    }
                });
            }
            else{
                Toast.makeText(getApplicationContext(),R.string.missing_fields,Toast.LENGTH_LONG).show();
                CommonMethods.clearFields(email,password);
            }
        }
        else{
            Toast.makeText(getApplicationContext(),R.string.no_connection,Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.signup) void signUp(){
        i = new Intent(this,UserMainActivity.class);
        performTransition(i,R.animator.slide_from_right,R.animator.slide_to_left);
    }

    @OnClick(R.id.forgot) void forgot(){
        i = new Intent(LoginActivity.this,UserRegisterActivity.class);
        performTransition(i,R.animator.slide_from_right,R.animator.slide_to_left);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
    }

    public void performTransition(Intent i, int from, int to){
        startActivity(i);
        overridePendingTransition(from,to);
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this,R.style.AlertDialogCustom);
        builder.setTitle(R.string.exit).setMessage(R.string.exit_application)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        realm.close();
                        Realm.deleteRealm(Realm.getDefaultConfiguration());
                        finishAffinity();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setIcon(R.drawable.logo)
                .show();
    }

    public void executeTransaction(Realm r, final ApiResults ar){
        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                UserRealm ur = realm.createObject(UserRealm.class,ar.getUr().getId());
                ur.setCity(ar.getUr().getCity());
                ur.setFirstName(ar.getUr().getFirstName());
                ur.setLastName(ar.getUr().getLastName());
                ur.setMail(ar.getUr().getMail());
                ur.setPhoneNumber(ar.getUr().getPhoneNumber());
                if(ar.getUr().getType() != null){
                    ur.setType(ar.getUr().getType());
                }
                if(ar.getUr().getBirthDate() != null){
                    ur.setBirthDate(ar.getUr().getBirthDate());
                }
                if(ar.getUr().getAddress() != null){
                    ur.setAddress(ar.getUr().getAddress());
                }
            }
        });
    }
}