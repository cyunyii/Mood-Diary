package com.IN6222.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.IN6222.myapplication.Adapter.RecordItemAdapter;
import com.IN6222.myapplication.bean.MoodType;
import com.IN6222.myapplication.bean.RecordBean;
import com.IN6222.myapplication.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class MainPage extends AppCompatActivity {

    ListView listView;
    //data
    List<RecordBean> recordList;
    RecordItemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        listView=findViewById(R.id.main_lv);

        recordList=new ArrayList<>();
        adapter=new RecordItemAdapter(this,recordList);
        listView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("$$$$$$$$$$$$$$$$$$$$ð");
        loadDataBase();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecordBean recordBean=recordList.get(i);
                Intent intent=new Intent(MainPage.this,RecordActivity.class);
                intent.putExtra("bean",recordBean);
                startActivity(intent);
            }
        });
    }


    private void loadDataBase() {
        List<RecordBean> list= DBManager.searchAllRecords();
        recordList.clear();
        recordList.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.main_search:

                break;
            case R.id.fab:
                Intent i=new Intent(this,RecordActivity.class);
                startActivity(i);
                break;

        }
    }
}