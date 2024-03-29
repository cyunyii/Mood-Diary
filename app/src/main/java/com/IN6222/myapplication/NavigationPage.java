package com.IN6222.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.IN6222.myapplication.Adapter.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class NavigationPage extends AppCompatActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{

    RadioButton home,visual,user;
    private ViewPager viewPager;
    private RadioGroup radioGroup;
    private MainFragment mainFragment;
    private VisualFragment visualFragment;
    private LogOutFragment logOutFragment;
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_page);

        //set radiobutton size
        home = (RadioButton) findViewById(R.id.navi_home);
        visual = (RadioButton) findViewById(R.id.navi_visual);
        user=(RadioButton)findViewById(R.id.navi_user);

        Drawable drawableFirst = getResources().getDrawable(R.drawable.home);
        drawableFirst.setBounds(0, 0, 69, 69);//left and right；top and bottom；width；height；
        home.setCompoundDrawables(null, drawableFirst, null, null);

        Drawable drawableSec = getResources().getDrawable(R.drawable.visualization);
        drawableSec.setBounds(0, 0, 69, 69);//
        visual.setCompoundDrawables(null, drawableSec, null, null);

        Drawable drawableThird = getResources().getDrawable(R.drawable.user);
        drawableThird.setBounds(0, 0, 69, 69);
        user.setCompoundDrawables(null, drawableThird, null, null);



        initView();


    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.navi_pager);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);



        mainFragment = new MainFragment();
        visualFragment =new VisualFragment();
        logOutFragment =new LogOutFragment();


        //add fragment to list
        list = new ArrayList<>();
        list.add(mainFragment);
        list.add(visualFragment);
        list.add(logOutFragment);

        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),list);
        viewPager.setAdapter(adapter);

        //set the first button as default
        radioGroup.check(R.id.navi_home);
        //设置Viewpager第一次显示的页面
        viewPager.setCurrentItem(0,true);

        home.setOnClickListener(this);
        visual.setOnClickListener(this);
        user.setOnClickListener(this);

        viewPager.addOnPageChangeListener(this);

    }


    @Override
    public void finish() {
        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        viewGroup.removeAllViews();
        super.finish();
    }

    @Override
    public void onClick(View v) {
        //different id response different viewpager
        switch (v.getId()) {
            case R.id.navi_home:
                viewPager.setCurrentItem(0,true);
                break;
            case R.id.navi_visual:
                viewPager.setCurrentItem(1,true);
                break;
            case R.id.navi_user:
                viewPager.setCurrentItem(2,true);
                break;
            default:
                break;
        }

    }


    //pageChanged
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        //set radioButton status as checked based on the current viewpager
        switch (position) {
            case 0:
                radioGroup.check(R.id.navi_home);
                break;
            case 1:
                radioGroup.check(R.id.navi_visual);
                break;
            case 2:
                radioGroup.check(R.id.navi_user);
                break;
            default:
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}