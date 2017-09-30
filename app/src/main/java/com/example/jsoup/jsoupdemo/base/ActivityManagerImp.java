package com.example.jsoup.jsoupdemo.base;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/15.
 */

public class ActivityManagerImp implements IActivityManager {

    public volatile List<Activity> mActivities;
    public static ActivityManagerImp mActivityManagerImp;

    public ActivityManagerImp() {
        mActivities = new ArrayList<>();
    }

    public static ActivityManagerImp getInstance() {
        if (mActivityManagerImp == null) {
            synchronized (ActivityManagerImp.class) {
                mActivityManagerImp = new ActivityManagerImp();
            }
        }
        return mActivityManagerImp;
    }

    @Override
    public List<Activity> getActiivtys() {
        return mActivities;
    }

    @Override
    public void add(Activity activity) {
        synchronized (this) {
            mActivities.add(activity);
        }
    }

    @Override
    public void delete(Activity activity) {
        synchronized (this) {
            mActivities.remove(activity);
        }
    }

    @Override
    public Activity delete(Class<? extends Activity> activity) {
        synchronized (this) {
            for (Activity a : mActivities) {
                mActivities.remove(a);
                return a;
            }
        }
        return null;
    }
}
