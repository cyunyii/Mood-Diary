package com.IN6222.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.IN6222.myapplication.R;
import com.IN6222.myapplication.bean.MoodType;
import com.IN6222.myapplication.bean.RecordBean;
import com.IN6222.myapplication.bean.chartBean;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static final String TAG = "search by type";
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
     *     int day;
     * @param recordBean
     */
    public static long insertRecord(RecordBean recordBean){

        ContentValues values=new ContentValues();
        values.put("mood",recordBean.getMood());
        values.put("imgId",recordBean.getImgId());
        values.put("title",recordBean.getTitle());
        values.put("content",recordBean.getContent());
        values.put("date",recordBean.getDate());
        values.put("year",recordBean.getYear());
        values.put("month",recordBean.getMonth());
        values.put("day",recordBean.getDay());
        long id=db.insert("record", null, values);
        Log.i("MoodType","insert mood type successfully.");
        return id;

    }

    public static void updateRecord(RecordBean recordBean){
        ContentValues values=new ContentValues();
        values.put("id",recordBean.getId());
        values.put("mood",recordBean.getMood());
        values.put("imgId",recordBean.getImgId());
        values.put("title",recordBean.getTitle());
        values.put("content",recordBean.getContent());
        values.put("date",recordBean.getDate());
        values.put("year",recordBean.getYear());
        values.put("month",recordBean.getMonth());
        values.put("day",recordBean.getDay());
        db.update("record",values, "id = ?", new String[] { String.valueOf(recordBean.getId())});

    }

    /**
     * find all records
     *     int id;
     *     String mood;
     *     int imgId;
     *     String title;
     *     String content;
     *     String date;
     *     int year;
     *     int month;
     *     int day;
     */
    public static List<RecordBean> searchAllRecords(){
        List<RecordBean> list=new ArrayList<>();
        String sql="select * from record order by id desc";
        Cursor cursor=db.rawQuery(sql,new String[]{});
        while(cursor.moveToNext()){
            int id= cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String mood=cursor.getString(cursor.getColumnIndexOrThrow("mood"));
            int imgId=cursor.getInt(cursor.getColumnIndexOrThrow("imgId"));
            String title=cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String content=cursor.getString(cursor.getColumnIndexOrThrow("content"));
            String date=cursor.getString(cursor.getColumnIndexOrThrow("date"));
            int year=cursor.getInt(cursor.getColumnIndexOrThrow("year"));
            int month=cursor.getInt(cursor.getColumnIndexOrThrow("month"));
            int day=cursor.getInt(cursor.getColumnIndexOrThrow("day"));
            System.out.println("*****************************************");
            System.out.println(""+id+mood+imgId+title+content+date+year+mood+day);
            RecordBean bean=new RecordBean(id,mood,imgId,title,content,date,year,month,day);
            list.add(bean);
        }
        return list;
    }


    /**
     * deleted record by id
     */
    public static int deleteRecoredById(int id){
        int i=db.delete("record","id=?",new String[]{id+""});
        return i;
    }

    /**
     * search records by keywords
     */
    public static List<RecordBean> searchRecordByKeywords(String keyword){
        List<RecordBean> list=new ArrayList<>();
        String sql="select * from record where title like '%'"+keyword+"'%' or content like '%'"+keyword+"'%' order by id desc";
        String str= "SELECT  * FROM record where title like '%"+keyword+"%' or content like '%"+keyword+"%' order by id desc";
        Cursor cursor=db.rawQuery(str,null);
        while(cursor.moveToNext()){
            int id= cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String mood=cursor.getString(cursor.getColumnIndexOrThrow("mood"));
            int imgId=cursor.getInt(cursor.getColumnIndexOrThrow("imgId"));
            String title=cursor.getString(cursor.getColumnIndexOrThrow("title"));
            String content=cursor.getString(cursor.getColumnIndexOrThrow("content"));
            String date=cursor.getString(cursor.getColumnIndexOrThrow("date"));
            int year=cursor.getInt(cursor.getColumnIndexOrThrow("year"));
            int month=cursor.getInt(cursor.getColumnIndexOrThrow("month"));
            int day=cursor.getInt(cursor.getColumnIndexOrThrow("day"));
            System.out.println("********************blur search*********************");
            System.out.println(""+id+mood+imgId+title+content+date+year+mood+day);
            RecordBean bean=new RecordBean(id,mood,imgId,title,content,date,year,month,day);
            list.add(bean);
        }
        cursor.close();
        return list;
    }


    /**
     * search record numbers for a month
     * @param month
     * @param year
     * @return
     */
    public static int searchCount(int month,int year){
        String sql="select count(*) from record where month=? and year=?";
        Cursor cursor=db.rawQuery(sql,new String[]{""+month,""+year});

        if (cursor.moveToFirst()) {
            int count = cursor.getInt(cursor.getColumnIndexOrThrow("count(*)"));
            return count;
        }
        return 0;

    }

    public static List<chartBean> searchByType(int month, int year) {
        List<chartBean> list=new ArrayList<>();

        Log.d(TAG, "searchByType");

        String sql="select mood,count(*),imgId from record where month=? and year=? group by mood";
        Cursor cursor=db.rawQuery(sql,new String[]{""+month,""+year});
        while(cursor.moveToNext()){
            String mood=cursor.getString(cursor.getColumnIndexOrThrow("mood"));
            int typeCount=cursor.getInt(cursor.getColumnIndexOrThrow("count(*)"));
            int imgId=cursor.getInt(cursor.getColumnIndexOrThrow("imgId"));

            System.out.println("********************type search*********************");
            System.out.println(""+mood+typeCount);
            chartBean bean=new chartBean(mood,typeCount,imgId);
            list.add(bean);
        }
        cursor.close();

        return list;
    }
}
