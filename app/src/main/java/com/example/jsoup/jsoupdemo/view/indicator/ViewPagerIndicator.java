package com.example.jsoup.jsoupdemo.view.indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.Space;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jsoup.jsoupdemo.R;
import com.example.jsoup.jsoupdemo.utils.ScreenUtils;
import com.example.jsoup.jsoupdemo.view.model.PositionData;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager指示器
 * Created by Administrator on 2017/9/21.
 */

public class ViewPagerIndicator extends HorizontalScrollView {

    /**
     * 绘制三角形的画笔
     */
    private Paint mPaint;
    /**
     * 默认画笔颜色
     */
    private int mDefaultPaintColor = Color.parseColor("#FCFCFC");

    /**
     * 画笔颜色
     */
    private int mPaintColor = mDefaultPaintColor;

    /**
     * path构成一个三角形
     */

    private Path mPath;

    /**
     * 三角形的宽度
     */
    private int mTriangleWidth;
    /**
     * 三角形的高度
     */
    private int mTriangleHeight;

    /**
     * 三角形的宽度为单个Tab的1/6
     */
    private static final float mTable_Traigle_Width = 1.0f / 3;

    /**
     * 三角形的最大宽度
     */
    private final int Max_Traigle_Width = (int) (getScreenWidthPx() / 3 * mTable_Traigle_Width);

    /**
     * 初始时，三角形指示器的偏移量
     */
    private float mInitTranslationX;

    /**
     * 手指滑动时的偏移量
     */
    private float mTranslationX;

    /**
     * 默认的Tab数量
     */
    private int mDefaultTableCount = 4;

    /**
     * tab数量
     */
    private int mTableCount = mDefaultTableCount;

    /**
     * tab上的内容
     */
    private List<String> mTitles;

    /**
     * 与之绑定的ViewPager
     */

    private ViewPager mViewPager;

    /**
     * 标题正常时的颜色
     */

    private int mNormalTitleColor = Color.parseColor("#F2F2F2");

    /**
     * 标题选中时的颜色
     */
    private int mSelectTitleColor = Color.parseColor("#ff0000");
    /**
     * 默认字体大小
     */
    private float mDefaultTitleSize = getResources().getDimensionPixelSize(R.dimen.base14dp);

    private float mTitleSize = mDefaultTitleSize;
    /**
     * 保存指示器标题的坐标
     */
    private ArrayList<PositionData> mPositionData = new ArrayList<>();
    /**
     * 指示器总宽度
     */
    public int mCountWidth = 0;
    private float mScrollPivotX = 0.5f;// 滚动中心点 0.0f - 1.0f
    private LinearLayout mLinear;

