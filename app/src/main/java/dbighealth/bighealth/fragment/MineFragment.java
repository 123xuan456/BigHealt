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
import dbighealth.bighealth.activity.InformationActivity1;
import dbighealth.bighealth.activity.LoginActivity;
import dbighealth.bighealth.activity.Me_LogoutActivity;
import dbighealth.bighealth.activity.PhysiqueActivity;
import dbighealth.bighealth.activity.RewritePhysical;
import dbighealth.bighealth.activity.SubscribeActivity;
import dbighealth.bighealth.bean.EveryDayBean;
import okhttp3.Call;

/**
 * simple {@link Fragment} subclass.
 * 我的页面
 */
public class MineFragment extends Fragment implements View.OnClickListener {
  /*  LinearLayout ra;
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
    String id;//用户id
    TextView archiving;
    private SharedPreferences sp;
<<<<<<< HEAD
    private boolean first;*/
    // private boolean first;
    //  private Thread mThread;
    //  String username;
  /*  private Handler mHandler = new Handler() {
        public void handleMessage (Message msg) {//此方法在ui线程运行
            switch(msg.what) {
                case 1:
                    id = BaseApplication.userid;
                    System.out.println("拿到id="+id);
                    if(!id.equals("")){//如果有id
                        rl1.setVisibility(View.VISIBLE);
                        rl.setVisibility(View.GONE);
                    }else
                    if (id.equals("")){//没有用户id
                        rl1.setVisibility(View.GONE);
                        rl.setVisibility(View.VISIBLE);
                    }
                    break;
            }

        }
    };*/

  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ra = (LinearLayout) inflater.inflate(R.layout.fragment_mine, null);
        TextView tvTab= (TextView) ra.findViewById(R.id.tvTab);
        tvTab.setText("我的");
        setView();
        username=BaseApplication.username;
        textView50.setText(username);
        sp = getActivity().getSharedPreferences("commit",
                Activity.MODE_PRIVATE);
        first = sp.getBoolean("First", false);
        Log.i("mhysa-->","是否保存了"+first);
        return ra;
    }*/
   /* private void setView() {
=======
    private void everyday() {
        String url = "http://192.168.0.120:8081/JianKangChanYe/HomePage/list";
        OkHttpUtils.get().url(url).id(1)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                System.out.println("每日一读失败"+e);
            }
            @Override
            public void onResponse(String response, int id) {
                System.out.println("每日一读"+response);
                Gson g=new Gson();
                EveryDayBean everyday = g.fromJson(response, EveryDayBean.class);
                if (everyday.getCode()==200){
                    EveryDayBean.MessageBean mes = everyday.getMessage();
                    List<EveryDayBean.MessageBean.DailyBean> day = mes.getDaily();
                    for (int i=0;i<day.size();i++){
                        EveryDayBean.MessageBean.DailyBean d = day.get(i);
                        int dailyId = d.getDailyId();
                        String dailyRead = d.getDailyRead();
                        textView11.setText(dailyRead);
                        String dailyDate = d.getDailyDate();

                    }
                    List<EveryDayBean.MessageBean.ReminderBean> r = mes.getReminder();
                    for (int j=0;j<r.size();j++){
                        EveryDayBean.MessageBean.ReminderBean remind = r.get(j);
                        int  reminderId=remind.getReminderId();
                        String  reminder=remind.getReminder();
                        textView9.setText(reminder);
                        String  reminderDate=remind.getReminderDate();
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
>>>>>>> f5f8d3b4c9a190e5e3297d07ab1f8b8a4b320fda
        rl=(RelativeLayout)ra.findViewById(R.id.rl);
        rl.setOnClickListener(this);
        rl1=(RelativeLayout)ra.findViewById(R.id.rl1);
        rl1.setOnClickListener(this);
        textView12=(TextView)ra.findViewById(R.id.textView12);
        textView12.setOnClickListener(this);
        textView13=(TextView)ra.findViewById(R.id.textView13);
        textView13.setOnClickListener(this);
        textView14=(TextView)ra.findViewById(R.id.textView14);
        textView14.setOnClickListener(this);
        textView15=(TextView)ra.findViewById(R.id.textView15);
        textView15.setOnClickListener(this);
        textView16=(TextView)ra.findViewById(R.id.textView16);
        textView16.setOnClickListener(this);
        textView19 = (TextView)ra.findViewById(R.id.textView19);
        textView50 = (TextView)ra.findViewById(R.id.textView50);
        textView19.setOnClickListener(this);
        archiving.setOnClickListener(this);

        textView9 = (TextView)ra.findViewById(R.id.textView9);
        textView11 = (TextView)ra.findViewById(R.id.textView11);

        id = BaseApplication.userid;
        System.out.println("拿到id="+id);
        if(!id.equals("")){//如果有id
            rl1.setVisibility(View.VISIBLE);
            rl.setVisibility(View.GONE);
        }else
        if (id.equals("")){//没有用户id
            rl1.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
        }
        everyday();//每日一读
    }
*/


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
    String id;//用户id
    TextView archiving;
    private SharedPreferences sp;
    private boolean first;
    private Thread mThread;
    String username;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {//此方法在ui线程运行
            switch (msg.what) {
                case 1:
                    id = BaseApplication.userid;
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
        intentFilter.addAction("android.intent.action.CART_BROADCAST");//
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                username = intent.getStringExtra("username");
                System.out.println("接收到了" + username);
                textView50.setText(username);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Fresco.initialize(getActivity());
        ra = (LinearLayout) inflater.inflate(R.layout.fragment_mine, null);
        TextView tvTab = (TextView) ra.findViewById(R.id.tvTab);
        textView50 = (TextView) ra.findViewById(R.id.textView50);
        tvTab.setText("我的");
        username = BaseApplication.username;
        ButterKnife.bind(this, ra);

        if (username != null) {
            textView50.setText(username);
        }
        sp1 = getActivity().getSharedPreferences("potrait", Activity.MODE_PRIVATE);
        touxiang = sp1.getString("touxiang", "");
        setView();


        return ra;
    }



    private void everyday() {
        String url = "http://192.168.0.120:8081/JianKangChanYe/HomePage/list";
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

        textView9 = (TextView) ra.findViewById(R.id.textView9);
        textView11 = (TextView) ra.findViewById(R.id.textView11);

        id = BaseApplication.userid;
        Log.i("mhysa-->","头像"+id);
//        if (!touxiang.isEmpty()) {
//            Uri uri = Uri.parse("\""+touxiang+"\"");
//            Log.i("mhysa-->","图片的Uri是："+uri);
//            ivTouxiang.setImageURI(uri);
//        }
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
                Intent i = new Intent(getContext(), LoginActivity.class);//没有登录点击进入登录页面
                startActivity(i);
                break;
            case R.id.rl1:
                Intent i1 = new Intent(getContext(), Me_LogoutActivity.class);//已经登录后，点击进入详情
                startActivity(i1);
                break;
            case R.id.textView12:
                /**
                 * 判断是否提交过
                 */

                if (!TextUtils.isEmpty(id)) {
                    Intent i2 = new Intent(getContext(), ConditionActivity.class);//每日情况
                    startActivity(i2);
                } else {
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                }
                break;

          /*  case R.id.rl:
                Intent i=new Intent(getContext(), LoginActivity.class);//没有登录点击进入登录页面
                startActivity(i);
                break;
            case R.id.rl1:
                //Intent i1=new Intent(getContext(), Me_LogoutActivity.class);//已经登录后，点击进入详情
                //startActivity(i1);
                break;
            case R.id.textView12:
                *//**
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
                        Intent intent = new Intent(getContext(), RewritePhysical.class);
                        startActivity(intent);
                    } else {
                        Intent i3 = new Intent(getContext(), PhysiqueActivity.class);//体质
                        startActivity(i3);
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
                    Intent i5 = new Intent(getContext(), InformationActivity1.class);//资讯
                    startActivity(i5);
                } else {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                }

              /*  Intent intent11 = new Intent(getContext(), BllDemo.class);
                startActivity(intent11);*/

                break;
            case R.id.textView16:
                if (!TextUtils.isEmpty(id)) {
                    Intent i6 = new Intent(getContext(), SubscribeActivity.class);//预约
                    startActivity(i6);
                } else {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.textView19:
                sp = getActivity().getSharedPreferences("commit",
                        Activity.MODE_PRIVATE);
                first = sp.getBoolean("First", false);
                if (!TextUtils.isEmpty(id)) {
                    if (first) {
                        Toast.makeText(getActivity(), "体检报告生成中，请稍后！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "请先填写体质测试！！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.textView18:
                if (!TextUtils.isEmpty(id)) {
                    Intent intent = new Intent(getContext(), ArchivingActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
