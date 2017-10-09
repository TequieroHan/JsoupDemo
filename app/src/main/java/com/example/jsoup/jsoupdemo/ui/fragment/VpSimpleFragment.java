package com.example.jsoup.jsoupdemo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jsoup.jsoupdemo.R;
import com.example.jsoup.jsoupdemo.utils.ScreenUtils;
import com.example.jsoup.jsoupdemo.view.dialog.MyDialog;

import butterknife.BindView;

public class VpSimpleFragment extends Fragment {
    public static final String BUNDLE_TITLE = "title";
    private String mTitle = "DefaultValue";
    public TextView tvDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.layout_fragment, container, false);
        tvDialog = (TextView) inflate.findViewById(R.id.tv_dialog);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(BUNDLE_TITLE);
        }
        tvDialog.setText(mTitle);
        tvDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog dialog = new MyDialog(getActivity());
                View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popuw_content_top_arrow_layout, null);
                final int[] location = new int[2];
                final int windowPos[] = new int[2];
                tvDialog.getLocationOnScreen(location);
                int height = tvDialog.getHeight();
                int x = location[0];
                int y = location[1];
                Log.e("TAG","x:" + x + "y:" + y);
                int screenHeight = ScreenUtils.getScreenHeight(getContext());
                int screenWidth = ScreenUtils.getScreenWidth(getContext());
                // 计算contentView的高宽
                final int windowHeight = contentView.getMeasuredHeight();
                final int windowWidth = contentView.getMeasuredWidth();
                // 判断需要向上弹出还是向下弹出显示
                final boolean isNeedShowUp = (screenHeight - location[1] - height < windowHeight);
                if (isNeedShowUp) {
                    windowPos[0] = screenWidth - windowWidth;
                    windowPos[1] = location[1] - windowHeight;
                } else {
                    windowPos[0] = screenWidth - windowWidth;
                    windowPos[1] = location[1] + height;
                }
                dialog.showDialog(contentView, windowPos[0], windowPos[1]);
            }
        });

        return inflate;
    }

    public static VpSimpleFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        VpSimpleFragment fragment = new VpSimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
