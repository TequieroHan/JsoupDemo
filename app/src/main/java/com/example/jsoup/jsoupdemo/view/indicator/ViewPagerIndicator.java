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
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jsoup.jsoupdemo.R;

import java.util.List;

/**
 * ViewPager指示器
 * Created by Administrator on 2017/9/21.
 */

public class ViewPagerIndicator extends LinearLayout {

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
    private static final float mTable_Traigle_Width = 1.0f / 6;

    /**
     * 三角形的最大宽度
     */
    private final int Max_Traigle_Width = (int) (getScreenWidthPx() / 3 * mTable_Traigle_Width);

    /**
     * 初始时，三角形指示器的偏移量
     */
    private int mInitTranslationX;

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
    private int mSelectTitleColor = Color.parseColor("#FAFAD2");
    /**
     * 默认字体大小
     */
    private float mDefaultTitleSize = getResources().getDimensionPixelSize(R.dimen.base16dp);

    private float mTitleSize = mDefaultTitleSize;

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
        Log.e("TAG", "mInitTranslationX =" + mInitTranslationX + "mTranslationX = " + mTranslationX + "mInitTranslationX + mTranslationX = " + (mInitTranslationX + mTranslationX));
        canvas.translate((mInitTranslationX * 2) + mTranslationX, getHeight() + 1);
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e("TAG", "onFinishInflate ------>");

        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            v.setLayoutParams(new LayoutParams(getScreenWidthPx() / mTableCount, ViewGroup.LayoutParams.WRAP_CONTENT, 0));
        }
        setItemClickEvent();
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

    public void setViewPager(ViewPager viewPager, int position) {
        this.mViewPager = viewPager;
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("TAG", "onPageScrolled----------" + position);
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
        View view = getChildAt(positon);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(mSelectTitleColor);
        }
    }

    /**
     * 设置默认标题颜色
     */
    protected void setNormalTitleText() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
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
//        mTranslationX = getScreenWidthPx() / mTableCount * (position + offset);
        mTranslationX = getChildAt(position).getMeasuredWidth() * (position + offset);
//        int tabWidth = getScreenWidthPx() / mTableCount;
        int tabWidth = getChildAt(position).getMeasuredWidth();
        float x = getChildAt(position).getX();
        int scrollX = getChildAt(position).getScrollX();
        int w = getChildAt(position).getMeasuredWidth();
        float x1 = getMeasuredWidth();
        int measuredWidth = getMeasuredWidth();
        Log.e("TAG", "x =" + x1 + "measuredWidth = " + measuredWidth + "childAt = " + x + "scrollX = " + scrollX + "w ==" + w);
        if (offset > 0 && position >= (mTableCount - 2)
                && getChildCount() == mTableCount) {
            if (mTableCount != 1) {
                this.scrollTo(((position - (mTableCount - 2)) * tabWidth + (int) (tabWidth * offset)), 0);
            } else {
                this.scrollTo((position * tabWidth + (int) (tabWidth * offset)), 0);
            }
        }
        invalidate();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTriangleWidth = (int) (w / mTableCount * mTable_Traigle_Width);
        mTriangleWidth = Math.min(Max_Traigle_Width, mTriangleWidth);
        initTriangle();
//        mInitTranslationX = getWidth() / mTableCount / 2 - mTriangleWidth / 2;
        mInitTranslationX = getChildAt(0).getMeasuredWidth() / 2 - mTriangleWidth / 2;
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
                // 添加view
                addView(generateTextView(title));
            }
            // 设置item的click事件
            setItemClickEvent();
        }
        requestLayout();
    }

    private void setItemClickEvent() {
        int countChild = getChildCount();
        for (int i = 0; i < countChild; i++) {
            final int j = i;
            View v = getChildAt(i);
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
        int width = (int) (title.length() * mTitleSize * 2);
        TextView view = new TextView(getContext());
        LayoutParams lp = new LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(20, 0, 20, 0);
        view.setLayoutParams(lp);
//        view.setLayoutParams(new LayoutParams(getScreenWidthPx() / mTableCount, LayoutParams.MATCH_PARENT));
        view.setText(title);
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
