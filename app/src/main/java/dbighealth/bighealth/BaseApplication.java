package dbighealth.bighealth;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

/**
 * Created by de on 2016/9/6.
 */
public class BaseApplication extends Application{

//    public static String userid="";//用户id
//    public static String username="";//用户昵称
    public static String photoPic="";//用户头像
    public static String password="";//用户密码
    public static String regphone="";//用户手机号
    public static Uri imgUrl = null;
    public static String sex="";//用户性别
    public static Bitmap bitmap =null; //传递本地图片
    public static String name;
    public static String age;//年龄

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
}
