package com.mycoaching.mycoaching.Views.Fragments.UserMenu;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Event;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Util.EventDecorator;
import com.mycoaching.mycoaching.Views.Adapters.EventAdapter;
import com.mycoaching.mycoaching.Views.Dialogs.AddEvent;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by kevin on 28/04/2018.
 */

public class EventFragment extends Fragment {

    private View v;
    private List<Event> listEvents = new ArrayList<>();
    private List<CalendarDay> listDaysSession = new ArrayList<>();
    private List<CalendarDay> listDaysAppointment = new ArrayList<>();
    private List<CalendarDay> listDaysCombined = new ArrayList<>();
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView rv;
    private Realm r;
    private UserRealm ur;
    private EventAdapter ea;
    private boolean isCoach = false;
    private Bundle b;

    @BindView(R.id.calendar)
    MaterialCalendarView mcv;

    @BindView(R.id.no_data)
    TextView label;

    @OnClick(R.id.buttonEvents)
    void action() {
        final AddEvent ae = new AddEvent(getActivity(),isCoach);
        ae.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ae.show();
        ae.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(ae.getIsOK()){
                    listEvents.clear();
                    listDaysAppointment.clear();
                    listDaysSession.clear();
                    listDaysCombined.clear();
                    prepareData();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_event, container, false);
        ButterKnife.bind(this, v);

        b = getArguments();
        isCoach = b.getBoolean("isCoach");

        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();

        rv = new RecyclerView(getContext());

        rv = v.findViewById(R.id.listEvents);
        ea = new EventAdapter(listEvents,getContext(),isCoach,ur.getId());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rv.setAdapter(ea);

        prepareData();

        return v;
    }

    private void prepareData() {
        ApiCall.getEvents(Integer.valueOf(ur.getId()), new ServiceResultListener() {
            @Override
            public void onResult(ApiResults ar) {
                if(ar.getResponseCode() == 200){
                    if(ar.getListEvent().size() == 0){
                        label.setVisibility(View.VISIBLE);
                        rv.setVisibility(View.GONE);
                    }
                    else{
                        label.setVisibility(View.GONE);
                        rv.setVisibility(View.VISIBLE);
                    }
                    listEvents.addAll(ar.getListEvent());
                    sortElements();
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
            }
        });
    }

    private void sortElements(){
        for(Event e : listEvents){
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

    private void addCombinedElements(){
        for(CalendarDay cd : listDaysAppointment){
            if(listDaysSession.contains(cd)){
                listDaysCombined.add(cd);
            }
        }
    }

    private void removeDuplicateElements(){
        for(CalendarDay cd : listDaysCombined){
            listDaysSession.remove(cd);
            listDaysAppointment.remove(cd);
        }
    }
}
