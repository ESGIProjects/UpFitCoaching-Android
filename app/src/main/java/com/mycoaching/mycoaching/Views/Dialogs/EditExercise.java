package com.mycoaching.mycoaching.Views.Dialogs;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Retrofit.Exercise;
import com.mycoaching.mycoaching.R;

import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mycoaching.mycoaching.Util.CommonMethods.checkFields;
import static com.mycoaching.mycoaching.Util.CommonMethods.getDate;
import static com.mycoaching.mycoaching.Util.Constants.DATE_FORMATTER;
import static com.mycoaching.mycoaching.Util.Constants.DATE_TIME_FORMATTER;

/**
 * Created by kevin on 09/07/2018.
 */

public class EditExercise extends Dialog{

    private boolean isOK = false;
    private boolean isCancel = false;
    private Exercise editedExercise;

    @BindView(R.id.layout_intensity)
    LinearLayout layout_intensity;

    @BindView(R.id.layout_timer)
    LinearLayout layout_timer;

    @BindView(R.id.repInput)
    TextInputLayout repInput;

    @BindView(R.id.serieInput)
    TextInputLayout serieInput;

    @BindView(R.id.serie)
    EditText serie;

    @BindView(R.id.rep)
    EditText rep;

    @BindView(R.id.timer)
    Button timer;

    @BindView(R.id.spinnerIntensity)
    Spinner intensity;

    @OnClick(R.id.remove_exercise)
    public void cancel(){
        editedExercise = null;
        isCancel = true;
        isOK = true;
        dismiss();
    }

