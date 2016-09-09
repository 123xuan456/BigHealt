package utils;

import android.os.Environment;
/**
 * 接口
 * Created by mhysa on 2016/8/31.
 */
public class UrlUtils {
    public static String ImageCachePath = Environment.getExternalStorageDirectory().getPath()+"/BigHealt/ImageCache/";// sd路径
    public  static String   BASEURL="http://192.168.0.43:8080/JianKangChanYe";
    public  static String   BASEURL2 = "http://192.168.0.120:8081/JianKangChanYe";
    public  static String   LOGIN="http://192.168.0.43:8080/JianKangChanYe/user/login.do?";
    public  static String   REGISTER_CODE="http://192.168.0.43:8080/JianKangChanYe/user/register?";
    public  static String   REGISTER="http://192.168.0.43:8080/JianKangChanYe/user/regiontoamaster?";
    //-------------------------------API接口----------------------------------
    public static String CommonHome=BASEURL+"/homepictures/sickness";
    public static String SpecialHome = BASEURL+"/homepictures/showhealthknowledge";
    public static  String LEAGUE = BASEURL+"/hathappenedat/savehealthcare";
    public static  String  Symptom = BASEURL2+"/Symptom/list";
    public static  String  FileSave = BASEURL2+"/filesave/save";
    //预约一级
    public static  String SUBSCRIBE =BASEURL +"/hathappenedat/onlinebookingselect";
    //详细预约particular
    public static  String SUBSCRIBE_PARTICULAR =BASEURL +"/hathappenedat/savemakeapp";

}
