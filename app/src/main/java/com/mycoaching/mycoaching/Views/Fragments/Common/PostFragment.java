package com.mycoaching.mycoaching.Views.Fragments.Common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.Models.Retrofit.Post;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Adapters.PostAdapter;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.checkFields;
import static com.mycoaching.mycoaching.Util.CommonMethods.getCorrespondingErrorMessage;
import static com.mycoaching.mycoaching.Util.CommonMethods.getDate;
import static com.mycoaching.mycoaching.Util.CommonMethods.isNetworkAvailable;
import static com.mycoaching.mycoaching.Util.CommonMethods.isTokenExpired;
import static com.mycoaching.mycoaching.Util.CommonMethods.refreshToken;

/**
 * Created by kevin on 02/06/2018.
 */
public class PostFragment extends Fragment {

    private Bundle bundle;
    private List<Post> lp = new ArrayList<>();
    private PostAdapter pa;
    private UserRealm ur;
    RecyclerView rv;
    ProgressDialog pd;
    View v;
    Realm r;

    @BindView(R.id.inputPost)
    EditText et;

    @OnClick(R.id.sendPost)
    void sendMessage() {
        if(isNetworkAvailable(getContext())) {
            if (checkFields(et.getText().toString())) {
                pd = new ProgressDialog(getContext(), R.style.StyledDialog);
                pd.setCancelable(false);
                pd.setMessage("Envoi du post en cours...");
                pd.show();
                if (isTokenExpired(ur.getToken())) {
                    refreshToken(ur.getToken(), getContext());
                }
                ApiCall.sendPost("Bearer " + ur.getToken(), String.valueOf(bundle.getInt("idThread")), getDate(), et.getText().toString(), ur.getId(), new ServiceResultListener() {
                    @Override
                    public void onResult(ApiResults ar) {
                        pd.dismiss();
                        if (ar.getResponseCode() == 201) {
                            lp.clear();
                            prepareData();
                            et.getText().clear();
                        } else {
                            Toast.makeText(getContext(), getCorrespondingErrorMessage(ar.getErrorMessage()),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
        else {
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_list_post, container, false);
        ButterKnife.bind(this, v);
        bundle = this.getArguments();

        rv = new RecyclerView(getContext());

        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();

        rv = v.findViewById(R.id.container_post);
        pa = new PostAdapter(lp);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rv.setAdapter(pa);

        prepareData();

        return v;
    }

    private void prepareData() {
        pd = new ProgressDialog(getContext(), R.style.StyledDialog);
        pd.setCancelable(false);
        pd.setMessage("Récupération des posts en cours...");
        pd.show();
        if (isNetworkAvailable(getContext())) {
            if (isTokenExpired(ur.getToken())) {
                refreshToken(ur.getToken(), getContext());
            }
            ApiCall.getPosts("Bearer " + ur.getToken(), bundle.getInt("idThread"), new ServiceResultListener() {
                @Override
                public void onResult(ApiResults ar) {
                    if (ar.getResponseCode() == 200) {
                        lp.addAll(ar.getListPost());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pa.notifyDataSetChanged();
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), getCorrespondingErrorMessage(ar.getErrorMessage()),
                                Toast.LENGTH_LONG).show();
                    }
                    pd.dismiss();
                }
            });
        }
        else {
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
            pd.dismiss();
        }
    }
}
