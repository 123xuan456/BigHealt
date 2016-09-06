package dbighealth.bighealth.welcome;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.View.OnClickListener;
import android.content.SharedPreferences;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dbighealth.bighealth.MainActivity;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.GuideViewPagerAdapter;
import dbighealth.bighealth.fragment.HomeFragment;

public class GuideActivity extends AppCompatActivity {

    private ViewPager viewpager;
    private List<View> list;
    private ViewGroup index, point;
    private ImageView img;
    private ImageView[] img_s;
    private TextView btn_go;

    @Override
    protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);

        LayoutInflater flater = getLayoutInflater();
        list = new ArrayList<View>();
        list.add(flater.inflate(R.layout.viewpager_1, null));
        list.add(flater.inflate(R.layout.viewpager_2, null));
        list.add(flater.inflate(R.layout.viewpager_3, null));
        list.add(flater.inflate(R.layout.viewpager_4, null));
        View v = flater.inflate(R.layout.viewpager_5, null);
        list.add(v);

    btn_go = (TextView) v.findViewById(R.id.guide_viewpager_btn_go);

    index = (ViewGroup) flater.inflate(R.layout.activity_guide, null);
    point = (ViewGroup) index.findViewById(R.id.guide_point);
    viewpager = (ViewPager) index.findViewById(R.id.guide_viewpager);
    img_s = new ImageView[list.size()];
    for (int i = 0; i < list.size(); i++) {
        LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        if (i == 0) {
            margin.setMargins(20, 10, 10, 20);
        } else if (i == 1) {
            margin.setMargins(20, 10, 10, 20);
        } else if (i == 2) {
            margin.setMargins(20, 10, 10, 20);
        }else if (i == 3) {
            margin.setMargins(20, 10, 10, 20);
        } else if (i == 4) {
            margin.setMargins(20, 10, 10, 20);
        }
        img = new ImageView(GuideActivity.this);
        img.setLayoutParams(new LayoutParams(10, 10));
        img_s[i] = img;
        if (i == 0) {
            img_s[i].setBackgroundResource(R.drawable.guide_dot_black);

        } else {
            img_s[i].setBackgroundResource(R.drawable.guide_dot_white);
        }
        point.addView(img_s[i], margin);
    }
    setContentView(index);

    GuideViewPagerAdapter adapter = new GuideViewPagerAdapter(list);
    viewpager.setAdapter(adapter);
    viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            for (int i = 0; i < img_s.length; i++) {
                        img_s[arg0].setBackgroundResource(R.drawable.guide_dot_black);
                        if (arg0 != i) {
                        img_s[i].setBackgroundResource(R.drawable.guide_dot_white);
                    }
            }
            if (arg0 == img_s.length - 1) {
                btn_go.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        Intent intent = new Intent(GuideActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }
    });

    // 实例化SharedPreferences对象（第一步）
    SharedPreferences mySharedPreferences = getSharedPreferences("test",
            Activity.MODE_PRIVATE);
    // 实例化SharedPreferences.Editor对象（第二步）
    SharedPreferences.Editor editor = mySharedPreferences.edit();
    // 用putString的方法保存数据
    editor.putString("bubaonum", "0");
    editor.putString("gonglinum", "1");
    editor.putString("kalulinum", "1");
        editor.putString("bubaonum1", "1");
        editor.putString("gonglinum1", "1");

    // 提交当前数据
    editor.commit();
    }
}
