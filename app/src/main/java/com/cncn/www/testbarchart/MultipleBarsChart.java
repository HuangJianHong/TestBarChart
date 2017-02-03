package com.cncn.www.testbarchart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class MultipleBarsChart extends AppCompatActivity implements OnChartValueSelectedListener {

    private BarChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_bars_chart);



        mChart = (BarChart) findViewById(R.id.chart1);
        mChart.setOnChartValueSelectedListener(this);
        mChart.getDescription().setEnabled(false);
//        mChart.setDrawBorders(true);

        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(false);

        mChart.setDrawBarShadow(false);

        mChart.setDrawGridBackground(false);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it

//        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
//        mv.setChartView(mChart); // For bounds control
//        mChart.setMarker(mv); // Set the marker to the chart

        Legend l = mChart.getLegend();         //指示器；
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setYOffset(0f);
        l.setYEntrySpace(0f);
        l.setTextSize(8f);
//        l.setStackSpace(30f);     //整块占据的空间;

        XAxis xl = mChart.getXAxis();
        xl.setGranularity(1f);
        xl.setCenterAxisLabels(true);
        xl.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });



        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setValueFormatter(new LargeValueFormatter());
        leftAxis.setSpaceTop(30f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mChart.getAxisRight().setEnabled(false);



        leftAxis.setDrawGridLines(true);    //画水平线;         //TODO;
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);

        xl.setDrawGridLines(false);     //取消画竖线:
        xl.setDrawAxisLine(true);

        onProgressChanged(7);
    }


    public void onProgressChanged(int progress) {


        int startYear = 0;
        int endYear = progress;


        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();            //(1) 服务端下来的数据; ArrayList<BarEntry>    yVals1.add(new BarEntry(i, val));
        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> yVals3 = new ArrayList<BarEntry>();

        float mult = progress * 1f;

        for (int i = startYear; i < endYear; i++) {
            float val = (float) (Math.random() * mult) + 3;
            yVals1.add(new BarEntry(i, val));
        }

        for (int i = startYear; i < endYear; i++) {
            float val = (float) (Math.random() * mult) + 3;
            yVals2.add(new BarEntry(i, val));
        }

        BarDataSet set1, set2;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)mChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet)mChart.getData().getDataSetByIndex(1);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create 3 datasets with different types
            set1 = new BarDataSet(yVals1, "Company A");                           //（2） BarDataSet.setValues(第一步的数据) ,,setColor
            // set1.setColors(ColorTemplate.createColors(getApplicationContext(),
            // ColorTemplate.FRESH_COLORS));
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(yVals2, "Company B");
            set2.setColor(Color.rgb(164, 228, 251));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            dataSets.add(set2);

            BarData data = new BarData(dataSets);                         //（3）BarData 使用到第二步的数据;在
            data.setValueFormatter(new LargeValueFormatter());           //TODO;

            // add space between the dataset groups in percent of bar-width
//            data.setValueTypeface(mTfLight);

            mChart.setData(data);
        }

//        float groupSpace = 0.04f;
//        float barSpace = 0.02f; // x3 dataset
//        float barWidth = 0.3f; // x3 dataset
//        // (0.3 + 0.02) * 3 + 0.04 = 1.00 -> interval per "group"

        float groupSpace = 0.2f;
        float barSpace = 0.1f; // x3 dataset
        float barWidth = 0.3f; // x3 dataset


        mChart.getBarData().setBarWidth(barWidth);                    //TODO;
        mChart.getXAxis().setAxisMinimum(startYear);
        mChart.getXAxis().setAxisMaximum(mChart.getBarData().getGroupWidth(groupSpace, barSpace) * progress + startYear);
        mChart.groupBars(startYear, groupSpace, barSpace);


        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
