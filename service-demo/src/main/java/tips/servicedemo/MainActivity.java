package tips.servicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/*
 * 利用Intent启动和停止Service
 */

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "### MainActivity";
    private boolean isServiceConnected = false;

    //Activity与Service通信
    private DemoService.DemoBinder mDemoBinder;
    //Interface for monitoring the state of an application service.
    private ServiceConnection mServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 按钮点击事件：启动服务
     */
    public void onStartServiceButtonClick(View view) {
        Intent intentStart = new Intent(this, DemoService.class);
        startService(intentStart);
    }

    /**
     * 按钮点击事件：停止服务
     */
    public void onStopServiceButtonClick(View view) {
        Intent intentStop = new Intent(this, DemoService.class);
        stopService(intentStop);
    }

    /**
     * 按钮点击事件：绑定服务
     * 绑定Service之后才能利用Binder与Service通信
     */
    public void onBindServiceButtonClick(View view) {

        mServiceConnection = new ServiceConnection() {
            @Override
            //Called when a connection to the Service has been established
            //每次绑定都会调用
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i(LOG_TAG, "onServiceConnected");

                //在onServiceConnected()方法中实例化mDemoBinder
                mDemoBinder = (DemoService.DemoBinder) service;//需要转型

                isServiceConnected = true;
            }

            @Override
            //Service正常关闭时不会调用该方法
            //Service异常关闭(例如内存不足被系统回收)时才会调用
            public void onServiceDisconnected(ComponentName name) {
                Log.i(LOG_TAG, "onServiceDisconnected");

                isServiceConnected = false;
            }
        };

        Intent intentBind = new Intent(this, DemoService.class);

        //BIND_AUTO_CREATE：Activity和Service绑定后自动创建Service
        bindService(intentBind, mServiceConnection, BIND_AUTO_CREATE);
    }

    /**
     * 按钮点击事件：解绑服务
     */
    public void onUnbindServiceButtonClick(View view) {
        //注意：bindService后才能unbindService
        //否则会抛出异常：Service not registered
        if (isServiceConnected) {
            unbindService(mServiceConnection);
        } else {
            Toast.makeText(MainActivity.this, "未绑定服务", Toast.LENGTH_SHORT).show();
        }
        isServiceConnected = false;
    }

    /**
     * 按钮点击事件：调用方法
     * 调用Binder中的自定义方法实现通信
     * <p>
     * 当Service与Activity绑定后，即可调用DemoBinder中的任何public方法
     */
    public void onBinderMethodButtonClick(View view) {
        //如果没有绑定就调用方法，会抛出空指针异常
        if (mDemoBinder == null) {
            Toast.makeText(MainActivity.this, "未绑定服务", Toast.LENGTH_SHORT).show();
            return;
        }

        mDemoBinder.demoMethod();
    }

    /**
     * 按钮点击事件：启动IntentService
     * 启动一个IntentService
     */
    public void onStartIntentServiceButtonClick(View view) {
        Intent intentService = new Intent(this, DemoIntentService.class);
        startService(intentService);
    }
}
