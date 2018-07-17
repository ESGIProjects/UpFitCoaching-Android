package com.mycoaching.mycoaching.Views.Fragments.Common;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.Models.Retrofit.Exercise;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Adapters.ExerciseAdapter;
import com.mycoaching.mycoaching.Views.Dialogs.AddExercise;
import com.mycoaching.mycoaching.Views.Dialogs.EditExercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.getCorrespondingErrorMessage;
import static com.mycoaching.mycoaching.Util.CommonMethods.getDate;
import static com.mycoaching.mycoaching.Util.CommonMethods.isNetworkAvailable;
import static com.mycoaching.mycoaching.Util.CommonMethods.isTokenExpired;
import static com.mycoaching.mycoaching.Util.CommonMethods.refreshToken;

/**
 * Created by kevin on 07/07/2018.
 */
public class PrescriptionFragment extends Fragment implements ExerciseAdapter.OnClick, SwipeRefreshLayout.OnRefreshListener{
    Bundle b;
    String id;
    Realm r;
    UserRealm ur;
    View v;
    ProgressDialog pd;
    List<Exercise> le = new ArrayList<>();
    private RecyclerView rv;
    ExerciseAdapter ea;

    @BindView(R.id.empty)
    TextView empty;

    @BindView(R.id.buttonPrescription)
    FloatingActionButton prescription;

    @BindView(R.id.swipe)
    SwipeRefreshLayout srl;

    @OnClick(R.id.buttonPrescription)
    public void prescription(){
        List<String> allExercises = new LinkedList<>(Arrays.asList(getResources().getStringArray(R.array.exercice)));
        for(Exercise e : le){
            if(allExercises.contains(e.getExercise())){
                allExercises.remove(e.getExercise());
            }
        }
        if(allExercises.isEmpty()){
            Toast.makeText(getContext(),"Vous ne pouvez plus ajouter d'exercice !",Toast.LENGTH_LONG).show();
        }
        else{
            final AddExercise ae = new AddExercise(getActivity(),allExercises);
            assert ae.getWindow() != null;
            ae.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ae.show();
            ae.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if(ae.getIsOK()){
                        le.add(ae.getExercise());
                        if(le.size() == 1){
                            addPrescription.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);
                        }
                        ea.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @BindView(R.id.addPrescription)
    Button addPrescription;

    @OnClick(R.id.addPrescription)
    public void add(){
        pd = new ProgressDialog(getContext(), R.style.StyledDialog);
        pd.setMessage("Création de la prescription en cours...");
        pd.setCancelable(false);
        pd.show();
        if(isNetworkAvailable(getContext())){
            if(isTokenExpired(ur.getToken())){
                refreshToken(ur.getToken(),getContext());
            }
            ApiCall.postPrescription("Bearer " + ur.getToken(), id, getDate(), new Gson().toJson(le), new ServiceResultListener() {
                @Override
                public void onResult(ApiResults ar) {
                    if(ar.getResponseCode() == 201){
                        Toast.makeText(getContext(),"La prescription est enregistrée !", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getContext(),getCorrespondingErrorMessage(ar.getErrorMessage()),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(getContext(),R.string.no_connection,Toast.LENGTH_LONG).show();
        }
        pd.dismiss();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_prescription, container, false);
        b = this.getArguments();
        ButterKnife.bind(this, v);
        srl.setOnRefreshListener(this);

        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();

        if(b != null){
            id = b.getString("id");
        }
        else{
            id = ur.getId();
            addPrescription.setVisibility(View.GONE);
            prescription.setVisibility(View.GONE);
        }

        rv = new RecyclerView(getContext());

        rv = v.findViewById(R.id.listExercices);
        ea = new ExerciseAdapter(le,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rv.setAdapter(ea);

        ea.setOnClick(this);

        getPrescription();

        return v;
    }

    public void getPrescription(){
        pd = new ProgressDialog(getActivity(), R.style.StyledDialog);
        pd.setMessage("Récupération des informations...");
        pd.setCancelable(false);
        pd.show();
        if(isNetworkAvailable(getContext())){
            if(isTokenExpired(ur.getToken())){
                refreshToken(ur.getToken(),getContext());
            }
            ApiCall.getPrescription("Bearer " + ur.getToken(),Integer.valueOf(id), new ServiceResultListener() {
                @Override
                public void onResult(ApiResults ar) {
                    if(ar.getResponseCode() == 200){
                        if(!ar.getListPrescription().isEmpty()){
                            le.addAll(ar.getListPrescription().get(ar.getListPrescription().size()-1).getExercises());
                            ea.notifyDataSetChanged();
                        }
                        else{
                            addPrescription.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                        }
                    }
                    else{
                        Toast.makeText(getContext(),getCorrespondingErrorMessage(ar.getErrorMessage()),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else{
            Toast.makeText(getContext(),R.string.no_connection,Toast.LENGTH_LONG).show();
        }
        if (srl.isRefreshing()) {
            srl.setRefreshing(false);
        }
        pd.dismiss();
    }

    @Override
    public void onItemClick(final int position) {
        if(b != null){
            final Exercise e = le.get(position);
            final EditExercise ee = new EditExercise(getActivity(),e);
            ee.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ee.show();
            ee.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if(ee.getIsOK()){
                        if(ee.getExercise() != null){
                            le.set(position, ee.getExercise());
                            ea.notifyDataSetChanged();
                        }
                        else{
                            le.remove(position);
                            rv.setAdapter(ea);
                            ea.notifyItemRemoved(position);
                            if(le.isEmpty()){
                                addPrescription.setVisibility(View.GONE);
                                empty.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onRefresh() {
        if(isNetworkAvailable(getContext())){
            le.clear();
        }
        getPrescription();
    }
}
