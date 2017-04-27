package util;

import com.google.gson.Gson;
/**
 * 用途：
 * author：王倩凤Administrator
 * date:2017/4/10.
 */

public class GsonUtil {
    public static <T>T getBean(String str,Class<T> cla){
        Gson gson = new Gson();
        T bean = gson.fromJson(str, cla);
        return bean;
    }
}
