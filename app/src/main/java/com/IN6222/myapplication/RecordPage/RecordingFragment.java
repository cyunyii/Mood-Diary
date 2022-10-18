package com.IN6222.myapplication.RecordPage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.IN6222.myapplication.R;
import com.IN6222.myapplication.db.DBManager;
import com.IN6222.myapplication.db.MoodType;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordingFragment extends Fragment {

    GridView gridView;
    List<MoodType> imglist;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridViewAdapter adapter;

    public RecordingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordingFragment newInstance(String param1, String param2) {
        RecordingFragment fragment = new RecordingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_recording, container, false);
        gridView=view.findViewById(R.id.mood_icon);
        LoadMoodImg();

        return view;
    }

    /**
     * load img icons in the recording page(Gridview)
     */
    private void LoadMoodImg() {
        System.out.println("HEHHHHHHH*******************************");
        imglist=new ArrayList<>();
        adapter = new GridViewAdapter(getContext(), imglist);
        gridView.setAdapter(adapter);
        //load data from database
        imglist.addAll(DBManager.getTypeList());
        adapter.notifyDataSetChanged();

    }
}