package com.example.jsoup.jsoupdemo.base;

import com.example.jsoup.jsoupdemo.net.ApiStores;
import com.example.jsoup.jsoupdemo.net.AppClient;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public abstract class BaseMainActivity extends BaseActivity {

    public ApiStores mApiStores =  AppClient.retrofit().create(ApiStores.class);

    @Override
    protected void initBaseView() {
        setContentView(getLayoutResId());
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected IActivityManager getIActivityManager() {
        return ActivityManagerImp.getInstance();
    }
}
