package com.mycoaching.mycoaching.Views.Fragments.CoachMenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycoaching.mycoaching.R;

/**
 * Created by kevin on 24/06/2018.
 */
public class ClientsFragment extends Fragment{

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_list_clients, container, false);
        return v;
    }
}
