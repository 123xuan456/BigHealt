package dbighealth.bighealth.imageUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.MyPageAdapter;
import dbighealth.bighealth.imageUtils.Bimp;
import dbighealth.bighealth.imageUtils.PhotoView;
import dbighealth.bighealth.imageUtils.PublicWay;
import dbighealth.bighealth.imageUtils.ViewPagerFixed;


public class GalleryActivity extends Activity implements OnClickListener{

	private ViewPagerFixed viewpager;
	private List<View> listViews;
	private MyPageAdapter adapter;
	private Button gallery_back;
	private Button gallery_del;
	//当前的位置
	private int location = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		PublicWay.activityList.add(this);
		
		for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
			initListViews( Bimp.tempSelectBitmap.get(i).getBitmap() );
		}
		
		initViews();
	}

	//获取数据
	private void initListViews(Bitmap bitmap) {
		if (listViews == null)
			listViews = new ArrayList<View>();
		PhotoView img = new PhotoView(this);
		img.setBackgroundColor(0xff000000);
		img.setImageBitmap(bitmap);
		img.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		listViews.add(img);
	}

	private void initViews() {
		viewpager = (ViewPagerFixed) findViewById(R.id.viewpager);
		gallery_back = (Button) findViewById(R.id.gallery_back);
		gallery_del = (Button) findViewById(R.id.gallery_del);
		
		gallery_back.setOnClickListener(this);
		gallery_del.setOnClickListener(this);
		viewpager.addOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				location = arg0;
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		adapter = new MyPageAdapter(this,listViews);
		viewpager.setAdapter(adapter);
		
		int id = getIntent().getIntExtra("ID", 0);
		viewpager.setCurrentItem(id);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gallery_back:
			finish();
			break;
		case R.id.gallery_del:
			if (listViews.size() == 1) {
				Bimp.tempSelectBitmap.clear();
				Bimp.max = 0;
				finish();
			} else {
				Bimp.tempSelectBitmap.remove(location);
				Bimp.max--;
				viewpager.removeAllViews();
				listViews.remove(location);
				adapter.setListViews(listViews);
				adapter.notifyDataSetChanged();
			}
			break;	

		default:
			break;
		}
	}
}

