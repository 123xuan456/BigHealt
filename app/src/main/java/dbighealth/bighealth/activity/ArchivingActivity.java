package dbighealth.bighealth.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.ArchivingBean;
import dbighealth.bighealth.wheel.JudgeDate;
import dbighealth.bighealth.wheel.ScreenInfo;
import dbighealth.bighealth.wheel.WheelMain;
import okhttp3.Call;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;

/**
 * Created by mhysa on 2016/9/6.
 * 健康档案
 */
public class ArchivingActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tit)
    TextView tit;
    @Bind(R.id.right_tv)
    TextView rightTv;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.nation)
    EditText nation;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.man)
    RadioButton man;
    @Bind(R.id.woman)
    RadioButton woman;
    @Bind(R.id.sex)
    RadioGroup sex;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.rb_blood_ab)
    RadioButton rbBloodAb;
    @Bind(R.id.rb_blood_a)
    RadioButton rbBloodA;
    @Bind(R.id.rb_blood_b)
    RadioButton rbBloodB;
    @Bind(R.id.rb_blood_o)
    RadioButton rbBloodO;
    @Bind(R.id.blood)
    RadioGroup blood;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.solitude)
    RadioButton solitude;
    @Bind(R.id.two_people)
    RadioButton twoPeople;
    @Bind(R.id.above)
    RadioButton above;
    @Bind(R.id.live)
    RadioGroup live;
    @Bind(R.id.height)
    EditText height;
    @Bind(R.id.weight)
    EditText weight;
    @Bind(R.id.btn_medicine)
    RadioButton btnMedicine;
    @Bind(R.id.btn_other)
    RadioButton btnOther;
    @Bind(R.id.diabetes)
    RadioButton diabetes;
    @Bind(R.id.dermatosis)
    RadioButton dermatosis;
    @Bind(R.id.heart)
    RadioButton heart;
    @Bind(R.id.heredity)
    RadioGroup heredity;
    @Bind(R.id.medical_history)
    EditText medicalHistory;
    @Bind(R.id.diet)
    CheckBox diet;
    @Bind(R.id.sleep)
    CheckBox sleep;
    @Bind(R.id.play)
    CheckBox play;
    @Bind(R.id.habit_et)
    EditText habitEt;
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.rg_guomin)
    RadioGroup rgGuomin;
    private String userName;
    private String nation1;
    private String sex1;
    private String bloodType1;
    private String liveCondition;
    private String height1;
    private String weight1;
    private String dateTime;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private String guomin;
    private String historyDisase;
    private String inheritance;
    private int diet1;
    private int sleep1;
    private int sport;
    private String habitDesciption;
    private String habit1;
    private static int CommitHealth=1;
    private static int Commit=2;
    WheelMain wheelMain;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_file);
        ButterKnife.bind(this);
        tit.setText("健康档案");
        rightTv.setText("提交");

        display();//先判断用户是否有填写
        initListener();



    }

    private void display() {

        String userid = SharedPreferencesUtils.getString(this, UrlUtils.LOGIN, "");
        OkHttpUtils.postString()
                .url( UrlUtils.FileSubmit+ SharedPreferencesUtils.getString(ArchivingActivity.this,UrlUtils.LOGIN,""))
                .content(getUserInfo())
                .id(Commit)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("健康档案失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        System.out.println("健康档案成功="+response);
                        Gson g=new Gson();
                        ArchivingBean archiving = g.fromJson(response, ArchivingBean.class);
                        int code = archiving.getCode();
                        if (code==400){
                            System.out.println("健康档案没有数据");
                            return ;
                        }else if (code==200){
                            List<ArchivingBean.MessageBean> message = archiving.getMessage();
                            for (int i=0;i<message.size();i++){
                                ArchivingBean.MessageBean mes = message.get(i);
                                name.setText(mes.getName());
                                nation.setText(mes.getNation());
                                height.setText(mes.getHeight()+"");
                                weight.setText(mes.getWeight()+"");
                                System.out.println("健康档案时间"+mes.getBirth()+"");
                                date.setText(mes.getBirth()+"");
                                medicalHistory.setText(mes.getPastMedical()+"");
                                habitEt.setText(mes.getTextExplain()+"");
                                String sex=mes.getSex();
                                if ("男".equals(sex)){
                                    man.setChecked(true);
                                }else if ("女".equals(sex)){
                                    woman.setChecked(true);
                                }
                                String bloodType = mes.getBloodType();
                                if ("AB".equals(bloodType)){
                                    rbBloodAb.setChecked(true);
                                }else if ("A".equals(bloodType)){
                                    rbBloodA.setChecked(true);
                                }else if ("B".equals(bloodType)){
                                    rbBloodB.setChecked(true);
                                }else if ("O".equals(bloodType)){
                                    rbBloodO.setChecked(true);
                                }
                                String allergy = mes.getAllergy()+"";
                                if ("药物过敏".equals(allergy)){
                                    btnMedicine.setChecked(true);
                                }else if ("其他".equals(allergy)){
                                    btnOther.setChecked(true);
                                }

                               String familyHistory=mes.getFamilyHistory()+"";
                                if ("糖尿病".equals(familyHistory)){
                                    diabetes.setChecked(true);
                                }else if ("皮肤病".equals(familyHistory)){
                                    dermatosis.setChecked(true);
                                }else if ("先天性心脏病".equals(familyHistory)){
                                    heart.setChecked(true);
                                }
                               String habit_diet=mes.getHabit_diet()+"";
                                if ("1".equals(habit_diet)){
                                    diet.setChecked(true);
                                }else if ("0".equals(habit_diet)){
                                }
                               String habit_sleep=mes.getHabit_sleep()+"";
                                if ("1".equals(habit_sleep)){
                                    sleep.setChecked(true);
                                }else if ("0".equals(habit_sleep)){
                                }
                               String habit_motion=mes.getHabit_motion()+"";
                                if ("1".equals(habit_motion)){
                                    play.setChecked(true);
                                }else if ("0".equals(habit_motion)){
                                }

                            }

                        }
                    }
                });
    }

    /**
     * 获取用户填写数据
     */
    public String getUserInfo() {

        userName = name.getText().toString();
        nation1 = nation.getText().toString();
        sex1 = man.isChecked() ? "男" : "女";
        bloodType1 = rbBloodAb.isChecked() ? "AB" : (rbBloodA.isChecked() ? "A" : (rbBloodB.isChecked() ? "B" : "O"));
        liveCondition = solitude.isChecked() ? "独居" : (twoPeople.isChecked() ? "两人" : "三人以上");
        height1 = height.getText().toString();
        weight1 = weight.getText().toString();
        dateTime = date.getText().toString();
        guomin = btnMedicine.isChecked() ? "药物过敏" : "其他";
        inheritance = diabetes.isChecked() ? "糖尿病" : (dermatosis.isChecked() ? "皮肤病" : "先天性心脏病");
        historyDisase = medicalHistory.getText().toString();
        diet1 = diet.isChecked() ? 1 : 0;
        sleep1 = sleep.isChecked() ?  1 : 0;
        sport = play.isChecked() ?  1 : 0;
        habitDesciption = habitEt.getText().toString();
        String id = SharedPreferencesUtils.getString(this, UrlUtils.LOGIN, "");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId",id);
            jsonObject.put("name",userName);
            jsonObject.put("nation",nation1);
            jsonObject.put("sex",sex1);
            jsonObject.put("bloodType",bloodType1);
            jsonObject.put("living",liveCondition);
            jsonObject.put("height",height1);
            jsonObject.put("weight",weight1);
            jsonObject.put("birth",dateTime);
            jsonObject.put("allergy",guomin);
            jsonObject.put("familyHistory",inheritance);
            jsonObject.put("pastMedical",historyDisase);
            jsonObject.put("habit_diet",diet1);
            jsonObject.put("habit_sleep",sleep1);
            jsonObject.put("habit_motion",sport);
            jsonObject.put("textExplain",habitDesciption);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 事件监听
     */
    public void initListener() {
        arrowLeft.setOnClickListener(this);
        rightTv.setOnClickListener(this);
        date.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            /**
             * 提交档案的代码
             */
            case R.id.right_tv:
                getUserInfo();
                if(userName==null||nation1==null||height1.equals("")||weight1.equals("")){
                    Toast.makeText(getApplicationContext(),"信息填写不全",Toast.LENGTH_SHORT).show();

                }else{
                    try{
                        if(Integer.parseInt(height1)!=0&&Integer.parseInt(weight1)!=0){
                            Log.i("mhysa-->",getUserInfo());
                            OkHttpUtils.postString()
                                    .url(UrlUtils.FileSave)
                                    .content(getUserInfo())
                                    .id(CommitHealth)
                                    .build()
                                    .execute(MyStringCallBack);
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"身高或体重请填写数字!",Toast.LENGTH_SHORT).show();
                    }


                }
                break;
            case R.id.date:
                    LayoutInflater inflater = LayoutInflater.from(ArchivingActivity.this);
                final View timepickerview = inflater.inflate(R.layout.timepicker,null);
                ScreenInfo screenInfo = new ScreenInfo(ArchivingActivity.this);
                wheelMain = new WheelMain(timepickerview);
                wheelMain.screenheight = screenInfo.getHeight();
                String time1 = date.getText().toString();
                Calendar calendar = Calendar.getInstance();
                if (JudgeDate.isDate(time1, "yyyy-MM-dd")) {
                    try {
                        calendar.setTime(dateFormat.parse(time1));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                wheelMain.initDateTimePicker(year, month, day);
                new AlertDialog.Builder(ArchivingActivity.this)
                        .setTitle("请选择时间")
                        .setView(timepickerview)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        date.setText(wheelMain.getTime());
                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                }).show();
                break;
        }
    }

    private StringCallback MyStringCallBack = new StringCallback(){


        @Override
        public void onError(Call call, Exception e, int id) {

            Toast.makeText(getApplicationContext(),"保存失败,请稍后提交",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            Toast.makeText(getApplicationContext(),"保存成功！！",Toast.LENGTH_SHORT).show();
            finish();
        }
    };


}
