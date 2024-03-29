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
import android.widget.Toast;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.Models.Retrofit.Appraisal;
import com.mycoaching.mycoaching.Models.Retrofit.Test;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Fragments.Common.FollowUpFragment;
import com.mycoaching.mycoaching.Views.Fragments.Common.PrescriptionFragment;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.getCorrespondingErrorMessage;
import static com.mycoaching.mycoaching.Util.CommonMethods.isNetworkAvailable;
import static com.mycoaching.mycoaching.Util.CommonMethods.isTokenExpired;
import static com.mycoaching.mycoaching.Util.CommonMethods.refreshToken;

/**
 * Created by kevin on 25/06/2018.
 * Version 1.0
 */

public class ClientProfileFragment extends Fragment{

    private Bundle b;
    private Appraisal a;
    private List<Test> lt = new ArrayList<>();
    private FragmentManager fm;
    protected View v;

    Realm r;
    UserRealm ur;

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

    @BindView(R.id.test)
    TextView test;

    @BindView(R.id.speed)
    TextView speed;

    @BindView(R.id.freq)
    TextView freq;

    @BindView(R.id.add_test)
    Button add_test;

    @BindView(R.id.add_prescription)
    Button add_prescription;

    /**
     * This method will perform an intent to the Phone application
     */
    @OnClick(R.id.call)
    public void call(){
        String phone = b.getString("phoneNumber");
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    /**
     * This method will perform an intent to the mail application (Gmail)
     */
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
        if(isNetworkAvailable(getContext())){
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
        else{
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.consult)
    public void consult(){
        if(isNetworkAvailable(getContext())){
            fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("id",b.getString("id"));
            FollowUpFragment fuf = new FollowUpFragment();
            fuf.setArguments(bundle);
            ft.hide(getFragmentManager().findFragmentByTag("PROFILE"));
            ft.add(R.id.container, fuf,"FOLLOW");
            ft.commit();
        }
        else{
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.add_test)
    public void test(){
        if(isNetworkAvailable(getContext())){
            fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("id",b.getString("id"));
            if(!lt.isEmpty()){
                bundle.putString("warming",lt.get(lt.size()-1).getWarmUp());
                bundle.putString("start_speed",lt.get(lt.size()-1).getStartSpeed());
                bundle.putString("increase",lt.get(lt.size()-1).getIncrease());
                bundle.putString("freq",lt.get(lt.size()-1).getFrequency());
                bundle.putString("knee",lt.get(lt.size()-1).getKneeFlexibility());
                bundle.putString("shin",lt.get(lt.size()-1).getShinFlexibility());
                bundle.putString("kick",lt.get(lt.size()-1).getHitFootFlexibility());
                bundle.putString("closedFist",lt.get(lt.size()-1).getClosedFistGroundFlexibility());
                bundle.putString("handFlat",lt.get(lt.size()-1).getHandFlatGroundFlexibility());
            }
            TestFragment tf = new TestFragment();
            tf.setArguments(bundle);
            ft.hide(getFragmentManager().findFragmentByTag("PROFILE"));
            ft.add(R.id.container, tf,"TEST");
            ft.commit();
        }
        else{
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.add_prescription)
    public void prescription(){
        if(isNetworkAvailable(getContext())){
            fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            PrescriptionFragment pf = new PrescriptionFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id",b.getString("id"));
            pf.setArguments(bundle);
            ft.hide(getFragmentManager().findFragmentByTag("PROFILE"));
            ft.add(R.id.container, pf,"PRESCRIPTION");
            ft.commit();
        }
        else{
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();
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
        pd.setCancelable(false);
        if(isNetworkAvailable(getContext())){
            if(isTokenExpired(ur.getToken())){
                refreshToken(ur.getToken(),getContext());
            }
            pd.show();
            ApiCall.getLastAppraisal("Bearer " + ur.getToken(),Integer.valueOf(b.getString("id")), new ServiceResultListener() {
                @Override
                public void onResult(ApiResults ar) {
                    pd.dismiss();
                    if(ar.getResponseCode() == 200){
                        ApiCall.getTests("Bearer " + ur.getToken(),Integer.valueOf(b.getString("id")), new ServiceResultListener() {
                            @Override
                            public void onResult(ApiResults ar) {
                                if(ar.getResponseCode() == 200){
                                    if(!ar.getListTest().isEmpty()){
                                        lt.addAll(ar.getListTest());
                                        test.setVisibility(View.VISIBLE);
                                        speed.setVisibility(View.VISIBLE);
                                        freq.setVisibility(View.VISIBLE);

                                        speed.setText(getResources().getString(R.string.test_speed,lt.get(lt.size()-1).getWarmUp()));
                                        freq.setText(getResources().getString(R.string.test_freq,lt.get(lt.size()-1).getFrequency()));
                                    }
                                    add_test.setVisibility(View.VISIBLE);
                                    add_prescription.setVisibility(View.VISIBLE);

                                }
                                else{
                                    Toast.makeText(getContext(),getCorrespondingErrorMessage(ar.getErrorMessage()),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        a = ar.getLastAppraisal();
                        appraisal.setVisibility(View.VISIBLE);
                        goal.setVisibility(View.VISIBLE);
                        rep.setVisibility(View.VISIBLE);
                        consult.setVisibility(View.VISIBLE);

                        follow.setText(getResources().getString(R.string.display_appraisal));
                        goal.setText(getResources().getString(R.string.goal,a.getGoal()));
                        rep.setText(getResources().getString(R.string.rep,a.getSessionsByWeek()));
                    }
                }
            });
        }
        else{
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }
}