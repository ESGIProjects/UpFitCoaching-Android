package com.mycoaching.mycoaching.Activities.UserActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.mycoaching.mycoaching.Activities.RegisterActivity;
import com.mycoaching.mycoaching.Fragments.Menu.CalendarFragment;
import com.mycoaching.mycoaching.Fragments.Menu.FollowUpFragment;
import com.mycoaching.mycoaching.Fragments.Menu.ChatFragment;
import com.mycoaching.mycoaching.Fragments.Menu.ForumFragment;
import com.mycoaching.mycoaching.Fragments.Menu.SessionFragment;
import com.mycoaching.mycoaching.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by tensa on 07/03/2018.
 */

public class UserMainActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Intent i;

    @OnClick(R.id.option) void settings(){
        i = new Intent(this,SettingsActivity.class);
        performTransition(i,R.animator.slide_from_right,R.animator.slide_to_left);
    }

    FollowUpFragment fuf = new FollowUpFragment();
    SessionFragment sf = new SessionFragment();
    CalendarFragment cf = new CalendarFragment();
    ChatFragment chf = new ChatFragment();
    ForumFragment ff = new ForumFragment();
    FragmentTransaction ft;

    Realm realm = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_followUp:
                    ft = getSupportFragmentManager().beginTransaction();
                    hideFragments();
                    ft.show(fuf);
                    ft.commit();
                    return true;
                case R.id.navigation_session:
                    ft = getSupportFragmentManager().beginTransaction();
                    hideFragments();
                    ft.show(sf);
                    ft.commit();
                    return true;
                case R.id.navigation_calendar:
                    ft = getSupportFragmentManager().beginTransaction();
                    hideFragments();
                    ft.show(cf);
                    ft.commit();
                    return true;
                case R.id.navigation_chat:
                    ft = getSupportFragmentManager().beginTransaction();
                    hideFragments();
                    ft.show(chf);
                    ft.commit();
                    return true;
                case R.id.navigation_forum:
                    ft = getSupportFragmentManager().beginTransaction();
                    hideFragments();
                    ft.show(ff);
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
        navigation.getMenu().getItem(2).setChecked(true);
        ft = getSupportFragmentManager().beginTransaction();
        addFragments();
        hideFragments();
        ft.show(cf);
        ft.commit();
        realm = realm.getDefaultInstance();
        ButterKnife.bind(this);
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

    public void addFragments(){
        ft.add(R.id.container,fuf);
        ft.add(R.id.container,sf);
        ft.add(R.id.container,cf);
        ft.add(R.id.container,chf);
        ft.add(R.id.container,ff);
    }

    public void hideFragments(){
        ft.hide(fuf);
        ft.hide(sf);
        ft.hide(cf);
        ft.hide(chf);
        ft.hide(ff);
    }

    public void performTransition(Intent i, int from, int to){
        startActivity(i);
        overridePendingTransition(from,to);
    }
}
