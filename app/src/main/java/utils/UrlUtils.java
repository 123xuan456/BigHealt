package utils;

import android.os.Environment;
/**
 * 接口
 * Created by mhysa on 2016/8/31.
 */
public class UrlUtils {
    public static String ImageCachePath = Environment.getExternalStorageDirectory().getPath()+"/BigHealt/ImageCache/";// sd路径
    public  static String   BASEURL="http://192.168.0.38:8080/JianKangChanYe";
    //-------------------------------API接口----------------------------------
    public static String CommonHome=BASEURL+"/homepictures/sickness";
    public static String SpecialHome = BASEURL+"homepictures/showhealthknowledge";

    
}
