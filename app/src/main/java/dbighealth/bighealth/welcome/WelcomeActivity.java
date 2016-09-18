package dbighealth.bighealth.welcome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.Toast;

import dbighealth.bighealth.MainActivity;
import dbighealth.bighealth.R;

public class WelcomeActivity extends Activity {


    private ImageView img;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    boolean time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        img = (ImageView)findViewById(R.id.welcome_img);
        initIntener();
    }

    private void IsHuaDong() {
        sp = getSharedPreferences("time", MODE_PRIVATE);
        editor = sp.edit();
        time = sp.getBoolean("time", false);
        AlphaAnimation alpha = new AlphaAnimation(1.0f, 1.0f);
        alpha.setDuration(3000);
        img.setAnimation(alpha);
        alpha.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
                if (time) {
                    Intent intent_main = new Intent(WelcomeActivity.this,
                            MainActivity.class);
                    startActivity(intent_main);
                    finish();
                } else {
                    editor.putBoolean("time", true);
                    editor.commit();
                    Intent intent_guide = new Intent(WelcomeActivity.this,
                            GuideActivity.class);
                    startActivity(intent_guide);
                    finish();
                }
            }
        });
    }

    /**
     * 网络可用就调用下一步需要进行的方法， 网络不可用则需设置
     */
    private void initIntener() {
        // 判断网络是否可用
        if (true == isOpenNetwork()) {
            IsHuaDong();
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(
                    WelcomeActivity.this);
            builder.setTitle("没有可用的网络").setMessage("是否对网络进行设置?");

            builder.setPositiveButton("是",
                    new DialogInterface.OnClickListener() {
                        @SuppressWarnings("deprecation")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = null;

                            try {
                                String sdkVersion = android.os.Build.VERSION.SDK;
                                if (Integer.valueOf(sdkVersion) > 10) {
                                    intent = new Intent(
                                            android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                                } else {
                                    intent = new Intent();
                                    ComponentName comp = new ComponentName(
                                            "com.android.settings",
                                            "com.android.settings.WirelessSettings");
                                    intent.setComponent(comp);
                                    intent.setAction("android.intent.action.VIEW");
                                }
                                WelcomeActivity.this.startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            System.exit(0);
                        }
                    })
                    .setNegativeButton("否",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.cancel();
                                    // finish();//因为网络不可用的状态，也是不让自己的程序结束运行，
                                    // 这是根据个人需要设置。
                                    Toast.makeText(WelcomeActivity.this,
                                            "", Toast.LENGTH_SHORT)
                                            .show();
                                    System.exit(0);// 这里是没有网络的时候，又不需要手动设置，则显示出来的一个静态页面，根据个人需要。
                                }
                            }).show();


        }
    }

    /**
     * 对网络连接状态进行判断
     *
     * @return true, 可用； false， 不可用
     */
    private boolean isOpenNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }

        return false;
    }
}
