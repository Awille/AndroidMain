package tips.intentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by qiuyu on 16-9-30.
 */

public class ExplicitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit);

        Intent intent = getIntent();
        //取出通过Intent传递的数据，使用getXxxExtra()方法，传入键
        //有些类型的数据取值时要设置默认值
        String stringData = intent.getStringExtra(MainActivity.INTENT_DATA_KEY_1);
        int intData = intent.getIntExtra(MainActivity.INTENT_DATA_KEY_2, -1);
        boolean boolData = intent.getBooleanExtra(MainActivity.INTENT_DATA_KEY_3, false);

        TextView tvIntentData = (TextView) findViewById(R.id.tv_intent_data);
        String data = stringData + " " + intData + " " + boolData;
        tvIntentData.setText(data);

    }
}
