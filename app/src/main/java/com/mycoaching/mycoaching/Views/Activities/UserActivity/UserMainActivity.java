package com.mycoaching.mycoaching.Views.Activities.UserActivity;

import android.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Activities.Common.LoginActivity;
import com.mycoaching.mycoaching.Views.Activities.Common.SettingsActivity;
import com.mycoaching.mycoaching.Views.Fragments.Common.ChatFragment;
import com.mycoaching.mycoaching.Views.Fragments.Common.EventFragment;
import com.mycoaching.mycoaching.Views.Fragments.Common.ThreadFragment;
import com.mycoaching.mycoaching.Views.Fragments.UserMenu.FollowUpFragment;
import com.mycoaching.mycoaching.Views.Fragments.UserMenu.TipsFragment;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.performTransition;

/**
 * Created by kevin on 07/03/2018.
 */

public class UserMainActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Intent intent;
    BottomNavigationView navigation;

    FollowUpFragment fuf = new FollowUpFragment();
    EventFragment ef = new EventFragment();
    TipsFragment tif = new TipsFragment();
    ChatFragment chf = new ChatFragment();
    ThreadFragment tf = new ThreadFragment();
    FragmentTransaction ft;
    Bundle b = new Bundle();

    boolean doubleTapToExit = false;

    Realm realm = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_followUp:
                    ft = getSupportFragmentManager().beginTransaction();
                    hideFragments();
                    hideTF();
                    ft.show(fuf);
                    ft.commit();
                    return true;
                case R.id.navigation_tips:
                    ft = getSupportFragmentManager().beginTransaction();
                    hideFragments();
                    hideTF();
                    ft.show(tif);
                    ft.commit();
                    return true;
                case R.id.navigation_calendar:
                    ft = getSupportFragmentManager().beginTransaction();
                    hideFragments();
                    hideTF();
                    ft.show(ef);
                    ft.commit();
                    return true;
                case R.id.navigation_chat:
                    ft = getSupportFragmentManager().beginTransaction();
                    hideFragments();
                    ChatFragment.isActive = true;
                    hideTF();
                    ft.show(chf);
                    ft.commit();
                    return true;
                case R.id.navigation_forum:
                    ft = getSupportFragmentManager().beginTransaction();
                    hideFragments();
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
        setContentView(R.layout.activity_user_main);

        b.putBoolean("isCoach",false);
        ef.setArguments(b);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);
        ft = getSupportFragmentManager().beginTransaction();
        addFragments();
        hideFragments();
        ft.show(ef);
        ft.commit();
        realm = Realm.getDefaultInstance();
        ButterKnife.bind(this);

        toolbar.inflateMenu(R.menu.overflow);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.settings:
                        intent = new Intent(UserMainActivity.this, SettingsActivity.class);
                        intent.putExtra("isCoach",false);
                        performTransition(UserMainActivity.this,intent, R.animator.slide_from_right, R.animator.slide_to_left);
                        return true;
                    case R.id.disconnect:
                        AlertDialog.Builder builder = new AlertDialog.Builder(UserMainActivity.this, R.style.AlertDialogCustom);
                        builder.setTitle(R.string.exit).setMessage(R.string.exit_application)
                                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        realm.beginTransaction();
                                        realm.deleteAll();
                                        realm.commitTransaction();
                                        realm.close();
                                        chf.getWs().close(1000,null);
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
                                        performTransition(UserMainActivity.this,intent, R.animator.slide_from_left, R.animator.slide_to_right);
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
        if(getSupportFragmentManager().findFragmentByTag("POSTS") != null && navigation.getMenu().getItem(4).isChecked()){
            if(getSupportFragmentManager().findFragmentByTag("TF").isHidden()){
                ft = getSupportFragmentManager().beginTransaction();
                ft.remove(getSupportFragmentManager().findFragmentByTag("POSTS"));
                tf.getLt().clear();
                tf.prepareData();
                ft.show(getSupportFragmentManager().findFragmentByTag("TF"));
                ft.commit();
            }
        }
        else {
            if(doubleTapToExit) {
                finishAffinity();
                chf.getWs().close(1000,null);
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
        ft.add(R.id.container, fuf);
        ft.add(R.id.container, ef);
        ft.add(R.id.container, tif);
        ft.add(R.id.container, chf);
        ft.add(R.id.container, tf,"TF");
    }

    public void hideFragments() {
        ChatFragment.isActive = false;
        ft.hide(fuf);
        ft.hide(ef);
        ft.hide(tif);
        ft.hide(chf);
        ft.hide(tf);
    }

    public void hideTF(){
        if(getSupportFragmentManager().findFragmentByTag("POSTS") != null){
            ft.hide(getSupportFragmentManager().findFragmentByTag("POSTS"));
        }
    }
}
