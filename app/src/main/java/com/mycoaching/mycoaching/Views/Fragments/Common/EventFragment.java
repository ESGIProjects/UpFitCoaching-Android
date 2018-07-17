package com.mycoaching.mycoaching.Views.Fragments.Common;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.Models.Retrofit.Event;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Util.Miscellaneous.EventDecorator;
import com.mycoaching.mycoaching.Views.Adapters.EventAdapter;
import com.mycoaching.mycoaching.Views.Dialogs.AddEvent;
import com.mycoaching.mycoaching.Views.Dialogs.EditEvent;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import static com.mycoaching.mycoaching.Util.Constants.DATE_FORMATTER;

/**
 * Created by kevin on 28/04/2018.
 * Version 1.0
 */

public class EventFragment extends Fragment implements EventAdapter.OnClick, SwipeRefreshLayout.OnRefreshListener{

    private List<Event> listEvents = new ArrayList<>();
    private List<Event> listToDisplay = new ArrayList<>();
    private List<CalendarDay> listDaysSession = new ArrayList<>();
    private List<CalendarDay> listDaysAppointment = new ArrayList<>();
    private List<CalendarDay> listDaysCombined = new ArrayList<>();
    private SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMATTER, Locale.getDefault());
    private RecyclerView rv;
    private UserRealm ur;
    private EventAdapter ea;
    private boolean isCoach = false;
    protected ProgressDialog pd;
    protected View v;
    protected Realm r;
    protected Bundle b;
    protected FragmentManager fm;

    @BindView(R.id.calendar)
    MaterialCalendarView mcv;

    @BindView(R.id.no_data)
    TextView label;

    @BindView(R.id.swipe)
    SwipeRefreshLayout srl;

    @OnClick(R.id.buttonEvents)
    void action() {
        if(isNetworkAvailable(getContext())){
            final AddEvent ae = new AddEvent(getActivity(),isCoach);
            assert ae.getWindow() != null;
            ae.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ae.show();
            ae.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if(ae.getIsOK()){
                        listEvents.clear();
                        listToDisplay.clear();
                        listDaysAppointment.clear();
                        listDaysSession.clear();
                        listDaysCombined.clear();
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
        v = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this, v);

        srl.setOnRefreshListener(this);

        fm = getActivity().getSupportFragmentManager();

        b = getArguments();
        isCoach = b.getBoolean("isCoach");

        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();

        rv = new RecyclerView(getContext());

        rv = v.findViewById(R.id.listEvents);
        ea = new EventAdapter(listToDisplay,getContext(),isCoach,ur.getId());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rv.setAdapter(ea);

        /*
            We set a listener to the calendar in order to display only events available for a
            specific date
         */
        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                listToDisplay.clear();
                for(Event e : listEvents){
                    if(e.getStart().split(" ")[0].equals(formatter.format(date.getDate()))){
                        listToDisplay.add(e);
                    }
                }
                if(listToDisplay.isEmpty()){
                    label.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.GONE);
                }
                else{
                    label.setVisibility(View.GONE);
                    rv.setVisibility(View.VISIBLE);
                }
                ea.notifyDataSetChanged();
            }
        });
        ea.setOnClick(this);
        prepareData();

        return v;
    }

    private void prepareData() {
        pd = new ProgressDialog(getContext(), R.style.StyledDialog);
        pd.setCancelable(false);
        pd.setMessage("Récupération des évènements en cours...");
        pd.show();
        if(isNetworkAvailable(getContext())){
            if(isTokenExpired(ur.getToken())){
                refreshToken(ur.getToken(),getContext());
            }
            ApiCall.getEvents("Bearer " + ur.getToken(),Integer.valueOf(ur.getId()), new ServiceResultListener() {
                @Override
                public void onResult(ApiResults ar) {
                    mcv.setSelectedDate(new Date());
                    if(ar.getResponseCode() == 200){
                        if(ar.getListEvent().size() != 0){
                            listEvents.addAll(ar.getListEvent());
                            for(Event e : listEvents){
                                if(e.getStart().split(" ")[0].equals(formatter.format(mcv.getSelectedDate().getDate()))){
                                    listToDisplay.add(e);
                                }
                            }
                            if(listToDisplay.isEmpty()){
                                label.setVisibility(View.VISIBLE);
                                rv.setVisibility(View.GONE);
                            }
                            else{
                                label.setVisibility(View.GONE);
                                rv.setVisibility(View.VISIBLE);
                            }

                            sortElements(listEvents);
                            addCombinedElements();
                            removeDuplicateElements();

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int colorsAppointment = Color.rgb(0, 0, 255);
                                    int colorsSession = Color.rgb(0, 255, 0);
                                    int[] colorsCombined = {Color.rgb(0, 0, 255) ,Color.rgb(0, 255, 0)};
                                    mcv.removeDecorators();
                                    mcv.addDecorator(new EventDecorator(listDaysAppointment,colorsAppointment));
                                    mcv.addDecorator(new EventDecorator(listDaysSession,colorsSession));
                                    mcv.addDecorator(new EventDecorator(listDaysCombined,colorsCombined));
                                    mcv.setSelectedDate(new Date());
                                    ea.notifyDataSetChanged();
                                }
                            });
                        }
                        else{
                            mcv.removeDecorators();
                            label.setVisibility(View.VISIBLE);
                            rv.setVisibility(View.GONE);
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
    public void onItemClick(int position) {
        if(isNetworkAvailable(getContext())){
            Event e = listToDisplay.get(position);
            final EditEvent ee = new EditEvent(getActivity(),e.getId(),e.getName(),e.getType(),e.getStart()
                    ,e.getEnd(),ur.getId());
            assert ee.getWindow() != null;
            ee.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ee.show();
            ee.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    if(ee.getIsOK()){
                        listEvents.clear();
                        listToDisplay.clear();
                        listDaysAppointment.clear();
                        listDaysSession.clear();
                        listDaysCombined.clear();
                        prepareData();
                    }
                }
            });
        }
        else{
            Toast.makeText(getContext(), R.string.no_connection, Toast.LENGTH_LONG).show();
        }
    }

    private void sortElements(List<Event> le){
        for(Event e : le){
            try{
                if(e.getType().equals("0")){
                    listDaysAppointment.add(CalendarDay.from(formatter.parse(e.getStart())));
                }
                else{
                    listDaysSession.add(CalendarDay.from(formatter.parse(e.getStart())));
                }
            }
            catch (ParseException pe){
                pe.printStackTrace();
            }
        }
    }

    /**
     * If an date is in listDaysSession and in listDaysAppointment, it means that
     * the date contained at least one event of each type, so we add it to listDaysCombined
     */
    private void addCombinedElements(){
        for(CalendarDay cd : listDaysAppointment){
            if(listDaysSession.contains(cd)){
                listDaysCombined.add(cd);
            }
        }
    }

    /**
     * If a date is already presents in listDaysCombined, we have to remove it from listDaysSession
     * and listDaysAppointment. Otherwise, dates will be duplicated
     */
    private void removeDuplicateElements(){
        for(CalendarDay cd : listDaysCombined){
            listDaysSession.remove(cd);
            listDaysAppointment.remove(cd);
        }
    }

    @Override
    public void onRefresh() {
        if(isNetworkAvailable(getContext())){
            listEvents.clear();
            listToDisplay.clear();
            listDaysAppointment.clear();
            listDaysSession.clear();
            listDaysCombined.clear();
        }
        prepareData();
    }
}