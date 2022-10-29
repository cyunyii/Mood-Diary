package com.IN6222.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.IN6222.myapplication.RecordPage.GridViewAdapter;
import com.IN6222.myapplication.bean.RecordBean;
import com.IN6222.myapplication.db.DBManager;
import com.IN6222.myapplication.bean.MoodType;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    GridView gridView;
    List<MoodType> imglist;
    ImageView selectImg;
    TextView selectDes;
    TextView recordTime;
    EditText title;
    EditText content;
    private GridViewAdapter adapter;
    RecordBean recordBean;
    FloatingActionButton saveButton;


    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        gridView=findViewById(R.id.mood_imgList);
        selectImg=findViewById(R.id.select_img);
        selectDes=findViewById(R.id.select_des);
        recordTime=findViewById(R.id.record_time);
        saveButton=findViewById(R.id.fab_save);
        title=findViewById(R.id.record_Title);
        content=findViewById(R.id.record_content);

        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        time=sdf.format(date);
        recordTime.setText(time);
        
        initBean();


        //load and fill gridview
        LoadMoodImg();
        //set image icon click event
        setImgClick();

        // click fab_save to store data
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String RecordTitle=title.getText().toString();
//                System.out.println("***");
//                System.out.println(RecordTitle);
                String RecordContent=content.getText().toString();
                recordBean.setTitle(RecordTitle);
                recordBean.setContent(RecordContent);
                savetoDB();
                Toast.makeText(getApplicationContext(), "Save Successfully",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void savetoDB() {
        DBManager.insertRecord(recordBean);
    }

    private void initBean() {
        recordBean=new RecordBean();
        recordBean.setDate(time);
        recordBean.setImgId(R.mipmap.happy);
        recordBean.setMood("Happy");

        Calendar calendar=Calendar.getInstance();
        recordBean.setYear(calendar.get(Calendar.YEAR));
        recordBean.setMonth(calendar.get(Calendar.MONTH));

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
    private void setImgClick() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //click icon
                adapter.selectPosition=i;
                adapter.notifyDataSetInvalidated();
                //change the content below
                MoodType moodType=imglist.get(i);
                selectImg.setImageResource(moodType.getImageId());
                selectDes.setText(moodType.getDescription());
                //update recordbean
                recordBean.setImgId(moodType.getImageId());
                recordBean.setMood(moodType.getDescription());
            }
        });
    }




}