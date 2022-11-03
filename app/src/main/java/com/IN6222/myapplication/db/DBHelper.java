package com.IN6222.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.IN6222.myapplication.R;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "moodDiary.db", null, 1);
    }

    /**
     * call the method when initializing database
     * @param sqLiteDatabase
     *     int id;
     *     String description;
     *     int imageId;
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create moodType table
        String sql="create table moodtype(id integer primary key autoincrement,description varchar(10),imageId integer)";
        sqLiteDatabase.execSQL(sql);
        insertType(sqLiteDatabase);

        //create record table
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
         */
        String RecordSql="create table record(id integer primary key autoincrement,mood varchar(10),uid varchar(200), imgId integer,title varchar(20),content varchar(65535)," +
                "date varchar(20), year integer, month integer, day integer)";
        sqLiteDatabase.execSQL(RecordSql);

    }

    private void insertType(SQLiteDatabase sqLiteDatabase) {
        String sql="insert into moodtype (description,imageId) values (?,?)";
        sqLiteDatabase.execSQL(sql,new Object[]{"Happy", R.mipmap.happy});
        sqLiteDatabase.execSQL(sql,new Object[]{"Cool", R.mipmap.cool});
        sqLiteDatabase.execSQL(sql,new Object[]{"Crying", R.mipmap.cry});
        sqLiteDatabase.execSQL(sql,new Object[]{"Sad", R.mipmap.sad});
        sqLiteDatabase.execSQL(sql,new Object[]{"Sick", R.mipmap.sick});
        sqLiteDatabase.execSQL(sql,new Object[]{"Angry", R.mipmap.angry});
        sqLiteDatabase.execSQL(sql,new Object[]{"Tired", R.mipmap.tired});
        sqLiteDatabase.execSQL(sql,new Object[]{"Scared", R.mipmap.scared});
        sqLiteDatabase.execSQL(sql,new Object[]{"Sleepy", R.mipmap.sleeping});
        sqLiteDatabase.execSQL(sql,new Object[]{"InLove", R.mipmap.inlove});

    }

    /**
     * call the method when updating database(version...)
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
