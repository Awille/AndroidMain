package tips.intentdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String DEMO_ACTION = "tips.intentdemo.DEMO_ACTION";
    public static final String DEMO_CATEGORY = "tips.intentdemo.DEMO_CATEGORY";

    public static final String INTENT_DATA_KEY_1 = "1";
    public static final String INTENT_DATA_KEY_2 = "2";
    public static final String INTENT_DATA_KEY_3 = "3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 按钮点击事件：显式INTENT
     * <p>
     * 指定具体的Activity并启动
     */
    public void onExplicitButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, ExplicitActivity.class);
        startActivity(intent);
    }

    /**
     * 按钮点击事件：隐式INTENT
     * <p>
     * 根据筛选条件自动启动Activity
     */
    public void onFilterButtonClick(View view) {

        //每个Intent只能指定一个action，但是可以指定多个category，
        //所以它们的添加方法一个用set，一个用add

        //Intent可以在实例化时直接传入action，
        //也可以通过setAction()方法添加action
        //intent.setAction(DEMO_ACTION);

        Intent intent = new Intent(DEMO_ACTION);
        //添加category
        intent.addCategory(DEMO_CATEGORY);

        //如果找不到Activity会抛出ActivityNotFoundException
        startActivity(intent);
    }

    /**
     * 按钮点击事件：隐式INTENT - DATA
     * <p>
     * 自动启动能过处理指定数据类型的Activity
     */
    public void onDataFilterButtonClick(View view) {

        /*
        * data标签用于指定Activity能够响应什么类型的数据
        *
        * 例如，在某个应用中要打开网址时，如果安装了多个浏览器，
        * 系统会弹出窗口，列出可用的浏览器，询问要使用哪个浏览器来打开链接，
        * 系统就是通过data标签知道这些应用可以处理网址。
        *
        * 如果系统已经设置了处理某种类型数据的默认应用，
        * 则处理该类型数据时不会弹窗提示选择应用
        * */

        String url_http = "http://www.baidu.com";//打开网址
        String url_tel = "tel:10010";//拨打电话

        Intent intent = new Intent(Intent.ACTION_VIEW);
        //设置Intent要处理的数据
        intent.setData(Uri.parse(url_http));

        startActivity(intent);
    }

    /**
     * 按钮点击事件：使用Intent传递数据
     * <p>
     * 使用putExtra()方法向Intent中存放数据，并传递给其他Activity
     */
    public void onDataExtraButtonClick(View view) {

        Intent intent = new Intent(MainActivity.this, ExplicitActivity.class);

        //向Intent中存放数据，使用键值对的形式
        intent.putExtra(INTENT_DATA_KEY_1, "string");
        intent.putExtra(INTENT_DATA_KEY_2, 1);
        intent.putExtra(INTENT_DATA_KEY_3, true);

        startActivity(intent);
    }
}
