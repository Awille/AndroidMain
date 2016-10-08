package tips.networkdemo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DemoHttpURLConnection {

    public static void sendHttpRequest(final String address, final Callback callback) {

        //可以配合AsyncTask代替手动开启线程，便于更新UI
        new Thread(new Runnable() {
            @Override
            public void run() {

                /**使用HttpURLConnection对象收发网络数据*/
                HttpURLConnection connection = null;//HttpsURLConnection

                try {
                    /**根据URL创建网络连接*/
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    /**配置连接参数*/
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    /**读取数据*/
                    InputStream ins = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    /**请求成功*/
                    callback.onSuccess(response.toString());

                } catch (Exception e) {
                    /**请求失败*/
                    callback.onError(e);
                    e.printStackTrace();

                } finally {
                    if (connection != null) {
                        /**使用完成后要关闭连接*/
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    interface Callback {
        /**
         * 请求成功
         * 根据需求选择合适的类型
         */
        void onSuccess(Object response);

        /**
         * 请求失败
         */
        void onError(Exception e);
    }
}
