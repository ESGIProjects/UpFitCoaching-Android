package com.mycoaching.mycoaching.Views.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
import static com.mycoaching.mycoaching.Util.CommonMethods.isTokenExpired;
import static com.mycoaching.mycoaching.Util.CommonMethods.refreshToken;

/**
 * Created by kevin on 04/07/2018.
 */
public class AddMeasurement extends Dialog {

    String id;
    boolean isOK = false;
    ProgressDialog pd;
    Realm r;
    UserRealm ur;

    public AddMeasurement(Activity a, String id) {
        super(a);
        this.id = id;
    }

    @BindView(R.id.weight)
    EditText weight;

    @BindView(R.id.height)
    EditText height;

    @BindView(R.id.hip)
    EditText hip;

    @BindView(R.id.waist)
    EditText waist;

    @BindView(R.id.thigh)
    EditText thigh;

    @BindView(R.id.arm)
    EditText arm;

    @OnClick(R.id.confirm_measurement)
    public void confirm(){
        pd = new ProgressDialog(getContext(), R.style.StyledDialog);
        pd.setCancelable(false);
        pd.setMessage("Ajout des mensurations...");
        pd.show();
        if(isTokenExpired(ur.getToken())){
            refreshToken(ur.getToken(),getContext());
        }
        if(checkFields(weight.getText().toString(),height.getText().toString(),hip.getText().toString(),
                waist.getText().toString(),thigh.getText().toString(),arm.getText().toString())){
            if(Float.valueOf(weight.getText().toString()) > 200.0f){
                Toast.makeText(getContext(),"Le poids ne peut excéder 200kg !", Toast.LENGTH_LONG).show();
            }
            else if(Float.valueOf(height.getText().toString()) > 250.0f){
                Toast.makeText(getContext(),"La taille ne peut excéder 2m50 !", Toast.LENGTH_LONG).show();
            }
            else if(Float.valueOf(hip.getText().toString()) > 120.0f){
                Toast.makeText(getContext(),"Le tour de taille ne peut excéder 120cm !", Toast.LENGTH_LONG).show();
            }
            else if(Float.valueOf(waist.getText().toString()) > 150.0f){
                Toast.makeText(getContext(),"Le tour de ventre ne peuxt excéder 150cm !", Toast.LENGTH_LONG).show();
            }
            else if(Float.valueOf(thigh.getText().toString()) > 100.0f){
                Toast.makeText(getContext(),"Le tour de cuisse ne peut excéder 100cm !", Toast.LENGTH_LONG).show();
            }
            else if(Float.valueOf(arm.getText().toString()) > 50.0f){
                Toast.makeText(getContext(),"Le tour de bras ne peut excéder 50cm !", Toast.LENGTH_LONG).show();
            }
            else{
                ApiCall.postMeasurement("Bearer " + ur.getToken(),id, getDate(), weight.getText().toString(), height.getText().toString()
                        ,hip.getText().toString(), waist.getText().toString(), thigh.getText().toString()
                        ,arm.getText().toString(), new ServiceResultListener() {
                            @Override
                            public void onResult(ApiResults ar) {
                                if (ar.getResponseCode() == 201) {
                                    pd.dismiss();
                                    isOK = true;
                                    dismiss();
                                }
                                else{
                                    pd.dismiss();
                                    Toast.makeText(getContext(),getCorrespondingErrorMessage(ar.getErrorMessage()),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }
        else{
            Toast.makeText(getContext(),"Il manque au moins un champs !", Toast.LENGTH_LONG).show();
        }
        pd.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_measurement);
        ButterKnife.bind(this);
    }

    public boolean getIsOK(){
        return isOK;
    }

}
