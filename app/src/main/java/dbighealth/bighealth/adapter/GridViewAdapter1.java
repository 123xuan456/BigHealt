package dbighealth.bighealth.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.ProductBean;
import dbighealth.bighealth.ben.Type;

public class GridViewAdapter1 extends BaseAdapter {

	private ArrayList<ProductBean.ProductList> list;
	private ProductBean.ProductList type;
	private Context context;
	Holder view;

	public GridViewAdapter1(Context context, ArrayList<ProductBean.ProductList> list) {
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
			convertView = View.inflate(context, R.layout.item_gridview, null);
			view = new Holder(convertView);
			convertView.setTag(view);
		} else {
			view = (Holder) convertView.getTag();
		}
		if (list != null && list.size() > 0) {
			type = list.get(position);
			/*view.icon.setBackground(context.getResources().getDrawable(
					type.getIcon()));
			view.name.setText(type.getTypename());*/
			Glide.with(context)
					.load(type.getImages())
					.centerCrop()
					.placeholder(R.mipmap.home)
					.error(R.mipmap.home)
					.crossFade()
					.into(view.icon);
			view.name.setText(type.getTitle());
		}

		return convertView;
	}

	private class Holder {
		private ImageView icon;
		private TextView name;

		public Holder(View view) {
			icon = (ImageView) view.findViewById(R.id.typeicon);
			name = (TextView) view.findViewById(R.id.typename);
		}
	}

}
