package com.mycoaching.mycoaching.Views.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Fragments.CoachMenu.ClientProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mycoaching.mycoaching.Util.CommonMethods.checkFields;
import static com.mycoaching.mycoaching.Util.CommonMethods.getDate;

/**
 * Created by kevin on 01/07/2018.
 */
public class TestFragment extends Fragment {

    View v;
    Bundle b;
    ProgressDialog pd;

    @BindView(R.id.warming)
    EditText warming;

    @BindView(R.id.start_speed)
    EditText start_speed;

    @BindView(R.id.increase)
    EditText increase;

    @BindView(R.id.freq)
    EditText freq;

    @BindView(R.id.knee)
    EditText knee;

    @BindView(R.id.shin)
    EditText shin;

    @BindView(R.id.kick)
    EditText kick;

    @BindView(R.id.closedFist)
    EditText closedFist;

    @BindView(R.id.handFlat)
    EditText handFlat;

    @OnClick(R.id.confirm_edit)
    public void confirm(){
        if(checkFields(warming.getText().toString(),start_speed.getText().toString(),
                increase.getText().toString(),freq.getText().toString(),knee.getText().toString(),
                shin.getText().toString(),kick.getText().toString(),closedFist.getText().toString(),
                handFlat.getText().toString())){
            pd = new ProgressDialog(getActivity(), R.style.StyledDialog);
            pd.setMessage("Création de la fiche bilan...");
            pd.show();
            ApiCall.postTest(b.getString("id"), getDate(), warming.getText().toString(), start_speed.getText().toString(),
                    increase.getText().toString(), freq.getText().toString(), knee.getText().toString(),
                    shin.getText().toString(), kick.getText().toString(), closedFist.getText().toString(),
                    handFlat.getText().toString(), new ServiceResultListener() {
                        @Override
                        public void onResult(ApiResults ar) {
                            if(ar.getResponseCode() == 201){
                                Toast.makeText(getContext(),"Nouveau test créé !",Toast.LENGTH_LONG).show();
                                ClientProfileFragment cpf = (ClientProfileFragment) getActivity().getSupportFragmentManager().findFragmentByTag("PROFILE");
                                cpf.getLastAppraisal();
                                pd.dismiss();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(getContext(),"Il manque au moins un champs !",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_test,container,false);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this,v);
        b = this.getArguments();
        if(b != null){
            warming.setText(b.getString("warming"), TextView.BufferType.EDITABLE);
            start_speed.setText(b.getString("start_speed"), TextView.BufferType.EDITABLE);
            increase.setText(b.getString("increase"), TextView.BufferType.EDITABLE);
            freq.setText(b.getString("freq"), TextView.BufferType.EDITABLE);
            knee.setText(b.getString("knee"), TextView.BufferType.EDITABLE);
            shin.setText(b.getString("shin"), TextView.BufferType.EDITABLE);
            kick.setText(b.getString("kick"), TextView.BufferType.EDITABLE);
            closedFist.setText(b.getString("closedFist"), TextView.BufferType.EDITABLE);
            handFlat.setText(b.getString("handFlat"), TextView.BufferType.EDITABLE);
        }
        return v;
    }

}
