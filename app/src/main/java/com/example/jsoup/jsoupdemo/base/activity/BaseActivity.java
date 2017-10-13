package com.example.jsoup.jsoupdemo.base.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.jsoup.jsoupdemo.base.IActivityManager;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getIActivityManager().add(this);
        initBaseView();
        initfindViewById();
        loadData();
    }
    protected abstract void initBaseView();

    /**
     * 初始化界面
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * 初始化控件
     */
    protected abstract void initfindViewById();

    /**
     * 初始化 数据加载
     */
    protected abstract void loadData();

    protected abstract IActivityManager getIActivityManager();

    protected Activity getActivity() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getIActivityManager().delete(this);
    }
}
