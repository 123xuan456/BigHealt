package dbighealth.bighealth.imageUtils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.AlbumAdapter;

/**相册选择页*/
public class AlbumActivity extends Activity implements OnClickListener {

	private AlbumHelper helper;
	public static List<ImageBucket> contentList;
	private List<ImageItem> dataList;
	private GridView mGridView;
	private AlbumAdapter adapter;
	private Button okButton;
	private Button preview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album);
		PublicWay.activityList.add(this);
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		contentList = helper.getImagesBucketList(false);
		dataList = new ArrayList<ImageItem>();
		for (int i = 0; i < contentList.size(); i++) {
			dataList.addAll(contentList.get(i).imageList);
		}

		initViews();
		initListener();
	}

	private void initListener() {
		adapter.setOnItemClickListener(new AlbumAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(final ToggleButton toggleButton,
					int position, boolean isChecked, Button chooseBt) {
				if (Bimp.tempSelectBitmap.size() >= PublicWay.num) {
					toggleButton.setChecked(false);
					chooseBt.setVisibility(View.GONE);
					if (!removeOneData(dataList.get(position))) {
						Toast.makeText(AlbumActivity.this, "超出可选图片张数", Toast.LENGTH_LONG).show();
					}
					return;
				}
				if (isChecked) {
					chooseBt.setVisibility(View.VISIBLE);
					Bimp.tempSelectBitmap.add(dataList.get(position));
					okButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
				} else {
					Bimp.tempSelectBitmap.remove(dataList.get(position));
					chooseBt.setVisibility(View.GONE);
					okButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
				}
				isShowOkBt();
			}
		});
	}

	/**
	 * 更新按钮颜色
	 */
	protected void isShowOkBt() {
		if (Bimp.tempSelectBitmap.size() > 0) {
			okButton.setText("完成(" + Bimp.tempSelectBitmap.size() + "/"+PublicWay.num+")");
			okButton.setPressed(true);
			okButton.setClickable(true);
			okButton.setTextColor(Color.WHITE);
			preview.setTextColor(Color.WHITE);
		} else {
			okButton.setText("完成(" + Bimp.tempSelectBitmap.size() + "/"+PublicWay.num+")");
			okButton.setPressed(false);
			okButton.setClickable(false);
			okButton.setTextColor(Color.parseColor("#E1E0DE"));
		}
	}

	private boolean removeOneData(ImageItem imageItem) {
		if (Bimp.tempSelectBitmap.contains(imageItem)) {
			Bimp.tempSelectBitmap.remove(imageItem);
			okButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
			return true;
		}
		return false;
	}

	/**
	 * 初始化view
	 */
	private void initViews() {
		mGridView = (GridView) findViewById(R.id.myGrid);
		RelativeLayout bottom_layout = (RelativeLayout) findViewById(R.id.bottom_layout);
		bottom_layout.getBackground().setAlpha(200);
		okButton = (Button) findViewById(R.id.ok_button);
		okButton.setOnClickListener(this);
		//设置默认
		okButton.setText("完成" + "(" + Bimp.tempSelectBitmap.size() + "/"+ PublicWay.num + ")");

		preview = (Button) findViewById(R.id.preview);
		preview.setOnClickListener(this);

		adapter = new AlbumAdapter(this, dataList, Bimp.tempSelectBitmap);
		mGridView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ok_button:
			overridePendingTransition(R.anim.activity_translate_in,R.anim.activity_translate_out);
			finish();
			break;
		case R.id.preview:
			finish();
			break;

		default:
			break;
		}
	}
}
