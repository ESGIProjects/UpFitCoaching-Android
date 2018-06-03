package com.mycoaching.mycoaching.Views.Fragments.UserMenu;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.mycoaching.mycoaching.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by kevin on 28/04/2018.
 */

public class FollowUpFragment extends Fragment {

    View v;
    CombinedChart cc;
    CombinedData cd;
    LineData ld;
    BarData bd;

    int bmiColor = Color.rgb(244, 67, 54);
    int bfpColor = Color.rgb(0, 150, 136);
    int weightColor = Color.rgb(33, 150, 243);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_combined_chart, container, false);

        cc = v.findViewById(R.id.chart);
        cc.getDescription().setEnabled(false);
        cc.setBackgroundColor(Color.WHITE);
        cc.setDrawGridBackground(false);
        cc.setDrawBarShadow(false);
        cc.setHighlightFullBarEnabled(false);

        Legend l = cc.getLegend();
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        YAxis weightAxis = cc.getAxisLeft();
        weightAxis.setDrawGridLines(false);
        weightAxis.setAxisMinimum(0f);

        YAxis indexAxis = cc.getAxisRight();
        indexAxis.setDrawGridLines(true);
        indexAxis.setAxisMinimum(0f);

        XAxis xAxis = cc.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        xAxis.setAxisMinimum(1f);
        xAxis.setGranularity(1f);

        cd = new CombinedData();
        ld = new LineData();
        bd = new BarData();
        generateBFP();
        generateBMI();
        generateWeight();
        cd.setData(ld);
        cd.setData(bd);

        cc.setData(cd);
        cc.invalidate();

        ButterKnife.bind(this, v);
        return v;
    }

    public void generateBMI() {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        entries.add(new Entry(2, 21.2f));
        entries.add(new Entry(4, 25));
        entries.add(new Entry(7, 24));

        LineDataSet set = new LineDataSet(entries, "IMC");
        set.setColor(bmiColor);
        set.setLineWidth(2.5f);
        set.setCircleColor(bmiColor);
        set.setCircleRadius(5f);
        set.setFillColor(bmiColor);
        set.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(bmiColor);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        ld.addDataSet(set);
    }

    public void generateBFP() {

        ArrayList<Entry> entries = new ArrayList<Entry>();

        entries.add(new Entry(2, 23));
        entries.add(new Entry(5, 22.4f));
        entries.add(new Entry(8, 22));

        LineDataSet set = new LineDataSet(entries, "IMG");
        set.setColor(bfpColor);
        set.setLineWidth(2.5f);
        set.setCircleColor(bfpColor);
        set.setCircleRadius(5f);
        set.setFillColor(bfpColor);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(10f);
        set.setValueTextColor(bfpColor);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        ld.addDataSet(set);
    }

    public void generateWeight() {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        entries.add(new BarEntry(2, 61.2f));
        entries.add(new BarEntry(3, 62.3f));
        entries.add(new BarEntry(6, 62));
        entries.add(new BarEntry(10, 63.3f));

        BarDataSet set = new BarDataSet(entries, "Poids");
        set.setColor(weightColor);
        set.setValueTextSize(10f);
        set.setValueTextColor(weightColor);
        set.setAxisDependency(YAxis.AxisDependency.RIGHT);
        bd.addDataSet(set);
    }
}