    @OnClick(R.id.timer)
    void addTime(final Button b){
        TimePickerDialog dialog = new TimePickerDialog(getContext(), R.style.customPicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                b.setText(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + ":00");
            }
        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY),Calendar.getInstance().get(Calendar.MINUTE),true);
        dialog.show();
    }

    @OnClick(R.id.confirm_edit)
    public void editExercise(){
        if(editedExercise.getExercise().equals("Abdominaux")){
            if(checkFields(rep.getText().toString(),serie.getText().toString())){
                editedExercise = new Exercise(null,"Abdominaux",null,
                        Integer.valueOf(rep.getText().toString()), Integer.valueOf(serie.getText().toString()));
                isOK = true;
                dismiss();
            }
            else{
                Toast.makeText(getContext(),"Il manque au moins un champs !",Toast.LENGTH_LONG).show();
            }
        }
        else if(editedExercise.getExercise().equals("Footing")){
            if(checkFields(timer.getText().toString())){
                String s = timer.getText().toString();
                String[] times = s.split(":");

                int hour = Integer.parseInt(times[0]);
                int minute = Integer.parseInt(times[1]);
                int second = Integer.parseInt(times[2]);

                int time = second + (60 * minute) + (3600 * hour);

                editedExercise = new Exercise(time,"Footing",intensity.getSelectedItemPosition(),
                        null,null);
                isOK = true;
                dismiss();
            }
            else{
                Toast.makeText(getContext(),"Il manque au moins un champs !",Toast.LENGTH_LONG).show();
            }
        }
        else if(editedExercise.getExercise().equals("Natation")){
            if(checkFields(timer.getText().toString())){
                String s = timer.getText().toString();
                String[] times = s.split(":");

                int hour = Integer.parseInt(times[0]);
                int minute = Integer.parseInt(times[1]);
                int second = Integer.parseInt(times[2]);

                int time = second + (60 * minute) + (3600 * hour);

                editedExercise = new Exercise(time,"Natation",null,
                        null,null);
                isOK = true;
                dismiss();
            }
            else{
                Toast.makeText(getContext(),"Il manque au moins un champs !",Toast.LENGTH_LONG).show();
            }
        }
        else if(editedExercise.getExercise().equals("Pompes")){
            if(checkFields(rep.getText().toString(),serie.getText().toString())){
                editedExercise = new Exercise(null,"Pompes",null,
                        Integer.valueOf(rep.getText().toString()), Integer.valueOf(serie.getText().toString()));
                isOK = true;
                dismiss();
            }
            else{
                Toast.makeText(getContext(),"Il manque au moins un champs !",Toast.LENGTH_LONG).show();
            }
        }
        else if(editedExercise.getExercise().equals("Squats")){
            if(checkFields(rep.getText().toString(),serie.getText().toString())){
                editedExercise = new Exercise(null,"Squats",null,
                        Integer.valueOf(rep.getText().toString()), Integer.valueOf(serie.getText().toString()));
                isOK = true;
                dismiss();
            }
            else{
                Toast.makeText(getContext(),"Il manque au moins un champs !",Toast.LENGTH_LONG).show();
            }
        }
        else if(editedExercise.getExercise().equals("Vélo")){
            if(checkFields(timer.getText().toString())){
                String s = timer.getText().toString();
                String[] times = s.split(":");

                int hour = Integer.parseInt(times[0]);
                int minute = Integer.parseInt(times[1]);
                int second = Integer.parseInt(times[2]);

                int time = second + (60 * minute) + (3600 * hour);

                editedExercise = new Exercise(time,"Vélo",intensity.getSelectedItemPosition(),
                        null,null);
                isOK = true;
                dismiss();
            }
            else{
                Toast.makeText(getContext(),"Il manque au moins un champs !",Toast.LENGTH_LONG).show();
            }
        }
    }

    public EditExercise(Activity a, Exercise editedExercise) {
        super(a);
        this.editedExercise = editedExercise;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_edit_exercise);
        ButterKnife.bind(this);
        if(editedExercise.getExercise().equals("Abdominaux")){
            layout_intensity.setVisibility(View.GONE);
            layout_timer.setVisibility(View.GONE);
            repInput.setVisibility(View.VISIBLE);
            serieInput.setVisibility(View.VISIBLE);
            rep.setText(String.valueOf(editedExercise.getRepetitions()));
            serie.setText(String.valueOf(editedExercise.getSeries()));
        }
        else if(editedExercise.getExercise().equals("Footing")){
            layout_intensity.setVisibility(View.VISIBLE);
            layout_timer.setVisibility(View.VISIBLE);
            repInput.setVisibility(View.GONE);
            serieInput.setVisibility(View.GONE);
            intensity.setSelection(editedExercise.getIntensity());
            LocalTime lt = new LocalTime(0, 0);
            lt = lt.plusSeconds(editedExercise.getDuration());
            String time = DateTimeFormat.forPattern("HH:mm:ss").print(lt);
            timer.setText(String.valueOf(time));
        }
        else if(editedExercise.getExercise().equals("Natation")){
            layout_intensity.setVisibility(View.GONE);
            layout_timer.setVisibility(View.VISIBLE);
            repInput.setVisibility(View.GONE);
            serieInput.setVisibility(View.GONE);
            LocalTime lt = new LocalTime(0, 0);
            lt = lt.plusSeconds(editedExercise.getDuration());
            String time = DateTimeFormat.forPattern("HH:mm:ss").print(lt);
            timer.setText(String.valueOf(time));
        }
        else if(editedExercise.getExercise().equals("Pompes")){
            layout_intensity.setVisibility(View.GONE);
            layout_timer.setVisibility(View.GONE);
            repInput.setVisibility(View.VISIBLE);
            serieInput.setVisibility(View.VISIBLE);
            rep.setText(String.valueOf(editedExercise.getRepetitions()));
            serie.setText(String.valueOf(editedExercise.getSeries()));
        }
        else if(editedExercise.getExercise().equals("Squats")){
            layout_intensity.setVisibility(View.GONE);
            layout_timer.setVisibility(View.GONE);
            repInput.setVisibility(View.VISIBLE);
            serieInput.setVisibility(View.VISIBLE);
            rep.setText(String.valueOf(editedExercise.getRepetitions()));
            serie.setText(String.valueOf(editedExercise.getSeries()));
        }
        else if(editedExercise.getExercise().equals("Vélo")){
            layout_intensity.setVisibility(View.VISIBLE);
            layout_timer.setVisibility(View.VISIBLE);
            repInput.setVisibility(View.GONE);
            serieInput.setVisibility(View.GONE);
            intensity.setSelection(editedExercise.getIntensity());
            LocalTime lt = new LocalTime(0, 0);
            lt = lt.plusSeconds(editedExercise.getDuration());
            String time = DateTimeFormat.forPattern("HH:mm:ss").print(lt);
            timer.setText(String.valueOf(time));
        }
    }

    public Exercise getExercise(){
        return editedExercise;
    }

    public boolean getIsOK(){
        return isOK;
    }

    public boolean getIsCancel(){
        return isCancel;
    }
}
