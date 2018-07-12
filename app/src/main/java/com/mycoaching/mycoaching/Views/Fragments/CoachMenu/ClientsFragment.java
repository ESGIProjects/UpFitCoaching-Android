package com.mycoaching.mycoaching.Views.Fragments.CoachMenu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.Message;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Adapters.ClientsAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.realm.Realm;

/**
 * Created by kevin on 24/06/2018.
 */
public class ClientsFragment extends Fragment implements ClientsAdapter.OnClick{

    Realm r;
    UserRealm ur,u;
    View v;
    RecyclerView rv;
    ClientsAdapter ca;
    List<UserRealm> lu = new ArrayList<>();
    private List<Integer> ids = new ArrayList<>();
    FragmentManager fm;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_list_clients, container, false);

        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();

        ca = new ClientsAdapter(lu);

        rv = v.findViewById(R.id.listClients);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setAdapter(ca);

        getUsers();
        ca.setOnClick(this);

        return v;
    }

    public void getUsers(){
        pd = new ProgressDialog(getActivity(), R.style.StyledDialog);
        pd.setMessage("Récupération des informations...");
        pd.setCancelable(false);
        pd.show();
        ApiCall.getConversation("Bearer " + ur.getToken(), Integer.valueOf(ur.getId()), new ServiceResultListener() {
            @Override
            public void onResult(ApiResults ar) {
                ids.add(Integer.valueOf(ur.getId()));
                for (Message m : ar.getListMessage()) {
                    if (!ids.contains(Integer.valueOf(m.getSender().getId()))) {
                        u = new UserRealm(m.getSender().getId(),m.getSender().getMail(),
                                m.getSender().getFirstName(), m.getSender().getLastName(),
                                m.getSender().getSex(),m.getSender().getBirthDate(),
                                m.getSender().getCity(),m.getSender().getPhoneNumber(),null);
                        ids.add(Integer.valueOf(m.getSender().getId()));
                        lu.add(u);
                    } else if (!ids.contains(Integer.valueOf(m.getReceiver().getId()))) {
                        u = new UserRealm(m.getReceiver().getId(),m.getReceiver().getMail(),
                                m.getReceiver().getFirstName(), m.getReceiver().getLastName(),
                                m.getReceiver().getSex(),m.getReceiver().getBirthDate(),
                                m.getReceiver().getCity(),m.getReceiver().getPhoneNumber(),null);
                        ids.add(Integer.valueOf(m.getReceiver().getId()));
                        lu.add(u);
                    }
                }
            }
        });
        Collections.sort(lu, new Comparator<UserRealm>() {
            @Override
            public int compare(UserRealm c1, UserRealm c2) {
                int res = c1.getLastName().compareToIgnoreCase(c2.getLastName());
                if (res != 0){
                    return res;
                }
                return c1.getFirstName().compareToIgnoreCase(c2.getFirstName());
            }
        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ca.notifyDataSetChanged();
            }
        });
        pd.dismiss();
    }

    @Override
    public void onItemClick(int position) {
        fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle b = new Bundle();
        b.putString("id",lu.get(position).getId());
        b.putString("mail",lu.get(position).getMail());
        b.putString("firstName",lu.get(position).getFirstName());
        b.putString("lastName",lu.get(position).getLastName());
        b.putString("sex",lu.get(position).getSex());
        b.putString("birthDate",lu.get(position).getBirthDate());
        b.putString("city",lu.get(position).getCity());
        b.putString("phoneNumber",lu.get(position).getPhoneNumber());
        ClientProfileFragment cpf = new ClientProfileFragment();
        cpf.setArguments(b);
        ft.hide(getFragmentManager().findFragmentByTag("CPF"));
        ft.add(R.id.container, cpf,"PROFILE");
        ft.commit();
    }
}
