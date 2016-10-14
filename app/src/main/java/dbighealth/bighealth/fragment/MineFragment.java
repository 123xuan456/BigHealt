package dbighealth.bighealth.fragment;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import dbighealth.bighealth.activity.ArchivingActivity;
import dbighealth.bighealth.activity.ConditionActivity;
import dbighealth.bighealth.activity.HasCommitReport;
import dbighealth.bighealth.activity.InformationActivity1;
import dbighealth.bighealth.activity.LoginActivity;
import dbighealth.bighealth.activity.ManageSiteActivity;
import dbighealth.bighealth.activity.Me_LogoutActivity;
import dbighealth.bighealth.activity.MedicalReportActivity;
import dbighealth.bighealth.activity.PhysiqueActivity;
import dbighealth.bighealth.activity.RewritePhysical;
import dbighealth.bighealth.bean.EveryDayBean;
import dbighealth.bighealth.bean.HasCommitBean;
import dbighealth.bighealth.bean.PhysicalBean;
import okhttp3.Call;
import utils.ConfigUsers;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;

/**
 * 我的页面
 */
public class MineFragment extends Fragment implements View.OnClickListener {
    LinearLayout ra;
    @Bind(R.id.iv_touxiang)
    SimpleDraweeView ivTouxiang;
    private RelativeLayout rl;//点击进入登录页面（没有登录时）
    private RelativeLayout rl1;//（登录之后）
    boolean isLogin;
    private TextView textView12;//今日
    private TextView textView13;//体质
    private TextView textView14;//方案
    private TextView textView15;//资讯
    private TextView textView16;//预约
    private TextView textView19;//体检报告
    private TextView textView9;//温馨提示
    private TextView textView11;//每日一读
    private TextView textView50;//昵称
    private TextView textView20;//年龄
    private String photoUrl;
    String id;//用户id
    TextView archiving;
    private SharedPreferences sp;
    private boolean first;
    private Thread mThread;
    String username;
    private int UPDATE = 101;
    private int SEARCH =102;
    private int SYMPTOM = 103;
    private Uri imgUrl;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {//此方法在ui线程运行
            switch (msg.what) {
                case 1:
                    id = SharedPreferencesUtils.getString(getContext(),UrlUtils.LOGIN,"");
                    System.out.println("拿到id=" + id);
                    if (!id.equals("")) {//如果有id
                        rl1.setVisibility(View.VISIBLE);
                        rl.setVisibility(View.GONE);
                    } else if (id.equals("")) {//没有用户id
                        rl1.setVisibility(View.GONE);
                        rl.setVisibility(View.VISIBLE);
                    }
                    break;
            }

        }
    };
    private SharedPreferences sp1;
    private String touxiang;
    private String photoPic;
    private String userid;
    private String sex;
    private ImageView imageView20;
    private ImageView imageView21;
    private SharedPreferences hasCommitReport;
    private boolean commitreport;
    private int count = 1;
    private String year;
    public static Fragment newInstance() {
        MineFragment f = new MineFragment();
        return f;
    }

    /**
     * 接收登录成功的广播
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");//修改昵称
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            private String photoPic;
            @Override
            public void onReceive(Context context, Intent intent){

                userid = SharedPreferencesUtils.getString(getContext(),UrlUtils.LOGIN,"");
                username = intent.getStringExtra("username");
                System.out.println("接收到了id" + userid+"ivtouxiang="+ivTouxiang);
                textView50.setText(username);
                userid = SharedPreferencesUtils.getString(getContext(),UrlUtils.LOGIN,"");
                if(!SharedPreferencesUtils.getString(getContext(),ConfigUsers.USERPIC,"").isEmpty()){
                    photoUrl = SharedPreferencesUtils.getString(getContext(),ConfigUsers.USERPIC,"");
                    imgUrl = BaseApplication.imgUrl;

                    Log.i("mhysa","打印此时的地址："+photoUrl);
                }
               /* if(!BaseApplication.photoPic.equals("")){
                    photoUrl = BaseApplication.photoPic;
                    imgUrl = BaseApplication.imgUrl;

                    Log.i("mhysa","打印此时的地址："+photoUrl);
                }*/else{
                    photoUrl = intent.getStringExtra("photoUrl");
                }

                if(imgUrl!=null&&ivTouxiang != null){
                    Log.i("mhysa","接收到的地址是："+imgUrl.toString());
                    ivTouxiang.setImageURI(imgUrl);
                }else if (photoUrl != null && ivTouxiang != null) {
                    Uri uri = Uri.parse(photoUrl);
                    Log.i("mhysa","接收到的地址是："+photoUrl);
                    ivTouxiang.setImageURI(uri);
                }

                Log.i("pengpeng--->", UrlUtils.UPDATEPIC + "?id=" + userid + "&image=" + intent.getStringExtra("photoUrl"));
                OkHttpUtils.get()
                        .url(UrlUtils.UPDATEPIC)
                        .id(UPDATE)
                        .addParams("id", userid)
                        .addParams("imgage", intent.getStringExtra("photoUrl"))
                        .build()
                        .execute(MyStringCallBack);

                mThread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Message msg = new Message();
                        msg.what = 1;
                        mHandler.sendMessage(msg);
                    }
                });
                mThread.start();//线程启动
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
        sex = SharedPreferencesUtils.getString(getContext(),ConfigUsers.USERSEX,"");
        intentFilter.addAction("android.intent.action.CART_SEX");//修改性别
        BroadcastReceiver mItemViewListClickReceiver1 = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent){
                sex = intent.getStringExtra("sex");
                System.out.println("接收到了b="+sex);
                if ("男".equals(sex)){
                    imageView20.setVisibility(View.VISIBLE);
                    imageView21.setVisibility(View.GONE);
                }else if ("女".equals(sex)){
                    imageView21.setVisibility(View.VISIBLE);
                    imageView20.setVisibility(View.GONE);
                }

            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver1, intentFilter);


        intentFilter.addAction("android.intent.action.CART_YEAR");//修改年龄
        BroadcastReceiver mItemViewListClickReceiver2 = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent){
                year = intent.getStringExtra("year");
                System.out.println("接收到了year="+year);
                textView20.setText(year);
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver2, intentFilter);
    }


    public StringCallback MyStringCallBack = new StringCallback() {

        @Override
        public void onError(Call call, Exception e, int id) {

            Log.i("pengpeng--->", "登录失败" + e.toString());
        }

        @Override
        public void onResponse(String response, int id) {

            if (id == UPDATE) {
                Log.i("pengpeng--->", response);
            }
            if(id==SEARCH){
              /* Gson gson = new Gson();
                JudgePhysicalStatus judgePhysicalStatus = gson.fromJson(response, JudgePhysicalStatus.class);
                int status = judgePhysicalStatus.getStatus();
                if(status==1){
                    Intent hascommit = new Intent(getActivity(), HasCommitReport.class);
                    startActivity(hascommit);
                }
                if(status==0){
                    Intent medicalintent = new Intent(getActivity(), MedicalReportActivity.class);
                    startActivity(medicalintent);
                }
*/

                Gson gson = new Gson();
                HasCommitBean hasCommit = gson.fromJson(response,HasCommitBean.class);

                int code = hasCommit.getCode();
                if(code==200){
                    Intent hascommit = new Intent(getActivity(), HasCommitReport.class);
                    startActivity(hascommit);
                }
                if(code ==400){
                    Intent medicalintent = new Intent(getActivity(), MedicalReportActivity.class);
                    startActivity(medicalintent);
                }

            }

            if(id==SYMPTOM){
                Gson gson = new Gson();
                PhysicalBean physicalBean = gson.fromJson(response, PhysicalBean.class);
                int code = physicalBean.getCode();
                if(code==400){
                    //重填体质
                    Intent intent = new Intent(getActivity(), RewritePhysical.class);
                    startActivity(intent);
                }
                if(code ==200){
                    Intent i3 = new Intent(getActivity(), PhysiqueActivity.class);//体质
                    startActivity(i3);
                }
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        username = BaseApplication.username;
        username = SharedPreferencesUtils.getString(getContext(), ConfigUsers.USERNAME,"");
        photoPic = BaseApplication.photoPic;
        photoPic = SharedPreferencesUtils.getString(getContext(), ConfigUsers.USERPIC,"");
        year = SharedPreferencesUtils.getString(getContext(), ConfigUsers.USERYEAR,"");
        //year = BaseApplication.age;
        Uri imgUrl = BaseApplication.imgUrl;
        Fresco.initialize(getActivity());

        userid = SharedPreferencesUtils.getString(getContext(),UrlUtils.LOGIN,"");

        ra = (LinearLayout) inflater.inflate(R.layout.fragment_mine, null);
        ButterKnife.bind(this, ra);

        TextView tvTab = (TextView) ra.findViewById(R.id.tvTab);
        textView50 = (TextView) ra.findViewById(R.id.textView50);
        textView20=(TextView)ra.findViewById(R.id.textView20);
        textView20.setText(year);
        tvTab.setText("我的");
        Log.i("mhysa--->", "用户名：" + username);

        if (username != null) {
            textView50.setText(username);
        }
        if(imgUrl!=null){
            ivTouxiang.setImageURI(imgUrl);
        }else if (!photoPic.equals("")) {
            Uri uri = Uri.parse(photoPic);
            ivTouxiang.setImageURI(uri);
        }
        setView();

        return ra;


    }

    private void everyday() {
        String url = UrlUtils.EVERYDAYHealth;
        OkHttpUtils.get().url(url).id(1)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                System.out.println("每日一读失败" + e);
            }

            @Override
            public void onResponse(String response, int id) {
                System.out.println("每日一读" + response);
                Gson g = new Gson();
                EveryDayBean everyday = g.fromJson(response, EveryDayBean.class);
                if (everyday.getCode() == 200) {
                    EveryDayBean.MessageBean mes = everyday.getMessage();
                    List<EveryDayBean.MessageBean.DailyBean> day = mes.getDaily();
                    for (int i = 0; i < day.size(); i++) {
                        EveryDayBean.MessageBean.DailyBean d = day.get(i);
                        int dailyId = d.getDailyId();
                        String dailyRead = d.getDailyRead();
                        textView11.setText(dailyRead);
                        String dailyDate = d.getDailyDate();
                    }
                    List<EveryDayBean.MessageBean.ReminderBean> r = mes.getReminder();
                    for (int j = 0; j < r.size(); j++) {
                        EveryDayBean.MessageBean.ReminderBean remind = r.get(j);
                        int reminderId = remind.getReminderId();
                        String reminder = remind.getReminder();
                        textView9.setText(reminder);
                        String reminderDate = remind.getReminderDate();
                    }
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setView() {

        //档案
        archiving = (TextView) ra.findViewById(R.id.textView18);
        rl = (RelativeLayout) ra.findViewById(R.id.rl);
        rl.setOnClickListener(this);
        rl1 = (RelativeLayout) ra.findViewById(R.id.rl1);
        rl1.setOnClickListener(this);
        textView12 = (TextView) ra.findViewById(R.id.textView12);
        textView12.setOnClickListener(this);
        textView13 = (TextView) ra.findViewById(R.id.textView13);
        textView13.setOnClickListener(this);
        textView14 = (TextView) ra.findViewById(R.id.textView14);
        textView14.setOnClickListener(this);
        textView15 = (TextView) ra.findViewById(R.id.textView15);
        textView15.setOnClickListener(this);
        textView16 = (TextView) ra.findViewById(R.id.textView16);
        textView16.setOnClickListener(this);
        textView19 = (TextView) ra.findViewById(R.id.textView19);
        textView50 = (TextView) ra.findViewById(R.id.textView50);
        textView19.setOnClickListener(this);
        archiving.setOnClickListener(this);

        textView9 = (TextView)ra.findViewById(R.id.textView9);
        textView11 = (TextView)ra.findViewById(R.id.textView11);
        imageView20 = (ImageView)ra.findViewById(R.id.imageView20);//男
        imageView21 = (ImageView)ra.findViewById(R.id.imageView21);//女
        sex = SharedPreferencesUtils.getString(getContext(),ConfigUsers.USERSEX,"");
        System.out.println("是否保存到了sp="+sex);
        if ("男".equals(sex)){
            imageView20.setVisibility(View.VISIBLE);
            imageView21.setVisibility(View.GONE);
        }else if ("女".equals(sex)){
            imageView21.setVisibility(View.VISIBLE);
            imageView20.setVisibility(View.GONE);
        }

        id = SharedPreferencesUtils.getString(getContext(),UrlUtils.LOGIN,"");
        if (!id.equals("")) {//如果有id
            rl1.setVisibility(View.VISIBLE);
            rl.setVisibility(View.GONE);
        } else if (id.equals("")) {//没有用户id
            rl1.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
        }
        everyday();//每日一读
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.rl:
                Intent i = new Intent(getActivity(), LoginActivity.class);//没有登录点击进入登录页面
                startActivity(i);
                break;
            case R.id.rl1:
                Intent i1 = new Intent();//已经登录后，点击进入详情
                Bundle bundle = new Bundle();
                if(BaseApplication.imgUrl!=null){
                    bundle.putString("imgurl",BaseApplication.imgUrl.toString());
                }
                bundle.putString("picUrl", SharedPreferencesUtils.getString(getContext(),ConfigUsers.USERPIC,""));
                bundle.putString("name", SharedPreferencesUtils.getString(getContext(),ConfigUsers.USERNAME,""));
//                bundle.putString("uid", BaseApplication.userid);
                i1.setClass(getActivity(), Me_LogoutActivity.class);
                i1.putExtras(bundle);
                startActivity(i1);
                break;
            case R.id.textView12:
                /**
                 * 判断是否提交过
                 */
                if (!TextUtils.isEmpty(id)) {
                    Intent i2 = new Intent(getActivity(), ConditionActivity.class);//每日情况
                    startActivity(i2);
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            /**
             * 判断是否提交过
             *//*
                    Intent i2=new Intent(getContext(), ConditionActivity.class);//每日情况
                    startActivity(i2);

                break;*/
            case R.id.textView13:
                sp = getActivity().getSharedPreferences("commit",
                        Activity.MODE_PRIVATE);
                first = sp.getBoolean("First", false);
                Log.i("mhysa-->", "是否保存了" + first);
                if (!TextUtils.isEmpty(id)) {
                    if (first) {
                        Intent intent = new Intent(getActivity(), RewritePhysical.class);
                        startActivity(intent);
                    } else {
                        Log.i("mhysa-->","此时的提交与否地址："+UrlUtils.JUDGEPHYSICAL+"?userId="+ SharedPreferencesUtils.getString(getContext(),UrlUtils.LOGIN,""));
                        OkHttpUtils.get()
                                .url(UrlUtils.JUDGEPHYSICAL)
                                .id(SYMPTOM)
                                .addParams("userId",userid)
                                .build()
                                .execute(MyStringCallBack);

                    }

                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.textView14:
                /*Intent i4=new Intent(getContext(), ConditionActivity.class);//方案
                startActivity(i4);*/
                if (!TextUtils.isEmpty(id)) {
                    Toast.makeText(getActivity(), "方案生成中，请稍后！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.textView15:
                if (!TextUtils.isEmpty(id)) {
                    Intent i5 = new Intent(getActivity(), InformationActivity1.class);
                    startActivity(i5);
                } else {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.textView16:
                if (!TextUtils.isEmpty(id)) {
//                    Intent i6 = new Intent(getActivity(), SubscribeActivity.class);//预约
                    Intent i6 = new Intent(getActivity(), ManageSiteActivity.class);//预约
                    startActivity(i6);
                } else {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.textView18:
                if (!TextUtils.isEmpty(id)) {
                    Intent intent = new Intent(getActivity(), ArchivingActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                }

                break;
            /**
             * 体检报告
             */
            case R.id.textView19:
                        if (!TextUtils.isEmpty(userid)){
                            initIntenet();

                        }else{
                            Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                        }

                        break;

                }
        }

    public void initIntenet(){

        Log.i("mhysa-->","此时的地址是："+UrlUtils.SEARCHREPORT+"?userId="+SharedPreferencesUtils.getString(getContext(),UrlUtils.LOGIN,""));
        OkHttpUtils.get()
                .url(UrlUtils.SEARCHREPORT)
                .id(SEARCH)
                .addParams("userId",SharedPreferencesUtils.getString(getContext(),UrlUtils.LOGIN,""))
                .build()
                .execute(MyStringCallBack);
    }


    @Override
    public void onDestroyView () {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
