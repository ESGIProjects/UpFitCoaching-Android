package com.mycoaching.mycoaching.Views.Fragments.Common;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.Models.Retrofit.Measurement;
import com.mycoaching.mycoaching.R;
import com.mycoaching.mycoaching.Views.Dialogs.AddMeasurement;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

import static com.mycoaching.mycoaching.Util.CommonMethods.getDate;

/**
 * Created by kevin on 28/04/2018.
 */

public class FollowUpFragment extends Fragment {

    View v;
    ProgressDialog pd;

    List<Measurement> lm = new ArrayList<>();
    List<Measurement> listSpecific = new ArrayList<>();
    List<String> listDate = new ArrayList<>();

    LineChart lcWeight;
    LineChart lcBody;
    LineChart lcMeasure;

    LineData ldWeight;
    LineData ldBody;
    LineData ldMeasure;

    String id;
    Bundle b;
    Realm r;
    UserRealm ur;

    boolean isMonthClicked = true;
    boolean isYearClicked = false;
    boolean isGlobalClicked = false;

    @BindView(R.id.weightValue)
    TextView weightValue;

    @BindView(R.id.bmi)
    TextView bmiValue;

    @BindView(R.id.bfp)
    TextView bfpValue;

    @BindView(R.id.hip)
    TextView hipValue;

    @BindView(R.id.waist)
    TextView waistValue;

    @BindView(R.id.thigh)
    TextView thighValue;

    @BindView(R.id.arm)
    TextView armValue;

    @BindView(R.id.global)
    Button global;

    @BindView(R.id.year)
    Button year;

    @BindView(R.id.month)
    Button month;

    @OnClick(R.id.global)
    public void global(){
        if(!isGlobalClicked && lm.size() > 0){
            listDate.clear();
            global.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            year.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            month.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            isMonthClicked = false;
            isYearClicked = false;
            isGlobalClicked = true;
            listSpecific.clear();
            clearCharts();
            getGlobalMeasurements(lm);
        }
    }

    @OnClick(R.id.year)
    public void year(){
        if(!isYearClicked && lm.size() > 0){
            listDate.clear();
            global.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            year.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            month.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            isMonthClicked = false;
            isYearClicked = true;
            isGlobalClicked = false;
            listSpecific.clear();
            clearCharts();
            getYearMeasurements(lm);
        }
    }

    @OnClick(R.id.month)
    public void month(){
        if(!isMonthClicked){
            listDate.clear();
            global.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            year.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            month.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            isMonthClicked = true;
            isYearClicked = false;
            isGlobalClicked = false;
            listSpecific.clear();
            clearCharts();
            getMonthMeasurements(lm);
        }
    }

    @OnClick(R.id.buttonMeasure)
    void addMeasurement() {
        final AddMeasurement am = new AddMeasurement(getActivity(), id);
        assert am.getWindow() != null;
        am.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        am.show();
        am.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if(am.getIsOK()) {
                    if(lm.size() != 0){
                        listDate.clear();
                        lm.clear();
                        clearCharts();
                        listSpecific.clear();
                        isMonthClicked = true;
                        isYearClicked = false;
                        isGlobalClicked = false;
                        month.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        global.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        year.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    }
                    getMeasurements();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pd = new ProgressDialog(getActivity(), R.style.StyledDialog);
        pd.setMessage("Récupération des informations...");
        pd.show();

        r = Realm.getDefaultInstance();
        ur = r.where(UserRealm.class).findFirst();

        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_combined_chart, container, false);
        ButterKnife.bind(this, v);
        month.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        lcWeight = v.findViewById(R.id.chartWeight);
        lcBody = v.findViewById(R.id.chartBody);
        lcMeasure = v.findViewById(R.id.chartMeasure);

        lcWeight.setDrawGridBackground(false);
        lcBody.setDrawGridBackground(false);
        lcMeasure.setDrawGridBackground(false);

        lcWeight.getLegend().setEnabled(false);
        lcBody.getLegend().setEnabled(false);
        lcMeasure.getLegend().setEnabled(false);

        lcWeight.getXAxis().setEnabled(false);
        lcBody.getXAxis().setEnabled(false);
        lcMeasure.getXAxis().setEnabled(false);

        lcWeight.getAxisLeft().setDrawLabels(false);
        lcBody.getAxisLeft().setDrawLabels(false);
        lcMeasure.getAxisLeft().setDrawLabels(false);

        lcWeight.setNoDataText("Pas de données pour le moment !");
        lcBody.setNoDataText("Pas de données pour le moment !");
        lcMeasure.setNoDataText("Pas de données pour le moment !");

        lcWeight.getDescription().setEnabled(false);
        lcBody.getDescription().setEnabled(false);
        lcMeasure.getDescription().setEnabled(false);

        lcBody.getXAxis().setDrawGridLines(false);

        ldWeight = new LineData();
        ldBody = new LineData();
        ldMeasure = new LineData();

        b = this.getArguments();
        if(b != null){
            id = b.getString("id");
        }
        else{
            id = ur.getId();
        }
        getMeasurements();
        return v;
    }

