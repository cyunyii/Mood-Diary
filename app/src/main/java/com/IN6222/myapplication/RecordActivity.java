package com.IN6222.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.IN6222.myapplication.Adapter.GridViewAdapter;
import com.IN6222.myapplication.bean.RecordBean;
import com.IN6222.myapplication.db.DBManager;
import com.IN6222.myapplication.bean.MoodType;
import com.IN6222.myapplication.utils.SelectTimeDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
    private FirebaseUser user;
    String uid;

    String time;

    //recordBean.id (use to update)
    long id;
    boolean firstTimeSave=true;


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

        user = FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();

        //load and fill gridview
        LoadMoodImg();

        Intent intent=getIntent();
        if(intent!=null){
            RecordBean bean=(RecordBean) intent.getSerializableExtra("bean");
            if(bean!=null){
                this.recordBean=bean;
                restoreDetail();
                firstTimeSave=false;
            }
            else {
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                time = sdf.format(date);
                recordTime.setText(time);
                initBean();
            }
        }
        //set image icon click event
        setImgClick();
        //inflate from activity_main, display details immediately


        // click fab_save to store data
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String RecordTitle=title.getText().toString();
                String RecordContent=content.getText().toString();
                if(TextUtils.isEmpty(RecordTitle)) {
                    Toast.makeText(RecordActivity.this, "Please input title", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(RecordContent)) {
                    Toast.makeText(RecordActivity.this, "Please input Ccontent", Toast.LENGTH_LONG).show();
                    return;
                }
                recordBean.setTitle(RecordTitle);
                recordBean.setContent(RecordContent);
                if(firstTimeSave){
                    id=savetoDB();
                    firstTimeSave=false;
                }else{
                    updateDB();
                }

                Toast.makeText(getApplicationContext(), "Save Successfully",
                        Toast.LENGTH_SHORT).show();

            }
        });

        recordTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog();
            }
        });
    }



    private void showTimeDialog() {
        SelectTimeDialog selectTimeDialog = new SelectTimeDialog(this);
        selectTimeDialog.show();
        //set ensureListener
        selectTimeDialog.setTimeEnsureListener(new SelectTimeDialog.TimeEnsureListener() {
            @Override
            public void onEnsure(String time, int year, int month, int day) {
                recordTime.setText(time);
                recordBean.setDate(time);
                recordBean.setDay(day);
                recordBean.setYear(year);
                recordBean.setMonth(month);
            }
        });


    }

    private long savetoDB() {
        return DBManager.insertRecord(recordBean);
    }
    private void updateDB() {
        DBManager.updateRecord(recordBean);
    }

    private void initBean() {
        recordBean=new RecordBean();
        recordBean.setDate(time);
        recordBean.setImgId(R.mipmap.happy);
        recordBean.setMood("Happy");
        recordBean.setUid(uid);
        Log.d("initBean", uid);
        Calendar calendar=Calendar.getInstance();
        recordBean.setYear(calendar.get(Calendar.YEAR));
        recordBean.setMonth(calendar.get(Calendar.MONTH)+1);
        recordBean.setDay(calendar.get(Calendar.DAY_OF_MONTH));


    }

    /**
     *     GridView gridView;
     *     List<MoodType> imglist;
     *     ImageView selectImg;
     *     TextView selectDes;
     *     TextView recordTime;
     *     EditText title;
     *     EditText content;
     *     private GridViewAdapter adapter;
     *     RecordBean recordBean;
     *     FloatingActionButton saveButton;
     */
    private void restoreDetail() {
        System.out.println("--------------");
        System.out.println(recordBean.getContent());
        selectImg.setImageResource(recordBean.getImgId());
        selectDes.setText(recordBean.getMood());
        recordTime.setText(recordBean.getDate());
        title.setText(recordBean.getTitle());
        content.setText(recordBean.getContent());

        adapter.notifyDataSetChanged();


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