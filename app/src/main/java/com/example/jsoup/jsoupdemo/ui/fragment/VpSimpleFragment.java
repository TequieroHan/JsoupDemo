package com.example.jsoup.jsoupdemo.ui.fragment;

import android.content.ClipData;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jsoup.jsoupdemo.R;
import com.example.jsoup.jsoupdemo.base.fragment.BaseFragment;

import butterknife.BindView;

public class VpSimpleFragment extends BaseFragment {
    public static final String BUNDLE_TITLE = "title";
    private String mTitle = "DefaultValue";
    @BindView(R.id.tv_dialog)
    TextView tvDialog;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    @BindView(R.id.iv_btn)
    ImageView ivBtn;

    @Override
    public int getLayoutResId() {
        return R.layout.layout_fragment;
    }

    @Override
    public void initVariable() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(BUNDLE_TITLE);
        }
    }

    @Override
    public void initView() {
        tvDialog.setText(mTitle);

        ivBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ClipData.Item cDataItem = new ClipData.Item("测试测试");

                return false;
            }
        });

        rlContent.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                return false;
            }
        });

    }

    public static VpSimpleFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        VpSimpleFragment fragment = new VpSimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
