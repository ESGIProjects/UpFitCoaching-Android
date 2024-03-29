package com.mycoaching.mycoaching.Views.Fragments.Common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycoaching.mycoaching.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by kevin on 23/06/2018.
 * Version 1.0
 */

public class LibrairiesFragment extends Fragment{

    protected View v;
    protected Realm realm;

    @OnClick(R.id.butterknife_label)
    public void openButterknife(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.butterknife_url)));
        startActivity(browserIntent);
    }

    @OnClick(R.id.firebase_label)
    public void openFirebase(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.firebase_url)));
        startActivity(browserIntent);
    }

    @OnClick(R.id.joda_label)
    public void openJoda(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.joda_url)));
        startActivity(browserIntent);
    }

    @OnClick(R.id.jwt_label)
    public void openJwt(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.jwt_url)));
        startActivity(browserIntent);
    }

    @OnClick(R.id.material_label)
    public void openMaterial(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.material_url)));
        startActivity(browserIntent);
    }

    @OnClick(R.id.MPAndroid_label)
    public void openMPAndroid(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.mp_url)));
        startActivity(browserIntent);
    }

    @OnClick(R.id.realm_label)
    public void openRealm(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.realm_url)));
        startActivity(browserIntent);
    }

    @OnClick(R.id.retrofit_label)
    public void openRetrofit(){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.retrofit_url)));
        startActivity(browserIntent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_librairies, container, false);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this,v);
        realm = Realm.getDefaultInstance();
        return v;
    }
}
