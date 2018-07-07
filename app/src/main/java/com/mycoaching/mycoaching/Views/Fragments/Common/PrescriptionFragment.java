package com.mycoaching.mycoaching.Views.Fragments.Common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycoaching.mycoaching.R;

import butterknife.ButterKnife;

/**
 * Created by kevin on 07/07/2018.
 */
public class PrescriptionFragment extends Fragment{

    View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_prescription, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

}
