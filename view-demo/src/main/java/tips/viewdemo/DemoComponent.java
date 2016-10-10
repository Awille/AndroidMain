package tips.viewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 通过组合不同控件创建自定义控件的示例。
 */
public class DemoComponent extends RelativeLayout {

    public static final int COMPONENT_ID_TITLE = 0X01;
    public static final int COMPONENT_ID_LEFT_BUTTON = 0X02;
    public static final int COMPONENT_ID_RIGHT_BUTTON = 0X03;

    //TypedArray
    private TypedArray mTypedArray;
    //components
    private TextView mTitleView;
    private Button mLeftButton;
    private Button mRightButton;
    //layout params
    private LayoutParams mTitleParams;
    private LayoutParams mLeftParams;
    private LayoutParams mRightParams;
    //title attributes
    private String mTitleText;
    private float mTitleTextSize;
    private int mTitleTextColor;
    //left component attributes
    private String mLeftText;
    private int mLeftTextColor;
    private Drawable mLeftBackground;
    //right component attributes
    private String mRightText;
    private int mRightTextColor;
    private Drawable mRightBackground;

    public DemoComponent(Context context) {
        this(context, null);
    }

    public DemoComponent(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DemoComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        /**调用初始化方法*/
        getAttrs(context, attrs);
        setView(context);
    }

    /**
     * 设置自定义View中的控件的点击事件
     */
    public void setOnComponentClickListener(final OnComponentClickListener listener) {

        mLeftButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onLeftButtonClick();
            }
        });

        mRightButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRightButtonClick();
            }
        });
    }

    /**
     * 设置自定义View中的控件的可见性
     *
     * @param componentId 要设置的控件的id
     * @param isVisible   控件是否可见
     */
    public void setComponentVisibility(int componentId, boolean isVisible) {
        switch (componentId) {
            case COMPONENT_ID_TITLE:
                if (isVisible) {
                    mTitleView.setVisibility(INVISIBLE);
                } else {
                    mTitleView.setVisibility(VISIBLE);
                }
                break;
            case COMPONENT_ID_LEFT_BUTTON:
                if (isVisible) {
                    mLeftButton.setVisibility(INVISIBLE);
                } else {
                    mLeftButton.setVisibility(VISIBLE);
                }
                break;
            case COMPONENT_ID_RIGHT_BUTTON:
                if (isVisible) {
                    mRightButton.setVisibility(INVISIBLE);
                } else {
                    mRightButton.setVisibility(VISIBLE);
                }
                break;
            default:
        }
    }

    /**
     * 获取自定义组合View中的控件的属性值
     */
    private void getAttrs(Context context, AttributeSet attrs) {

        /**将attr文件中自定义的控件的所有属性存入TypedArray中*/
        mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.DemoComponent);

        /**取出属性的值*/
        //title
        mTitleText = mTypedArray.getString(R.styleable.DemoComponent_title);
        mTitleTextSize = mTypedArray.getDimension(R.styleable.DemoComponent_titleTextSize, 10);
        mTitleTextColor = mTypedArray.getColor(R.styleable.DemoComponent_titleTextColor, 0);
        //left component
        mLeftText = mTypedArray.getString(R.styleable.DemoComponent_leftText);
        mLeftTextColor = mTypedArray.getColor(R.styleable.DemoComponent_leftTextColor, 0);
        mLeftBackground = mTypedArray.getDrawable(R.styleable.DemoComponent_leftBackground);
        //right component
        mRightText = mTypedArray.getString(R.styleable.DemoComponent_rightText);
        mRightTextColor = mTypedArray.getColor(R.styleable.DemoComponent_rightTextColor, 0);
        mRightBackground = mTypedArray.getDrawable(R.styleable.DemoComponent_rightBackground);

        /**TypedArray使用后要回收，避免再次创建时发生错误*/
        mTypedArray.recycle();
    }

    /**
     * 设置自定义组合View中的控件的属性
     */
    private void setView(Context context) {

        /**实例化控件*/
        mTitleView = new TextView(context);
        mLeftButton = new Button(context);
        mRightButton = new Button(context);

        /**设置控件属性*/
        //title
        mTitleView.setText(mTitleText);
        mTitleView.setTextSize(mTitleTextSize);
        mTitleView.setTextColor(mTitleTextColor);
        mTitleView.setGravity(Gravity.CENTER);
        //left component
        mLeftButton.setText(mLeftText);
        mLeftButton.setTextColor(mLeftTextColor);
        mLeftButton.setBackground(mLeftBackground);
        //right component
        mRightButton.setText(mRightText);
        mRightButton.setTextColor(mRightTextColor);
        mRightButton.setBackground(mRightBackground);

        /**为控件设置布局参数*/
        /*
        * 布局方法接受的尺寸参数都是以px为单位的，
        * 如果希望根据dp进行设置，需要先将dp转换为px。
        * */
        int marginPx = ConversionTools.dp2px(context, 4);
        //title
        mTitleParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mTitleParams.addRule(CENTER_IN_PARENT, TRUE);
        //left component
        mLeftParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mLeftParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        mLeftParams.addRule(ALIGN_PARENT_LEFT, TRUE);
        //right component
        mRightParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        mRightParams.setMargins(marginPx, marginPx, marginPx, marginPx);
        mRightParams.addRule(ALIGN_PARENT_RIGHT, TRUE);

        /**将控件添加到所在的ViewGroup*/
        addView(mTitleView, mTitleParams);
        addView(mLeftButton, mLeftParams);
        addView(mRightButton, mRightParams);
    }

    /**
     * 自定义View中的控件的点击事件回调接口
     */
    public interface OnComponentClickListener {
        /**
         * 左侧按钮的点击事件
         */
        void onLeftButtonClick();

        /**
         * 右侧按钮的点击事件
         */
        void onRightButtonClick();
    }
}
