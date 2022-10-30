package com.IN6222.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.IN6222.myapplication.bean.MoodType;
import com.IN6222.myapplication.bean.RecordBean;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static SQLiteDatabase db;
    public static void innitDB(Context context){
        DBHelper helper=new DBHelper(context);
        //get database
        db=helper.getWritableDatabase();
    }


    public static List<MoodType> getTypeList(){
        List<MoodType> list=new ArrayList<>();
        //read database:table moodtype
        String sql="select * from moodtype";
        Cursor cursor=db.rawQuery(sql,null);
        //loop to read the cursor to store in list
        while(cursor.moveToNext()){
            String des=cursor.getString(cursor.getColumnIndexOrThrow("description"));
            System.out.println("*****************************************");
            System.out.println(des);
            System.out.println("*******************************************");
            int imgId=cursor.getInt(cursor.getColumnIndexOrThrow("imageId"));
            int id=cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            list.add(new MoodType(id,des,imgId));
        }


        return list;
    }

    /**
     *     int id;
     *     String mood;
     *     int imgId;
     *     String title;
     *     String content;
     *     String date;
     *     int year;
     *     int month;
     * @param recordBean
     */
    public static void insertRecord(RecordBean recordBean){

        ContentValues values=new ContentValues();
        values.put("mood",recordBean.getMood());
        values.put("imgId",recordBean.getImgId());
        values.put("title",recordBean.getTitle());
        values.put("content",recordBean.getContent());
        values.put("date",recordBean.getDate());
        values.put("year",recordBean.getYear());
        values.put("month",recordBean.getMonth());
        db.insert("record", null, values);
        Log.i("MoodType","insert mood type successfully.");
    }



}
