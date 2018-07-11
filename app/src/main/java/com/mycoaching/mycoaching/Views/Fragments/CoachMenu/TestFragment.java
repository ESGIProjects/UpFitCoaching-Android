package com.mycoaching.mycoaching.Views.Fragments.CoachMenu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
 * Created by kevin on 01/07/2018.
 */
public class TestFragment extends Fragment {

    View v;
    Bundle b;
    ProgressDialog pd;
    Realm r;
    UserRealm ur;

    @BindView(R.id.warming)
    EditText warming;

    @BindView(R.id.start_speed)
    EditText start_speed;

    @BindView(R.id.increase)
    EditText increase;

    @BindView(R.id.freq)
    EditText freq;

    @BindView(R.id.spinnerKnee)
    Spinner knee;

    @BindView(R.id.spinnerShin)
    Spinner shin;

    @BindView(R.id.spinnerKick)
    Spinner kick;

    @BindView(R.id.spinnerClosedFist)
    Spinner closedFist;

    @BindView(R.id.spinnerHandFlat)
    Spinner handFlat;

    @OnClick(R.id.confirm_edit)
    public void confirm(){
        if(checkFields(warming.getText().toString(),start_speed.getText().toString(),
                increase.getText().toString(),freq.getText().toString())){
            pd = new ProgressDialog(getActivity(), R.style.StyledDialog);
            pd.setMessage("Création de la fiche bilan...");
            pd.show();
            ApiCall.postTest("Bearer " + ur.getToken(), b.getString("id"), getDate(), warming.getText().toString(), start_speed.getText().toString(),
                    increase.getText().toString(), freq.getText().toString(), String.valueOf(knee.getSelectedItemPosition()),
                    String.valueOf(shin.getSelectedItemPosition()), String.valueOf(kick.getSelectedItemPosition()),
                    String.valueOf(closedFist.getSelectedItemPosition()),String.valueOf(handFlat.getSelectedItemPosition()), new ServiceResultListener() {
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
        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();
        v = inflater.inflate(R.layout.fragment_test,container,false);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this,v);
        b = this.getArguments();
        if(b != null){
            warming.setText(b.getString("warming"), TextView.BufferType.EDITABLE);
            start_speed.setText(b.getString("start_speed"), TextView.BufferType.EDITABLE);
            increase.setText(b.getString("increase"), TextView.BufferType.EDITABLE);
            freq.setText(b.getString("freq"), TextView.BufferType.EDITABLE);
            if(b.getString("knee") != null){
                knee.setSelection(Integer.valueOf(b.getString("knee")));
                shin.setSelection(Integer.valueOf(b.getString("shin")));
                kick.setSelection(Integer.valueOf(b.getString("kick")));
                closedFist.setSelection(Integer.valueOf(b.getString("closedFist")));
                handFlat.setSelection(Integer.valueOf(b.getString("handFlat")));
            }
        }
        else{
            knee.setSelection(0);
            shin.setSelection(0);
            kick.setSelection(0);
            closedFist.setSelection(0);
            handFlat.setSelection(0);
        }
        return v;
    }

}
