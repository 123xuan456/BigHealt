package dbighealth.bighealth.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbighealth.bighealth.R;
import dbighealth.bighealth.imageUtils.Bimp1;
import dbighealth.bighealth.imageUtils.BitmapCache;
import dbighealth.bighealth.imageUtils.ImageItem;
import utils.HttpPostUploadUtil;
import utils.UrlUtils;


public class AlbumAdapter extends BaseAdapter{

	private Context mContext;
	private List<ImageItem> dataList;
	private List<ImageItem> selectedDataList;
	private LayoutInflater inflater;
	private BitmapCache cache;
	private DisplayMetrics dm;
	
	public AlbumAdapter(Context context, List<ImageItem> dataList, List<ImageItem> tempSelectBitmap) {
		this.mContext = context;
		this.dataList = dataList;
		this.selectedDataList = tempSelectBitmap;
		cache = new BitmapCache();
		inflater = LayoutInflater.from(context);
		dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() {
		@Override
		public void imageLoad(ImageView imageView, Bitmap bitmap,
				Object... params) {
			if (imageView != null && bitmap != null) {
				String url = (String) params[0];
				if (url != null && url.equals((String) imageView.getTag())) {
					((ImageView) imageView).setImageBitmap(bitmap);
				} 
			} 
		}
	};
	
	private class ToggleClickListener implements OnClickListener{
		Button chooseBt;
		public ToggleClickListener(Button choosebt){
			this.chooseBt = choosebt;
		}
		
		@Override
		public void onClick(View view) {
			if (view instanceof ToggleButton) {
				ToggleButton toggleButton = (ToggleButton) view;
				int position = (Integer) toggleButton.getTag();
				if (dataList != null && mOnItemClickListener != null
						&& position < dataList.size()) {
					mOnItemClickListener.onItemClick(toggleButton, position, toggleButton.isChecked(),chooseBt);
				}
			}
		}
	}
	private OnItemClickListener mOnItemClickListener;
	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}
	
	public interface OnItemClickListener {
		public void onItemClick(ToggleButton view, int position,
								boolean isChecked, Button chooseBt);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.album_item, parent,false);
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.image_view);
			viewHolder.toggleButton = (ToggleButton) convertView.findViewById(R.id.toggle_button);
			viewHolder.choosetoggle = (Button) convertView.findViewById(R.id.choosedbt);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		final String path;
		if (dataList != null && dataList.size() > position)
		{
			path = dataList.get(position).imagePath;
			Log.i("mhysa","相册中的图片地址是："+path);

		}	else

			path = "camera_default";
		if (path.contains("camera_default")) {
			viewHolder.imageView.setImageResource(R.drawable.plugin_camera_no_pictures);
		} else {
			final ImageItem item = dataList.get(position);
			viewHolder.imageView.setTag(item.imagePath);
			cache.displayBmp(viewHolder.imageView, item.thumbnailPath, item.imagePath,callback);

		}
		viewHolder.toggleButton.setTag(position);
		viewHolder.choosetoggle.setTag(position);
		viewHolder.toggleButton.setOnClickListener(new ToggleClickListener(viewHolder.choosetoggle));
		if (selectedDataList.contains(dataList.get(position))) {
			viewHolder.toggleButton.setChecked(true);
			viewHolder.choosetoggle.setVisibility(View.VISIBLE);
		} else {
			viewHolder.toggleButton.setChecked(false);
			viewHolder.choosetoggle.setVisibility(View.GONE);
		}
		
		return convertView;
	}
	
	private class ViewHolder {
		public ImageView imageView;
		public ToggleButton toggleButton;
		public Button choosetoggle;
	}
	
	public int dipToPx(int dip) {
		return (int) (dip * dm.density + 0.5f);
	}
}

