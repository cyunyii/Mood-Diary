package com.IN6222.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.IN6222.myapplication.RecordPage.GridViewAdapter;
import com.IN6222.myapplication.db.DBManager;
import com.IN6222.myapplication.db.MoodType;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    GridView gridView;
    List<MoodType> imglist;
    private GridViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        gridView=findViewById(R.id.mood_imgList);
        LoadMoodImg();
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.record_back:
                finish();
                break;
        }
    }


    /**
     * load img icons in the recording page(Gridview)
     */
    private void LoadMoodImg() {
        System.out.println("HEHHHHHHH*******************************");
        imglist=new ArrayList<>();
        adapter = new GridViewAdapter(this, imglist);
        gridView.setAdapter(adapter);
        //load data from database
        imglist.addAll(DBManager.getTypeList());
        adapter.notifyDataSetChanged();

    }
}