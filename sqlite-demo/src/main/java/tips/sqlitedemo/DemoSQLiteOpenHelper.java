package tips.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * A helper class to manage database creation and version management.
 * This class takes care of opening the database if it exists,
 * creating it if it does not, and upgrading it as necessary.
 */
public class DemoSQLiteOpenHelper extends SQLiteOpenHelper {

    private Context mContext;

    public DemoSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        mContext = context;
    }

    /**
     * Called when the database is created for the first time.
     * This is where the creation of tables and the
     * initial population of the tables should happen.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //模拟安装不同版本
        switch (MainActivity.VERSION_CODE) {
            case 1:
                //execSQL()用于执行没有返回结果的SQL语句
                /**这里的数据库语句不要加分号*/

                //安装第1版，只有一个表
                db.execSQL(Constant.CREATE_TABLE_1);
                break;
            case 2:
                //直接安装第2版，比第1版新增了一个表
                //这里一起创建两个表是考虑到用户没有安装第1版
                //而是直接安装第2版应用的情况，不存在从旧版本更新
                db.execSQL(Constant.CREATE_TABLE_1);
                db.execSQL(Constant.CREATE_TABLE_2);//新增的表
                break;
            case 3:
                //直接安装第3版
                db.execSQL(Constant.CREATE_TABLE_1);
                db.execSQL(Constant.CREATE_TABLE_2_VERSION_3);//新增了字段
                break;
            default:
                Toast.makeText(mContext, "版本错误", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(mContext, "创建表 VERSION CODE:" + MainActivity.VERSION_CODE, Toast.LENGTH_SHORT).show();
    }

    /**
     * Called when the database needs to be upgraded.
     * The implementation should use this method to drop tables, add tables,
     * or do anything else it needs to upgrade to the new schema version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        switch (oldVersion) {
            /**
             * 这里的所有case中都不加break，保证跨版本升级
             * 时每个版本对数据库的所有修改都能得到执行
             * */
            case 1:
                //从第1版更新到第2版，新增一个数据表
                db.execSQL(Constant.CREATE_TABLE_2);
                if (newVersion == 2) {
                    //最高版本就是第2版，不应该继续执行升级到第2版的操作，下同
                    break;
                }
            case 2:
                //从第2版更新到第3版，数据表中新增一个字段
                db.execSQL(Constant.ALTER_TABLE);
                if (newVersion == 3) {
                    break;
                }
            default:
        }

        Toast.makeText(mContext, "更新表 VERSION CODE:" + newVersion, Toast.LENGTH_SHORT).show();
    }
}
