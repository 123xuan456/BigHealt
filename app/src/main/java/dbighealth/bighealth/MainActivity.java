package dbighealth.bighealth;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioGroup;

import dbighealth.bighealth.fragment.HomeFragment;
import dbighealth.bighealth.fragment.MineFragment;
import dbighealth.bighealth.fragment.ProductFragment;
import dbighealth.bighealth.fragment.SystemFragment;
import dbighealth.bighealth.fragment.TreatmentFragment;
import utils.JudgeIntenet;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    RadioGroup radioGroup;

    private Fragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.activity_main);

        //判断网络是否可用
        JudgeIntenet judgeIntenet = new JudgeIntenet(this);
        judgeIntenet.initIntener();

        radioGroup= (RadioGroup) findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener(this);
        fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);

        if (fragment == null) {
            fragment = HomeFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment,HomeFragment.newInstance()).commit();
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int i) {
        switch (i){
            case R.id.tab_one:{
                fragment = HomeFragment.newInstance();//首页
                /*getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment,HomeFragment.newInstance()).commit();*/
            }
            break;
            case R.id.tab_two:{
                fragment =SystemFragment.newInstance();//体系
            }
            break;
            case R.id.tab_three:{
                fragment =TreatmentFragment.newInstance();//医疗养生
            }
            break;
            case R.id.tab_four:{
                fragment =ProductFragment.newInstance();//产品
            }
            break;
            case R.id.tab_four1:{
                fragment =MineFragment.newInstance();//我的
            }
            break;
        }
        if(fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
        }
    }

        
    }
