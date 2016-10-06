package tips.broadcastdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * 自定义广播接收器
 * 当收到广播时，会执行onReceive()方法
 * <p>
 * 广播接收器中不允许开启线程
 * 当onReceive()方法中执行了较长时间而没有结束时，
 * 程序会报错
 */
public class DemoBroadcastReceiver extends BroadcastReceiver {

    public static final String LOG_TAG = "### DemoReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        //在这里执行接收到广播后要执行的操作

        Log.i(LOG_TAG, "onReceive()");
        Log.i(LOG_TAG, intent.getAction());

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Toast.makeText(context, "开机自启", Toast.LENGTH_SHORT).show();
        }

        if (intent.getAction().equals("tips.broadcastdemo.DEMO_ORDERED_BROADCAST_RECEIVER")) {
            /**截断有序广播，优先级更低的接收器将无法接收到该广播*/
            abortBroadcast();
        }
    }
}
