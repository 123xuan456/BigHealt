package utils;

import android.os.Environment;
/**
 * 接口
 * Created by mhysa on 2016/8/31.
 */
public class UrlUtils {
    public static String ImageCachePath = Environment.getExternalStorageDirectory().getPath()+"/BigHealt/ImageCache/";// sd路径
    public  static String   BASEURL="http://showtest.dancw.com";

    public static String URL="http://192.168.0.43:8080/";
    //-------------------------------API接口----------------------------------
    public static String CommonHome=BASEURL+"/homepictures/sickness";
    public static String SpecialHome = BASEURL+"homepictures/showhealthknowledge";
    //登录
    public  static String   LOGIN=URL+"JianKangChanYe/user/login.do?";
    //注册验证码
    public  static String   REGISTER_CODE=URL+"JianKangChanYe/user/register?";
    //注册
    public  static String   REGISTER=URL+"JianKangChanYe/user/regiontoamaster?";
    //忘记密码验证码
    public  static String   LOSTPASSWORD_CODE=URL+"JianKangChanYe/user/forgetpassword?";
    //忘记密码完成
    public  static String   SUBMIT=URL+"JianKangChanYe/user/dauthentication?";

}
