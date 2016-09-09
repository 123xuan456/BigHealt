package dbighealth.bighealth.view;

/**
 * Created by mhysa on 2016/9/8.
 */

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.SwipeRefreshTrigger;
import com.aspsine.swipetoloadlayout.SwipeTrigger;

/**
 * CircleView 圆盘控件，
 */
public class CircleView extends View {

    /**
     * 控件的半径
     */
    private int mRadius;

    /**
     * 绘制弧形的画笔
     */
    private Paint mArcPaint;

    /**
     * 绘制弧形的区域
     */
    private RectF mRange;


    private int[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};

    public CircleView(Context context) {
        this(context, null, 0);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setDither(true);
        mArcPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = 0;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45, getResources().getDisplayMetrics());
        }

        //获取半径
        mRadius = Math.min(width, height) / 2;
        /**
         * 设置宽高为固定值
         */
        setMeasuredDimension(mRadius * 2, mRadius * 2);

        mRange = new RectF(0, 0, mRadius * 2, mRadius * 2);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        float degree = 360/colors.length/2f;

        for (int i = 0; i < 8; i++) {
            mArcPaint.setColor(colors[i%4]);
            canvas.drawArc(mRange,-90f+degree*i,degree,true,mArcPaint);
        }

    }

    public class CircleRefreshHeaderView extends RelativeLayout implements SwipeTrigger, SwipeRefreshTrigger {

        CircleView mCircleView;

        TextView mDescText;

        private ObjectAnimator anim;

        private boolean isRelease;

        public CircleRefreshHeaderView(Context context) {
            this(context, null, 0);
        }

        public CircleRefreshHeaderView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public CircleRefreshHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);

            initView();
        }

        /**
         * 初始化布局
         */
        private void initView() {

            int circlewidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());

            mCircleView = new CircleView(getContext());

            LinearLayout.LayoutParams circleParams = new LinearLayout.LayoutParams(circlewidth, circlewidth);

            mCircleView.setLayoutParams(circleParams);

            mDescText = new TextView(getContext());

            LinearLayout.LayoutParams descParams = new LinearLayout.LayoutParams(circlewidth * 3, ViewGroup.LayoutParams.WRAP_CONTENT);

            descParams.gravity = Gravity.CENTER;
            descParams.setMargins(circlewidth / 2, 0, 0, 0);
            mDescText.setLayoutParams(descParams);
            mDescText.setTextSize(12);
            mDescText.setTextColor(Color.GRAY);
            mDescText.setText("下拉刷新");

            //添加线性的父布局
            LinearLayout ll = new LinearLayout(getContext());
            RelativeLayout.LayoutParams llParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            llParams.addRule(CENTER_IN_PARENT);
            ll.setLayoutParams(llParams);
            ll.setPadding(10, 10, 10, 10);

            ll.addView(mCircleView);
            ll.addView(mDescText);

            addView(ll);
        }

        @Override
        public void onRefresh() {

            //开始刷新，启动动画
            anim = ObjectAnimator.ofFloat(mCircleView, "rotation", mCircleView.getRotation(), mCircleView.getRotation() + 360f)
                    .setDuration(500);
            anim.setRepeatCount(ValueAnimator.INFINITE);
            anim.setRepeatMode(ValueAnimator.RESTART);
            anim.start();

            mDescText.setText("正在加载数据");
        }

        @Override
        public void onMove(int yScroll, boolean isComplete, boolean b1) {
            if (!isComplete) {
                if (yScroll < getHeight()) {
                    mDescText.setText("下拉刷新");
                } else {
                    mDescText.setText("松开刷新更多");
                }

                //如果是仍在下拉状态，则圆环跟随滑动进行滚动
                if (!isRelease)
                    mCircleView.setRotation(((float) yScroll) / getHeight() * 360f);
            }


        }

        @Override
        public void onPrepare() {

        }

        @Override
        public void onRelease() {
            isRelease = true;
        }

       @Override
        public void onComplete() {
            anim.cancel();
            mDescText.setText("加载完成");
        }

        @Override
        public void onReset() {
            //重置时，将动画置为初始状态
            mCircleView.setRotation(0f);
        }

    }
}
