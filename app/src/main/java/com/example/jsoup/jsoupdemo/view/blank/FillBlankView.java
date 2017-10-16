package com.example.jsoup.jsoupdemo.view.blank;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jsoup.jsoupdemo.R;
import com.example.jsoup.jsoupdemo.view.blank.mobel.AnswerRange;

import java.util.ArrayList;
import java.util.List;

/**
 * 可做完形填空
 * 填空视图
 * Created by Administrator on 2017/10/13.
 */

public class FillBlankView extends RelativeLayout {


    private TextView tvContent;
    private LinearLayout mLinearVertical;
    // 答案集合
    private List<String> answerList;
    // 答案范围集合
    private List<AnswerRange> rangeList;
    // 填空题内容
    private SpannableStringBuilder mSpannableStrBuilder;

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
        if (!answerList.isEmpty() && answerList.size() != 0) {
            mLinearVertical = (LinearLayout) findViewById(R.id.linear_vertical);
        }
    }

    public void setData(String content, ArrayList<AnswerRange> aRangeAll) {
        if (TextUtils.isEmpty(content) || aRangeAll == null || aRangeAll.isEmpty()) {
            return;
        }
        mSpannableStrBuilder = new SpannableStringBuilder(content);

        answerList = new ArrayList<>();
    }


    public void setAnswerList(ArrayList<String> strList) {
        this.answerList = strList;
        invalidate();
    }

}
