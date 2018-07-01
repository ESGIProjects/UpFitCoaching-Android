package com.mycoaching.mycoaching.Views.Fragments.CoachMenu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Retrofit.Appraisal;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Fragments.Common.FollowUpFragment;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kevin on 25/06/2018.
 */
public class ClientProfileFragment extends Fragment{

    View v;
    Bundle b;
    Appraisal a;
    FragmentManager fm;

    ProgressDialog pd;

    @BindView(R.id.title)
    TextView titleLabel;

    @BindView(R.id.sex)
    TextView sex;

    @BindView(R.id.birthDate)
    TextView birthDate;

    @BindView(R.id.location)
    TextView location;

    @BindView(R.id.appraisalLabel)
    TextView appraisal;

    @BindView(R.id.goal)
    TextView goal;

    @BindView(R.id.rep)
    TextView rep;

    @BindView(R.id.follow)
    Button follow;

    @BindView(R.id.consult)
    Button consult;

    @OnClick(R.id.call)
    public void call(){
        String phone = b.getString("phoneNumber");
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    @OnClick(R.id.mail)
    public void mail(){
        Uri uri = Uri.parse("mailto:" + b.getString("mail"))
                .buildUpon()
                .build();

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        startActivity(Intent.createChooser(emailIntent, "UpFit Coaching"));
    }

    @OnClick(R.id.follow)
    public void follow(){
        fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        AppraisalFragment af = new AppraisalFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id",b.getString("id"));
        if(a != null){
            bundle.putString("goal",a.getGoal());
            bundle.putString("rep",a.getSessionsByWeek());
            bundle.putString("contre",a.getContraindication());
            bundle.putString("antecedent",a.getSportAntecedents());
            bundle.putString("help",a.getHelpNeeded());
            bundle.putString("nutritionist",a.getHasNutritionist());
            bundle.putString("comment",a.getComments());
        }
        af.setArguments(bundle);
        ft.hide(getFragmentManager().findFragmentByTag("PROFILE"));
        ft.add(R.id.container, af,"APPRAISAL");
        ft.commit();
    }

    @OnClick(R.id.consult)
    public void consult(){
        fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("id",b.getString("id"));
        FollowUpFragment fuf = new FollowUpFragment();
        fuf.setArguments(b);
        ft.hide(getFragmentManager().findFragmentByTag("PROFILE"));
        ft.add(R.id.container, fuf,"FOLLOW");
        ft.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_user_profile, container, false);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this,v);
        b = this.getArguments();
        if(b != null){
            String splitBirth[] = b.getString("birthDate").split(" ")[0].split("-");
            LocalDate birth = new LocalDate(Integer.valueOf(splitBirth[0])
                    ,Integer.valueOf(splitBirth[1]),Integer.valueOf(splitBirth[2]));
            LocalDate now = new LocalDate();
            Years age = Years.yearsBetween(birth,now);
            String title = b.getString("firstName") + " " + b.getString("lastName") + " (" +
                    age.getYears() + " ans)";
            titleLabel.setText(title);
            if(b.getString("sex").equals("0")){
                sex.setText("Femme");
            }
            else{
                sex.setText("Homme");
            }
            birthDate.setText("Né(e) le " + b.getString("birthDate").split(" ")[0]);
            location.setText("Habite à " + b.get("city"));
        }
        getLastAppraisal();
        return v;
    }

    public void getLastAppraisal(){
        pd = new ProgressDialog(getActivity(), R.style.StyledDialog);
        pd.setMessage("Récupération des informations...");
        pd.show();
        ApiCall.getLastAppraisal(Integer.valueOf(b.getString("id")), new ServiceResultListener() {
            @Override
            public void onResult(ApiResults ar) {
                if(ar.getResponseCode() == 200){
                    pd.dismiss();
                    a = ar.getLastAppraisal();
                    appraisal.setVisibility(View.VISIBLE);
                    goal.setVisibility(View.VISIBLE);
                    rep.setVisibility(View.VISIBLE);
                    consult.setVisibility(View.VISIBLE);

                    follow.setText(getResources().getString(R.string.display_appraisal));
                    goal.setText(getResources().getString(R.string.goal,a.getGoal()));
                    rep.setText(getResources().getString(R.string.rep,a.getSessionsByWeek()));
                }
                else{
                    pd.dismiss();
                }
            }
        });
    }
}