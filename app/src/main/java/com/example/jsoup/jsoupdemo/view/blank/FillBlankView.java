package com.example.jsoup.jsoupdemo.view.blank;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jsoup.jsoupdemo.R;

/**
 * 可做完形填空
 * 填空视图
 * Created by Administrator on 2017/10/13.
 */

public class FillBlankView extends RelativeLayout {


    private int mMenuFlag = 0;//非零的显示底部

    private TextView tvContent;
    private LinearLayout mLinearVertical;

    public FillBlankView(Context context) {
        this(context, null);
    }

    public FillBlankView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FillBlankView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.layout_fillblank, this);
        tvContent = (TextView) findViewById(R.id.tv_content);
        if (mMenuFlag != 0) {
            mLinearVertical = (LinearLayout) findViewById(R.id.linear_vertical);
        }
    }

//    public void setData(String content,)


    public void setMenuFlag(int menuFlag) {
        this.mMenuFlag = menuFlag;
        invalidate();
    }

}
