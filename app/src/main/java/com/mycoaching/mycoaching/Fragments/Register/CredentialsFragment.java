package com.mycoaching.mycoaching.Fragments.Register;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mycoaching.mycoaching.Activities.RegisterActivity;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Util.Api.ApiResults;
import com.mycoaching.mycoaching.Util.Api.CallService;
import com.mycoaching.mycoaching.Util.Api.ServiceResultListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mycoaching.mycoaching.Util.CommonMethods.checkEmail;
import static com.mycoaching.mycoaching.Util.CommonMethods.checkFields;
import static com.mycoaching.mycoaching.Util.CommonMethods.checkPassword;
import static com.mycoaching.mycoaching.Util.CommonMethods.clearFields;
import static com.mycoaching.mycoaching.Util.CommonMethods.isSame;

public class CredentialsFragment extends Fragment {

    View v;
    Bundle b;
    ProgressDialog pd = null;

    @BindView(R.id.mail)
    EditText mail;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.passwordConfirmation)
    EditText passwordConfirmation;

    @OnClick(R.id.next) void dataTransition(){
        if(checkFields(mail.getText().toString(),password.getText().toString(), passwordConfirmation.getText().toString())){
            if(checkEmail(mail.getText().toString()) && checkPassword(password.getText().toString()) &&
                    isSame(password.getText().toString(), passwordConfirmation.getText().toString())){
                pd = new ProgressDialog(getContext(),R.style.StyledDialog);
                pd.show();
                CallService.checkMail(mail.getText().toString(), new ServiceResultListener() {
                    @Override
                    public void onResult(ApiResults ar) {
                        pd.dismiss();
                        if(ar.getResponseCode() == 200){
                            if(b == null){
                                b = new Bundle();
                                b.putString("mail",mail.getText().toString());
                                b.putString("password",password.getText().toString());
                                UserDataFragment udf = new UserDataFragment();
                                udf.setArguments(b);
                                ((RegisterActivity)getActivity()).replaceFragment(udf,R.id.container);
                            }
                            else{
                                b.putString("mail",mail.getText().toString());
                                b.putString("password",password.getText().toString());
                                CoachDataFragment cdf = new CoachDataFragment();
                                cdf.setArguments(b);
                                ((RegisterActivity)getActivity()).replaceFragment(cdf,R.id.container);
                            }
                        }
                        else{
                            Toast.makeText(getContext(),R.string.already_exists,Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
            else{
                Toast.makeText(getContext(),R.string.wrong_credentials,Toast.LENGTH_LONG).show();
                clearFields(mail,password,passwordConfirmation);
            }
        }
        else{
            Toast.makeText(getContext(),R.string.missing_fields,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_credentials, container, false);
        ButterKnife.bind(this,v);
        b = getArguments();
        return v;
    }
}
