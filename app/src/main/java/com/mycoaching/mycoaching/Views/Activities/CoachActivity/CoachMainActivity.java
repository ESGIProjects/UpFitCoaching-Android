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
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.Util.Singletons.OkHttpSingleton;
import com.mycoaching.mycoaching.Views.Activities.Common.LoginActivity;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Fragments.CoachMenu.ListChatFragment;

import io.realm.Realm;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class CoachMainActivity extends AppCompatActivity {

    Realm realm = null;
    Intent intent;
    UserRealm ur;

    ListChatFragment lcf = new ListChatFragment();
    FragmentTransaction ft;
    WebSocket ws;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_calendar:
                    return true;
                case R.id.navigation_chat:
                    ft.add(R.id.container,lcf,"LIST");
                    ft.show(lcf);
                    ft.commit();
                    return true;
                case R.id.navigation_forum:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ft = getSupportFragmentManager().beginTransaction();

        realm = Realm.getDefaultInstance();
        ur = realm.where(UserRealm.class).findFirst();

    }

    @Override
    public void onBackPressed(){
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if(count > 0 && getSupportFragmentManager().findFragmentByTag("MESSAGES").isVisible()){
            getSupportFragmentManager().popBackStack();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(CoachMainActivity.this,R.style.AlertDialogCustom);
            builder.setTitle(R.string.exit).setMessage(R.string.exit_application)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            intent = new Intent(getApplicationContext(), LoginActivity.class);
                            realm.beginTransaction();
                            realm.deleteAll();
                            realm.commitTransaction();
                            realm.close();
                            performTransition(intent,R.animator.slide_from_left,R.animator.slide_to_right);
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

    public void performTransition(Intent i, int from, int to){
        startActivity(i);
        overridePendingTransition(from,to);
    }
}
