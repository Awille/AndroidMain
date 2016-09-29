package tips.volleytest;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

/**
 * Volley的ImageRequest使用示例
 */

public class VolleyImageRequestDemo {

    public static final String IMAGE_URL = "https://www.baidu.com/img/bd_logo1.png";
    public static final String VOLLEY_TAG = "tag_volley_image_request";

    public void volleyImageRequestDemo(final Callback callback) {

        // @param maxWidth : Maximum width to decode this bitmap to, or zero for none
        // @param maxHeight : Maximum height to decode this bitmap to, or zero for none
        // If both width and height are zero, the image will be decoded to its natural size
        ImageRequest imageRequest = new ImageRequest(IMAGE_URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                callback.onSuccess(response);
            }
        }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("### onErrorResponse", "ImageRequest:" + error.toString());
                callback.onError(error);
            }
        });

        imageRequest.setTag(VOLLEY_TAG);

        MyApplication.getRequestQueue().add(imageRequest);
    }

    public interface Callback {
        void onSuccess(Bitmap response);

        void onError(VolleyError error);
    }
}
