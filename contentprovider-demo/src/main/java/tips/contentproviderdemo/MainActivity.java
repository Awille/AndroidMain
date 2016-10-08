package tips.contentproviderdemo;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

/**
 * 借助ContentResolver类访问其他程序提供的数据，通过Context类
 * 的getContentResolver()方法获取ContentResolver的实例。
 * <p>
 * ContentResolver需要一个Uri参数(内容URI)来指定目标程序的数据库。
 * Uri的构成格式如下：<br/>
 * 协议头："content://"，表明该字符串是内容URI；<br/>
 * 权限(authority)：通常为要访问的数据库所属程序的包名；<br/>
 * 路径(path)："/"加上要访问的数据库表名；<br/>
 * ID：可选内容，在路径后加上"/"和要访问的id。
 * <p>
 * 使用Uri.parse(String uriString)方法将字符串解析为Uri对象。
 * <p>
 * 例如，要访问当前程序的表DemoTable，所需的内容URI就是：
 * "content://tips.contentproviderdemo/DemoTable"
 * <br/>要访问表中id为1的数据，那相应的内容URI就是：
 * "content://tips.contentproviderdemo/DemoTable/1"
 */
public class MainActivity extends AppCompatActivity {

    /**
     * 通过Context类的getContentResolver()方法获取ContentResolver的实例
     */
    private ContentResolver mContentResolver;
    /**
     * 使用Uri.parse(String uriString)方法将字符串解析为Uri对象
     */
    private String mDemoUriString = "content://tips.contentproviderdemo/DemoTable";
    private Uri mDemoUri = Uri.parse(mDemoUriString);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**Activity初始化完成后才能调用该方法*/
        mContentResolver = getContentResolver();
    }

    /**
     * 按钮点击事件：query
     * <p>
     * ContentResolver的查询示例
     */
    public void onQueryButtonClick(View view) {

        //用于访问手机通讯录的Uri和数据库字段
        Uri mPhoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String display_name = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;//联系人姓名
        String data1 = ContactsContract.CommonDataKinds.Phone.NUMBER;//电话号码

        /**执行查询*/
        Cursor cursor = null;
        try {
            cursor = mContentResolver.query(
                    mPhoneUri, //Uri
                    null, //限定要查询的字段，默认查全部
                    null, //查询条件，使用"?"作为占位符，默认查全部
                    null, //上一个参数中占位符的参数，使用字符串数组
                    null  //查询结果的排序方式
            );

            //取出数据
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(display_name));
                String number = cursor.getString(cursor.getColumnIndex(data1));
                Log.i("### query", name + " : " + number);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            /**使用后要关闭Cursor*/
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * 按钮点击事件：update
     * <p>
     * ContentResolver的更新示例
     */
    public void onUpdateButtonClick(View view) {
        ContentValues contentValues = new ContentValues();
        /**使用ContentValues更新数据，更新的列名应该是数据库中已经存在的列名*/
        contentValues.put("column_1", "new value");
        mContentResolver.update(mDemoUri, //Uri
                contentValues,  //新值
                "column = ?", //限定要更新的列，使用"?"作为占位符，默认更新全部
                new String[]{"old value"}); //占位符的参数
    }

    /**
     * 按钮点击事件：insert
     * <p>
     * ContentResolver的插入示例
     */
    public void onInsertButtonClick(View view) {
        /**使用ContentValues添加数据，插入的列名应该是数据库中已经存在的列名*/
        ContentValues contentValues = new ContentValues();
        contentValues.put("column_1", "value1");
        contentValues.put("column_2", "value2");
        //插入成功后会返回插入的数据的Uri
        mContentResolver.insert(mDemoUri, contentValues);
    }

    /**
     * 按钮点击事件：delete
     * <p>
     * ContentResolver的删除示例
     */
    public void onDeleteButtonClick(View view) {
        mContentResolver.delete(mDemoUri, //Uri
                "column_1 = ?", //限定要删除的列，使用"?"作为占位符，默认删除全部
                new String[]{"value"}); //占位符的参数
    }
}
