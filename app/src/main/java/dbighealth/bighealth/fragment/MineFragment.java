package dbighealth.bighealth.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import dbighealth.bighealth.R;
import dbighealth.bighealth.activity.ArchivingActivity;
import dbighealth.bighealth.activity.ConditionActivity;
import dbighealth.bighealth.activity.Home_Page_Details;
import dbighealth.bighealth.activity.InformationActivity;
import dbighealth.bighealth.activity.LoginActivity;
import dbighealth.bighealth.activity.SubscribeActivity;

/**
 *  simple {@link Fragment} subclass.
 * 我的页面
 */
public class MineFragment extends Fragment implements View.OnClickListener{
    LinearLayout ra;
    private RelativeLayout rl;//点击进入登录页面（没有登录时）
    private RelativeLayout rl1;//（登录之后）
     boolean isLogin= true;
    private TextView textView12;//今日
    private TextView textView13;//体质
    private TextView textView14;//方案
    private TextView textView15;//资讯
    private TextView textView16;//预约
    private TextView textView19;//体检报告
    TextView archiving;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ra = (LinearLayout) inflater.inflate(R.layout.fragment_mine, null);
        TextView tvTab= (TextView) ra.findViewById(R.id.tvTab);
        //档案
        archiving = (TextView) ra.findViewById(R.id.textView18);
        tvTab.setText("我的");
        setView();
        return ra;
    }

    private void setView() {
        rl=(RelativeLayout)ra.findViewById(R.id.rl);
        rl.setOnClickListener(this);
        rl1=(RelativeLayout)ra.findViewById(R.id.rl1);
        rl1.setOnClickListener(this);
//        if (isLogin=true){
//            rl1.setVisibility(View.VISIBLE);
//            rl.setVisibility(View.GONE);
//        }else if (isLogin=false){
//            rl1.setVisibility(View.GONE);
//            rl.setVisibility(View.VISIBLE);
//        }
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
        textView19.setOnClickListener(this);
        archiving.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl:
                Intent i=new Intent(getContext(), LoginActivity.class);//没有登录点击进入登录页面
                startActivity(i);
                break;
            case R.id.rl1:
                //Intent i1=new Intent(getContext(), Me_LogoutActivity.class);//已经登录后，点击进入详情
                //startActivity(i1);
                break;
            case R.id.textView12:
                Intent i2=new Intent(getContext(), ConditionActivity.class);//每日情况
                startActivity(i2);
                break;
            case R.id.textView13:
                Intent i3=new Intent(getContext(), ConditionActivity.class);//体质
                startActivity(i3);
                break;
            case R.id.textView14:
                Intent i4=new Intent(getContext(), ConditionActivity.class);//方案
                startActivity(i4);
                break;
            case R.id.textView15:
                Intent i5=new Intent(getContext(), InformationActivity.class);//资讯
                startActivity(i5);
                break;
            case R.id.textView16:
                Intent i6=new Intent(getContext(), SubscribeActivity.class);//预约
                startActivity(i6);
                break;
            case R.id.textView19:
                Intent i9=new Intent(getContext(), Home_Page_Details.class);//体检报告
                startActivity(i9);
                break;
            case R.id.textView18:
                Intent intent = new Intent(getContext(), ArchivingActivity.class);
                startActivity(intent);
                break;
        }
    }
}
