package tips.volleytest;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/*
* ImageLoader用法：
* ImageLoader mImageLoader = new ImageLoader(MyApplication.getRequestQueue(), new VolleyBitmapCacheDemo());
* ImageLoader.ImageListener mImageListener = ImageLoader.getImageListener(ImageView, Drawable, Drawable);
* mImageLoader.get(url, mImageListener);
*/

/**
 * Simple cache adapter interface. If provided to the ImageLoader, it
 * will be used as an L1 cache before dispatch to Volley. Implementations
 * must not block. Implementation with an LruCache is recommended.
 */
public class VolleyBitmapCacheDemo implements ImageLoader.ImageCache {

    private LruCache<String, Bitmap> mCache;

    public VolleyBitmapCacheDemo() {
        int maxMemorySize = 1024 * 1024 * 10;
        mCache = new LruCache<String, Bitmap>(maxMemorySize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String key) {
        return mCache.get(key);
    }

    @Override
    public void putBitmap(String key, Bitmap bitmap) {
        mCache.put(key, bitmap);
    }

}
