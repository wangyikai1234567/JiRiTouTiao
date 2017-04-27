package util;

import android.app.Application;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import org.xutils.DbManager;
import org.xutils.x;
import util.NetUtil.Utils;

/**
 * 用途：
 * author：王艺凯
 * date:2017/4/10.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);
        x.Ext.setDebug(false);

        Utils.init(this);//初始化Utils类

        UMShareAPI.get(this);
    }

    {
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
//        ... 可配置多个平台 ...
//        由 我们从 腾讯 open开放平台申请应用所给以的 AppID 以及 AppKey。
//        参数一：String AppID
//        参数二：String AppKey
//        String
    }

    //这个配置最好写在工具类中，方便调用：
    //创建数据库
    private static DbManager.DaoConfig daoConfig;

    public static DbManager.DaoConfig getDaoConfig() {
        if (daoConfig == null) {
            daoConfig = new DbManager.DaoConfig()
                    .setAllowTransaction(true)//设置允许开启事务
                    .setDbName("mySql.db")//创建数据库的名称
                    //数据库版本号
                    .setDbVersion(1);
        }
        return daoConfig;
    }
}
