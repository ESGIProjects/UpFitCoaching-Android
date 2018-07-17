package com.mycoaching.mycoaching.Views.Activities.Common;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Fragments.Register.CredentialsFragment;

import static com.mycoaching.mycoaching.Util.CommonMethods.performTransition;

/**
 * Created by kevin on 06/03/2018.
 * Version 1.0
 */

public class RegisterActivity extends AppCompatActivity {

    private FragmentTransaction ft;
    private Bundle b = new Bundle();
    private CredentialsFragment cf = new CredentialsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*
          if the application is killed by the system (due to RAM issue for example), we kill the activity
          and we restart from the splashscreen
        */
        if(savedInstanceState != null){
            Intent i = new Intent(this, SplashScreenActivity.class);
            performTransition(this,i, R.animator.slide_from_left, R.animator.slide_to_right);
            finish();
            return;
        }
        getSupportActionBar().hide();
        // we put 0 as type value because signup is just for regular user at the moment
        b.putString("type", "0");
        cf.setArguments(b);
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, cf);
        ft.commit();
    }

    public void replaceFragment(Fragment f, int layout) {
        ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.slide_from_right, R.animator.slide_to_left);
        ft.replace(layout, f);
        ft.commit();
    }

    /**
     * This method manages the behaviour of the back button.
     * If the user choose to exit, it will kill the RegisterActivity and will return to LoginActivity
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this, R.style.AlertDialogCustom);
        builder.setTitle(R.string.exit).setMessage(R.string.exit_register)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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
}