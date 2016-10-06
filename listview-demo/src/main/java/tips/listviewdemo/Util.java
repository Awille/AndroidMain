package tips.listviewdemo;

import java.util.List;

/**
 * Created by qiuyu on 16-10-6.
 */

public class Util {

    /**
     * 初始化ListView所需的数据源
     * 填充dataSource的数据
     */
    public static void initDataSource(List<ListItemBean> list) {

        for (int i = 0; i < 10; i++) {
            list.add(new ListItemBean("Title " + i, "Content " + i, R.mipmap.ic_launcher));
        }
    }
}
