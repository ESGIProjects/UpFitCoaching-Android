package com.mycoaching.mycoaching.Fragments.Register;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycoaching.mycoaching.Views.Activities.Common.RegisterActivity;
import com.mycoaching.mycoaching.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterChoiceFragment extends Fragment{

    Bundle b = new Bundle();
    CredentialsFragment cf = new CredentialsFragment();

    @OnClick(R.id.user) void userTransition(){
        b.putString("type","0");
        cf.setArguments(b);
        ((RegisterActivity)getActivity()).replaceFragment(cf,R.id.container);
    }

    @OnClick(R.id.coach) void coachTransition(){
        b.putString("type","2");
        cf.setArguments(b);
        ((RegisterActivity)getActivity()).replaceFragment(cf,R.id.container);
    }

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_register_choice, container, false);
        ButterKnife.bind(this,v);
        return v;
    }
}