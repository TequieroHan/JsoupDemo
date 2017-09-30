package com.example.jsoup.jsoupdemo.base;

import android.app.Activity;

import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface IActivityManager {
    List<Activity> getActiivtys();

    void add(Activity activity);

    void delete(Activity activity);

    Activity delete(Class<? extends Activity> activity);
}
