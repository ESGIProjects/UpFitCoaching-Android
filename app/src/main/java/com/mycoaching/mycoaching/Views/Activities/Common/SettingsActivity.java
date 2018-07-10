package com.mycoaching.mycoaching.Views.Activities.Common;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Fragments.Common.EditProfileFragment;
import com.mycoaching.mycoaching.Views.Fragments.Common.LibrairiesFragment;
import com.mycoaching.mycoaching.Views.Fragments.UserMenu.CoachProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by kevin on 04/05/2018.
 */

public class SettingsActivity extends AppCompatActivity {

    Realm r;
    FragmentTransaction ft;
    Intent intent;

    boolean isCoach;

    @OnClick(R.id.edit_profile)
    void editProfile(){
        EditProfileFragment epf = new EditProfileFragment();
        Bundle b = new Bundle();
        b.putBoolean("isCoach",isCoach);
        epf.setArguments(b);
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container,epf);
        ft.show(epf);
        ft.addToBackStack(null);
        ft.commit();
    }

    @BindView(R.id.check_coach_profile)
    Button button;

    @OnClick(R.id.check_coach_profile)
    void checkCoachProfile(){
        CoachProfileFragment cpf = new CoachProfileFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,cpf);
        ft.addToBackStack(null);
        ft.commit();
    }

    @OnClick(R.id.more)
    void showMore(){
        LibrairiesFragment lf = new LibrairiesFragment();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,lf);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        r = Realm.getDefaultInstance();
        isCoach = getIntent().getExtras().getBoolean("isCoach",false);
        if(!isCoach){
            button.setVisibility(View.VISIBLE);
        }
    }
}
