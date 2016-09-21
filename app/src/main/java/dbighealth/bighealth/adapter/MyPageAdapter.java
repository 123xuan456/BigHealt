package dbighealth.bighealth.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import java.util.List;

import dbighealth.bighealth.imageUtils.ViewPagerFixed;


/**
 * @项目名: uploadDemo
 * @包名: com.example.uploaddemo.adapter
 * @类名: MyPageAdapter
 * @创建者: wp
 * @创建时间: 2016-3-9 上午11:03:00

 */
public class MyPageAdapter extends PagerAdapter {

	private Context mContext;
	private List<View> listViews;
	
	public MyPageAdapter(Context context, List<View> listViews) {
		this.mContext = context;
		this.listViews = listViews;
	}
	
	public void setListViews(List<View> listViews) {
		this.listViews = listViews;
	}

	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}
	
	@Override
	public int getCount() {
		return listViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}
	
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % listViews.size()));
	}
	
	public Object instantiateItem(View arg0, int arg1) {
		try {
			((ViewPagerFixed) arg0).addView(listViews.get(arg1 % listViews.size()), 0);

		} catch (Exception e) {
		}
		return listViews.get(arg1 % listViews.size());
	}
}

