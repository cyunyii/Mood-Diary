package com.IN6222.myapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.IN6222.myapplication.bean.chartBean;
import com.IN6222.myapplication.db.DBManager;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VisualFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisualFragment extends Fragment {


    PieChart pieChart;
    BarChart barChart;
    ImageView chooseTime;
    List<PieEntry> datalist;
    List<BarEntry> bardataList;
    List<String> moodTypes;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VisualFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VisualFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VisualFragment newInstance(String param1, String param2) {
        VisualFragment fragment = new VisualFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_visual, container, false);
        pieChart=view.findViewById(R.id.viusal_piechart);
        barChart=view.findViewById(R.id.visual_barchart);
        chooseTime=view.findViewById(R.id.visual_calendar);
        datalist=new ArrayList<>();
        bardataList=new ArrayList<>();
        moodTypes=new ArrayList<>();

        chooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        datalist.clear();
        bardataList.clear();
        moodTypes.clear();

        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH)+1;


        System.out.println(month);
        System.out.println(year);

        List<chartBean> res= DBManager.searchByType(month,year);
        Log.d("resSize", ""+res.size());
        for(int i=0;i<res.size();i++){
//            System.out.println("*************lalalalalalala**********");
//            System.out.println(res.get(i).getCount());
//            System.out.println(res.get(i).getMoodType());
//            System.out.println(res.get(i).getPercentage());
//            System.out.println("**********lalalal************");
            chartBean temp=res.get(i);
            datalist.add(new PieEntry(temp.getCount(),temp.getMoodType()));
            bardataList.add(new BarEntry((float) i,(float) temp.getCount()));
            moodTypes.add(temp.getMoodType());

        }

        drawPie();
        drawbar();


    }

    private void drawPie() {

        PieDataSet pieDataSet=new PieDataSet(datalist,"");
        pieDataSet.setDrawValues(true);
        pieDataSet.setValueTextSize(16);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueFormatter(new PercentFormatter());
        pieDataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        pieChart.animateXY(2000,2000);
        PieData data=new PieData(pieDataSet);
        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawEntryLabels(true);
        pieChart.setEntryLabelTypeface(Typeface.SERIF);
        pieChart.setExtraOffsets(30f, 0f, 20f, 10f);
        pieChart.setEntryLabelColor(Color.BLACK);
        Legend legend=pieChart.getLegend();
        legend.setEnabled(false);
//        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
//        legend.setTextSize(14);
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
    }


    private void drawbar() {
        BarDataSet barDataSet=new BarDataSet(bardataList,"Bar chart");

        BarData data=new BarData(barDataSet);
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        barChart.setData(data);
        barChart.getDescription().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.animateY(1500);
        barChart.setExtraOffsets(18f, 10f, 18f, 15f);
        barDataSet.setDrawValues(false);

        Legend legend=barChart.getLegend();
        legend.setEnabled(false);

        XAxis xAxis=barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12);
        xAxis.setLabelCount(moodTypes.size());

        xAxis.setValueFormatter(new IndexAxisValueFormatter(moodTypes));
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-20f);

        YAxis leftYAxis = barChart.getAxisLeft();
        leftYAxis.setDrawGridLines(false);
        //hide right YAxis
        YAxis rightYAxis = barChart.getAxisRight();
        rightYAxis.setEnabled(false);
        data.setBarWidth(0.8f);


    }
}