package tips.multithreaddemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final int MSG_UPDATE_TEXT = 0x01;

    /**
     * Android不允许在子线程中更新UI，
     * 可以通过Handler实现子线程对UI的操作
     */
    private Handler mHandler;

    private DemoAsyncTask mDemoAsyncTask;

    private int flag;
    private TextView mTextViewFlag;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        flag = 0;
        mTextViewFlag = (TextView) findViewById(R.id.tv_flag_text);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                /**可以在Handler中更新UI*/

                switch (msg.what) {
                    case MSG_UPDATE_TEXT:
                        mTextViewFlag.setText(flag + "");
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "未知的参数", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

    }

    @Override
    protected void onPause() {
        super.onPause();
        //Activity退出后停止后台任务
        if (mDemoAsyncTask != null && mDemoAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            /**该方法只将AsyncTask的状态设置为cancel，并不会取消AsyncTask的执行*/
            mDemoAsyncTask.cancel(true);
        }
    }

    /**
     * 按钮点击事件：执行线程
     * <p>
     * 开启一个子线程，将标志数flag加1,然后通过Handler更新UI
     */
    public void onStartThreadButtonClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                flag++;

                Message message = new Message();
                //User-defined message code so that the recipient can
                //identify what this message is about
                message.what = MSG_UPDATE_TEXT;

                mHandler.sendMessage(message);
//                mHandler.sendEmptyMessage(MSG_UPDATE_TEXT);//与上一句效果相同
                //延时发送，单位毫秒
//                mHandler.sendEmptyMessageDelayed(MSG_UPDATE_TEXT, 1000 * 3);
//                mHandler.sendMessageDelayed(message, 1000 * 3);
            }
        }).start();
    }

    /**
     * 按钮点击事件：执行AsyncTask
     */
    public void onStartAsyncTAskButtonClick(View view) {

        if (mDemoAsyncTask != null && mDemoAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            //避免点击多次重复执行
            Toast.makeText(MainActivity.this, "任务已经在执行", Toast.LENGTH_SHORT).show();
        } else {
            /**a task can be executed only once*/
            mDemoAsyncTask = new DemoAsyncTask();
            /**使用execute()方法启动AsyncTask*/
            mDemoAsyncTask.execute();
        }
    }

    /**
     * 按钮点击事件：取消AsyncTask
     */
    public void onCancelAsyncTAskButtonClick(View view) {
        if (mDemoAsyncTask != null && mDemoAsyncTask.getStatus() == AsyncTask.Status.RUNNING) {
            /**该方法只将AsyncTask的状态设置为cancel，并不会取消AsyncTask的执行*/
            mDemoAsyncTask.cancel(true);
        }
    }

    /**
     * AsyncTask需要3个泛型参数，3个参数可以为空(Void)
     * 每个AsyncTask实例只能执行一次，再次执行要重新实例化
     * <p>
     * 第一个参数 - Params：启动任务时传入的参数的类型
     * 例如执行下载任务，可以将参数类型设置为String，传入URL
     * <p>
     * 第二个参数 - Progress：后台任务执行过程中返回的进度值的类型
     * <p>
     * 第三个参数 - Result：后台任务执行完成后返回的结果的类型
     */
    class DemoAsyncTask extends AsyncTask<Void, Integer, Boolean> {

        /**
         * 后台任务开始执行之前会自动调用该方法
         * 通常在该方法中完成一些初始化操作
         */
        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        /**
         * 只有该方法执行在子线程中，不可以进行UI操作
         * 在该方法中执行耗时任务
         *
         * @return 任务执行成功返回true，否则返回false
         */
        @Override
        protected Boolean doInBackground(Void... params) {

            //进度条的最大值是100
            for (int i = 1; i < 101; i++) {

                /**使用该方法发布任务进度，通知onProgressUpdate()方法进行更新*/
                publishProgress(i);

                //如果任务状态变成了cancel，主动结束耗时操作停止任务
                if (isCancelled()) {
                    //如果i小于100,说明取消任务时没有完成操作
                    //如果i等于100,说明虽然任务被取消，但是操作完成了
                    return i >= 100;
                }

                //模拟耗时任务
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return true;
        }

        /**
         * 在后台任务doInBackground()方法中调用了publishProgress()方法后，就会调用该方法
         * 可以在该方法中根据任务进度对UI进行更新
         *
         * @param values 任务进度
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            //处理进度条进度
            mProgressBar.setProgress(values[0]);
        }

        /**
         * 后台任务doInBackground()执行完成后调用该方法
         * AsyncTask被取消则不会执行该方法
         *
         * @param isSucceed 任务是否执行成功
         */
        @Override
        protected void onPostExecute(Boolean isSucceed) {

            mProgressBar.setVisibility(View.GONE);
            if (isSucceed) {
                Toast.makeText(MainActivity.this, "执行成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "执行失败", Toast.LENGTH_SHORT).show();
            }
        }

        /**
         * AsyncTask被取消后不会执行onPostExecute()方法，
         * 而是执行该方法
         */
        @Override
        protected void onCancelled(Boolean isSucceed) {
            super.onCancelled(isSucceed);

            mProgressBar.setVisibility(View.GONE);
            if (isSucceed) {
                Toast.makeText(MainActivity.this, "执行成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "执行失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
