package dbighealth.bighealth.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.HasCommitBean;
import dbighealth.bighealth.ben.Type;

public class ReportPicAdapter extends BaseAdapter {

	List<HasCommitBean.UrlsBean> list;
	private Context context;
	Holder view;

	public ReportPicAdapter(Context context,  List<HasCommitBean.UrlsBean> list) {
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		if (list != null && list.size() > 0)
			return list.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("NewApi") @Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_report_gridview, null);
			view = new Holder(convertView);
			convertView.setTag(view);
		} else {
			view = (Holder) convertView.getTag();
		}
		String url = list.get(position).getUrl();
		Uri uri = Uri.parse(url);
		view.icon.setImageURI(uri);
		return convertView;
	}
	private class Holder {
		private SimpleDraweeView icon;
		public Holder(View view) {
			icon = (SimpleDraweeView) view.findViewById(R.id.iv_reporticon);
		}
	}

}
