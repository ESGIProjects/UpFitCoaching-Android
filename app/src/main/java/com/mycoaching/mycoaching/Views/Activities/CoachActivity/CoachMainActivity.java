package com.mycoaching.mycoaching.Views.Activities.CoachActivity;

/**
 * Created by kevin on 07/03/2018.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Activities.Common.LoginActivity;
import com.mycoaching.mycoaching.Views.Activities.Common.SettingsActivity;
import com.mycoaching.mycoaching.Views.Fragments.CoachMenu.ClientsFragment;
import com.mycoaching.mycoaching.Views.Fragments.CoachMenu.ListChatFragment;
import com.mycoaching.mycoaching.Views.Fragments.Common.ChatFragment;
import com.mycoaching.mycoaching.Views.Fragments.Common.EventFragment;
import com.mycoaching.mycoaching.Views.Fragments.Common.ThreadFragment;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.performTransition;

public class CoachMainActivity extends AppCompatActivity {

    Realm realm = null;
    Intent intent;
    UserRealm ur;

    BottomNavigationView navigation;

    ClientsFragment cf = new ClientsFragment();
    ListChatFragment lcf = new ListChatFragment();
    ThreadFragment tf = new ThreadFragment();
    EventFragment ef = new EventFragment();
    Bundle b = new Bundle();
    FragmentTransaction ft;

    boolean doubleTapToExit = false;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_users:
                    ft = getSupportFragmentManager().beginTransaction();
                    hideFragments();
                    hideSpecificFragments();
                    if(getSupportFragmentManager().findFragmentByTag("PROFILE") != null){
                        if(getSupportFragmentManager().findFragmentByTag("FOLLOW") != null){
                            ft.show(getSupportFragmentManager().findFragmentByTag("FOLLOW"));
                        }
                        else{
                            ft.show(getSupportFragmentManager().findFragmentByTag("PROFILE"));
                        }
                    }
                    else{
                        ft.show(cf);
                    }
                    ft.commit();
                    return true;
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
        navigation.getMenu().getItem(2).setChecked(true);

        ft = getSupportFragmentManager().beginTransaction();

        realm = Realm.getDefaultInstance();
        ur = realm.where(UserRealm.class).findFirst();

        ft = getSupportFragmentManager().beginTransaction();
        addFragments();
        hideFragments();
        ft.show(lcf);
        ft.commit();
        ButterKnife.bind(this);

        toolbar.inflateMenu(R.menu.overflow);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.settings:
                        intent = new Intent(CoachMainActivity.this, SettingsActivity.class);
                        intent.putExtra("isCoach",true);
                        performTransition(CoachMainActivity.this,intent, R.animator.slide_from_right, R.animator.slide_to_left);
                        return true;
                    case R.id.disconnect:
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
                        return true;
                    default:
                        System.out.println("");
                        return true;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().findFragmentByTag("POSTS") != null && navigation.getMenu().getItem(3).isChecked()){
            ft = getSupportFragmentManager().beginTransaction();
            ft.remove(getSupportFragmentManager().findFragmentByTag("POSTS"));
            tf.getLt().clear();
            tf.prepareData();
            ft.show(getSupportFragmentManager().findFragmentByTag("TF"));
            ft.commit();
        }
        else if(getSupportFragmentManager().findFragmentByTag("MESSAGES") != null && navigation.getMenu().getItem(2).isChecked()){
            ft = getSupportFragmentManager().beginTransaction();
            ft.remove(getSupportFragmentManager().findFragmentByTag("MESSAGES"));
            ft.show(getSupportFragmentManager().findFragmentByTag("LCF"));
            ft.commit();
        }
        else if(getSupportFragmentManager().findFragmentByTag("PROFILE") != null && navigation.getMenu().getItem(0).isChecked()){
            if(getSupportFragmentManager().findFragmentByTag("FOLLOW") != null && navigation.getMenu().getItem(0).isChecked()){
                ft = getSupportFragmentManager().beginTransaction();
                ft.remove(getSupportFragmentManager().findFragmentByTag("FOLLOW"));
                ft.show(getSupportFragmentManager().findFragmentByTag("PROFILE"));
                ft.commit();
            }
            else{
                ft = getSupportFragmentManager().beginTransaction();
                ft.remove(getSupportFragmentManager().findFragmentByTag("PROFILE"));
                ft.show(getSupportFragmentManager().findFragmentByTag("CPF"));
                ft.commit();
            }
        }
        else{
            if(doubleTapToExit) {
                finishAffinity();
                lcf.getWs().close(1000,null);
            }
            this.doubleTapToExit = true;
            Toast.makeText(this, "Veuillez appuyer une seconde fois pour quitter.", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleTapToExit=false;
                }
            }, 2000);
        }
    }

    public void addFragments() {
        ft.add(R.id.container, cf, "CPF");
        ft.add(R.id.container, ef);
        ft.add(R.id.container, lcf,"LCF");
        ft.add(R.id.container, tf,"TF");
    }

    public void hideFragments() {
        ListChatFragment.isActive = false;
        ChatFragment.isActive = false;
        ft.hide(cf);
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
        if(getSupportFragmentManager().findFragmentByTag("PROFILE") != null){
            ft.hide(getSupportFragmentManager().findFragmentByTag("PROFILE"));
        }
        if(getSupportFragmentManager().findFragmentByTag("FOLLOW") != null){
            ft.hide(getSupportFragmentManager().findFragmentByTag("FOLLOW"));
        }
    }
}
