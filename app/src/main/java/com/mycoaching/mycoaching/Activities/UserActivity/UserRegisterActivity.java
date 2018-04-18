package com.mycoaching.mycoaching.Activities.UserActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mycoaching.mycoaching.Activities.LoginActivity;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Util.Api.ApiResults;
import com.mycoaching.mycoaching.Util.Api.CallService;
import com.mycoaching.mycoaching.Util.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Util.CommonMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by tensa on 17/03/2018.
 */

public class UserRegisterActivity extends AppCompatActivity {

    private ProgressDialog pd = null;
    private Intent i;

    @BindView(R.id.type_label)
    TextView type_label;

    @BindView(R.id.user_type)
    Spinner user_type;

    @BindView(R.id.mail)
    EditText email;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.firstName)
    EditText firstName;

    @BindView(R.id.lastName)
    EditText lastName;

    @BindView(R.id.birthDate)
    EditText birthDate;

    @BindView(R.id.city)
    EditText city;

    @BindView(R.id.phoneNumber)
    EditText phoneNumber;

    @OnClick(R.id.account_creation) void createUser(){
        if((CommonMethods.checkFields(email.getText().toString(),password.getText().toString(),
                firstName.getText().toString(),lastName.getText().toString(),birthDate.getText().toString(),
                city.getText().toString(),phoneNumber.getText().toString())) && CommonMethods.checkEmail(email.getText().toString())
                && CommonMethods.checkPassword(password.getText().toString())){
            pd = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
            pd.setMessage("Création du compte en cours...");
            pd.show();
            CallService.signUp(String.valueOf(user_type.getSelectedItemPosition()), email.getText().toString(),
                    password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(),
                    birthDate.getText().toString(), city.getText().toString(), phoneNumber.getText().toString(),
                    new ServiceResultListener() {
                        @Override
                        public void onResult(ApiResults ar) {
                            pd.dismiss();
                            if(ar.getResponseCode() == 201){
                                i = new Intent(getApplicationContext(),LoginActivity.class);
                                performTransition(i,R.animator.slide_from_left,R.animator.slide_to_right);
                                Toast.makeText(getApplicationContext(),"Compte créé !",Toast.LENGTH_LONG).show();
                            }
                            else if(ar.getResponseCode() == 409){
                                CommonMethods.clearFields(email,password,firstName,lastName,birthDate,city,phoneNumber);
                                Toast.makeText(getApplicationContext(),"Ce compte existe déjà !",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(getApplicationContext(),"Au moins un des champs n'est pas valide !",Toast.LENGTH_LONG).show();
        }
    }


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register2);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        overridePendingTransition(R.animator.slide_from_left,R.animator.slide_to_right);
    }

    public void performTransition(Intent i, int from, int to){
        startActivity(i);
        overridePendingTransition(from,to);
    }
}
