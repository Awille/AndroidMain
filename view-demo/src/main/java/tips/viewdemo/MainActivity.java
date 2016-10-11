package tips.viewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DemoComponent mDemoComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setEvent();
    }

    private void initView() {
        mDemoComponent = (DemoComponent) findViewById(R.id.demo_component_0);
    }

    private void setEvent() {
        mDemoComponent.setOnComponentClickListener(new DemoComponent.OnComponentClickListener() {
            @Override
            public void onLeftButtonClick() {
                Toast.makeText(MainActivity.this, "bt1", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightButtonClick() {
                Toast.makeText(MainActivity.this, "bt2", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
