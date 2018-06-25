package com.mycoaching.mycoaching.Views.Fragments.CoachMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycoaching.mycoaching.R;

/**
 * Created by kevin on 25/06/2018.
 */
public class ClientProfileFragment extends Fragment{

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_user_profile, container, false);
        return v;
    }

}
