package com.mycoaching.mycoaching.Views.Dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.checkFields;
import static com.mycoaching.mycoaching.Util.CommonMethods.getCorrespondingErrorMessage;
import static com.mycoaching.mycoaching.Util.CommonMethods.getDate;
import static com.mycoaching.mycoaching.Util.CommonMethods.isTokenExpired;
import static com.mycoaching.mycoaching.Util.CommonMethods.refreshToken;
import static com.mycoaching.mycoaching.Util.Constants.DATE_FORMATTER;
import static com.mycoaching.mycoaching.Util.Constants.DATE_TIME_FORMATTER;

/**
 * Created by kevin on 18/06/2018.
 */
public class EditEvent extends Dialog{

    private boolean isOK = false;
    Realm r;
    UserRealm ur;
    private String eventID, name, type, start, end, updatedBy;
    private ProgressDialog pd;
    private SimpleDateFormat formatterDate = new SimpleDateFormat(DATE_FORMATTER, Locale.getDefault());
    private SimpleDateFormat formatterDateTime = new SimpleDateFormat(DATE_TIME_FORMATTER, Locale.getDefault());

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

    @OnClick(R.id.event_start_date)
    public void addStartDate(final Button b){
        DatePickerDialog dialog = new DatePickerDialog(getContext(),R.style.customPicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                Date date = cal.getTime();
                b.setText(formatterDate.format(date));
            }
        },Integer.valueOf(start.split(" ")[0].split("-")[0]),Integer.valueOf(start.split(" ")[0].split("-")[1])-1
                ,Integer.valueOf(start.split(" ")[0].split("-")[2]));
        dialog.show();
    }

    @OnClick(R.id.event_end_date)
    public void addEndDate(final Button b){
        DatePickerDialog dialog = new DatePickerDialog(getContext(),R.style.customPicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                Date date = cal.getTime();
                b.setText(formatterDate.format(date));
            }
        },Integer.valueOf(end.split(" ")[0].split("-")[0]),Integer.valueOf(end.split(" ")[0].split("-")[1])-1
                ,Integer.valueOf(end.split(" ")[0].split("-")[2]));
        dialog.show();
    }

    @OnClick(R.id.event_start_time)
    void addStartTime(final Button b){
        TimePickerDialog dialog = new TimePickerDialog(getContext(), R.style.customPicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                b.setText(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + ":00");
            }
        },Integer.valueOf(start.split(" ")[1].split(":")[0]),Integer.valueOf(start.split(" ")[1].split(":")[1]),true);
        dialog.show();
    }

    @OnClick(R.id.event_end_time)
    void addEndTime(final Button b){
        TimePickerDialog dialog = new TimePickerDialog(getContext(), R.style.customPicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                b.setText(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + ":00");
            }
        },Integer.valueOf(end.split(" ")[1].split(":")[0]),Integer.valueOf(end.split(" ")[1].split(":")[1]),true);
        dialog.show();
    }

    @OnClick(R.id.confirm_edit)
    void editEvent(){
        pd = new ProgressDialog(getContext(), R.style.StyledDialog);
        pd.setMessage("Mise à jour de l'évènement en cours...");
        pd.setCancelable(false);
        pd.show();
        if(isTokenExpired(ur.getToken())){
            refreshToken(ur.getToken(),getContext());
        }
        if(checkFields(title.getText().toString(), event_start_date.getText().toString()
                ,event_end_date.getText().toString(),event_start_time.getText().toString()
                , event_end_time.getText().toString(),typeSpinner.getSelectedItem().toString())) {
            if(typeSpinner.getSelectedItem().equals("Bilan")){
                type = "0";
            }
            else{
                type = "1";
            }
            try {
                if (formatterDateTime.parse(event_start_date.getText().toString() + " " + event_start_time.getText().toString())
                        .compareTo(formatterDateTime.parse(event_end_date.getText().toString() + " " + event_end_time.getText().toString())) > 0) {
                    Toast.makeText(getContext(), "La date de fin est antérieure à la date de départ", Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
                else if(formatterDateTime.parse(event_start_date.getText().toString() + " " + event_start_time.getText().toString()).compareTo(
                        formatterDateTime.parse(getDate()))<0){
                    Toast.makeText(getContext(),"La date de début est antérieure à la date d'aujourd'hui",Toast.LENGTH_LONG).show();
                    pd.dismiss();
                }
                else{
                    ApiCall.updateEvent("Bearer " + ur.getToken(), eventID, title.getText().toString(), type, event_start_date.getText().toString() + " " +
                            event_start_time.getText().toString(), event_end_date.getText().toString() + " " +
                            event_end_time.getText().toString(), getDate(), updatedBy, new ServiceResultListener() {
                        @Override
                        public void onResult(ApiResults ar) {
                            if(ar.getResponseCode() == 200){
                                pd.dismiss();
                                isOK = true;
                                Toast.makeText(getContext(),"L'évènement a été mis à jour",Toast.LENGTH_LONG).show();
                                dismiss();
                            }
                            else{
                                pd.dismiss();
                                Toast.makeText(getContext(),getCorrespondingErrorMessage(ar.getErrorMessage()),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
            catch (ParseException pe){
                pe.printStackTrace();
            }
        }
        else{
            pd.dismiss();
            Toast.makeText(getContext(),"Certains champs sont manquants !", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.cancel_event)
    void cancelEvent(){
        pd = new ProgressDialog(getContext(), R.style.AppCompatAlertDialogStyle);
        pd.setMessage("Annulation de l'évènement en cours...");
        pd.show();
        ApiCall.deleteEvent("Bearer " + ur.getToken(), eventID, updatedBy, new ServiceResultListener() {
            @Override
            public void onResult(ApiResults ar) {
                if(ar.getResponseCode() == 200){
                    pd.dismiss();
                    isOK = true;
                    Toast.makeText(getContext(),"L'évènement a été annulé",Toast.LENGTH_LONG).show();
                    dismiss();
                }
            }
        });
    }

    public EditEvent(Activity a, String eventID, String name, String type, String start
            ,String end, String updatedBy) {
        super(a);
        this.eventID = eventID;
        this.name = name;
        this.type = type;
        this.start = start;
        this.end = end;
        this.updatedBy = updatedBy;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_edit_event);
        ButterKnife.bind(this);
        title.setText(name);
        if(type.equals("0")){
            typeSpinner.setSelection(getIndex(typeSpinner,"Bilan"));
        }
        else{
            typeSpinner.setSelection(getIndex(typeSpinner,"Session"));
        }
        String s[] = start.split(" ");
        event_start_date.setText(s[0]);
        event_start_time.setText(s[1]);
        s = end.split(" ");
        event_end_date.setText(s[0]);
        event_end_time.setText(s[1]);
    }

    private int getIndex(Spinner spinner, String s){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                return i;
            }
        }
        return 0;
    }

    public boolean getIsOK(){
        return isOK;
    }
}
