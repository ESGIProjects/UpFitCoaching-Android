package com.mycoaching.mycoaching.Views.Fragments.Common;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.mycoaching.mycoaching.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by kevin on 28/04/2018.
 */

public class FollowUpFragment extends Fragment {

    View v;

    LineChart lcWeight;
    LineData weightData;
    LineChart lcBody;
    LineData bodyData;
    LineChart lcMeasure;
    LineData measureData;

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
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_combined_chart, container, false);
        ButterKnife.bind(this, v);
        month.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        lcWeight = v.findViewById(R.id.chartWeight);
        lcWeight.getDescription().setEnabled(false);
        lcWeight.setDrawGridBackground(false);
        lcWeight.getLegend().setEnabled(false);
        lcWeight.getXAxis().setEnabled(false);

        lcBody = v.findViewById(R.id.charts);
        lcBody.getDescription().setEnabled(false);
        lcBody.setDrawGridBackground(false);
        lcBody.getLegend().setEnabled(false);

        XAxis lcbAxis = lcBody.getXAxis();
        lcbAxis.setDrawGridLines(false);
        lcbAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis lcwYAxis = lcWeight.getAxisLeft();
        lcwYAxis.setDrawLabels(false);
        YAxis lcbYAxis = lcBody.getAxisLeft();
        lcbYAxis.setDrawLabels(false);

        weightData = new LineData();
        bodyData = new LineData();

        generateBFP();
        generateBMI();
        generateWeight();

        lcWeight.setData(weightData);
        lcBody.setData(bodyData);

        lcWeight.invalidate();
        lcBody.invalidate();
        return v;
    }

    public void generateBMI() {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        entries.add(new Entry(1, 19));
        entries.add(new Entry(3, 18.2f));
        entries.add(new Entry(6, 18.8f));
        entries.add(new Entry(10, 19.1f));

        LineDataSet set = new LineDataSet(entries, "IMC");
        set.setColor(R.color.bmi);
        set.setCircleRadius(5f);
        set.setDrawValues(false);
        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        bodyData.addDataSet(set);
    }

    public void generateBFP() {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        entries.add(new Entry(1, 22));
        entries.add(new Entry(3, 23.3f));
        entries.add(new Entry(6, 21));
        entries.add(new Entry(10, 20.3f));

        LineDataSet set = new LineDataSet(entries, "IMG");
        set.setColor(R.color.bfp);
        set.setCircleRadius(5f);
        set.setDrawValues(false);
        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        bodyData.addDataSet(set);
    }

    public void generateWeight() {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        entries.add(new Entry(1, 61.2f));
        entries.add(new Entry(3, 62.3f));
        entries.add(new Entry(6, 62));
        entries.add(new Entry(10, 57.3f));

        LineDataSet set = new LineDataSet(entries, "Poids");
        set.setColor(R.color.weight);
        set.setCircleRadius(5f);
        set.setDrawValues(false);
        set.setDrawFilled(true);
        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        weightData.addDataSet(set);
    }
}
