package com.example.jsoup.jsoupdemo.ui.fragment;

import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jsoup.jsoupdemo.R;
import com.example.jsoup.jsoupdemo.base.fragment.BaseFragment;
import com.example.jsoup.jsoupdemo.ui.fragment.mobel.MenuInfo;
import com.example.jsoup.jsoupdemo.view.circelimage.CircelImageView;

import java.util.List;

import butterknife.BindView;
import fj.mtsortbutton.lib.DynamicSoreView;
import fj.mtsortbutton.lib.Interface.IDynamicSore;

public class VpSimpleFragment extends BaseFragment implements IDynamicSore {
    public static final String BUNDLE_TITLE = "title";
    private String mTitle = "DefaultValue";
    @BindView(R.id.tv_dialog)
    TextView tvDialog;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    @BindView(R.id.checkbox)
    CircelImageView checkbox;
    @BindView(R.id.dynamicsoreview)
    DynamicSoreView dynamicSoreview;
    private List<MenuInfo> menuInfos;
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
        checkbox.setImageResource(R.drawable.icon_bg);
        rlContent.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                return false;
            }
        });
        dynamicSoreview.setiDynamicSore(this);
        dynamicSoreview.setGridView(R.layout.viewpager_page).init(menuInfos);
    }

    public static VpSimpleFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        VpSimpleFragment fragment = new VpSimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setGridView(View view, int type, List data) {
        List<MenuInfo> menuInfoList = data;
        GridView gridView = (GridView)view.findViewById(R.id.gridView);
        dynamicSoreview.setNumColumns(gridView);

    }
}
