package com.mycoaching.mycoaching.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mycoaching.mycoaching.Fragments.Register.RegisterChoiceFragment;
import com.mycoaching.mycoaching.R;

import io.realm.Realm;

public class RegisterActivity extends AppCompatActivity {

    FragmentTransaction ft;
    RegisterChoiceFragment cf = new RegisterChoiceFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,cf);
        ft.commit();
    }

    public void replaceFragment(Fragment f, int layout){
        ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.animator.slide_from_right,R.animator.slide_to_left);
        ft.replace(layout, f);
        ft.commit();
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this,R.style.AlertDialogCustom);
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
