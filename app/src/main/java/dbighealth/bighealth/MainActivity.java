package dbighealth.bighealth;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import dbighealth.bighealth.fragment.HomeFragment;
import dbighealth.bighealth.fragment.MineFragment;
import dbighealth.bighealth.fragment.ProductFragment;
import dbighealth.bighealth.fragment.SystemFragment;
import dbighealth.bighealth.fragment.TreatmentFragment;
import utils.JudgeIntenet;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {
    RadioGroup radioGroup;

    private Fragment fragment;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.activity_main);

        //判断网络是否可用
        JudgeIntenet judgeIntenet = new JudgeIntenet(this);
        judgeIntenet.initIntener();

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        radioGroup.setOnCheckedChangeListener(this);
        fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);

        if (fragment == null) {
            fragment = HomeFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, HomeFragment.newInstance()).commit();
        }
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    // 点击俩次退出的判断
    private long firstTime = 0;
   // private Editor edit;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) { // 如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出换钱", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;// 更新firstTime
                    return true;
                } else { // 两次按键小于2秒时，退出应用

                    System.exit(0);
                }
                break;
        }

        return super.onKeyUp(keyCode, event);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int i) {
        switch (i) {
            case R.id.tab_one: {
                fragment = HomeFragment.newInstance();//首页
                /*getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment,HomeFragment.newInstance()).commit();*/
            }
            break;
            case R.id.tab_two: {
                fragment = SystemFragment.newInstance();//体系
            }
            break;
            case R.id.tab_three: {
                fragment = TreatmentFragment.newInstance();//医疗养生
            }
            break;
            case R.id.tab_four: {
                fragment = ProductFragment.newInstance();//产品
            }
            break;
            case R.id.tab_four1: {
                fragment = MineFragment.newInstance();//我的
            }
            break;
        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
        }
    }
}
