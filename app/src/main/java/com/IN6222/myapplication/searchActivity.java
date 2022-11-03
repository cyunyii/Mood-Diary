package com.IN6222.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.IN6222.myapplication.Adapter.RecordItemAdapter;
import com.IN6222.myapplication.bean.RecordBean;
import com.IN6222.myapplication.db.DBManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class searchActivity extends AppCompatActivity {

    ImageView back,search;
    EditText editText;
    ListView listView;
    List<RecordBean> datas;
    RecordItemAdapter adapter;
    private FirebaseAuth mFirebaseAuth;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        editText=findViewById(R.id.search_editText);
        listView=findViewById(R.id.search_listview);
        datas=new ArrayList<>();
        adapter=new RecordItemAdapter(this,datas);
        listView.setAdapter(adapter);
        mFirebaseAuth=FirebaseAuth.getInstance();
        uid=mFirebaseAuth.getUid();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecordBean recordBean=datas.get(i);
                Intent intent=new Intent(searchActivity.this,RecordActivity.class);
                intent.putExtra("bean",recordBean);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecordBean clickbean=datas.get(i);
                DeleteDialog(clickbean);
                return true;
            }
        });

    }


    private void DeleteDialog(RecordBean bean) {
        AlertDialog.Builder builder=new AlertDialog.Builder(searchActivity.this);
        builder.setTitle("Alert").setMessage("Are you sure to delete this record?")
                .setNegativeButton("cancel",null)
                .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBManager.deleteRecoredById(bean.getId());
                        datas.remove(bean);
                        adapter.notifyDataSetChanged();
                    }
                });
        builder.create().show();
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
                List<RecordBean> list= DBManager.searchRecordByKeywords(keyword,uid);
                if(list.size()==0){
                    Toast.makeText(this,"No records found",Toast.LENGTH_SHORT).show();
                    datas.clear();
                    datas.addAll(list);
                    adapter.notifyDataSetChanged();
                }else{
                    datas.clear();
                    datas.addAll(list);
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }
}