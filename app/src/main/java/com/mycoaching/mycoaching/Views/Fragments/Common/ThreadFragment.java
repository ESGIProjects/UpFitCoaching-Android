package com.mycoaching.mycoaching.Views.Fragments.Common;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.Models.Retrofit.Thread;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Adapters.ThreadAdapter;
import com.mycoaching.mycoaching.Views.Dialogs.AddThread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.getCorrespondingErrorMessage;
import static com.mycoaching.mycoaching.Util.CommonMethods.isNetworkAvailable;
import static com.mycoaching.mycoaching.Util.CommonMethods.isTokenExpired;
import static com.mycoaching.mycoaching.Util.CommonMethods.refreshToken;
import static com.mycoaching.mycoaching.Util.Constants.DATE_TIME_FORMATTER;

/**
 * Created by kevin on 28/04/2018.
 * Version 1.0
 */

public class ThreadFragment extends Fragment implements ThreadAdapter.OnClick, SwipeRefreshLayout.OnRefreshListener {

    private List<Thread> lt = new ArrayList<>();
    private SimpleDateFormat formatterDate = new SimpleDateFormat(DATE_TIME_FORMATTER, Locale.getDefault());
    private ThreadAdapter ta;
    private PostFragment pf;
    private ProgressDialog pd;
    private UserRealm ur;
    protected View v;
    protected RecyclerView rv;
    protected FragmentManager fm;
    protected Realm r;

    @BindView(R.id.swipe)
    SwipeRefreshLayout srl;

    @OnClick(R.id.buttonThread) void openDialog(){
        if(isNetworkAvailable(getContext())){
            final AddThread at = new AddThread(getActivity());
            at.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            at.show();
            at.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if(at.getIsOK()){
                        lt.clear();
                        prepareData();
                    }
                }
            });
        }
        else{
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_list_thread, container, false);
        ButterKnife.bind(this, v);

        srl.setOnRefreshListener(this);

        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();

        rv = new RecyclerView(getContext());
        pf = null;

        rv = v.findViewById(R.id.container_thread);
        ta = new ThreadAdapter(lt,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rv.setAdapter(ta);

        ta.setOnClick(this);

        prepareData();
        return v;
    }

    public void prepareData(){
        pd = new ProgressDialog(getContext(), R.style.StyledDialog);
        pd.setCancelable(false);
        pd.setMessage("Récupération des sujets en cours...");
        pd.show();
        if(isNetworkAvailable(getContext())) {
            if (isTokenExpired(ur.getToken())) {
                refreshToken(ur.getToken(), getContext());
            }
            ApiCall.getThreads("Bearer " + ur.getToken(), 1, new ServiceResultListener() {
                @Override
                public void onResult(ApiResults ar) {
                    if (ar.getResponseCode() == 200) {
                        Collections.sort(ar.getListThread(), new Comparator<Thread>() {
                            @Override
                            public int compare(Thread t1, Thread t2) {
                                int comp = 0;
                                try {
                                    comp = formatterDate.parse(t1.getLastUpdated()).compareTo(formatterDate.parse(t2.getLastUpdated()));
                                } catch (ParseException pe) {
                                    pe.printStackTrace();
                                }
                                return comp;
                            }
                        });
                        Collections.reverse(ar.getListThread());
                        lt.addAll(ar.getListThread());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ta.notifyDataSetChanged();
                            }
                        });
                    }
                    else {
                        Toast.makeText(getContext(), getCorrespondingErrorMessage(ar.getErrorMessage()),
                                Toast.LENGTH_LONG).show();
                    }
                    pd.dismiss();
                }
            });
        }
        else{
            pd.dismiss();
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }
        if (srl.isRefreshing()) {
            srl.setRefreshing(false);
        }
    }

    public List<Thread> getLt(){
        return lt;
    }

    @Override
    public void onItemClick(int position) {
        if(isNetworkAvailable(getContext())){
            int id = lt.get(position).getId();
            fm = getActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Bundle b = new Bundle();
            b.putInt("idThread", id);
            pf = new PostFragment();
            pf.setArguments(b);
            lt.clear();
            ft.add(R.id.container, pf, "POSTS");
            ft.hide(getActivity().getSupportFragmentManager().findFragmentByTag("TF"));
            ft.show(pf);
            ft.commit();
        }
        else{
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRefresh() {
        if(isNetworkAvailable(getContext())){
            lt.clear();
        }
        prepareData();
    }
}
