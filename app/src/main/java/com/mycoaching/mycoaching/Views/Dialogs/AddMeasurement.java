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
import static com.mycoaching.mycoaching.Util.CommonMethods.getDate;

/**
 * Created by kevin on 04/07/2018.
 */
public class AddMeasurement extends Dialog {

    boolean isCoach;
    String id;
    boolean isOK = false;
    ProgressDialog pd;

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
        pd.setMessage("Ajout des mensurations...");
        pd.show();
        if(checkFields(weight.getText().toString(),height.getText().toString(),hip.getText().toString(),
                waist.getText().toString(),thigh.getText().toString(),arm.getText().toString())){
            ApiCall.postMeasurement(id, getDate(), weight.getText().toString(), height.getText().toString()
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
                                Log.i("ERROR ADDMEASUREMENT : ", ""+ar.getResponseCode());
                            }
                        }
                    });
        }
        else{
            Toast.makeText(getContext(),"Il manque au moins un champs !", Toast.LENGTH_LONG).show();
        }
        pd.dismiss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_measurement);
        ButterKnife.bind(this);
    }

    public boolean getIsOK(){
        return isOK;
    }

}
