package com.IN6222.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
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