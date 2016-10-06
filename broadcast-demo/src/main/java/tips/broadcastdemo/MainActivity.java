package tips.broadcastdemo;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/*
* 广播分为标准广播和有序广播
*
* 标准广播是完全异步执行的广播，
* 广播发出后所有的广播接收器几乎同时收到广播，不分先后
* 标准广播的效率比较高，不可截断
* 使用sendBroadcast(Intent)发送的广播是标准广播
*
* 有序广播是同步执行的广播，
* 优先级高的广播接收器会先接收到广播，
* 当接收到广播的接收器执行完处理任务后后面的接收器才能接到广播，
* 有序广播可以被截断，被截断后后面的广播接收器就无法接受到该广播
* 使用sendOrderedBroadcast(Intent, String)发送的广播是有序广播
* */

/*
* 广播的注册方式分为动态注册和静态注册
*
* 在Java代码中注册广播称为动态广播
* 动态注册的广播可以自主的控制注册与注销，更灵活
* 动态注册的广播必须在程序启动后才能接受广播
*
* 在Manifest文件中注册广播称为静态注册
* 静态注册的广播可以在程序未启动时接受广播
* */

/*
* 一个程序发出的广播可以被其他程序接收到
* 如果只想让发出广播的程序自己接受自己的广播，可以使用本地广播
* 本地广播的注册、注销和发送全部通过LocalBroadcastManager的实例来执行
* */

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "### MainActivity";

    public static final String STANDER_BROADCAST_ACTION = "tips.broadcastdemo.DEMO_STANDER_BROADCAST_RECEIVER";
    public static final String ORDERED_BROADCAST_ACTION = "tips.broadcastdemo.DEMO_ORDERED_BROADCAST_RECEIVER";
    public static final String LOCAL_BROADCAST_ACTION = "tips.broadcastdemo.DEMO_LOCAL_BROADCAST_RECEIVER";

    private DemoBroadcastReceiver mReceiver;
    private DemoBroadcastReceiver mLocalReceiver;

    private LocalBroadcastManager mLocalBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dynamicRegisteredBroadcastDemo();
        registerLocalBroadcastReceiverDemo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(LOG_TAG, "onDestroy()");

        /**程序退出后要注销广播*/
        unregisterReceiver(mReceiver);
        /**使用LocalBroadcastManager.unregisterReceiver()方法注销本地广播*/
        mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
    }

    /**
     * 动态注册广播示例
     */
    private void dynamicRegisteredBroadcastDemo() {
        IntentFilter intentFilter = new IntentFilter();
        /**设置要监听的action*/
        //监听网络状态变化的广播
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mReceiver = new DemoBroadcastReceiver();

        /**需要注册后才能生效，将接收器和action绑定*/
        registerReceiver(mReceiver, intentFilter);
    }

    /**
     * 注册本地广播示例
     */
    private void registerLocalBroadcastReceiverDemo() {
        //获取LocalBroadcastManager实例
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(LOCAL_BROADCAST_ACTION);
        mLocalReceiver = new DemoBroadcastReceiver();

        /**使用LocalBroadcastManager.registerReceiver()方法注册本地广播*/
        mLocalBroadcastManager.registerReceiver(mLocalReceiver, intentFilter);
    }

    /**
     * 按钮点击事件：发送标准广播
     */
    public void onSendStanderBroadcastButtonClick(View view) {
        //使用Intent传送广播信息，
        //可以在Intent中放入数据，然后在BroadcastReceiver中取出数据
        Intent intent = new Intent(STANDER_BROADCAST_ACTION);

        /**使用sendBroadcast(Intent)方法发送标准广播*/
        sendBroadcast(intent);
    }

    /**
     * 按钮点击事件：发送有序广播
     */
    public void onSendOrderedBroadcastButtonClick(View view) {
        //使用Intent传送广播信息，
        //可以在Intent中放入数据，然后在BroadcastReceiver中取出数据
        Intent intent = new Intent(ORDERED_BROADCAST_ACTION);

        /**使用sendOrderedBroadcast(Intent, String)方法发送有序广播*/
        //String naming a permissions that a receiver
        //must hold in order to receive your broadcast.
        //If null, no permission is required.
        sendOrderedBroadcast(intent, null);
    }

    /**
     * 按钮点击事件：发送本地广播
     */
    public void onSendLocalBroadcastButtonClick(View view) {
        Intent intent = new Intent(LOCAL_BROADCAST_ACTION);
        /**使用LocalBroadcastManager.sendBroadcast()方法发送本地广播*/
        mLocalBroadcastManager.sendBroadcast(intent);
    }
}
