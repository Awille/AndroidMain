package tips.sqlitedemo;

/**
 * 一些数据库语句
 */
public class Constant {
    public static final String CREATE_TABLE_1 = "CREATE TABLE DemoTable1 (\n"
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "info TEXT)";

    //最初的(第2版)DemoTable2表
    public static final String CREATE_TABLE_2 = "CREATE TABLE DemoTable2 (\n"
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "info TEXT)";

    //第3版数据库的DemoTable2表多了一个字段
    public static final String CREATE_TABLE_2_VERSION_3 = "CREATE TABLE DemoTable2 (\n"
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "info TEXT)";

    public static final String ALTER_TABLE = "ALTER TABLE DemoTable2 \n"
            + "ADD COLUMN new_column INTEGER";
}
