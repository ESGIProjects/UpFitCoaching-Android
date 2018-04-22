package com.mycoaching.mycoaching.Activities.UserActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.mycoaching.mycoaching.Fragments.CalendarFragment;
import com.mycoaching.mycoaching.Fragments.FollowUpFragment;
import com.mycoaching.mycoaching.Fragments.ChatFragment;
import com.mycoaching.mycoaching.R;

import butterknife.BindView;

public class UserMainActivity extends FragmentActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.title)
    TextView title;

    CalendarFragment cf = new CalendarFragment();
    FollowUpFragment fuf = new FollowUpFragment();
    ChatFragment chf = new ChatFragment();
    FragmentTransaction ft;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container,cf);
                    ft.commit();
                    return true;
                case R.id.navigation_followUp:
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container,fuf);
                    ft.commit();
                    return true;
                case R.id.navigation_chat:
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.container,chf);
                    ft.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,cf);
        ft.commit();
    }
}
