package utils;

import android.os.Environment;

/**
 * 接口
 * Created by mhysa on 2016/8/31.
 */
public class UrlUtils {

  public static String ImageCachePath = Environment.getExternalStorageDirectory().getPath()+"/BigHealt/ImageCache/";// sd路径

//  public static String BASEURL="http://192.168.0.120:8081//JianKangChanYe";
//  public  static String   BASEURL2 = "http://192.168.0.120:8081//JianKangChanYe";//本地测试
//  public  static String   BASEURL3 = "http://192.168.0.120:8081//JianKangChanYe";//本地测试
  //http://106.2.219.210:8090/JianKangChanYe/homepictures/sickn
  public static String BASEURL=" http://106.2.219.210:1314";//本地测试
  public  static String   BASEURL2 = " http://106.2.219.210:1314";//本地测试
  public  static String   BASEURL3 = " http://106.2.219.210:1314";//本地测试
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
  public static String SEARCHREPORT = BASEURL3+"/medicalReport/searchReport";
  //保存体检报告
  public static String UPLOADREPORT = BASEURL3+"/medicalReport/saveReporty";
  //判读是否填写过体质
  public static String JUDGEPHYSICAL = BASEURL3+"/Symptom/alllist";
  //我的收藏展示
  public static String COLLECTIONSHOW = BASEURL3+"/transfer/mycollection";
  //产品详细
  public static String INPRODUCT = BASEURL2+"/mountinformation/inProduct";
  //确认订单
  public static String DOBUYNOW = BASEURL2+"/shoppingtemp/doBuy";
  //确认订单中提交
  public static String SUBMITS = BASEURL2+"/order/goOrder";
  //收货地址查询
  public static String SEARCH_MANAGESITE =  BASEURL3+"/addressmanager/searchall?userId=";
  //收货地址设为默认
  public static String SET_MANAGESITE =  BASEURL3+"/addressmanager/defaults";
  //删除收货地址
  public static String DELETE_MANAGESITE =  BASEURL3+"/addressmanager/deleteAddress?addressId=";
  //查询购物车
  public static String SELECTSHOPCART = BASEURL3 +"/shoppingtemp/searchCart";
  //添加收货地址
  public static String ADD_MANAGESITE =  BASEURL3+"/addressmanager/saveAddress";
  //删除收藏
  public static String DELETECOLLECTION = BASEURL3+"/transfer/deletecollection";
  //删除购物车
  public static String DELETESHOPCART = BASEURL3+"/shoppingtemp/deleteCart";
  //修改购物车中单件商品的数量
  public static String UPDATENUMS = BASEURL3+"/shoppingtemp/updateNum";
  //存储购物车要结算的订单
  public static String SETTLEMENTORDER = BASEURL3 +"/shoppingtemp/doBuyTemp";
//  public static String DELETECOLLECTION = BASEURL3+"/transfer/deletecollection";
  //编辑收货地址
  public static String EDIT_MANAGESITE =  BASEURL3+"/addressmanager/updateAddress";
  //加入购物车
  public static String ADDSHOPCART = BASEURL3+"/shoppingtemp/saveCart";
  //添加收藏
  public static String ADDCOLLECTIONS =BASEURL3+"/transfer/insertcollection";
  //立即购买的订单
  public static String BUYNOW = BASEURL3+"/shoppingtemp/doBuyNow";

}
