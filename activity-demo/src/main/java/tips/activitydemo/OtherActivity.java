package tips.activitydemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


public class OtherActivity extends AppCompatActivity {

    public static final String RESULT_DATA = "RESULT_DATA";
    public static final String BUNDLE_KEY = "BUNDLE_KEY";

    private EditText mEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        mEditText = (EditText) findViewById(R.id.et_result_text);

        //取出Activity意外退出的时候保存下来的数据
        if (savedInstanceState != null) {
            //通过getXxx()方法从Bundle中取出数据
            mEditText.setText(savedInstanceState.getString(BUNDLE_KEY));
        }

    }

    /**
     * 按钮点击事件：返回数据
     * <p>
     * 返回文本框中输入的数据给期望接受数据的Activity
     */
    public void onReturnButtonClick(View view) {
        String data = mEditText.getText().toString();
        //通过Intent将数据返回
        Intent intent = new Intent();
        intent.putExtra(RESULT_DATA, data);
        //操作完成，放入RESULT_OK
        setResult(RESULT_OK, intent);

        finish();
    }

    /**
     * 按钮点击事件：取消返回数据
     * <p>
     * 不返回数据，直接结束Activity
     */
    public void onCancelButtonClick(View view) {
        //不返回数据，直接结束Activity
        //没有setResult，期望接受数据的Activity会收到RESULT_CANCELED
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Activity被回收之前一定会调用该方法
        //在该方法中保存希望Activity恢复时仍然需要的数据
        super.onSaveInstanceState(outState);

        if (mEditText.getText().toString().equals("")) {
            return;
        }

        String data = mEditText.getText().toString();
        //通过putXxx()方法，使用键值对的形式将数据保存在Bundle中
        //在此启动Activity时，onCreate()方法会接受Bundle对象
        //Bundle可以和Intent配合使用，将Bundle放入Intent中
        outState.putString(BUNDLE_KEY, data);
    }

//    @Override
//    public void onBackPressed() {
//        //处理Back键的点击事件
//    }
}
