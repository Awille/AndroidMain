package tips.networkdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String ADDRESS = "http://www.baidu.com";

    public static final int MSG_SUCCESS = 0X01;
    public static final int MSG_ERR0R = 0X02;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_SUCCESS:
                        Toast.makeText(MainActivity.this, "请求成功", Toast.LENGTH_SHORT).show();
                        break;
                    case MSG_ERR0R:
                        Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "未知操作", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    /**
     * 按钮点击事件：发送网络请求
     */
    public void onSendNetworkRequestButtonClick(View view) {

        DemoHttpURLConnection.sendHttpRequest(ADDRESS, new DemoHttpURLConnection.Callback() {

            /*
            * 这里仍然属于子线程中的方法，不可以更新UI，可以通过Handler与主线程交互
            *
            * 考虑去掉DemoHttpURLConnection中的线程，然后将DemoHttpURLConnection的
            * sendHttpRequest()方法放入AsyncTask中执行
            * */

            @Override
            public void onSuccess(Object response) {
                Log.i("### onSuccess", response.toString());
                mHandler.sendEmptyMessage(MSG_SUCCESS);
            }

            @Override
            public void onError(Exception e) {
                Log.i("### onError", e.getMessage());
                mHandler.sendEmptyMessage(MSG_ERR0R);
            }
        });
    }
}
