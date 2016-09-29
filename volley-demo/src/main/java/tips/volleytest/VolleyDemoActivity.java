package tips.volleytest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class VolleyDemoActivity extends AppCompatActivity {

    public static final String IMAGE_URL = "https://www.baidu.com/img/bd_logo1.png";


    private ImageView mRequestImage;
    private ImageView mLoaderImage;
    private NetworkImageView mNetworkImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_demo);
        initView();

        //用于测试Volley各种方法的类的实例
        VolleyRequestDemo demo = new VolleyRequestDemo();

        /*
        * 测试Volley的:
        * 1. StringRequest
        * 2. JsonObjectRequest
        * 3. JsonArrayRequest
        * */
        demo.volleyStringRequestDemo_GET();
        demo.volleyStringRequestDome_POST();
        demo.volleyJsonObjectRequestDemo_GET();
        demo.volleyJsonObjectRequestDome_POST();
        demo.volleyJsonArrayRequestDemo_GET();
        demo.volleyJsonArrayRequestDemo_POST();

        /*
        * 测试Volley的：
        * 1. 自定义的简单回调封装(GET)
        * 2. 自定义的简单回调封装(POST)
        * */
        demo.myVolleyRequestDemo_GET(this);
        demo.myVolleyRequestDemo_POST(this);

        /*
        * 测试Volley的：
        * 1. ImageRequest
        * */
        VolleyImageRequestDemo imageRequest = new VolleyImageRequestDemo();
        imageRequest.volleyImageRequestDemo(new VolleyImageRequestDemo.Callback() {
            @Override
            public void onSuccess(Bitmap response) {
                mRequestImage.setImageBitmap(response);
            }

            @Override
            public void onError(VolleyError error) {
                mRequestImage.setImageResource(R.mipmap.ic_launcher);
                Toast.makeText(VolleyDemoActivity.this, "error:use default image", Toast.LENGTH_SHORT).show();
            }
        });

        /*
        * 测试Volley的：
        * 1. ImageLoader
        * */
        ImageLoader imageLoader = new ImageLoader(MyApplication.getRequestQueue(), new VolleyBitmapCacheDemo());
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(mLoaderImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        imageLoader.get(IMAGE_URL, imageListener);

        /*
        * 测试Volley的：
        * 1. NetWorkImage
        * */
        mNetworkImage.setDefaultImageResId(R.mipmap.ic_launcher);
        mNetworkImage.setErrorImageResId(R.mipmap.ic_launcher);
        //需要一个ImageLoader，直接使用上面的ImageLoader
        mNetworkImage.setImageUrl(IMAGE_URL, imageLoader);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mRequestImage = (ImageView) findViewById(R.id.iv_image_request);
        mLoaderImage = (ImageView) findViewById(R.id.iv_image_loader);
        mNetworkImage = (NetworkImageView) findViewById(R.id.iv_image_network);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //当Activity停止运行后，取消Activity的所有网络请求
        MyApplication.getRequestQueue().cancelAll(VolleyRequestDemo.VOLLEY_TAG);
        Log.i("### onStop", "cancel all:tag=" + VolleyRequestDemo.VOLLEY_TAG);
    }
}
