package tips.servicedemo;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/*
 * Service需要在AndroidManifest中注册
 * 每个Service只会存在一个实例
 * 系统内存不足时后台Service可能被回收
 *
 * Service不会自动开启线程，所有代码默认都是运行在主线程
 * 需要在Service的内部手动创建子线程
 *
 * 使用stopSelf();方法主动停止服务
 */

/*
* 通过startService()启动的Service，在Activity退出后仍然在后台运行，
* 通过bindService()启动的Service，会随Activity一同销毁。
*
* 同时调用两个方法后，退出Activity后Service仍会运行。
* */

public class DemoService extends Service {

    public static final String LOG_TAG = "### DemoService";

    private DemoBinder mDemoBinder = new DemoBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //Return the communication channel to the service.
        //在Activity中配合ServiceConnection使用
        return mDemoBinder;
    }

    /**
     * 系统首次创建Service时调用该方法
     */
    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(LOG_TAG, "onCreate()");

        /**以下是前台服务代码，注释掉该段代码服务即变成后台服务*/
        //前台服务，可以防止内存不足时被系统回收，实时显示数据
        Notification notification = new Notification.Builder(this)
                .setContentTitle("ForegroundService") //前台Service显示的标题
                .setContentText("This is a foreground service.") //前台Service显示的内容
                .setSmallIcon(R.mipmap.ic_launcher) //前台Service在状态栏显示的小图标
                .build();
        //传入Notification的id和实例
        startForeground(1, notification);
        /**以上是前台服务代码，注释掉该段代码服务即变成后台服务*/
    }

    /**
     * 系统每次通过Context.startService()方法启动Service时调用该方法
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(LOG_TAG, "onStartCommend()");

        //为避免处理耗时任务时出现ANR，需要手动开启线程，在其中处理耗时操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                //do something

                //休眠10秒，模拟耗时操作
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //处理完成后要记得关闭服务
                stopSelf();
            }
        }).start();

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 系统销毁Service前调用该方法
     */
    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(LOG_TAG, "onDestroy()");
    }

    /**
     * 用于与启动Service的Activity通信的类
     * <p>
     * 在onBind()方法中返回该类的实例
     */
    class DemoBinder extends Binder {
        public void demoMethod() {
            //do something
            Log.i(LOG_TAG, "Binder:demoMethod()");
        }
    }
}