    public void getMeasurements(){
        ApiCall.getMeasurements("Bearer " + ur.getToken(),Integer.valueOf(id), new ServiceResultListener() {
            @Override
            public void onResult(ApiResults ar) {
                if(ar.getResponseCode() == 200){
                    if(ar.getListMeasurement().size() != 0){
                        lm.addAll(ar.getListMeasurement());
                        getMonthMeasurements(lm);
                    }
                }
                pd.dismiss();
            }
        });
    }

    public void getMonthMeasurements(List<Measurement> lm){
        Collections.reverse(lm);
        for(Measurement m : lm){
            String month = getDate().split(" ")[0].split("-")[0]+getDate().split(" ")[0].split("-")[1];
            if(!month.equals(m.getDate().split(" ")[0].split("-")[0]+getDate().split(" ")[0].split("-")[1])){
                break;
            }
            if(!listDate.contains(m.getDate().split(" ")[0])){
                listDate.add(m.getDate().split(" ")[0]);
                listSpecific.add(m);
            }
        }
        for(Measurement m : listSpecific){
            Log.i("TEST MEASURE : ", m.getDate());
        }
        Collections.reverse(lm);
        Collections.reverse(listSpecific);
        Collections.reverse(listDate);
        loadCharts();
    }

    public void getYearMeasurements(List<Measurement> lm){
        Collections.reverse(lm);
        for(Measurement m : lm){
            String year = getDate().split(" ")[0].split("-")[0];
            if(!year.equals(m.getDate().split(" ")[0].split("-")[0])){
                break;
            }
            if(!listDate.contains(m.getDate().split(" ")[0])){
                listDate.add(m.getDate().split(" ")[0]);
                listSpecific.add(m);
            }
        }
        Collections.reverse(lm);
        Collections.reverse(listSpecific);
        Collections.reverse(listDate);
        loadCharts();
    }

    public void getGlobalMeasurements(List<Measurement> lm){
        Collections.reverse(lm);
        for(Measurement m : lm){
            if(!listDate.contains(m.getDate().split(" ")[0])){
                listDate.add(m.getDate().split(" ")[0]);
                listSpecific.add(m);
            }
        }
        Collections.reverse(lm);
        Collections.reverse(listSpecific);
        Collections.reverse(listDate);
        loadCharts();
    }

