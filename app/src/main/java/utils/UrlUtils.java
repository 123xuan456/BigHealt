package utils;

import android.os.Environment;

/**
 * 接口
 * Created by mhysa on 2016/8/31.
 */
public class UrlUtils {

  public static String ImageCachePath = Environment.getExternalStorageDirectory().getPath()+"/BigHealt/ImageCache/";// sd路径

  //public static String BASEURL="http://192.168.0.43:8080/JianKangChanYe";
  public static String BASEURL="http://192.168.0.120:8081/JianKangChanYe";//本地测试
  public  static String   BASEURL2 = "http://192.168.0.120:8081/JianKangChanYe";//本地测试
  public  static String   BASEURL3 = "http://192.168.0.120:8081/JianKangChanYe";//本地测试
//  public  static String   BASEURL3 = "http://106.2.219.210:1314/";//
//   public static String BASEURL="http://106.2.219.210:1314/";
//   public  static String   BASEURL2 = "http://106.2.219.210:1314/";


  //-------------------------------API接口----------------------------------
  public static String CommonHome=BASEURL+"/homepictures/sickness";
  public static String SpecialHome = BASEURL+"/homepictures/showhealthknowledge";
  //   public static  String LEAGUE = BASEURL+"/hathappenedat/savehealthcare";

  public static  String  Symptom = BASEURL2+"/Symptom/list";
  //提交健康档案
  public static  String  FileSave = BASEURL2+"/filesave/save";
  //获取健康档案数据
  public static  String  FileSubmit = BASEURL2+"/filesave/searchFile?userId=";

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
  //修改昵称
  public  static String   CHANGUSER=BASEURL2+"/user/changereguser";
  //修改年龄
  public  static String   CHANGYEAR=BASEURL3+"/user/changeage";

  //修改性别
  public  static String   CHANGESEX=BASEURL2+"/user/changesex";
  //注册验证码
  public  static String   REGISTER_CODE=BASEURL+"/user/register?";
  //注册
  public  static String   REGISTER=BASEURL+"/user/regiontoamaster?";
  //忘记密码验证码
  public  static String   LOSTPASSWORD_CODE=BASEURL+"/user/forgetpassword?";
  //忘记密码完成
  public  static String   SUBMIT=BASEURL+"/user/dauthentication?";
  //医疗养生加盟
  public static  String LEAGUE = BASEURL2+"/hathappenedat/savehealthcare";
  //每日情况
  public static  String CONDITION = BASEURL2+"/DailyReport/list?userId=";
  //每日情况
  public static  String CONDITIONADD = BASEURL2+"/DailyReport/saveDaily";
  //眼病二级界面
  public static  String  FORMATIONDETAIL = BASEURL +"/homepictures/formationdetails";
  //评论
  public static  String COMMENT = BASEURL+"/transfer/comment";
  //s上传图片
  public static  String UPLOADPIC = BASEURL2+"/PublicController/uploadPic";
  //首页特殊页条目点击
  public static String SPECIALITEM = BASEURL +"/homepictures/commonsensedetails";
  //更改头像
  public static  String   UPDATEPIC = BASEURL+"/user/updatemodifypicture";
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
  //设置提醒advice/saveAddQuest
  public static String REMINDSET = BASEURL2+"/medicineremind/newremind";
  //提醒listview
  public static String REMIND = BASEURL2+"/medicineremind/remindlist";
  //查询体检报告
  public static String SEARCHREPORT = BASEURL3+"/medicalReportarchReport";
  //保存体检报告
  public static String UPLOADREPORT = BASEURL3+"/medicalReportveReporty";
  //判读是否填写过体质
  public static String JUDGEPHYSICAL = BASEURL3+"/Symptom/alllist";
  //我的收藏展示
  public static String COLLECTIONSHOW = BASEURL3+"/transfer/mycollection";
  //我的收藏展示
  public static String  DELETECOLLECTION = BASEURL3+"/transfer/deletecollection?";


  //收货地址查询
  public static String SEARCH_MANAGESITE =  BASEURL3+"/addressmanager/searchall?userId=";
  //收货地址设为默认
  public static String SET_MANAGESITE =  BASEURL3+"/addressmanager/defaults";
  //删除收货地址
  public static String DELETE_MANAGESITE =  BASEURL3+"/addressmanager/deleteAddress?addressId=";
  //添加收货地址
  public static String ADD_MANAGESITE =  BASEURL3+"/addressmanager/saveAddress";
  //编辑收货地址
  public static String EDIT_MANAGESITE =  BASEURL3+"/addressmanager/updateAddress";

}
