package dbighealth.bighealth.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import dbighealth.bighealth.R;

/**
 * A simple {@link Fragment} subclass.
 * 体系页面
 */
public class SystemFragment extends Fragment implements View.OnClickListener{

    View ra;
    private RelativeLayout r1,r2,r3,r4,r5;
    private LinearLayout l1,l2,l3,l4,l5;
    private ImageView image4,image5,image6,image7,image8;
    private TextView tv1,tv2,tv3,tv4,tv5;
    private View l11;

    public SystemFragment() {
    }
    private static int TOUCH;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ra = inflater.inflate(R.layout.fragment_system, container, false);
        TextView tvTab= (TextView) ra.findViewById(R.id.tvTab);
        tvTab.setText("体系");
        setView();
        return ra;
    }

    private void setView() {
        l1= (LinearLayout) ra.findViewById(R.id.l1);
        l2= (LinearLayout) ra.findViewById(R.id.l2);
        l3= (LinearLayout) ra.findViewById(R.id.l3);
        l4= (LinearLayout) ra.findViewById(R.id.l4);
        l5= (LinearLayout) ra.findViewById(R.id.l5);
        r1=(RelativeLayout)ra.findViewById(R.id.rl);
        r1.setOnClickListener(this);
        r2=(RelativeLayout)ra.findViewById(R.id.r2);
        r2.setOnClickListener(this);
        r3=(RelativeLayout)ra.findViewById(R.id.r3);
        r3.setOnClickListener(this);
        r4=(RelativeLayout)ra.findViewById(R.id.r4);
        r4.setOnClickListener(this);
        r5=(RelativeLayout)ra.findViewById(R.id.r5);
        r5.setOnClickListener(this);
        image4=(ImageView)ra.findViewById(R.id.imageView4);
        image5=(ImageView)ra.findViewById(R.id.imageView5);
        image6=(ImageView)ra.findViewById(R.id.imageView6);
        image7=(ImageView)ra.findViewById(R.id.imageView7);
        image8=(ImageView)ra.findViewById(R.id.imageView8);

        tv1=(TextView)ra.findViewById(R.id.tv1);
        tv2=(TextView)ra.findViewById(R.id.tv2);
        tv3=(TextView)ra.findViewById(R.id.tv3);
        tv4=(TextView)ra.findViewById(R.id.tv4);
        tv5=(TextView)ra.findViewById(R.id.tv5);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl:
                if (TOUCH==0){
                    image4.setBackgroundResource(R.drawable.down);
                    is(image5, l2);
                    is(image6, l3);
                    is(image7, l4);
                    is(image8, l5);

                    l1.setVisibility(View.VISIBLE);
                    tv1.setText("二型糖尿病康复技术体系");
                    tv2.setText("重度肥胖病诊断技术与绿色不反弹减肥法");
                    tv3.setText("重度脂肪肝精准治愈康复方案");
                    tv4.setText("高尿酸患者体质分析技术与快速复原方案");
                    tv5.setText("痛风精准自然医学方案");
                    TOUCH=1;
                }else if (TOUCH==1){

                    is(image4, l1);
                    TOUCH=0;
                }

                break;
            case R.id.r2:
                if (TOUCH==0){
                    image5.setBackgroundResource(R.drawable.down);
                    is(image4,l1);
                    is(image6, l3);
                    is(image7,l4);
                    is(image8, l5);
                    l2.setVisibility(View.VISIBLE);
                    TOUCH=1;

                }else if (TOUCH==1){
                    is(image5,l2);
                    TOUCH=0;
                }
                break;

            case R.id.r3:

                if (TOUCH==0){
                    image6.setBackgroundResource(R.drawable.down);

                    is(image4,l1);
                    is(image5, l2);
                    is(image7, l4);
                    is(image8, l5);
                    l3.setVisibility(View.VISIBLE);
                    TOUCH=1;
                }else if (TOUCH==1){
                    is(image6, l3);
                    TOUCH=0;
                }
                break;
            case R.id.r4:

                if (TOUCH==0){
                    image7.setBackgroundResource(R.drawable.down);
                    is(image4,l1);
                    is(image5, l2);
                    is(image6,l3);
                    is(image8, l5);
                    l4.setVisibility(View.VISIBLE);
                    TOUCH=1;
                }else if (TOUCH==1){
                    is(image7, l4);
                    TOUCH=0;
                }
                break;
            case R.id.r5:

                if (TOUCH==0){
                    image8.setBackgroundResource(R.drawable.down);

                    is(image4,l1);
                    is(image5,l2);
                    is(image6,l3);
                    is(image7,l4);
                    l5.setVisibility(View.VISIBLE);
                    TOUCH=1;
                }else if (TOUCH==1){
                    is(image8,l5);
                    TOUCH=0;
                }
                break;
        }
    }

    private void is(ImageView image, LinearLayout l) {
        image.setBackgroundResource(R.drawable.up);
        l.setVisibility(View.GONE);

    }
}
