package com.mycoaching.mycoaching.Views.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.checkFields;
import static com.mycoaching.mycoaching.Util.CommonMethods.getCorrespondingErrorMessage;
import static com.mycoaching.mycoaching.Util.CommonMethods.getDate;
import static com.mycoaching.mycoaching.Util.CommonMethods.isNetworkAvailable;
import static com.mycoaching.mycoaching.Util.CommonMethods.isTokenExpired;
import static com.mycoaching.mycoaching.Util.CommonMethods.refreshToken;

/**
 * Created by kevin on 03/06/2018.
 * Version 1.0
 */

public class AddThread extends Dialog {

    private ProgressDialog pd;
    private UserRealm ur;
    private boolean isOK = false;
    protected Realm r;
    protected Activity activity;

    @BindView(R.id.thread_title)
    EditText threadTitle;

    @BindView(R.id.content)
    EditText content;

    @OnClick(R.id.confirm)
    void createNews() {
        pd = new ProgressDialog(getContext(), R.style.StyledDialog);
        pd.setMessage("Création du sujet en cours...");
        pd.setCancelable(false);
        pd.show();
        if (isNetworkAvailable(getContext())) {
            if (isTokenExpired(ur.getToken())) {
                refreshToken(ur.getToken(), getContext());
            }
            if (checkFields(threadTitle.getText().toString(), content.getText().toString())) {
                ApiCall.createThread("Bearer " + ur.getToken(), threadTitle.getText().toString(), getDate(), content.getText().toString(), "1", ur.getId(), new ServiceResultListener() {
                    @Override
                    public void onResult(ApiResults ar) {
                        if (ar.getResponseCode() == 201) {
                            isOK = true;
                            pd.dismiss();
                            dismiss();
                        } else {
                            pd.dismiss();
                            Toast.makeText(getContext(), getCorrespondingErrorMessage(ar.getErrorMessage()),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                pd.dismiss();
                Toast.makeText(getContext(), "Il manque au moins un champs !", Toast.LENGTH_LONG).show();
            }
        }
        else {
            pd.dismiss();
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }

    public AddThread(Activity a) {
        super(a);
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_thread);
        ButterKnife.bind(this);

        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();
    }

    public boolean getIsOK(){
        return isOK;
    }
}
