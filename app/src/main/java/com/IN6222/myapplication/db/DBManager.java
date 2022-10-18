package com.IN6222.myapplication.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Array;
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


}
