package tips.contentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 通过ContentProvider向其他程序提供数据，
 * 需要继承ContentProvider类并重写6个抽象方法。
 * <p>
 * ContentProvider要在Manifest文件中注册。
 */

public class DemoContentProvider extends ContentProvider {

    public static final String AUTHORITY = "tips.contentproviderdemo";
    public static final String PATH = "DemoTable";

    public static final int CODE_SELECT_ALL = 0X01;//查询整张表
    public static final int CODE_SELECT_ONE = 0X02;//查询表中的指定数据

    private static UriMatcher sUriMatcher;

    //初始化
    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        /**
         * 向UriMatcher中添加Uri，第三个参数是自定义的整数代码，
         * 如果调用match(Uri)方法找到了匹配的Uri，就返回这个自定义代码。
         * 不要添加隐私数据的Uri，避免数据泄漏。
         *
         * 支持通配符：
         * *：匹配任意个字符
         * #：匹配任意个数字
         */
        sUriMatcher.addURI(AUTHORITY, PATH, CODE_SELECT_ALL);
        sUriMatcher.addURI(AUTHORITY, PATH + "/#", CODE_SELECT_ONE);
    }

    /**
     * 初始化ContentProvider。
     * 只在有ContentResolver尝试访问数据时，ContentProvider才会被初始化。
     * 该方法运行在程序主线程中，请勿在其中执行耗时操作。
     *
     * @return 如果初始化成功，返回true，否则返回false。
     */
    @Override
    public boolean onCreate() {
        /*在此完成ContentProvider的初始化工作，通常进行数据库的创建和升级等操作*/

        return true;
    }

    /**
     * 返回Uri对象所对应的MIME类型，如果没有类型，返回null。
     * <p>
     * Android中MIME字符串由3部分组成： <br/>
     * 1. 以"vnd."开头； <br/>
     * 2. 如果Uri中以路径(path)结尾，就在第1部分后加上"android.cursor.dir/"，
     * 如果Uri中以id结尾，就在第1部分后加上"android.cursor.item/"； <br/>
     * 3. 最后以"vnd."加上权限(authority)加上"."再加上路径(path)结尾。
     * <p>
     * 例如： <br/>
     * Uri "content://tips.contentproviderdemo/DemoTable"对应的MIME为：
     * "vnd.android.cursor.dir/vnd.tips.contentproviderdemo.DemoTable"； <br/>
     * Uri "content://tips.contentproviderdemo/DemoTable/1"对应的MIME为：
     * "vnd.android.cursor.item/vnd.tips.contentproviderdemo.DemoTable"。
     */
    @Nullable
    @Override
    public String getType(Uri uri) {
        String mime = null;
        switch (sUriMatcher.match(uri)) {
            case CODE_SELECT_ALL:
                mime = "vnd.android.cursor.dir/vnd.tips.contentproviderdemo.DemoTable";
                break;
            case CODE_SELECT_ONE:
                mime = "vnd.android.cursor.item/vnd.tips.contentproviderdemo.DemoTable";
                break;
            default:
                break;
        }
        return mime;
    }

    /*
    * 下面以query()方法为例进行简单说名，其他几个方法类似
    * */
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        /*没有实现具体逻辑，只做基本说明*/
        switch (sUriMatcher.match(uri)) {
            case CODE_SELECT_ALL:
                //查询整张表的数据
                break;
            case CODE_SELECT_ONE:
                //查询指定的数据
                break;
            default:
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Log.i("### insert()", "没有实现insert方法");

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        Log.i("### delete()", "没有实现delete方法");

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        Log.i("### update()", "没有实现update方法");

        return 0;
    }
}
