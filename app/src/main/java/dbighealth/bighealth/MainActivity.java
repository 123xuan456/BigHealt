package dbighealth.bighealth;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import dbighealth.bighealth.fragment.HomeFragment;
import dbighealth.bighealth.fragment.MineFragment;
import dbighealth.bighealth.fragment.ProductFragment;
import dbighealth.bighealth.fragment.SystemFragment;
import dbighealth.bighealth.fragment.TreatmentFragment;

public class MainActivity extends FragmentActivity implements TabHost.OnTabChangeListener{
    private FragmentTabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tabHost=(FragmentTabHost)super.findViewById(android.R.id.tabhost);
        tabHost.setup(this,super.getSupportFragmentManager()
                ,R.id.contentLayout);
        tabHost.getTabWidget().setDividerDrawable(null);
        tabHost.setOnTabChangedListener(this);
        initTab();
    }
    String[] tabs={"首页","体系","医疗养生","产品"," 我的"};
    Class[] clz={HomeFragment.class,SystemFragment.class,
            TreatmentFragment.class,ProductFragment.class,
            MineFragment.class};

    private void initTab() {
        for(int i=0;i<tabs.length;i++){
            TabHost.TabSpec tabSpec=tabHost.newTabSpec(tabs[i]).setIndicator(getTabView(i));
            tabHost.addTab(tabSpec,clz[i],null);
            tabHost.setTag(i);
        }

    }

    private View getTabView(int idx) {
        View view= LayoutInflater.from(this).inflate(R.layout.footer_tabs,null);
        ((TextView)view.findViewById(R.id.tvTab)).setText(tabs[idx]);
        if(idx==0){
            ((TextView)view.findViewById(R.id.tvTab)).setTextColor(Color.RED);
        }else{
        }
        return view;
    }
    @Override
    public void onTabChanged(String tabId) {
        updateTab();
    }

    private void updateTab() {
        TabWidget tabw=tabHost.getTabWidget();
        for(int i=0;i<tabw.getChildCount();i++){
            View view=tabw.getChildAt(i);
            if(i==tabHost.getCurrentTab()){
                ((TextView)view.findViewById(R.id.tvTab)).setTextColor(Color.YELLOW);
            }else{
                ((TextView)view.findViewById(R.id.tvTab)).setTextColor(Color.WHITE);
            }

        }

    }
}
