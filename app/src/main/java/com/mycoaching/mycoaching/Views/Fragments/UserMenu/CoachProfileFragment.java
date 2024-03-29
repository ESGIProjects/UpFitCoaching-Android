package com.mycoaching.mycoaching.Views.Fragments.UserMenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by kevin on 23/06/2018.
 * Version 1.0
 */

public class CoachProfileFragment extends Fragment {

    protected View v;
    protected Realm r;
    private UserRealm ur;

    @BindView(R.id.firstName)
    TextView firstName;

    @BindView(R.id.lastName)
    TextView lastName;

    @BindView(R.id.mail)
    TextView mail;

    @BindView(R.id.phoneNumber)
    TextView phoneNumber;

    @BindView(R.id.address)
    TextView address;

    /**
        This method opens the GPS app of the phone (Google maps)
     */
    @OnClick(R.id.address)
    public void intentMaps(){
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + ur.getAddressCoach() + " , " + ur.getCityCoach());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    /**
        This method opens the call app of the phone
     */
    @OnClick(R.id.phoneNumber)
    public void call(){
        String phone = ur.getPhoneNumberCoach();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    /**
        This method opens the email client installed on the phone
     */
    @OnClick(R.id.mail)
    public void mail(){
        Uri uri = Uri.parse("mailto:" + ur.getMailCoach())
                .buildUpon()
                .build();

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        startActivity(Intent.createChooser(emailIntent, "UpFit Coaching"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_coach_profile, container, false);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this,v);
        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();
        firstName.setText(getResources().getString(R.string.coach_firstName,ur.getFirstNameCoach()));
        lastName.setText(getResources().getString(R.string.coach_lastName,ur.getLastNameCoach()));
        mail.setText(getResources().getString(R.string.coach_mail,ur.getMailCoach()));
        phoneNumber.setText(getResources().getString(R.string.coach_tel,ur.getPhoneNumberCoach()));
        address.setText(getResources().getString(R.string.event_location,ur.getAddressCoach(),ur.getCityCoach()));
        return v;
    }
}
