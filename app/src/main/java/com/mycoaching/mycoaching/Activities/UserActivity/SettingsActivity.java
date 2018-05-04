package com.mycoaching.mycoaching.Activities.UserActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mycoaching.mycoaching.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by kevin on 04/05/2018.
 */

public class SettingsActivity extends AppCompatActivity {

    Realm realm;

    @BindView(R.id.test)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
    }
}
