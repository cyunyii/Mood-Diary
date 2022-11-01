package com.IN6222.myapplication.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;

import com.IN6222.myapplication.R;


public class SelectTimeDialog extends Dialog {
    ImageView cancel;
    TextView done;
    TextView selectedTime;
    DatePicker datePicker;
    TimePicker timePicker;
    TimeEnsureListener timeEnsureListener;
    public interface TimeEnsureListener {
        void onEnsure(String time,int year,int month,int day);
    }

    public void setTimeEnsureListener(TimeEnsureListener timeEnsureListener){
        this.timeEnsureListener=timeEnsureListener;
    }


    public SelectTimeDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_time);
        cancel=findViewById(R.id.dialog_time_cancel);
        done=findViewById(R.id.dialog_done);
        selectedTime=findViewById(R.id.dialog_time_selectedTime);
        datePicker=findViewById(R.id.dialog_time_datePicker);
        timePicker=findViewById(R.id.dialog_timepicker);
        timePicker.setIs24HourView(true);

        //click back to cancel
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year=datePicker.getYear();
                int month=datePicker.getMonth()+1;
                int day=datePicker.getDayOfMonth();
                System.out.println(year);
                System.out.println(month);
                System.out.println(day);
                String monthStr=(month<10)?"0"+month:String.valueOf(month);
                String dayStr=(day<10)?"0"+day:String.valueOf(day);


                int hour=timePicker.getHour();
                int minute=timePicker.getMinute();
                String hourStr=(hour<10)?"0"+hour:String.valueOf(hour);
                String minuteStr=(minute<10)?"0"+minute:String.valueOf(minute);
                System.out.println(hourStr);

                String time=String.valueOf(year)+'/'+monthStr+'/'+dayStr+' '+hourStr+':'+minuteStr;
                if(timeEnsureListener!=null) {
                    timeEnsureListener.onEnsure(time, year, month, day);
                }
                cancel();

            }
        });

    }
}
