package com.example.jsoup.jsoupdemo.base.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/12.
 */

public abstract class BaseFragment extends Fragment {

    public View mView;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", " Fragment onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        mView = View.inflate(getActivity(), getLayoutResId(), null);
        return mView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initVariable();
        initView();
    }

    /**
     * 获取父布局layout
     *
     * @return layoutResId
     */
    @LayoutRes
    public abstract int getLayoutResId();

    /**
     * 初始化变量
     */
    public abstract void initVariable();

    /**
     * 初始化UI界面
     */
    public abstract void initView();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }


}
