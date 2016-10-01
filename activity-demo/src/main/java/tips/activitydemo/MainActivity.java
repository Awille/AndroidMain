package tips.activitydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /*
    * Activity主要用于与用户交互，各种视图、组件都要放在Activity中。
    * */

    /*
    * Activity有4中启动模式：
    * standard：默认的启动模式，每次onCreate()都会创建新的实例到返回栈中
    * singleTop：如果要启动的Activity已经在栈顶，则不会创建新的实例
    * singleTask：如果要启动的Activity已经在栈中，则将其上的所有Activity出栈(onDestroy)
    * singleInstance：启用一个新的返回栈管理Activity
    * */

    public static final int REQUEST_CODE_1 = 0X1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    /**
     * 按钮点击事件：OtherActivity
     * <p>
     * 启动一个Activity，并获取其返回的数据
     */
    public void onOtherActivityButtonClick(View view) {
        //获取从其他活动返回的数据
        Intent intent = new Intent(MainActivity.this, OtherActivity.class);
        //启动一个Activity，并获取其返回的数据
        //需要重写onActivityResult()方法
        startActivityForResult(intent, REQUEST_CODE_1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //处理其他Activity返回的数据
        switch (requestCode) {
            case REQUEST_CODE_1:
                if (resultCode == RESULT_OK) {
                    //操作成功
                    Log.i("ActivityResult", "RESULT_OK");
                    //返回的数据通过Intent传递
                    String resultData = data.getStringExtra(OtherActivity.RESULT_DATA);
                    resultData = resultData.equals("") ? "null" : resultData;
                    Toast.makeText(MainActivity.this, resultData, Toast.LENGTH_SHORT).show();
                } else if (resultCode == RESULT_CANCELED) {
                    //操作被取消
                    Log.i("ActivityResult", "RESULT_CANCELED");
                    Toast.makeText(MainActivity.this, "RESULT_CANCELED", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Activity", "RESULT_OTHER");
                    Toast.makeText(MainActivity.this, "RESULT_OTHER", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
