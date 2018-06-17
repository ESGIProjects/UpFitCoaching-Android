package com.mycoaching.mycoaching.Views.Dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.checkFields;

/**
 * Created by kevin on 16/06/2018.
 */
public class AddEvent extends Dialog{

    Realm r;
    UserRealm ur;

    boolean isCoach = false;

    Activity activity;
    ProgressDialog pd;
    int type;

    boolean isOK = false;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @BindView(R.id.event_title)
    EditText eventTitle;

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
    public void addStartDate(final Button b){
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
    void addStartTime(final Button b){
        TimePickerDialog dialog = new TimePickerDialog(getContext(), R.style.customPicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                b.setText(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + ":00");
            }
        },Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE),true);
        dialog.show();
    }

    @OnClick(R.id.confirm_event)
    void createNews() {
        pd = new ProgressDialog(getContext(), R.style.AppCompatAlertDialogStyle);
        pd.setMessage("Création de l'évènement en cours...");
        pd.show();
        isOK = true;
        if(checkFields(eventTitle.getText().toString(), event_start_date.getText().toString()
                ,event_end_date.getText().toString(),event_start_time.getText().toString()
                , event_end_time.getText().toString(),typeSpinner.getSelectedItem().toString())) {
            if(typeSpinner.getSelectedItem().equals("Bilan")){
                type = 0;
            }
            else{
                type = 1;
            }
            try{
                if(formatter2.parse(event_start_date.getText().toString() + " " + event_start_time.getText().toString())
                        .compareTo(formatter2.parse(event_end_date.getText().toString() + " " + event_end_time.getText().toString())) > 0){
                    Toast.makeText(getContext(),"La date de fin est antérieure à la date de départ",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
                else{
                    ApiCall.addEvent(eventTitle.getText().toString(), String.valueOf(type)
                            , ur.getId(), "15", event_start_date.getText().toString() + " " +
                                    event_start_time.getText().toString(), event_end_date.getText().toString()
                                    + " " + event_end_time.getText().toString(), event_start_date.getText().toString()
                                    + " " + event_start_time.getText().toString(), ur.getId(), new ServiceResultListener() {
                                @Override
                                public void onResult(ApiResults ar) {
                                    if (ar.getResponseCode() == 201) {
                                        pd.dismiss();
                                        isOK = true;
                                        dismiss();
                                    }
                                    else{
                                        pd.dismiss();
                                        System.out.println(ar.getResponseCode());
                                    }
                                }
                            });
                }
            }
            catch(ParseException pe){
                pe.printStackTrace();
            }
        }
        else{
            pd.dismiss();
            Toast.makeText(getContext(),"Certains champs sont manquants !", Toast.LENGTH_LONG).show();
        }
    }

    public AddEvent(Activity a, boolean isCoach) {
        super(a);
        this.activity = a;
        this.isCoach = isCoach;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_event);
        ButterKnife.bind(this);
    }

    public boolean getIsOK(){
        return isOK;
    }

}
