package dbighealth.bighealth.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import dbighealth.bighealth.ben.Model;
import dbighealth.bighealth.R;

/**
 *  simple {@link Fragment} subclass.
 * 产品
 */
public class ProductFragment extends Fragment {

    private String[] list;
    private TextView[] tvList;
    private View[] views;
    private LayoutInflater inflater;
    private ScrollView scrollView;
    private ViewPager mPager;
    private int currentItem = 0;
    View ra;

    public static Fragment newInstance() {
        ProductFragment f = new ProductFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(getActivity());
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ra = inflater.inflate(R.layout.fragment_product, container, false);
        TextView tvTab= (TextView) ra.findViewById(R.id.tvTab);
        tvTab.setText("产品");
        mPager = (ViewPager) ra.findViewById(R.id.goods_pager);
        scrollView = (ScrollView) ra.findViewById(R.id.tools_scrlllview);
        list = Model.toolsList;
        setView();
        showToolsView();
        return ra;
    }


    private void showToolsView() {

        LinearLayout toolsLayout = (LinearLayout) ra.findViewById(R.id.tools);
        int s = list.length;
        System.out.println("ssadadaas="+s);
        tvList = new TextView[s];
        views = new View[s];
        for (int i = 0; i < s; i++) {
            View view = inflater.inflate(R.layout.item_list_layout, null);
            view.setId(i);
            view.setOnClickListener(toolsItemListener);
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText(list[i]);
            toolsLayout.addView(view);
            tvList[i] = textView;
            views[i] = view;
        }
        changeTextColor(0);
    }

    private void changeTextColor(int id) {
        for (int i = 0; i < tvList.length; i++) {
            if (i != id) {
                tvList[i].setBackgroundColor(0x00000000);
                tvList[i].setTextColor(0xFF000000);
            }
        }
        tvList[id].setBackgroundColor(0xFFFFFFFF);
        tvList[id].setTextColor(0xFFFF5D5E);

    }

    private View.OnClickListener toolsItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mPager.setCurrentItem(v.getId());
        }
    };
    private void setView() {
        mPager.setAdapter(new ShopAdapter(getChildFragmentManager()));
        mPager.setOnPageChangeListener(onPageChangeListener);
        mPager.setCurrentItem(0);




    }

    /**
     * OnPageChangeListener<br/>
     * 监听ViewPager选项卡变化事的事件
     */
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int arg0) {
            if (mPager.getCurrentItem() != arg0)
                mPager.setCurrentItem(arg0);
            if (currentItem != arg0) {
                changeTextColor(arg0);
                changeTextLocation(arg0);
            }
            currentItem = arg0;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    };

    private void changeTextLocation(int clickPosition) {
        int x = (views[clickPosition].getTop());
        scrollView.smoothScrollTo(0, x);

    }


    private class ShopAdapter extends FragmentPagerAdapter {
        public ShopAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {
            Fragment fragment = new KakaFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", index);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return list.length;
        }



    }
}
