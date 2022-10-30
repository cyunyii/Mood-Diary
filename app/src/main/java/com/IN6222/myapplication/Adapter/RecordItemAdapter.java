package com.IN6222.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.IN6222.myapplication.R;
import com.IN6222.myapplication.bean.RecordBean;

import java.util.List;
import java.util.zip.Inflater;

public class RecordItemAdapter extends BaseAdapter {
    Context context;
    List<RecordBean> recordsList;
    LayoutInflater inflater;

    public RecordItemAdapter(Context context, List<RecordBean> recordsList) {
        this.context = context;
        this.recordsList = recordsList;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return recordsList.size();
    }

    @Override
    public Object getItem(int i) {
        return recordsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            view=inflater.inflate(R.layout.item_detail,viewGroup,false);
            viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) view.getTag();
        }
        RecordBean recordBean=recordsList.get(i);
        viewHolder.mood.setImageResource(recordBean.getImgId());
        viewHolder.des.setText(recordBean.getMood());
        viewHolder.title.setText(recordBean.getTitle());
        viewHolder.time.setText(recordBean.getDate());
        return view;
    }

    class ViewHolder{
        ImageView mood;
        TextView des,title,time;
        public ViewHolder(View view){
            mood=view.findViewById(R.id.item_img);
            des=view.findViewById(R.id.item_mood);
            title=view.findViewById(R.id.item_title);
            time=view.findViewById(R.id.item_time);
        }
    }

}
