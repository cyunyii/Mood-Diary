package com.IN6222.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.IN6222.myapplication.Adapter.RecordItemAdapter;
import com.IN6222.myapplication.bean.RecordBean;
import com.IN6222.myapplication.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class searchActivity extends AppCompatActivity {

    ImageView back,search;
    EditText editText;
    ListView listView;
    List<RecordBean> datas;
    RecordItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        editText=findViewById(R.id.search_editText);
        listView=findViewById(R.id.search_listview);
        datas=new ArrayList<>();
        adapter=new RecordItemAdapter(this,datas);
        listView.setAdapter(adapter);

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.search_back:
                finish();
                break;
            case R.id.search_search_btn:
                String keyword=editText.getText().toString().trim();
                if(TextUtils.isEmpty(keyword)){
                    Toast.makeText(this,"Invalid Input",Toast.LENGTH_SHORT).show();
                    return;
                }
                List<RecordBean> list= DBManager.searchRecordByKeywords(keyword);
                if(list.size()==0){
                    Toast.makeText(this,"No records found",Toast.LENGTH_SHORT).show();
                }else{
                    datas.clear();
                    datas.addAll(list);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }
}