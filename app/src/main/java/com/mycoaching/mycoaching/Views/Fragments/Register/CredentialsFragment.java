package com.mycoaching.mycoaching.Views.Fragments.Register;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Activities.Common.RegisterActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mycoaching.mycoaching.Util.CommonMethods.checkEmail;
import static com.mycoaching.mycoaching.Util.CommonMethods.checkFields;
import static com.mycoaching.mycoaching.Util.CommonMethods.checkPassword;
import static com.mycoaching.mycoaching.Util.CommonMethods.clearFields;
import static com.mycoaching.mycoaching.Util.CommonMethods.getSHAPassword;
import static com.mycoaching.mycoaching.Util.CommonMethods.isSame;

public class CredentialsFragment extends Fragment {

    View v;
    private Bundle b;
    private ProgressDialog pd;

    @BindView(R.id.mail)
    EditText mail;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.passwordConfirmation)
    EditText passwordConfirmation;

    @OnClick(R.id.next)
    void dataTransition() {
        if (checkFields(mail.getText().toString(), password.getText().toString(), passwordConfirmation.getText().toString())) {
            if (checkEmail(mail.getText().toString()) && checkPassword(password.getText().toString()) &&
                    isSame(password.getText().toString(), passwordConfirmation.getText().toString())) {
                pd = new ProgressDialog(getContext(), R.style.StyledDialog);
                pd.setCancelable(false);
                pd.setMessage("Vérification de l'adresse mail...");
                pd.show();
                ApiCall.checkMail(mail.getText().toString(), new ServiceResultListener() {
                    @Override
                    public void onResult(ApiResults ar) {
                        pd.dismiss();
                        if (ar.getResponseCode() == 200) {
                            b.putString("mail", mail.getText().toString());
                            b.putString("password", getSHAPassword(password.getText().toString()));
                            UserDataFragment udf = new UserDataFragment();
                            udf.setArguments(b);
                            ((RegisterActivity) getActivity()).replaceFragment(udf, R.id.container);

                            //that part is commented for the moment due to single coach capacity

                            /*
                            if(b.getString("type") == "0"){
                                b.putString("mail",mail.getText().toString());
                                b.putString("password", getSHAPassword(password.getText().toString()));
                                UserDataFragment udf = new UserDataFragment();
                                udf.setArguments(b);
                                ((RegisterActivity)getActivity()).replaceFragment(udf,R.id.container);
                            }
                            else{
                                b.putString("mail",mail.getText().toString());
                                b.putString("password", getSHAPassword(password.getText().toString()));
                                CoachDataFragment cdf = new CoachDataFragment();
                                cdf.setArguments(b);
                                ((RegisterActivity)getActivity()).replaceFragment(cdf,R.id.container);
                            }*/
                        } else {
                            Toast.makeText(getContext(), R.string.already_exists, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                Toast.makeText(getContext(), R.string.wrong_credentials, Toast.LENGTH_LONG).show();
                clearFields(password, passwordConfirmation);
            }
        } else {
            Toast.makeText(getContext(), R.string.missing_fields, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_credentials, container, false);
        ButterKnife.bind(this, v);
        b = getArguments();
        return v;
    }
}
