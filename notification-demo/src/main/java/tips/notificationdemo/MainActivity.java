package tips.notificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/*
* 关于Notification的详细用法：
* https://developer.android.com/guide/topics/ui/notifiers/notifications.html
* */

public class MainActivity extends AppCompatActivity {

    //每个通知都需要一个唯一ID
    public static final int NOTIFICATION_ID = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * 按钮点击事件：发送通知
     */
    public void onSendNotificationButtonClick(View view) {

        /**通过NotificationManager对象对通知进行管理*/
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //通知点击事件
        Intent intent = new Intent(this, OtherActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        /**通过Notification.Builder.build()方法创建Notification实例*/
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);//任务栏通知小图标
        builder.setContentTitle("Notification Title");//通知标题
        builder.setContentText("Notification Content");//通知内容
        builder.setTicker("New Notification");//通知被创建时任务栏闪过的提示内容
//        builder.setSound(Uri sonud);//设置提示音
        builder.setContentIntent(pendingIntent);//通知的点击事件
        builder.setAutoCancel(true);//点击事件完成后自动取消通知
        Notification notification = builder.build();

        /**通过NotificationManager.notify()方法开启通知*/
        manager.notify(NOTIFICATION_ID, notification);
    }

    /**
     * 按钮点击事件：取消通知
     */
    public void onCancelNotificationButtonClick(View view) {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //通过ID取消通知
        manager.cancel(NOTIFICATION_ID);
//        manager.cancelAll();//取消所有通知
    }
}
