package obelab.com.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class UpDownChart extends View {
    private Rect mRectBackGround = new Rect();
    private Rect mRectGroundInner = new Rect();
    private Rect mRectBarTop = new Rect();
    private Rect mRectBottom = new Rect();
    private float mCorners = 35;
    boolean isDEMO = true;

    private float tick;
    private int max = 10;
    private int tickCurrent = 0;
    private int BackWidth = 3;
    private GradientDrawable mTopDrawable;
    private GradientDrawable mBottomDrawable;
    private GradientDrawable mInnerBack;
    private GradientDrawable BackDraw;

    public UpDownChart(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UpDownChart(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        BackWidth = dpToPx(BackWidth);
        int mHeightBar = h - (BackWidth * 2);
        tick = (float) mHeightBar / (float) (max * 2);
        mCorners = dpToPx(10);


        mRectBarTop = new Rect(BackWidth, BackWidth, w - BackWidth, (mHeightBar) / 2 + BackWidth);
        mRectBottom = new Rect(BackWidth, mHeightBar / 2 + BackWidth, w - BackWidth, mHeightBar);


        mRectBackGround = new Rect(0, 0, w, h);
        mRectGroundInner = new Rect(BackWidth, BackWidth, w - BackWidth, h - BackWidth);
        init();
    }

    private void init() {


        mTopDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                new int[]{0xFFFFFFFF,
                        0xFFff2c9c});
        mTopDrawable.setShape(GradientDrawable.RECTANGLE);
        mTopDrawable.setGradientRadius((float) (Math.sqrt(2) * 60));


        mBottomDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{0xFFFFFFFF,
                        0xFF0078ff});
        mBottomDrawable.setShape(GradientDrawable.RECTANGLE);
        mBottomDrawable.setGradientRadius((float) (Math.sqrt(2) * 60));


        mInnerBack = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{0xFF8b8e8d, 0xFF8b8e8d});
        mInnerBack.setShape(GradientDrawable.RECTANGLE);
        mInnerBack.setGradientRadius((float) (Math.sqrt(2) * 60));

        BackDraw = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{0xFFFFFFFF, 0xFFFFFFFF});
        BackDraw.setShape(GradientDrawable.RECTANGLE);
        BackDraw.setGradientRadius((float) (Math.sqrt(2) * 60));
        invalidate();
    }

    void setCornerRadius(GradientDrawable drawable, float r0, float r1, float r2, float r3) {

        drawable.setCornerRadii(new float[]{r0, r0, r1, r1, r2, r2, r3, r3});
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawBar(canvas, BackDraw, mRectBackGround, mCorners, mCorners, mCorners, mCorners);
        onDrawBar(canvas, mInnerBack, mRectGroundInner, mCorners, mCorners, mCorners, mCorners);

        if (isDEMO) {
            mRectBarTop.top = (int) (mRectBarTop.bottom - (tick * max)) + BackWidth;
            onDrawBar(canvas, mTopDrawable, mRectBarTop, mCorners, mCorners, 0, 0);
            mRectBottom.bottom = mRectBottom.top + (int) (tick * Math.abs(max));
            onDrawBar(canvas, mBottomDrawable, mRectBottom, 0, 0, mCorners, mCorners);
        }


        if (tickCurrent == 0) {

        } else if (tickCurrent > 0) {
            mRectBarTop.top = (int) (mRectBarTop.bottom - (tick * tickCurrent));
            onDrawBar(canvas, mTopDrawable, mRectBarTop, mCorners, mCorners, 0, 0);
        } else {
            mRectBottom.bottom = mRectBottom.top + (int) (tick * Math.abs(tickCurrent));
            onDrawBar(canvas, mBottomDrawable, mRectBottom, 0, 0, mCorners, mCorners);
        }


    }

    private void onDrawBar(Canvas canvas, GradientDrawable gradientDrawable, Rect mRect, float r0, float r1, float r2, float r3) {
        gradientDrawable.setBounds(mRect);
        canvas.save();
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        setCornerRadius(gradientDrawable, r0, r1, r2, r3);
        gradientDrawable.draw(canvas);
        canvas.restore();
    }

    public void setLevel(int Level) {
        isDEMO = false;
        tickCurrent = Level;
        invalidate();

    }

    public void setDEMOmode(boolean isFull) {
        this.isDEMO = isFull;
        invalidate();
    }
}
