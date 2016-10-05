package tips.servicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * IntentService
 * 自动在子线程中执行任务的Service
 * 用法了普通Service类似
 */

public class DemoIntentService extends IntentService {

    public static final String LOG_TAG = "### DemoIntentService";

    //必须调用父类的有参构造方法
    public DemoIntentService() {
        //随便传入一个字符串该线程命名，仅用于调试
        super("DemoIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //该方法中的操作自动在子线程中执行
        //在这里处理耗时操作

        Log.i(LOG_TAG, "Thread id : " + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy()");
    }
}
