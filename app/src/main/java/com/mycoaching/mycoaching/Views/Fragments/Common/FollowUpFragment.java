package com.mycoaching.mycoaching.Views.Fragments.Common;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.mycoaching.mycoaching.Api.ApiCall;
import com.mycoaching.mycoaching.Api.ApiResults;
import com.mycoaching.mycoaching.Api.ServiceResultListener;
import com.mycoaching.mycoaching.Models.Realm.UserRealm;
import com.mycoaching.mycoaching.Models.Retrofit.Measurements;
import com.mycoaching.mycoaching.R;

import org.joda.time.LocalDate;
import org.joda.time.Years;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by kevin on 28/04/2018.
 */

public class FollowUpFragment extends Fragment {

    View v;
    ProgressDialog pd;

    List<Measurements> lm = new ArrayList<>();
    List<Measurements> lDisplay = new ArrayList<>();

    LineChart lcWeight;
    LineChart lcBody;
    LineChart lcMeasure;

    LineData ldWeight;
    LineData ldBody;
    LineData ldMeasure;

    String id;
    Bundle b;
    Realm r;

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
        global.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        year.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        month.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    @OnClick(R.id.year)
    public void year(){
        global.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        year.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        month.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    @OnClick(R.id.month)
    public void month(){
        global.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        year.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        month.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pd = new ProgressDialog(getActivity(), R.style.StyledDialog);
        pd.setMessage("Récupération des informations...");
        pd.show();

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

        lcBody.getXAxis().setDrawGridLines(false);

        ldWeight = new LineData();
        ldBody = new LineData();
        ldMeasure = new LineData();

        b = this.getArguments();
        if(b != null){
            id = b.getString("id");
        }
        else{
            r = Realm.getDefaultInstance();
            id = r.where(UserRealm.class).findFirst().getId();
        }

        getMeasurements();

        return v;
    }

    public void getMeasurements(){
        ApiCall.getMeasurements(Integer.valueOf(id), new ServiceResultListener() {
            @Override
            public void onResult(ApiResults ar) {
                if(ar.getResponseCode() == 200){
                    if(ar.getListMeasurement().size() != 0){
                        lm.addAll(ar.getListMeasurement());

                        loadCharts();
                    }
                }
                pd.dismiss();
            }
        });
    }

    public void loadCharts(){

        List<Entry> weightEntries = new ArrayList<>();
        List<Entry> bfpEntries = new ArrayList<>();
        List<Entry> bmiEntries = new ArrayList<>();
        List<Entry> hipEntries = new ArrayList<>();
        List<Entry> waistEntries = new ArrayList<>();
        List<Entry> thighEntries = new ArrayList<>();
        List<Entry> armEntries = new ArrayList<>();

        for(int i = 0; i < lm.size(); i++){
            String splitBirth[] = lm.get(i).getUser().getBirthDate().split(" ")[0].split("-");
            LocalDate birth = new LocalDate(Integer.valueOf(splitBirth[0])
                    ,Integer.valueOf(splitBirth[1]),Integer.valueOf(splitBirth[2]));
            LocalDate now = new LocalDate();
            Years age = Years.yearsBetween(birth,now);
            double bmi = Double.valueOf(lm.get(i).getWeight())/((Double.valueOf(lm.get(i).getHeight())/100)*(Double.valueOf(lm.get(i).getHeight())/100));
            double bfp = 1.2*bmi+0.23*(double)age.getYears()-10.8*Double.valueOf(lm.get(i).getUser().getSex())-5.4;

            Entry e = new Entry(i,Float.valueOf(lm.get(i).getWeight()));
            Entry e1 = new Entry(i,(float)bmi);
            Entry e2 = new Entry(i,(float)bfp);
            Entry e3 = new Entry(i,Float.valueOf(lm.get(i).getHipCircumference()));
            Entry e4 = new Entry(i,Float.valueOf(lm.get(i).getWaistCircumference()));
            Entry e5 = new Entry(i,Float.valueOf(lm.get(i).getThighCircumference()));
            Entry e6 = new Entry(i,Float.valueOf(lm.get(i).getArmCircumference()));

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

        weight.setCircleRadius(5f);
        weight.setDrawValues(false);
        weight.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        weight.setAxisDependency(YAxis.AxisDependency.RIGHT);
        weight.setColor(getResources().getColor(R.color.bmi));
        weight.setDrawFilled(true);

        bmi.setCircleRadius(5f);
        bmi.setDrawValues(false);
        bmi.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        bmi.setAxisDependency(YAxis.AxisDependency.RIGHT);
        bmi.setColor(getResources().getColor(R.color.bmi));

        bfp.setCircleRadius(5f);
        bfp.setDrawValues(false);
        bfp.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        bfp.setAxisDependency(YAxis.AxisDependency.RIGHT);
        bfp.setColor(getResources().getColor(R.color.bmi));

        hip.setCircleRadius(5f);
        hip.setDrawValues(false);
        hip.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        hip.setAxisDependency(YAxis.AxisDependency.RIGHT);
        hip.setColor(getResources().getColor(R.color.bmi));

        waist.setCircleRadius(5f);
        waist.setDrawValues(false);
        waist.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        waist.setAxisDependency(YAxis.AxisDependency.RIGHT);
        waist.setColor(getResources().getColor(R.color.bmi));

        thigh.setCircleRadius(5f);
        thigh.setDrawValues(false);
        thigh.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        thigh.setAxisDependency(YAxis.AxisDependency.RIGHT);
        thigh.setColor(getResources().getColor(R.color.bmi));

        arm.setCircleRadius(5f);
        arm.setDrawValues(false);
        arm.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        arm.setAxisDependency(YAxis.AxisDependency.RIGHT);
        arm.setColor(getResources().getColor(R.color.bmi));

        ldWeight.addDataSet(weight);
        ldBody.addDataSet(bmi);
        ldBody.addDataSet(bfp);
        ldMeasure.addDataSet(hip);
        ldMeasure.addDataSet(waist);
        ldMeasure.addDataSet(thigh);
        ldMeasure.addDataSet(arm);

        lcWeight.setData(ldWeight);
        lcBody.setData(ldBody);
        lcMeasure.setData(ldMeasure);

        lcWeight.invalidate();
        lcBody.invalidate();
        lcMeasure.invalidate();

        weightValue.setVisibility(View.VISIBLE);
        bmiValue.setVisibility(View.VISIBLE);
        bfpValue.setVisibility(View.VISIBLE);
        hipValue.setVisibility(View.VISIBLE);
        waistValue.setVisibility(View.VISIBLE);
        thighValue.setVisibility(View.VISIBLE);
        armValue.setVisibility(View.VISIBLE);

        weightValue.setText(getResources().getString(R.string.weightValue,lm.get(lm.size()-1).getWeight()));
        bmiValue.setText(getResources().getString(R.string.bmi,String.format("%.2f", bmiEntries.get(lm.size()-1).getY())));
        bfpValue.setText(getResources().getString(R.string.bfp,String.format("%.2f", bfpEntries.get(lm.size()-1).getY())));
        hipValue.setText(getResources().getString(R.string.hip,lm.get(lm.size()-1).getHipCircumference()));
        waistValue.setText(getResources().getString(R.string.waist,lm.get(lm.size()-1).getWaistCircumference()));
        thighValue.setText(getResources().getString(R.string.thigh,lm.get(lm.size()-1).getThighCircumference()));
        armValue.setText(getResources().getString(R.string.arm,lm.get(lm.size()-1).getArmCircumference()));
    }
}
