package dbighealth.bighealth.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.NewsFragmentPagerAdapter;
import dbighealth.bighealth.ben.Channel;
import dbighealth.bighealth.ben.ChannelDb;
import dbighealth.bighealth.fragment.home.CommonFragment;
import dbighealth.bighealth.fragment.home.NewsChannelFragment;
import dbighealth.bighealth.fragment.home.SpecialFragment1;
import utils.UrlUtils;

/**首页*/
public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener{
    private View view=null;
    private RadioGroup rgChannel=null;
    private ViewPager viewPager;
    private HorizontalScrollView hvChannel=null;
    private List<Fragment> newsChannelList=new ArrayList<Fragment>();
    private NewsFragmentPagerAdapter adapter;
    public static Fragment newInstance() {
        HomeFragment f = new HomeFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view==null){
            view=inflater.inflate(R.layout.fragment_home, null);

            rgChannel=(RadioGroup)view.findViewById(R.id.rgChannel);
            viewPager=(ViewPager)view.findViewById(R.id.vpNewsList);
            hvChannel=(HorizontalScrollView)view.findViewById(R.id.hvChannel);
            rgChannel.setOnCheckedChangeListener(
                    new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group,
                                                     int checkedId) {
                         //   Toast.makeText(getActivity(),"当前坐标："+checkedId,Toast.LENGTH_SHORT).show();
                            viewPager.setCurrentItem(checkedId);
                        }
                    });
            initTab(inflater);
            initViewPager();
        }
        ViewGroup parent=(ViewGroup)view.getParent();
        if(parent!=null){
            parent.removeView(view);
        }
        return view;
    }

    private void initViewPager(){

        List<Channel> channelList= ChannelDb.getSelectedChannel();//获取到上标题///暂时用不着
                NewsChannelFragment fragment=new NewsChannelFragment();
             //   CommonFragment recommandFragment=new CommonFragment(UrlUtils.CommonHome,"1");
                CommonFragment TangNiaoBing=new CommonFragment(UrlUtils.CommonHome,"2");
                CommonFragment JingZhuiBing=new CommonFragment(UrlUtils.CommonHome,"3");
                CommonFragment JianZhouyan=new CommonFragment(UrlUtils.CommonHome,"4");
                CommonFragment XinNaoXueGuan=new CommonFragment(UrlUtils.CommonHome,"5");
        //        CommonFragment Fukebing=new CommonFragment(UrlUtils.CommonHome,"6");
       //         CommonFragment Qianliexian=new CommonFragment(UrlUtils.CommonHome,"7");
      //         CommonFragment PiFuBing=new CommonFragment();
                SpecialFragment1 PiFuBing = new SpecialFragment1();
        //        CommonFragment Jiaoqi=new CommonFragment(UrlUtils.CommonHome,"8");

               // newsChannelList.add(recommandFragment);
                newsChannelList.add(TangNiaoBing);
                newsChannelList.add(JingZhuiBing);
                newsChannelList.add(JianZhouyan);
                newsChannelList.add(XinNaoXueGuan);
            //    newsChannelList.add(Fukebing);
              //  newsChannelList.add(Qianliexian);
                newsChannelList.add(PiFuBing);
              //  newsChannelList.add(Jiaoqi);
      //  adapter=new NewsFragmentPagerAdapter(super.getActivity().getSupportFragmentManager(), newsChannelList);
        adapter=new NewsFragmentPagerAdapter(getChildFragmentManager(), newsChannelList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        int currentItem = viewPager.getCurrentItem();
           viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
    }
    private void initTab(LayoutInflater inflater){
        List<Channel> channelList=ChannelDb.getSelectedChannel();
        for(int i=0;i<channelList.size();i++){
            RadioButton rb=(RadioButton)inflater.
                    inflate(R.layout.tab_rb, null);
            rb.setId(i);
            rb.setText(channelList.get(i).getName());
            RadioGroup.LayoutParams params=new
                    RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT);
            rb.setLayoutParams(params);
            rgChannel.addView(rb);
        }
        rgChannel.check(0);
    }


    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
    }

    @Override
    public void setArguments(Bundle args) {
        // TODO Auto-generated method stub
        super.setArguments(args);
    }
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }
    @Override
    public void onPageSelected(int idx) {
        setTab(idx);
    }
    private void setTab(int idx){
        RadioButton rb=(RadioButton)rgChannel.getChildAt(idx);
        rb.setChecked(true);
        int left=rb.getLeft();
        int width=rb.getMeasuredWidth();
        DisplayMetrics metrics=new DisplayMetrics();
        super.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth=metrics.widthPixels;
        int len=left+width/2-screenWidth/2;
        hvChannel.smoothScrollTo(len, 0);
    }


}
