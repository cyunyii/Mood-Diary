package com.IN6222.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public abstract class BaseFragment extends Fragment {

    protected String TAG = BaseFragment.class.getSimpleName();

    //Root View
    protected View view;

    //布局是否初始化完成
    private boolean isLayoutInitialized = false;
    //记录页面可见性
    private boolean isVisibleToUser = false;
    //不可见时释放部分资源
    private boolean isInVisibleRelease = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, getClass().getSimpleName() + "  onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, getClass().getSimpleName() + "  onCreateView");
        view = inflater.inflate(initLayout(),null);
        initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, getClass().getSimpleName() + "  onDestroyView");

        //页面释放后，重置布局初始化状态变量
        isLayoutInitialized = false;
        this.view = null;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, getClass().getSimpleName() + "  onActivityCreated");
        //此方法是在第一次初始化时onCreateView之后触发的
        //onCreateView和onActivityCreated中分别应该初始化哪些数据可以参考：
        //https://stackoverflow.com/questions/8041206/android-fragment-oncreateview-vs-onactivitycreated

        isLayoutInitialized = true;
        //第一次初始化后需要处理一次可见性事件
        //因为第一次初始化时setUserVisibleHint方法的触发要先于onCreateView
        dispatchVisibleEvent();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, getClass().getSimpleName() + "  onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, getClass().getSimpleName() + "  onResume");

        //页面从其他Activity返回时，重新加载被释放的资源
        if(isLayoutInitialized && isInVisibleRelease){
            dispatchVisibleEvent();
            isInVisibleRelease = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, getClass().getSimpleName() + "  onPause");

        //当从Fragment切换到其他Activity释放部分资源
        if(isVisibleToUser){
            //页面从可见切换到不可见时触发，可以释放部分资源，配合reload方法再次进入页面时加载
//            inVisibleRelease();

            isInVisibleRelease = true;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, getClass().getSimpleName() + "  onDestroy");

        //重置所有数据
        this.view = null;
        isLayoutInitialized = false;
        isVisibleToUser = false;
        isInVisibleRelease = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, getClass().getSimpleName() + "  setUserVisibleHint isVisibleToUser = " + isVisibleToUser);

        dispatchVisibleEvent();
    }

    /**
     * 处理可见性事件
     */
    private void dispatchVisibleEvent(){
        Log.d(TAG, getClass().getSimpleName() + "  dispatchVisibleEvent isVisibleToUser = " + getUserVisibleHint()
                + " --- isLayoutInitialized = " + isLayoutInitialized );

        if(getUserVisibleHint() && isLayoutInitialized){
            lazyLoad();
        }
        //处理完可见性事件之后修改isVisibleToUser状态
        this.isVisibleToUser = getUserVisibleHint();
    }

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 绑定布局
     * @return 布局ID
     */
    protected abstract int initLayout();

    /**
     * 懒加载<br/>
     * 只会在初始化后第一次可见时调用一次。
     */
    protected abstract void lazyLoad();

    /**
     * 刷新数据加载<br/>
     * 配合{@link #lazyLoad()}，在页面非第一次可见时刷新数据
     */

}