package tips.viewdemo;

import android.content.Context;
import android.util.TypedValue;

/**
 * dp与px、sp与pt的相互转换工具。
 * <p>
 * 相关概念： <br/>
 * 屏幕大小：屏幕对角线长度，单位通常为寸。 <br/>
 * 分辨率：屏幕水平和垂直方向的像素个数。 <br/>
 * PPI(或DPI)：Pixels Per Inch(或Dots Per Inch)，由屏幕对角线像素点数除以屏幕大小得到。 <br/>
 * PPI = sqrt(屏幕水平方向像素个数<sup>2</sup> + 屏幕垂直方向像素个数<sup>2</sup>) / 屏幕大小
 * <p>
 * 标准dpi、密度值、分辨率和dp/px比例参考： <br/>
 * 密度   | 密度值 | 分辨率     | dp:px    <br/>
 * ldpi     120     240×320      1:0.75  <br/>
 * mdpi     160     320×480      1:1     <br/>
 * hdpi     240     480×800      1:1.5   <br/>
 * xhdpi    320     720×1280     1:2     <br/>
 * xxhdpi   480     1080×1920    1:3     <br/>
 */

public class ConversionTools {

    public static int dp2px(Context context, float dp) {
        //自己实现的转换方法
        //float scalingFactor = context.getResources().getDisplayMetrics().density;
        //return (int) (dp * scalingFactor + 0.5f);

        /**TypedValue类中提供了转换方法*/
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                context.getResources().getDisplayMetrics()
        );
    }

    public static int px2dp(Context context, float px) {
        /**获取屏幕的逻辑密度(计算dpi时的缩放因子，即dp/px的值)*/
        float scalingFactor = context.getResources().getDisplayMetrics().density;
        /**类型转换时不四舍五入，会丢失精度，所以加0.5f*/
        return (int) (px / scalingFactor + 0.5f);
    }

    public static int sp2px(Context context, float sp) {

        //float scalingFactor = context.getResources().getDisplayMetrics().scaledDensity;
        //return (int) (sp * scalingFactor + 0.5f);

        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                sp,
                context.getResources().getDisplayMetrics()
        );
    }

    public static int px2sp(Context context, float px) {
        /**获取字体显示到屏幕上时的缩放因子，通常和dp/px的值相同*/
        float scalingFactor = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / scalingFactor + 0.5f);
    }
}
