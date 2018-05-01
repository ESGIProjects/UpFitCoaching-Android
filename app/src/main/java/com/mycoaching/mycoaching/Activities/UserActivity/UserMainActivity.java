package com.mycoaching.mycoaching.Activities.UserActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.mycoaching.mycoaching.Activities.LoginActivity;
import com.mycoaching.mycoaching.Fragments.Menu.CalendarFragment;
import com.mycoaching.mycoaching.Fragments.Menu.FollowUpFragment;
import com.mycoaching.mycoaching.Fragments.Menu.ChatFragment;
import com.mycoaching.mycoaching.R;

import butterknife.BindView;
import io.realm.Realm;

public class UserMainActivity extends FragmentActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.title)
    TextView title;

    CalendarFragment cf = new CalendarFragment();
    FollowUpFragment fuf = new FollowUpFragment();
    ChatFragment chf = new ChatFragment();
    FragmentTransaction ft;

    Realm realm = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.hide(fuf);
                    ft.hide(chf);
                    ft.show(cf);
                    ft.commit();
                    return true;
                case R.id.navigation_followUp:
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.hide(cf);
                    ft.hide(chf);
                    ft.show(fuf);
                    ft.commit();
                    return true;
                case R.id.navigation_chat:
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.hide(fuf);
                    ft.hide(cf);
                    ft.show(chf);
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
        ft.add(R.id.container,cf);
        ft.add(R.id.container,fuf);
        ft.add(R.id.container,chf);
        ft.show(cf);
        ft.commit();
        realm = realm.getDefaultInstance();
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserMainActivity.this,R.style.AlertDialogCustom);
        builder.setTitle(R.string.exit).setMessage(R.string.exit_application)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setIcon(R.drawable.logo)
                .show();
    }
}
