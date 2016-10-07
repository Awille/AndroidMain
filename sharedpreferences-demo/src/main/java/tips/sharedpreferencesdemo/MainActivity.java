package tips.sharedpreferencesdemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * SharedPreferences使用键值对的形式将数据存放在XML文件中
 * <p>
 * 三种主要的用于获取SharedPreferences对象的方法：
 * <p>
 * 1. Context.getSharedPreferences(String name, int mode)
 * <p>
 * 2. Activity.getPreferences(int mode)
 * 该方法自动将当前活动的类名作为SharedPreferences的名称
 * <p>
 * 3. PreferencesManager.getDefaultSharedPreferences(Context context)
 * 该方法自动将当前程序的包名作为前缀，后面加上"_preferences"
 * 作为SharedPreferences的名称， 使用默认的MODE_PRIVATE模式
 * <p>
 * 这几个方法中，如果访问的SharedPreferences不存在，则创建新的实例，
 * 如果已存在，则打开已存在的实例。
 */
public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFERENCES_NAME = "DemoSharedPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 按钮点击事件：存放数据
     * 向SharedPreferences中存入数据
     */
    public void onPutDataButtonClick(View view) {
        /**获取一个Editor对象，用于修改SharedPreferences中的数据*/
        //MODE_PRIVATE：只有当前程序能访问SharedPreferences中的数据
        SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE).edit();

        /**使用putXxx()方法以键值对的形式放入数据*/
        editor.putInt("id", 123456);
        editor.putString("name", "ZHANG");

        /**最后要提交操作，使用commit()方法或者apply()方法*/
        /*
        * commit()和apply()的区别：
        * 1. apply()方法没有返回值而commit()方法返回boolean表明修改是否提交成功
        * 2. apply()方法不会提示任何失败的提示
        * 3. apply()方法先将对数据的修改原子提交到内存，而后异步真正提交到磁盘；
        *    而commit()将对数据的修改同步的原子提交到硬件磁盘
        *
        * 两个方法的共同点：当有多个Editor对象调用方法时，最后调用方法的对象所做的修改会被保存下来。
        *
        * 当有多个并发的commit()的时候，它们会等待正在处理的commit()将修改保存到磁盘后再继续操作，
        * 从而降低了效率；而apply()只是将修改原子提交到内存，后面调用apply()的其他函数的将会直接
        * 覆盖前面的内存数据，这样从一定程度上提高了效率。
        *
        * 当数据被修改后不会立即使用时，建议使用apply()方法；
        * 否则使用commit()方法，避免数据还没有从内存提交到磁盘就从磁盘中取数据造成错误。
        *
        * */
        //editor.commit();
        editor.apply();
    }

    /**
     * 按钮点击事件：取出数据
     * 从SharedPreferences中取出数据
     */
    public void onGetDataButtonClick(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);

        /**使用getXxx()方法取数据，传入键和默认值*/
        int id = sharedPreferences.getInt("id", -1);
        String name = sharedPreferences.getString("name", "default");

        Toast.makeText(MainActivity.this, "id=" + id + "  name=" + name, Toast.LENGTH_SHORT).show();
    }

    public void onClearDataButtonClick(View view) {
        SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE).edit();
        /**使用clear()方法清空SharedPreferences中存放的所有数据*/
        editor.clear();
        editor.apply();
    }
}
