package com.mycoaching.mycoaching.Views.Fragments.CoachMenu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
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
import butterknife.OnTouch;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.checkFields;
import static com.mycoaching.mycoaching.Util.CommonMethods.getDate;

/**
 * Created by kevin on 29/06/2018.
 */
public class AppraisalFragment extends Fragment {

    View v;
    Bundle b;
    ProgressDialog pd;
    Realm r;
    UserRealm ur;

    @OnTouch(R.id.comment)
    boolean handleNestedScroll(View v, MotionEvent event) {
        v.getParent().getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                v.getParent().getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return false;
    }

    @BindView(R.id.goal_input)
    EditText goal;

    @BindView(R.id.rep_input)
    EditText rep;

    @BindView(R.id.contraindication_input)
    EditText contraindication;

    @BindView(R.id.sportAntecedent_input)
    EditText sportAntecedent;

    @BindView(R.id.help_input)
    CheckBox help;

    @BindView(R.id.nutritionist_input)
    CheckBox nutritionist;

    @BindView(R.id.comment)
    EditText comment;

    @OnClick(R.id.confirm_edit)
    public void edit(){
        if(checkFields(goal.getText().toString(),rep.getText().toString())){
            pd = new ProgressDialog(getActivity(), R.style.StyledDialog);
            pd.setMessage("Création de la fiche bilan...");
            pd.show();
            ApiCall.postAppraisal("Bearer " + ur.getToken(),b.getString("id"), getDate(), goal.getText().toString(),
                    rep.getText().toString(), contraindication.getText().toString(),
                    sportAntecedent.getText().toString(), String.valueOf(help.isChecked()),
                    String.valueOf(nutritionist.isChecked()), comment.getText().toString(), new ServiceResultListener() {
                        @Override
                        public void onResult(ApiResults ar) {
                            if(ar.getResponseCode() == 201){
                                Toast.makeText(getContext(),"Fiche bilan mise à jour !",Toast.LENGTH_LONG).show();
                                ClientProfileFragment cpf = (ClientProfileFragment) getActivity().getSupportFragmentManager().findFragmentByTag("PROFILE");
                                cpf.getLastAppraisal();
                                pd.dismiss();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(getContext(),"Il manque au minimum un champs !",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_appraisal, container, false);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this,v);
        b = this.getArguments();
        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();
        if(b != null){
            goal.setText(b.getString("goal"), TextView.BufferType.EDITABLE);
            rep.setText(b.getString("rep"),TextView.BufferType.EDITABLE);
            contraindication.setText(b.getString("contre"),TextView.BufferType.EDITABLE);
            sportAntecedent.setText(b.getString("antecedent"),TextView.BufferType.EDITABLE);
            if(b.getString("help") != null){
                if(b.getString("help").equalsIgnoreCase("true")){
                    help.setChecked(true);
                }
            }
            if(b.getString("nutritionist") != null){
                if(b.getString("nutritionist").equalsIgnoreCase("true")){
                    nutritionist.setChecked(true);
                }
            }
            comment.setText(b.getString("comment"),TextView.BufferType.EDITABLE);
        }
        return v;
    }

}
