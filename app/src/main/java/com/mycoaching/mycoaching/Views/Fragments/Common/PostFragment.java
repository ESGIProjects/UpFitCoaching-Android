package com.mycoaching.mycoaching.Views.Fragments.Common;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Post;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Adapters.PostAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.getDate;

/**
 * Created by kevin on 02/06/2018.
 */
public class PostFragment extends Fragment {

    View v;
    Bundle bundle;
    List<Post> lp = new ArrayList<>();
    PostAdapter pa;
    RecyclerView rv;
    ProgressDialog pd;

    Realm r;
    UserRealm ur;

    @BindView(R.id.inputPost)
    EditText et;

    @OnClick(R.id.sendPost)
    void sendMessage() {
        if (!et.getText().toString().isEmpty()) {
            ApiCall.sendPost(String.valueOf(bundle.getInt("idThread")), getDate(), et.getText().toString(), ur.getId(), new ServiceResultListener() {
                @Override
                public void onResult(ApiResults ar) {
                    if (ar.getResponseCode() == 201) {
                        lp.clear();
                        prepareData();
                        et.getText().clear();
                    }
                }
            });
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

        pd = new ProgressDialog(getActivity(), R.style.StyledDialog);
        pd.setMessage("Récupération des posts...");
        pd.show();
        prepareData();
        pd.dismiss();
        return v;
    }

    private void prepareData() {
        ApiCall.getPosts(bundle.getInt("idThread"), new ServiceResultListener() {
            @Override
            public void onResult(ApiResults ar) {
                lp.addAll(ar.getListPost());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pa.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
