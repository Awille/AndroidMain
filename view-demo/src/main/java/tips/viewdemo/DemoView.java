package tips.viewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 简单的自定义View内容示例。
 */
public class DemoView extends View {

    private Paint mPaint = new Paint();
    private int mRadius = 1;//onDraw()方法中的圆半径
    private boolean expand = true;//true:圆半径增长，false:圆半径缩减

    public DemoView(Context context) {
        super(context);
    }

    public DemoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 对View及其内容进行测量，决定View的大小。
     * <p>
     * 默认的onMeasure()方法只支持EXACTLY模式，如果要让自定义的View能够
     * 支持wrap_content属性，必须重写该方法，处理AT_MOST模式。
     * 该类中，在measureWidth()方法中对AT_MOST模式进行了处理。
     * <p>
     * 3种测量模式： <br/>
     * 1. EXACTLY：当控件的大小设置为具体的值或者设置为match_parent时，
     * 系统使用EXACTLY模式。该模式为控件设置精确的大小，不管其实际尺寸。 <br/>
     * 2. AT_MOST：当控件的大小设置为wrap_content时，系统使用AT_MOST模式。 <br/>
     * 3. UNSPECIFIED：不指定大小的测量模式，View想多大就多大。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        /*
        * 重写onMeasure()方法的目的是给View提供wrap_content属性的默认大小，
        * 如果不重写该方法，当View的长度被设置为wrap_content时，它会充满父容器。
        * */

        /*调用setMeasuredDimension()方法存储View的宽高*/
        setMeasuredDimension(
                measureWidth(widthMeasureSpec), //测量宽度
                measureHeight(heightMeasureSpec) //测量高度
        );
    }

    /**
     * 在View中绘图。
     * <p>
     * 重写该方法，实现自定义View的内容绘制。
     */
    @Override
    protected void onDraw(Canvas canvas) {
        /*
        * Canvas作为画布，Paint作为画笔，设置好Paint的各种属性后，
        * 调用Canvas的drawXxx()方法绘制图形。
        * */
        super.onDraw(canvas);

        int xy = canvas.getWidth() / 2;//中心坐标
        //控制缩放
        if (expand) {
            mRadius++;
            if (mRadius >= ConversionTools.px2dp(getContext(), xy)) {
                expand = false;
            }
        } else {
            mRadius--;
            if (mRadius <= ConversionTools.px2dp(getContext(), 1)) {
                expand = true;
            }
        }
        //画圆
        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(xy, xy, ConversionTools.dp2px(getContext(), mRadius), mPaint);

//        /**通知View进行重绘，该方法具有多个重载*/
//        invalidate();
        /**定时通知View进行重绘，该方法具有多个重载*/
        //每次重绘都会调用onDraw()方法
        postInvalidateDelayed(5);
    }

    /**
     * 从XML文件中加载完所有组件后回调该方法
     */
    @Override
    protected void onFinishInflate() {

        /*即使重写了该方法，也应该调用父类的方法*/
        super.onFinishInflate();
    }

    /**
     * 计算控件应该在容器中显示的宽度，单位是像素(px)。
     *
     * @param widthMeasureSpec 控件在水平方向上的所需求的(受父布局约束的)空间，该数值由控件的大小和模式组成。
     *                         当控件的大小被设置为wrap_content时，widthMeasureSpec是一个固定的负数；
     *                         当控件的大小被设置为具体的数值或者match_parent时，
     *                         widthMeasureSpec会根据设置发生变化。
     * @return 控件的宽度(px)
     */
    private int measureWidth(int widthMeasureSpec) {
        /**为控件设置一个默认的长度(px)*/
        int result = 600;

        /**提取测量模式和人为设置的控件的长度(px)*/
        /*
        * 如果控件的长度被设置为具体的数值或者match_parent，specMode就是EXACTLY；
        * 如果控件的长度被设置为wrap_content，specMode就是AT_MOST。
        * */
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        /*
        * 如果控件的长度被设置为具体的数值，specSize就是该长度转换成像素后的数值；
        * 如果控件的长度被设置为match_parent或者wrap_content，specSize就是控件
        * 所在容器的长度(像素个数)，即父容器所允许的最大长度。
        * */
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        /**计算控件要显示的长度*/
        if (specMode == MeasureSpec.EXACTLY) {
            /*
            * 如果是EXACTLY模式，那么控件要显示的长度就是前面提取出来的specSize
            * */
            result = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                /*
                * 在AT_MOST模式中，如果默认的长度没有超出父容器所允许的最大长度，
                * 就将长度设置为默认值，否则将长度设为父容器所允许的最大长度。
                * */
                result = Math.min(result, specSize);
            }
        }
        /**返回计算得到的控件长度*/
        return result;
    }

    /**
     * 计算控件应该在容器中显示的高度，单位是像素(px)。
     *
     * @param heightMeasureSpec 控件在垂直方向上的所需求的(受父布局约束的)空间，该数值由控件的大小和模式组成。
     *                          当控件的大小被设置为wrap_content时，heightMeasureSpec是一个固定的负数；
     *                          当控件的大小被设置为具体的数值或者match_parent时，
     *                          heightMeasureSpec会根据设置发生变化。
     * @return 控件的高度(px)
     */
    private int measureHeight(int heightMeasureSpec) {
        //和宽度的测量方法相同
        return measureWidth(heightMeasureSpec);
    }
}
