package com.mycoaching.mycoaching.Views.Activities.CoachActivity;

/**
 * Created by kevin on 07/03/2018.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Activities.Common.LoginActivity;
import com.mycoaching.mycoaching.Views.Fragments.CoachMenu.ListChatFragment;
import com.mycoaching.mycoaching.Views.Fragments.Common.ChatFragment;
import com.mycoaching.mycoaching.Views.Fragments.Common.ThreadFragment;
import com.mycoaching.mycoaching.Views.Fragments.UserMenu.EventFragment;

import java.io.IOException;

import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.performTransition;

public class CoachMainActivity extends AppCompatActivity {

    Realm realm = null;
    Intent intent;
    UserRealm ur;

    BottomNavigationView navigation;

    ListChatFragment lcf = new ListChatFragment();
    ThreadFragment tf = new ThreadFragment();
    EventFragment ef = new EventFragment();
    Bundle b = new Bundle();
    FragmentTransaction ft;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    ft = getSupportFragmentManager().beginTransaction();
                    hideFragments();
                    hideSpecificFragments();
                    ft.show(ef);
                    ft.commit();
                    return true;
                case R.id.navigation_chat:
                    ft = getSupportFragmentManager().beginTransaction();
                    hideFragments();
                    hideSpecificFragments();
                    if(getSupportFragmentManager().findFragmentByTag("MESSAGES") != null){
                        ft.show(getSupportFragmentManager().findFragmentByTag("MESSAGES"));
                        ChatFragment.isActive = true;
                    }
                    else{
                        ft.show(lcf);
                        ListChatFragment.isActive = true;
                    }
                    ft.commit();
                    return true;
                case R.id.navigation_forum:
                    ft = getSupportFragmentManager().beginTransaction();
                    hideFragments();
                    hideSpecificFragments();
                    if(getSupportFragmentManager().findFragmentByTag("POSTS") != null){
                        ft.show(getSupportFragmentManager().findFragmentByTag("POSTS"));
                    }
                    else{
                        ft.show(tf);
                    }
                    ft.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_main);

        b.putBoolean("isCoach",true);
        ef.setArguments(b);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(true);

        ft = getSupportFragmentManager().beginTransaction();

        realm = Realm.getDefaultInstance();
        ur = realm.where(UserRealm.class).findFirst();

        ft = getSupportFragmentManager().beginTransaction();
        addFragments();
        hideFragments();
        ft.show(lcf);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentByTag("POSTS") != null && navigation.getMenu().getItem(2).isChecked()){
            ft = getSupportFragmentManager().beginTransaction();
            ft.remove(getSupportFragmentManager().findFragmentByTag("POSTS"));
            tf.getLt().clear();
            tf.prepareData();
            ft.show(getSupportFragmentManager().findFragmentByTag("TF"));
            ft.commit();
        }
        else if(getSupportFragmentManager().findFragmentByTag("MESSAGES") != null && navigation.getMenu().getItem(1).isChecked()){
            ft = getSupportFragmentManager().beginTransaction();
            ft.remove(getSupportFragmentManager().findFragmentByTag("MESSAGES"));
            tf.getLt().clear();
            tf.prepareData();
            ft.show(getSupportFragmentManager().findFragmentByTag("LCF"));
            ft.commit();
        }
        else{
            displayAlert();
        }
    }

    public void displayAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CoachMainActivity.this, R.style.AlertDialogCustom);
        builder.setTitle(R.string.exit).setMessage(R.string.exit_application)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                        realm.beginTransaction();
                        realm.deleteAll();
                        realm.commitTransaction();
                        realm.close();
                        lcf.getWs().close(1000,null);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    FirebaseInstanceId.getInstance().deleteInstanceId();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        performTransition(CoachMainActivity.this,intent, R.animator.slide_from_left, R.animator.slide_to_right);
                        finish();
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

    public void addFragments() {
        ft.add(R.id.container, ef);
        ft.add(R.id.container, lcf,"LCF");
        ft.add(R.id.container, tf,"TF");
    }

    public void hideFragments() {
        ListChatFragment.isActive = false;
        ChatFragment.isActive = false;
        ft.hide(ef);
        ft.hide(lcf);
        ft.hide(tf);
    }

    public void hideSpecificFragments(){
        if(getSupportFragmentManager().findFragmentByTag("MESSAGES") != null){
            ft.hide(getSupportFragmentManager().findFragmentByTag("MESSAGES"));
        }
        if(getSupportFragmentManager().findFragmentByTag("POSTS") != null){
            ft.hide(getSupportFragmentManager().findFragmentByTag("POSTS"));
        }
    }
}
