package com.mycoaching.mycoaching.Activities;

import android.app.ProgressDialog;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tensa on 01/03/2018.
 */

public class LoginActivity extends AppCompatActivity {

    private Intent i = null;
    private ProgressDialog pd = null;
    private String type = null;
    private Button user,coach;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.password)
    EditText password;

    @OnClick(R.id.user) void setUser(){
        type = "0";
        Log.i("TYPE : ", type);
        findViewById(R.id.user).setBackgroundColor(getResources().getColor(R.color.colorAccent));
        findViewById(R.id.coach).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @OnClick(R.id.coach) void setCoach(){
        type = "1";
        Log.i("TYPE : ", type);
        findViewById(R.id.coach).setBackgroundColor(getResources().getColor(R.color.colorAccent));
        findViewById(R.id.user).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @OnClick(R.id.signin) void signIn(){
        if(CommonMethods.isAvailable(getApplicationContext())){
            if(CommonMethods.checkEmail(email.getText().toString())
                    && CommonMethods.checkPassword(password.getText().toString()) && type != null){
                pd = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
                pd.setMessage("Connection en cours...");
                pd.show();
                CallService.signIn(type,email.getText().toString(),password.getText().toString(),new ServiceResultListener(){
                    @Override
                    public void onResult(ApiResults ar){
                        pd.dismiss();
                        if(ar.getResponseCode() == 200){
                            if(type.equals("1")){
                                i = new Intent(getApplicationContext(),CoachMainActivity.class);
                                performTransition(i,R.animator.slide_from_right,R.animator.slide_to_right);
                            }
                            else{
                                i = new Intent(getApplicationContext(),UserMainActivity.class);
                                performTransition(i,R.animator.slide_from_right,R.animator.slide_to_right);
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
            else if(type == null){
                Toast.makeText(getApplicationContext(),R.string.no_type_defined,Toast.LENGTH_LONG).show();
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
        i = new Intent(this,MessagingActivity.class);
        performTransition(i,R.animator.slide_from_right,R.animator.slide_to_right);
    }

    @OnClick(R.id.forgot) void forgot(){
        i = new Intent(LoginActivity.this,UserRegisterActivity.class);
        performTransition(i,R.animator.slide_from_right,R.animator.slide_to_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
    }

    public void performTransition(Intent i, int from, int to){
        startActivity(i);
        overridePendingTransition(from,to);
    }
}