package com.mycoaching.mycoaching.Views.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mycoaching.mycoaching.Models.Retrofit.Exercise;
import com.mycoaching.mycoaching.R;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mycoaching.mycoaching.Util.CommonMethods.checkFields;

/**
 * Created by kevin on 08/07/2018.
 */
public class AddExercise extends Dialog {

    private boolean isOK = false;
    List<String> listExercise;
    Exercise newExercise;

    @BindView(R.id.spinner)
    Spinner spinner;

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

    @OnClick(R.id.confirm_exercise)
    public void addExercise(){
        if(spinner.getSelectedItem().equals("Abdominaux")){
            if(checkFields(rep.getText().toString(),serie.getText().toString())){
                newExercise = new Exercise(null,"Abdominaux",null,
                        Integer.valueOf(rep.getText().toString()), Integer.valueOf(serie.getText().toString()));
                isOK = true;
                dismiss();
            }
            else{
                Toast.makeText(getContext(),"Il manque au moins un champs !",Toast.LENGTH_LONG).show();
            }
        }
        else if(spinner.getSelectedItem().equals("Footing")){
            if(checkFields(timer.getText().toString())){
                String s = timer.getText().toString();
                String[] times = s.split(":");

                int hour = Integer.parseInt(times[0]);
                int minute = Integer.parseInt(times[1]);
                int second = Integer.parseInt(times[2]);

                int time = second + (60 * minute) + (3600 * hour);

                newExercise = new Exercise(time,"Footing",intensity.getSelectedItemPosition(),
                       null,null);
                isOK = true;
                dismiss();
            }
            else{
                Toast.makeText(getContext(),"Il manque au moins un champs !",Toast.LENGTH_LONG).show();
            }
        }
        else if(spinner.getSelectedItem().equals("Natation")){
            if(checkFields(timer.getText().toString())){
                String s = timer.getText().toString();
                String[] times = s.split(":");

                int hour = Integer.parseInt(times[0]);
                int minute = Integer.parseInt(times[1]);
                int second = Integer.parseInt(times[2]);

                int time = second + (60 * minute) + (3600 * hour);

                newExercise = new Exercise(time,"Natation",null,
                        null,null);
                isOK = true;
                dismiss();
            }
            else{
                Toast.makeText(getContext(),"Il manque au moins un champs !",Toast.LENGTH_LONG).show();
            }
        }
        else if(spinner.getSelectedItem().equals("Pompes")){
            if(checkFields(rep.getText().toString(),serie.getText().toString())){
                newExercise = new Exercise(null,"Pompes",null,
                        Integer.valueOf(rep.getText().toString()), Integer.valueOf(serie.getText().toString()));
                isOK = true;
                dismiss();
            }
            else{
                Toast.makeText(getContext(),"Il manque au moins un champs !",Toast.LENGTH_LONG).show();
            }
        }
        else if(spinner.getSelectedItem().equals("Squats")){
            if(checkFields(rep.getText().toString(),serie.getText().toString())){
                newExercise = new Exercise(null,"Squats",null,
                        Integer.valueOf(rep.getText().toString()), Integer.valueOf(serie.getText().toString()));
                isOK = true;
                dismiss();
            }
            else{
                Toast.makeText(getContext(),"Il manque au moins un champs !",Toast.LENGTH_LONG).show();
            }
        }
        else if(spinner.getSelectedItem().equals("Vélo")){
            if(checkFields(timer.getText().toString())){
                String s = timer.getText().toString();
                String[] times = s.split(":");

                int hour = Integer.parseInt(times[0]);
                int minute = Integer.parseInt(times[1]);
                int second = Integer.parseInt(times[2]);

                int time = second + (60 * minute) + (3600 * hour);

                newExercise = new Exercise(time,"Vélo",intensity.getSelectedItemPosition(),
                        null,null);
                isOK = true;
                dismiss();
            }
            else{
                Toast.makeText(getContext(),"Il manque au moins un champs !",Toast.LENGTH_LONG).show();
            }
        }
    }

    @OnClick(R.id.timer)
    void addTime(final Button b){
        TimePickerDialog dialog = new TimePickerDialog(getContext(), R.style.customPicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                b.setText(timePicker.getCurrentHour() + ":" + timePicker.getCurrentMinute() + ":00");
            }
        }, 0,0,true);
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_prescription);
        ButterKnife.bind(this);

        final ArrayAdapter<String> list =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, listExercise);
        spinner.setAdapter(list);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(list.getItem(i).equals("Abdominaux")){
                    layout_intensity.setVisibility(View.GONE);
                    layout_timer.setVisibility(View.GONE);
                    repInput.setVisibility(View.VISIBLE);
                    serieInput.setVisibility(View.VISIBLE);
                }
                else if(list.getItem(i).equals("Footing")){
                    layout_intensity.setVisibility(View.VISIBLE);
                    layout_timer.setVisibility(View.VISIBLE);
                    repInput.setVisibility(View.GONE);
                    serieInput.setVisibility(View.GONE);
                }
                else if(list.getItem(i).equals("Natation")){
                    layout_intensity.setVisibility(View.GONE);
                    layout_timer.setVisibility(View.VISIBLE);
                    repInput.setVisibility(View.GONE);
                    serieInput.setVisibility(View.GONE);
                }
                else if(list.getItem(i).equals("Pompes")){
                    layout_intensity.setVisibility(View.GONE);
                    layout_timer.setVisibility(View.GONE);
                    repInput.setVisibility(View.VISIBLE);
                    serieInput.setVisibility(View.VISIBLE);
                }
                else if(list.getItem(i).equals("Squats")){
                    layout_intensity.setVisibility(View.GONE);
                    layout_timer.setVisibility(View.GONE);
                    repInput.setVisibility(View.VISIBLE);
                    serieInput.setVisibility(View.VISIBLE);
                }
                else if(list.getItem(i).equals("Vélo")){
                    layout_intensity.setVisibility(View.VISIBLE);
                    layout_timer.setVisibility(View.VISIBLE);
                    repInput.setVisibility(View.GONE);
                    serieInput.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public AddExercise(Activity a, List<String> listExercise) {
        super(a);
        this.listExercise = listExercise;
    }

    public Exercise getExercise(){
        return newExercise;
    }

    public boolean getIsOK(){
        return isOK;
    }

}
