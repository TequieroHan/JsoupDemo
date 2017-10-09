package com.example.jsoup.jsoupdemo.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.jsoup.jsoupdemo.R;

/**
 * Created by Administrator on 2017/10/9.
 */

public class MyDialog extends Dialog {
    private Window mWindow;

    public MyDialog(@NonNull Context context) {
        super(context);
    }

    public void showDialog(View view, int x, int y) {
        setContentView(view);
        widowsDeploy(x, y);
        setCanceledOnTouchOutside(true);
        show();
    }

    private void widowsDeploy(int x, int y) {
        mWindow = getWindow();
        mWindow.setWindowAnimations(R.style.dialogWindow);
        mWindow.setBackgroundDrawableResource(R.color.colorAccent);//vifrification
        WindowManager.LayoutParams attributes = mWindow.getAttributes();
        attributes.x = x;
        attributes.y = y;
        mWindow.setAttributes(attributes);
    }

}
