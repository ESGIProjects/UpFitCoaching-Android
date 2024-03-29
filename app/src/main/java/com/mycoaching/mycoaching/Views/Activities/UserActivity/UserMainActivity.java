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
import com.mycoaching.mycoaching.Views.Activities.Common.SplashScreenActivity;
import com.mycoaching.mycoaching.Views.Fragments.Common.ChatFragment;
import com.mycoaching.mycoaching.Views.Fragments.Common.EventFragment;
import com.mycoaching.mycoaching.Views.Fragments.Common.FollowUpFragment;
import com.mycoaching.mycoaching.Views.Fragments.Common.PrescriptionFragment;
import com.mycoaching.mycoaching.Views.Fragments.Common.ThreadFragment;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.performTransition;

/**
 * Created by kevin on 07/03/2018.
 * Version 1.0
 */

public class UserMainActivity extends AppCompatActivity {

    private Intent intent;
    private BottomNavigationView navigation;
    private FollowUpFragment fuf = new FollowUpFragment();
    private PrescriptionFragment pf = new PrescriptionFragment();
    private EventFragment ef = new EventFragment();
    private ChatFragment chf = new ChatFragment();
    private ThreadFragment tf = new ThreadFragment();
    private FragmentTransaction ft;
    private Bundle b = new Bundle();
    private boolean doubleTapToExit = false;
    private Realm realm = null;

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
                case R.id.navigation_prescription:
                    ft = getSupportFragmentManager().beginTransaction();
                    hideFragments();
                    hideTF();
                    ft.show(pf);
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

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);
        ButterKnife.bind(this);

        if(savedInstanceState != null){
            Intent i = new Intent(this, SplashScreenActivity.class);
            performTransition(this,i, R.animator.slide_from_left, R.animator.slide_to_right);
            finish();
            return;
        }

        // we put a boolean which indicates to EventFragment that the current user is a Coach
        b.putBoolean("isCoach",false);
        ef.setArguments(b);

        // by default, the first item to display in the navigation bar is the ChatFragment
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(2).setChecked(true);

        ft = getSupportFragmentManager().beginTransaction();
        // we add all fragments to the activity
        addFragments();
        // then we hide all fragments in order to display only the EventFragment at the creation of the view
        hideFragments();
        ft.show(ef);
        ft.commit();
        realm = Realm.getDefaultInstance();

        /*
         * The following code is used to display the overflow three dots button in the Toolbar.
         * This overflow button gives access to deconnection feature et settings.
         * If the user choose the decconnection feature, all data of the application will be erased.
         */
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
                        return true;
                }
            }
        });
    }
    /**
     * This method manages the behaviour of the back button.
     * If the user is on a nested fragment, it will display the previous fragment in the stack.
     * For example, if the user is on PostFragment, it will display ThreadFragment instead.
     * If there is no nested fragment on the screen, the user have to tap the back button twice during the delay
     * in order to quit the application.
     */
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
            Toast.makeText(this, getResources().getString(R.string.tap_twice), Toast.LENGTH_SHORT).show();
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
        ft.add(R.id.container, pf);
        ft.add(R.id.container, ef);
        ft.add(R.id.container, chf);
        ft.add(R.id.container, tf,"TF");
    }

    public void hideFragments() {
        /*
          The isActive variable is used by MyFirebaseMessagingService in order to display push notifications
          only if ChatFragment is not displayed
          */
        ChatFragment.isActive = false;
        ft.hide(fuf);
        ft.hide(ef);
        ft.hide(chf);
        ft.hide(tf);
        ft.hide(pf);
    }

    // This method is used in order to hide PostFragment if it's displayed
    public void hideTF(){
        if(getSupportFragmentManager().findFragmentByTag("POSTS") != null){
            ft.hide(getSupportFragmentManager().findFragmentByTag("POSTS"));
        }
    }
}
