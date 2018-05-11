package com.mycoaching.mycoaching.Fragments.Menu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycoaching.mycoaching.R;

import butterknife.ButterKnife;

/**
 * Created by kevin on 28/04/2018.
 */

public class SessionFragment extends Fragment {

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this,v);
        return v;
    }
}
