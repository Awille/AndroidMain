package tips.volleytest;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by qiuyu on 16-9-28.
 */

public class MyApplication extends Application {

    //Volley的全局请求队列
    public static RequestQueue sRequestQueue;

    /**
     * @return Volley全局请求队列
     */
    public static RequestQueue getRequestQueue() {
        return sRequestQueue;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //实例化Volley全局请求队列
        sRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }
}
