package dbighealth.bighealth.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import dbighealth.bighealth.R;
import dbighealth.bighealth.imageUtils.Bimp;
import dbighealth.bighealth.imageUtils.Bimp1;


public class GridAdapter2 extends BaseAdapter{

	private Context mContext;
	private LayoutInflater inflater;
	private boolean shape;

	public GridAdapter2(Context context) {
		this.mContext = context;
		inflater = LayoutInflater.from(context);
	}

	public boolean isShape() {
		return shape;
	}

	public void setShape(boolean shape) {
		this.shape = shape;
	}
	
	public void update() {
		loading();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				notifyDataSetChanged();
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	private void loading() {
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					if (Bimp1.max == Bimp1.tempSelectBitmap.size()) {
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
						break;
					} else {
						Bimp1.max += 1;
						Message message = new Message();
						message.what = 1;
						handler.sendMessage(message);
					}
				}
			}
		}).start();
	}

	@Override
	public int getCount() {
		if(Bimp1.tempSelectBitmap.size() == 9){
			return 9;
		}
		return (Bimp1.tempSelectBitmap.size() + 1);
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.grid_item,parent, false);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.grid_item_img);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position ==Bimp1
				.tempSelectBitmap.size()) {
			holder.image.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon_addpic_unfocused));
			if (position == 9) {
				holder.image.setVisibility(View.GONE);
			}
		} else {
			holder.image.setImageBitmap(Bimp1.tempSelectBitmap.get(position).getBitmap());
		}

		return convertView;
	}

	public class ViewHolder {
		public ImageView image;
	}
}

