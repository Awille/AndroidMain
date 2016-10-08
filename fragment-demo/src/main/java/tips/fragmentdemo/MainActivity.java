package tips.fragmentdemo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * 关于Fragment的注释写在类Fragment1中
 */
public class MainActivity extends AppCompatActivity {

    private DemoFragment1 mDemoFragment1;//用于与Fragment通信
    private DemoFragment3 mDemoFragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDemoFragment3 = new DemoFragment3();

        /**
         * Activity与Fragment通信：
         * 通过getFragmentManager().findFragmentById()方法获取到Fragment的实例，
         * 再通过该实例访问Fragment中的方法，实现Activity与Fragment的通信
         * */
        mDemoFragment1 = (DemoFragment1) getFragmentManager()
                .findFragmentById(R.id.fragment_1);
    }

    /**Fragment中的按钮点击事件也写在Activity中*/

    /**
     * 按钮点击事件：替换Fragment
     * <p>
     * 动态地修改Fragment
     */
    public void onReplaceButtonClick(View view) {
        /**动态修改Fragment*/
        /**开启Fragment事务*/
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        /**传入父容器和Fragment对象，使用Fragment对象替换容器中已有的Fragment对象*/
        transaction.replace(R.id.frame_layout, mDemoFragment3);
        /**如果不将Fragment加入返回栈，替换后不能通过Back按钮返回到原来的Fragment*/
        transaction.addToBackStack(null);
        /**提交后修改才生效*/
        transaction.commit();
    }

    /**
     * 按钮点击事件：移除Fragment
     */
    public void onRemoveButtonClick(View view) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        /**还有一些其他方法*/
        transaction.remove(mDemoFragment3);

        transaction.commit();
    }
}