    public void loadCharts(){

        List<Entry> weightEntries = new ArrayList<>();
        List<Entry> bfpEntries = new ArrayList<>();
        List<Entry> bmiEntries = new ArrayList<>();
        List<Entry> hipEntries = new ArrayList<>();
        List<Entry> waistEntries = new ArrayList<>();
        List<Entry> thighEntries = new ArrayList<>();
        List<Entry> armEntries = new ArrayList<>();

        for(int i = 0; i < listSpecific.size(); i++){

            String splitBirth[] = listSpecific.get(i).getUser().getBirthDate().split(" ")[0].split("-");
            LocalDate birth = new LocalDate(Integer.valueOf(splitBirth[0])
                    ,Integer.valueOf(splitBirth[1]),Integer.valueOf(splitBirth[2]));
            LocalDate now = new LocalDate();
            Years age = Years.yearsBetween(birth,now);
            double bmi = Double.valueOf(listSpecific.get(i).getWeight())/((Double.valueOf(listSpecific.get(i).getHeight())/100)*(Double.valueOf(listSpecific.get(i).getHeight())/100));
            double bfp = 1.2*bmi+0.23*(double)age.getYears()-10.8*Double.valueOf(listSpecific.get(i).getUser().getSex())-5.4;

            Entry e = new Entry(i,Float.valueOf(listSpecific.get(i).getWeight()));
            Entry e1 = new Entry(i,(float)bmi);
            Entry e2 = new Entry(i,(float)bfp);
            Entry e3 = new Entry(i,Float.valueOf(listSpecific.get(i).getHipCircumference()));
            Entry e4 = new Entry(i,Float.valueOf(listSpecific.get(i).getWaistCircumference()));
            Entry e5 = new Entry(i,Float.valueOf(listSpecific.get(i).getThighCircumference()));
            Entry e6 = new Entry(i,Float.valueOf(listSpecific.get(i).getArmCircumference()));

            weightEntries.add(e);
            bmiEntries.add(e1);
            bfpEntries.add(e2);
            hipEntries.add(e3);
            waistEntries.add(e4);
            thighEntries.add(e5);
            armEntries.add(e6);
        }

        LineDataSet weight = new LineDataSet(weightEntries, "Poids");
        LineDataSet bmi = new LineDataSet(bmiEntries, "IMC");
        LineDataSet bfp = new LineDataSet(bfpEntries, "IMG");
        LineDataSet hip = new LineDataSet(hipEntries, "Tour de hanche");
        LineDataSet waist = new LineDataSet(waistEntries, "Tour de ventre");
        LineDataSet thigh = new LineDataSet(thighEntries, "Tour de cuisse");
        LineDataSet arm = new LineDataSet(armEntries, "Tour de bras");

        weight.setCircleRadius(3f);
        weight.setDrawValues(false);
        weight.setLineWidth(2f);
        weight.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        weight.setAxisDependency(YAxis.AxisDependency.RIGHT);
        weight.setColor(getResources().getColor(R.color.weight));
        weight.setDrawFilled(true);

        bmi.setCircleRadius(3f);
        bmi.setDrawValues(false);
        bmi.setLineWidth(2f);
        bmi.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        bmi.setAxisDependency(YAxis.AxisDependency.RIGHT);
        bmi.setColor(getResources().getColor(R.color.bmi));

        bfp.setCircleRadius(4f);
        bfp.setDrawValues(false);
        bfp.setLineWidth(2f);
        bfp.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        bfp.setAxisDependency(YAxis.AxisDependency.RIGHT);
        bfp.setColor(getResources().getColor(R.color.bfp));

        hip.setCircleRadius(3f);
        hip.setDrawValues(false);
        hip.setLineWidth(2f);
        hip.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        hip.setAxisDependency(YAxis.AxisDependency.RIGHT);
        hip.setColor(getResources().getColor(R.color.hip));

        waist.setCircleRadius(3f);
        waist.setDrawValues(false);
        waist.setLineWidth(2f);
        waist.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        waist.setAxisDependency(YAxis.AxisDependency.RIGHT);
        waist.setColor(getResources().getColor(R.color.waist));

        thigh.setCircleRadius(3f);
        thigh.setDrawValues(false);
        thigh.setLineWidth(2f);
        thigh.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        thigh.setAxisDependency(YAxis.AxisDependency.RIGHT);
        thigh.setColor(getResources().getColor(R.color.thigh));

        arm.setCircleRadius(3f);
        arm.setDrawValues(false);
        arm.setLineWidth(2f);
        arm.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        arm.setAxisDependency(YAxis.AxisDependency.RIGHT);
        arm.setColor(getResources().getColor(R.color.arm));

        ldWeight.addDataSet(weight);
        ldBody.addDataSet(bmi);
        ldBody.addDataSet(bfp);
        ldMeasure.addDataSet(hip);
        ldMeasure.addDataSet(waist);
        ldMeasure.addDataSet(thigh);
        ldMeasure.addDataSet(arm);

        lcWeight.getXAxis().setGranularity(1f);
        lcWeight.getXAxis().setGranularityEnabled(true);
        lcWeight.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lcWeight.getXAxis().setEnabled(true);
        lcWeight.getXAxis().setLabelRotationAngle(45f);
        lcWeight.getXAxis().setTextSize(1f);
        lcWeight.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(value >= 0){
                    if (listDate.size() > (int)value) {
                        return listDate.get((int)value);
                    }
                    else{
                        return "";
                    }
                }
                return "";
            }
        });

        lcBody.getXAxis().setGranularity(1f);
        lcBody.getXAxis().setGranularityEnabled(true);
        lcBody.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lcBody.getXAxis().setEnabled(true);
        lcBody.getXAxis().setLabelRotationAngle(45f);
        lcBody.getXAxis().setTextSize(1f);
        lcBody.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(value >= 0){
                    if (listDate.size() > (int)value) {
                        return listDate.get((int)value);
                    }
                    else{
                        return "";
                    }
                }
                return "";
            }
        });

        lcMeasure.getXAxis().setGranularity(1f);
        lcMeasure.getXAxis().setGranularityEnabled(true);
        lcMeasure.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lcMeasure.getXAxis().setEnabled(true);
        lcMeasure.getXAxis().setLabelRotationAngle(45f);
        lcMeasure.getXAxis().setTextSize(1f);
        lcMeasure.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(value >= 0){
                    if (listDate.size() > (int)value) {
                        return listDate.get((int)value);
                    }
                    else{
                        return "";
                    }
                }
                return "";
            }
        });

        lcBody.getLegend().setEnabled(true);
        lcBody.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        lcMeasure.getLegend().setEnabled(true);
        lcMeasure.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        lcWeight.setData(ldWeight);
        lcBody.setData(ldBody);
        lcMeasure.setData(ldMeasure);

        weightValue.setVisibility(View.VISIBLE);
        bmiValue.setVisibility(View.VISIBLE);
        bfpValue.setVisibility(View.VISIBLE);
        hipValue.setVisibility(View.VISIBLE);
        waistValue.setVisibility(View.VISIBLE);
        thighValue.setVisibility(View.VISIBLE);
        armValue.setVisibility(View.VISIBLE);

        weightValue.setText(getResources().getString(R.string.weightValue,listSpecific.get(listSpecific.size()-1).getWeight()));
        bmiValue.setText(getResources().getString(R.string.bmi,String.format("%.2f", bmiEntries.get(listSpecific.size()-1).getY())));
        bfpValue.setText(getResources().getString(R.string.bfp,String.format("%.2f", bfpEntries.get(listSpecific.size()-1).getY())));
        hipValue.setText(getResources().getString(R.string.hip,listSpecific.get(listSpecific.size()-1).getHipCircumference()));
        waistValue.setText(getResources().getString(R.string.waist,listSpecific.get(listSpecific.size()-1).getWaistCircumference()));
        thighValue.setText(getResources().getString(R.string.thigh,listSpecific.get(listSpecific.size()-1).getThighCircumference()));
        armValue.setText(getResources().getString(R.string.arm,listSpecific.get(listSpecific.size()-1).getArmCircumference()));

        lcWeight.invalidate();
        lcBody.invalidate();
        lcMeasure.invalidate();
    }

    public void clearCharts(){
        lcWeight.clearValues();
        lcBody.clearValues();
        lcMeasure.clearValues();
    }
}
