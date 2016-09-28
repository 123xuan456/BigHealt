package utils;

import android.os.Environment;
/**
 * 接口
 * Created by mhysa on 2016/8/31.
 */
public class UrlUtils {

    public static String ImageCachePath = Environment.getExternalStorageDirectory().getPath()+"/BigHealt/ImageCache/";// sd路径
    public static String BASEURL="http://192.168.0.43:8080/JianKangChanYe";
    public  static String   BASEURL2 = "http://192.168.0.120:8081/JianKangChanYe";
  //  public  static String   REGISTER_CODE="http://192.168.0.43:8080/JianKangChanYe/user/register?";
  //  public  static String   REGISTER="http://192.168.0.43:8080/JianKangChanYe/user/regiontoamaster?";
    //-------------------------------API接口----------------------------------
    public static String CommonHome=BASEURL+"/homepictures/sickness";
    public static String SpecialHome = BASEURL+"/homepictures/showhealthknowledge";
 //   public static  String LEAGUE = BASEURL+"/hathappenedat/savehealthcare";

    public static  String  Symptom = BASEURL2+"/Symptom/list";
    public static  String  FileSave = BASEURL2+"/filesave/save";

    //预约一级
    public static  String SUBSCRIBE =BASEURL +"/hathappenedat/onlinebookingselect";
    //详细预约particular
    public static  String SUBSCRIBE_PARTICULAR =BASEURL +"/hathappenedat/savemakeapp";
    //保存用户体质测试
    public static String SAVESYMPTON = BASEURL2+"/Symptom/save";
    //产品
    public static String PRODUCT = BASEURL+"/mountinformation/product";
    //医疗养生
    public static String TREATMENT = BASEURL+"/hathappenedat/selecthealth";
    //医疗养生选择
    public static String SELECTED_TREATMENT = BASEURL+"/hathappenedat/condition";
    //医疗养生详情页
    public static String DETAIL_TREATMENT = BASEURL2+"/medicalcareInside/healthlist";

    //登录
    public  static String   LOGIN=BASEURL+"/user/login.do?";
    //修改密码
    public  static String   CHANGPASSWORD=BASEURL2+"/user/changepsw";
    //注册验证码
    public  static String   REGISTER_CODE=BASEURL+"/user/register?";
    //注册
    public  static String   REGISTER=BASEURL+"/user/regiontoamaster?";
    //忘记密码验证码
    public  static String   LOSTPASSWORD_CODE=BASEURL+"/user/forgetpassword?";
    //忘记密码完成
    public  static String   SUBMIT=BASEURL+"/user/dauthentication?";
    //医疗养生加盟
    public static  String LEAGUE = BASEURL+"/hathappenedat/savehealthcare";
    //每日情况
    public static  String CONDITION = BASEURL2+"/DailyReport/list?userId=";
    //每日情况
    public static  String CONDITIONADD = BASEURL2+"/DailyReport/saveDaily";
    //每日一读
    public static  String EVERYDAYHealth = BASEURL2+"/HomePage/list";
    //咨询的listview
    public static String INFORMATIONLISTVIEW =BASEURL2+ "/advice/advicelist";
    //咨询详情list
    public static String INFORMATION_DETAILES_LISTVIEW =BASEURL2+ "/advice/talk";
    //咨询详情内容
    public static String INFORMATION_DETAILES =BASEURL2+ "/advice/saveAddQuest";
    //我要咨询
    public static String INFORMATION =BASEURL2+ "/advice/needhelp";



}