    public ViewPagerIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("TAG", "ViewPagerIndicator");
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.viewpagerIndicator);
        mNormalTitleColor = typedArray.getColor(R.styleable.viewpagerIndicator_normalTitleColor, mNormalTitleColor);
        mSelectTitleColor = typedArray.getColor(R.styleable.viewpagerIndicator_selectTitleColor, mSelectTitleColor);
        mPaintColor = typedArray.getColor(R.styleable.viewpagerIndicator_paintColor, mDefaultPaintColor);
        mTitleSize = typedArray.getDimension(R.styleable.viewpagerIndicator_titleSize, mDefaultTitleSize);
        if (mTitles != null && mTitles.size() > 0) {
            mTableCount = mTitles.size();
        }
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mPaintColor);
        mPaint.setTextSize(mTitleSize);
        mPaint.setAntiAlias(true);
        mPaint.setPathEffect(new CornerPathEffect(2.0f));//将Path线连接

        mTriangleWidth = ScreenUtils.dip2px(context, 14);
        mTriangleHeight = ScreenUtils.dip2px(context, 8);

        mLinear = new LinearLayout(context);
        mLinear.setOrientation(LinearLayout.HORIZONTAL);
        mLinear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    /**
     * 绘制指示器
     *
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.e("TAG", "dispatchDraw ------>");
        canvas.save();
        canvas.translate(mInitTranslationX + mTranslationX - 20, getHeight() + 1);//创建TextView的时候给了Margins
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    // 对外的ViewPager的回调接口
    private PageChangeListener onPageChangeListener;

    /**
     * 对外的ViewPager的回调接口
     */
    public interface PageChangeListener {
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels);

        public void onPageSelected(int position);

        public void onPageScrollStateChanged(int state);
    }


    // 对外的ViewPager的回调接口的设置
    public void setOnPageChangeListener(PageChangeListener pageChangeListener) {
        this.onPageChangeListener = pageChangeListener;
    }


    public static PositionData getImitativePositionData(List<PositionData> positionDataList, int index) {
        if (index >= 0 && index <= positionDataList.size() - 1) { // 越界后，返回假的PositionData
            return positionDataList.get(index);
        } else {
            PositionData result = new PositionData();
            PositionData referenceData;
            int offset;
            if (index < 0) {
                offset = index;
                referenceData = positionDataList.get(0);
            } else {
                offset = index - positionDataList.size() + 1;
                referenceData = positionDataList.get(positionDataList.size() - 1);
            }
            result.mLeft = referenceData.mLeft + offset * referenceData.width();
            result.mTop = referenceData.mTop;
            result.mRight = referenceData.mRight + offset * referenceData.width();
            result.mBottom = referenceData.mBottom;
            result.mContentLeft = referenceData.mContentLeft + offset * referenceData.width();
            result.mContentTop = referenceData.mContentTop;
            result.mContentRight = referenceData.mContentRight + offset * referenceData.width();
            result.mContentBottom = referenceData.mContentBottom;
            return result;
        }
    }

    public void setViewPager(ViewPager viewPager, int position) {
        this.mViewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("TAG", "onPageScrolled----------" + position);
                if (mPositionData == null || mPositionData.isEmpty()) {
                    return;
                }
                PositionData currentPosition = getImitativePositionData(mPositionData, position);
                PositionData nextPosition = getImitativePositionData(mPositionData, position + 1);
                float leftX = currentPosition.mLeft + (currentPosition.mRight - currentPosition.mLeft) / 2;
                float rightX = nextPosition.mLeft + (nextPosition.mRight - nextPosition.mLeft) / 2;

                mInitTranslationX = leftX + (rightX - leftX) * positionOffset;

                onscroll(position, positionOffset);
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

            }

            @Override
            public void onPageSelected(int position) {
                Log.e("TAG", "onPageSelected----------" + position);
                setNormalTitleText();
                setSelectTitleText(position);
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("TAG", "onPageScrollStateChanged----------" + state);
                if (onPageChangeListener != null) {
                    onPageChangeListener.onPageScrollStateChanged(state);
                }
            }
        });
    }


    /**
     * 高亮标题
     *
     * @param positon
     */
    protected void setSelectTitleText(int positon) {
        View view = mLinear.getChildAt(positon);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(mSelectTitleColor);
        }
    }

    /**
     * 设置默认标题颜色
     */
    protected void setNormalTitleText() {
        int childCount = mLinear.getChildCount();
        if (childCount < 1) {
            return;
        }
        for (int i = 0; i < childCount; i++) {
            View view = mLinear.getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(mNormalTitleColor);
            }
        }
    }


    /**
     * 偏移
     *
     * @param position
     */

    protected void onscroll(int position, float offset) {

        if (mPositionData == null || mPositionData.isEmpty()) {
            return;
        }
        // 手指跟随滚动
        if (mPositionData.size() > 0 && position >= 0 && position < mPositionData.size()) {
            int currentPosition = Math.min(mPositionData.size() - 1, position);
            int nextPosition = Math.min(mPositionData.size() - 1, position + 1);
            PositionData current = mPositionData.get(currentPosition);
            PositionData next = mPositionData.get(nextPosition);
            float scrollTo = current.horizontalCenter() - getScreenWidthPx() * mScrollPivotX;
            float nextScrollTo = next.horizontalCenter() - getScreenWidthPx() * mScrollPivotX;
            scrollTo((int) (scrollTo + (nextScrollTo - scrollTo) * offset), 0);
        }

        invalidate();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initTriangle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        preparePositionData();
    }

    /**
     * 将每个子控件的属性值存上
     * 三角形指示器时，滑动指示器时会用到
     */
    private void preparePositionData() {
        mPositionData.clear();
        for (int i = 0, j = mLinear.getChildCount(); i < j; i++) {
            PositionData data = new PositionData();
            View v = mLinear.getChildAt(i);
            if (v != null) {
                data.mLeft = v.getLeft();
                data.mTop = v.getTop();
                data.mRight = v.getRight();
                data.mBottom = v.getBottom();
                mCountWidth += v.getMeasuredWidth();
            }
            mPositionData.add(data);
        }
    }

    /**
     * 初始化三角形指示器
     */
    private void initTriangle() {
        Log.e("TAG", "initTriangle-----------");
        mPath = new Path();
        mTriangleHeight = (int) (mTriangleWidth / 2 / Math.sqrt(2));
        mPath.moveTo(0, 0);
        mPath.lineTo(mTriangleWidth, 0);
        mPath.lineTo(mTriangleWidth / 2, -mTriangleHeight);
        mPath.close();
    }


    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public int getScreenWidthPx() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }


    public void setmPaintColor(int mPaintColor) {
        this.mPaintColor = mPaintColor;
    }

    public void setmTableCount(int mTableCount) {
        this.mTableCount = mTableCount;
    }

    public void setmTitles(List<String> titles) {
        if (titles != null && titles.size() > 0) {
            this.removeAllViews();
            this.mTitles = titles;
            mTableCount = titles.size();
            for (String title : titles) {
                TextView textView = generateTextView(title); // 添加view
                mLinear.addView(textView);
            }
            addView(mLinear);
            // 设置item的click事件
            setItemClickEvent();
            setNormalTitleText();
            setSelectTitleText(0);
        }
        requestLayout();
    }

    private void setItemClickEvent() {
        int countChild = mLinear.getChildCount();
        for (int i = 0; i < countChild; i++) {
            final int j = i;
            View v = mLinear.getChildAt(i);
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    mViewPager.setCurrentItem(j);
                }
            });
        }
    }


    /**
     * 根据标题生成我们的TextView
     *
     * @param title
     * @return
     */
    private TextView generateTextView(String title) {
        TextView view = new TextView(getContext());
        view.setText(title);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        lp.setMargins(20, 0, 20, 0);
        view.setLayoutParams(lp);
        view.setGravity(Gravity.CENTER);
        view.setTextColor(mNormalTitleColor);
        view.setTextSize(mTitleSize);
        return view;
    }

    public void setmNormalTitleColor(int mNormalTitleColor) {
        this.mNormalTitleColor = mNormalTitleColor;
    }

    public void setmSelectTitleColor(int mSelectTitleColor) {
        this.mSelectTitleColor = mSelectTitleColor;
    }

    public void setmTitleSize(float mTitleSize) {
        this.mTitleSize = mTitleSize;
    }

}
