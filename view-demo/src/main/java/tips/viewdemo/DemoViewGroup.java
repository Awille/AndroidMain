package tips.viewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义ViewGroup示例。
 * <p>
 * ViewGroup管理其内部的View。
 */
public class DemoViewGroup extends ViewGroup {

    private int mScreenWidth;
    private int mScreenHeight;

    public DemoViewGroup(Context context) {
        super(context);
        init(context);
    }

    public DemoViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DemoViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        /**获取屏幕宽高*/
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
//        /*另一种获取屏幕宽高的方法*/
//        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
//        mScreenWidth = displayMetrics.widthPixels;
//        mScreenHeight = displayMetrics.heightPixels;
    }

    /**
     * 若要支持wrap_content属性，ViewGroup也必须重写onMeasure()方法。
     * 当ViewGroup的大小被设置为wrap_content时，ViewGroup会遍历所有子View，
     * 通过调用子View的测量方法来获取子View的大小，进而决定自己的大小。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //对子View进行测量
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * ViewGroup中通常没有什么需要绘制的内容，在没有指定ViewGroup的背景色时，
     * 不会调用ViewGroup的onDraw()方法。
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 分配内部的子View的大小和位置。
     * <p>
     * 子View测量完成后，要对子View进行布局管理，
     * 遍历子View并调用其布局方法，来决定其显示的位置。
     *
     * @param changed View有了新的大小或位置
     * @param l       左边界相对于父容器的位置
     * @param t       上边界相对于父容器的位置
     * @param r       右边界相对于父容器的位置
     * @param b       下边界相对于父容器的位置
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();
        /**获取与当前View相关联的LayoutParams并处理布局参数*/
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) getLayoutParams();
        //处理布局参数。。。
        setLayoutParams(marginLayoutParams);

        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != GONE) {
                childView.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);
            }
        }
    }

    //处理屏幕的触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://手指按下
                //...
                break;
            case MotionEvent.ACTION_MOVE://手指移动
                //...
                break;
            case MotionEvent.ACTION_UP://手指抬起
                //...
                break;
            //...
        }

        postInvalidate();
        return true;
    }

    //分发屏幕的触摸事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //...
        return super.dispatchTouchEvent(ev);
    }

    //拦截屏幕的触摸事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //...
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //...
    }
}
