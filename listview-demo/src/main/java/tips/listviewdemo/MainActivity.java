package tips.listviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private DemoAdapter mDemoAdapter;
    private List<ListItemBean> dataSource = new ArrayList<>();

    private EditText mEditTextTitle;
    private EditText mEditTextContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //填充数据源
        Util.initDataSource(dataSource);

        initView();
    }

    /**
     * 初始化组件
     */
    private void initView() {

        /**通过Adapter将ListView、布局以及数据源绑定到一起*/
        mDemoAdapter = new DemoAdapter(MainActivity.this, R.layout.list_view_item, dataSource);

        mListView = (ListView) findViewById(R.id.lv_list_view);
        mEditTextTitle = (EditText) findViewById(R.id.et_title);
        mEditTextContent = (EditText) findViewById(R.id.et_content);

        /**设置适配器*/
        mListView.setAdapter(mDemoAdapter);

        /**ListView的点击/长按事件*/
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //处理点击事件

                Toast.makeText(MainActivity.this, "id:" + id + " " + "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ListItemBean bean = dataSource.get(position);
                dataSource.remove(bean);
                mDemoAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    /**
     * 按钮点击事件：添加
     * <p>
     * 向ListView添加数据
     */
    public void onAddButtonClick(View view) {

        String title = mEditTextTitle.getText().toString();
        String content = mEditTextContent.getText().toString();

        if (title.equals("") || content.equals("")) {
            return;
        }

        mEditTextTitle.setText("");
        mEditTextContent.setText("");

        ListItemBean bean = new ListItemBean(title, content, R.mipmap.ic_launcher);
        dataSource.add(bean);

        /**ListView的数据发生变化，通知刷新*/
        mDemoAdapter.notifyDataSetChanged();
        /**将ListView定位到指定的某行*/
        mListView.setSelection(dataSource.size());
    }
}
