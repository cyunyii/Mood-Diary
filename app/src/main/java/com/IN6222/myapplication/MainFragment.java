package com.IN6222.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.IN6222.myapplication.Adapter.GridViewAdapter;
import com.IN6222.myapplication.Adapter.RecordItemAdapter;
import com.IN6222.myapplication.bean.MoodType;
import com.IN6222.myapplication.bean.RecordBean;
import com.IN6222.myapplication.db.DBManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {


    ListView listView;
    //data
    List<RecordBean> recordList;
    RecordItemAdapter adapter;
    FloatingActionButton fab;
    ImageView search;
    FirebaseUser user;
    private String uid;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        listView = view.findViewById(R.id.main_lv);
        fab=view.findViewById(R.id.fab);
        search=view.findViewById(R.id.main_search);

        recordList = new ArrayList<>();
        adapter = new RecordItemAdapter(getContext(), recordList);
        listView.setAdapter(adapter);

        user = FirebaseAuth.getInstance().getCurrentUser();

        uid=user.getUid();
        Log.d("user", uid);

        return view;
    }


    private void DeleteDialog(RecordBean bean) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Alert").setMessage("Are you sure to delete this record?")
                .setNegativeButton("cancel",null)
                .setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DBManager.deleteRecoredById(bean.getId());
                        recordList.remove(bean);
                        adapter.notifyDataSetChanged();
                    }
                });
        builder.create().show();
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("$$$$$$$$$$$$$$$$$$$$ð");
        loadDataBase();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecordBean recordBean=recordList.get(i);
                Intent intent=new Intent(getContext(),RecordActivity.class);
                intent.putExtra("bean",recordBean);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecordBean clickbean=recordList.get(i);
                DeleteDialog(clickbean);
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),RecordActivity.class);
                startActivity(i);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),searchActivity.class);
                startActivity(i);
            }
        });


    }

    private void loadDataBase() {
        List<RecordBean> list= DBManager.searchAllRecords(uid);
        Log.d("MainFragmentData", uid+list.size());
        recordList.clear();
        recordList.addAll(list);
        adapter.notifyDataSetChanged();
    }

}