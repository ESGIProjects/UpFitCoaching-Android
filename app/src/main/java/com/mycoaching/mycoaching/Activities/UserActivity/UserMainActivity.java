package com.mycoaching.mycoaching.Activities.UserActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import com.mycoaching.mycoaching.Activities.LoginActivity;
import com.mycoaching.mycoaching.Fragments.Menu.CalendarFragment;
import com.mycoaching.mycoaching.Fragments.Menu.FollowUpFragment;
import com.mycoaching.mycoaching.Fragments.Menu.ChatFragment;
import com.mycoaching.mycoaching.Fragments.Menu.ForumFragment;
import com.mycoaching.mycoaching.Fragments.Menu.SessionFragment;
import com.mycoaching.mycoaching.R;

import butterknife.BindView;
import io.realm.Realm;

/**
 * Created by tensa on 07/03/2018.
 */

public class UserMainActivity extends FragmentActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.title)
    TextView title;

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
        setActionBar(toolbar);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);
        ft = getSupportFragmentManager().beginTransaction();
        addFragments();
        hideFragments();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                return true;
            case R.id.logout:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
}
