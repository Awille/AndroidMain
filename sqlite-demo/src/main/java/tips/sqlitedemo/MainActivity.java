package tips.sqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String DATABASE_NAME = "DemoDatabase";
    public static final String TABLE_NAME_1 = "DemoTable1";
    public static final int VERSION_CODE = 1;

    private DemoSQLiteOpenHelper mHelper;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    /**
     * 执行初始化操作
     * 打开数据库
     */
    private void init() {
        /**提升VERSION_CODE后重新安装程序，模拟数据库升级*/
        //从高版本改回低版本要将高版本的应用卸载再重新安装
        mHelper = new DemoSQLiteOpenHelper(this, DATABASE_NAME, null, VERSION_CODE);

        /*
        * getReadableDatabase()和getWritableDatabase()方法
        * 用于打开或者创建一个数据库，如果数据库不存在，就创建一个
        * 新的数据库，如果数据库已存在，就直接打开数据库。
        *
        * 使用数据库后要记得关闭数据库
        *
        * 两个方法得到的数据库都是可读写的，区别在于，如果数据库
        * 出现问题而无法写入数据，例如权限问题或者空间不足，
        * getReadableDatabase()方法会将数据库以只读的方式打开，
        * getWritableDatabase()方法会抛出异常。
        *
        * 数据库操作可能耗时较长，正常情况下不要在主线程中调用这两个方法
        * */
        mDatabase = mHelper.getWritableDatabase();
        //mDatabase = mHelper.getReadableDatabase();
    }

    /**
     * 按钮点击事件：insert
     */
    public void onInsertButtonClick(View view) {
        long id;

        /**使用ContentValues向数据库中传入数据*/
        ContentValues contentValues = new ContentValues();
        /**ContentValues中放入的键和值必须和表中的字段名和其类型对应*/
        //如果有多个字段，可以同时放入多组数据
        contentValues.put("info", "information");
        //return the row ID of the newly inserted row,
        //or -1 if an error occurred
        id = mDatabase.insert(TABLE_NAME_1, null, contentValues);
        //如果还要继续插入数据，记得清除存入的数据，避免干扰下次insert操作
        contentValues.clear();

        if (id == -1) {
            Toast.makeText(MainActivity.this, "插入数据失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "插入数据成功 id=" + id, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 按钮点击事件：delete
     */
    public void onDeleteButtonClick(View view) {
        int rowCounts;
        /**
         * 第2、3个参数用于控制删除范围，如果不指定(null)，则删除全部数据
         * 第2个参数为条件，使用半角问号(?)作为占位符
         * 第3个参数为占位符对应的数据，使用字符串数组，非字符型字段也通过数组中的字符串传递
         * */
        //return the number of rows affected
        rowCounts = mDatabase.delete(TABLE_NAME_1, "id > ?", new String[]{"3"});

        Toast.makeText(MainActivity.this, "删除了" + rowCounts + "行数据", Toast.LENGTH_SHORT).show();
    }

    /**
     * 按钮点击事件：update
     */
    public void onUpdateButtonClick(View view) {
        int rowCounts;

        ContentValues contentValues = new ContentValues();
        contentValues.put("info", "new information");
        /**
         * 第3、4个参数用于控制更新范围，如果不指定(null)，则更新全部数据
         * 第3个参数为条件，使用半角问号(?)作为占位符
         * 第4个参数为占位符对应的数据，使用字符串数组，非字符型字段也通过数组中的字符串传递
         * */
        //return the number of rows affected
        rowCounts = mDatabase.update(TABLE_NAME_1, contentValues, "id = ?", new String[]{"1"});

        Toast.makeText(MainActivity.this, "更新了" + rowCounts + "行数据", Toast.LENGTH_SHORT).show();
    }

    /**
     * 按钮点击事件：select
     */
    public void onSelectButtonClick(View view) {
        /**
         * 第一个参数：表名
         * 第二个参数：要查询的字段名，默认查询全部
         * 第三个参数：查询条件，默认查询全部
         * 第四个参数：查询条件参数，默认查询全部
         * 第五个参数：GROUP BY，默认不进行GROUP BY操作
         * 第六个参数：GROUP BY之后的过滤操作，默认不进行过滤
         * 第七个参数：排序方式，不指定则使用默认排序
         * */
        Cursor cursor = mDatabase.query(TABLE_NAME_1, new String[]{"id", "info"},
                "id > ? and id < ?", new String[]{"2", "6"}, null, null, "id DESC");

        //This method will return false if the cursor is empty.
        if (cursor.moveToFirst()) {
            do {
                //遍历Cursor对象，取出查询结果
                //getColumnIndex() return
                //the zero-based column index for the given column name,
                //or -1 if the column name does not exist.
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String info = cursor.getString(cursor.getColumnIndex("info"));
                Log.i("### select", id + " " + info);
            } while (cursor.moveToNext());
            //cursor.moveToNext()
            //This method will return false if the cursor is
            //already past the last entry in the result set.
        }

        /**使用后要关闭游标*/
        cursor.close();
    }

    /**
     * 按钮点击事件：transaction
     */
    public void onTransactionButtonClick(View view) {
        /**开始事务*/
        mDatabase.beginTransaction();

        try {
            /**在这里执行数据库操作，这些操作会自动在事务中执行*/
            //do something

            /**设置事务执行成功*/
            mDatabase.setTransactionSuccessful();
            /**
             * 在setTransactionSuccessful()方法和endTransaction()方法之间不应该再执行任何数据库操作。
             * 如果在setTransactionSuccessful()方法和endTransaction()方法之间出现了任何错误，
             * 事务仍然会被提交。
             * */
        } finally {
            /**结束事务*/
            mDatabase.endTransaction();
        }

    }
}
