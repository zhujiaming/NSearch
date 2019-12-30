package top.zhujm.searchapp.data;

import com.dbflow5.annotation.Database;

@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {

    //版本号
    public static final int VERSION = 1;
    //数据库名称
    public static final String NAME = "applist";
}