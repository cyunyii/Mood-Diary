package com.IN6222.myapplication.RecordPage;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.IN6222.myapplication.R;
import com.IN6222.myapplication.bean.MoodType;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    public int selectPosition=0;
    Context context;
    List<MoodType> moodTypesList;

    public GridViewAdapter(Context context, List<MoodType> moodTypesList) {
        this.context = context;
        this.moodTypesList = moodTypesList;
    }

    @Override
    public int getCount() {
        return moodTypesList.size();
    }

    @Override
    public Object getItem(int i) {
        return moodTypesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= LayoutInflater.from(context).inflate(R.layout.record_gridview,viewGroup,false);
        //find the components
        ImageView img=view.findViewById(R.id.record_gv_img);
        TextView text=view.findViewById(R.id.record_gv_text);
        //set values
        MoodType moodType=moodTypesList.get(i);
        img.setImageResource(moodType.getImageId());
        text.setText(moodType.getDescription());
        if(selectPosition==i){
            text.setTextSize(14);
            text.setTypeface(null, Typeface.BOLD);
        }


        return view;
    }
}
