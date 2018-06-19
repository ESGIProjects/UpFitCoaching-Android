package com.mycoaching.mycoaching.Views.Dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mycoaching.mycoaching.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kevin on 18/06/2018.
 */
public class EditEvent extends Dialog{

    Activity activity;
    boolean isCoach = false;
    boolean isOK = false;
    String eventID, name, type, start, end, updatedBy;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @BindView(R.id.event_title)
    TextView title;

    @BindView(R.id.spinner)
    Spinner typeSpinner;

    @BindView(R.id.event_start_date)
    Button event_start_date;

    @BindView(R.id.event_end_date)
    Button event_end_date;

    @BindView(R.id.event_start_time)
    Button event_start_time;

    @BindView(R.id.event_end_time)
    Button event_end_time;

    @OnClick({R.id.event_start_date,R.id.event_end_date})
    public void addDate(final Button b){
        DatePickerDialog dialog = new DatePickerDialog(getContext(),R.style.customPicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                Date date = cal.getTime();
                b.setText(formatter.format(date));
            }
        },Calendar.getInstance().get(Calendar.YEAR),Calendar.getInstance().get(Calendar.MONTH)
                ,Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    @OnClick({R.id.event_start_time,R.id.event_end_time})
    void addTime(final Button b){
        TimePickerDialog dialog = new TimePickerDialog(getContext(), R.style.customPicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                b.setText(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + ":00");
            }
        },Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE),true);
        dialog.show();
    }

    @OnClick(R.id.confirm_edit)
    void editEvent(){
        //TODO
    }

    @OnClick(R.id.cancel_event)
    void cancelEvent(){
        //TODO
    }

    public EditEvent(Activity a, boolean isCoach, String eventID, String name, String type, String start
            ,String end, String updatedBy) {
        super(a);
        this.activity = a;
        this.isCoach = isCoach;
        this.eventID = eventID;
        this.name = name;
        this.type = type;
        this.start = start;
        this.end = end;
        this.updatedBy = updatedBy;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_edit_event);

        ButterKnife.bind(this);
    }

    public boolean getIsOK(){
        return isOK;
    }
}
