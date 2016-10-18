package dbighealth.bighealth.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.CompanyDetail;
import dbighealth.bighealth.bean.InProductBean;

/**
 * Created by liu on 2016/10/14.
 */
public class InProductAdapter extends BaseAdapter {

    private Context context;
    private List<InProductBean.Littleimages> list;
    private LayoutInflater mInflater = null;
    public InProductAdapter(Context context, List<InProductBean.Littleimages> list){
         this.context = context;
         this.list = list;
         mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
//        System.out.println("list长度"+list.size());
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder =null;
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_detail, null);
            holder = new ViewHolder();
            holder.imageview = (ImageView) convertView.findViewById(R.id.iv_item_detail);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        Glide.with(context)
                .load(list.get(position).getLittle())
                .placeholder(R.mipmap.home)
                .error(R.mipmap.home)
                .centerCrop()
                .crossFade()
                .into(holder.imageview);
        return convertView;
    }

    public class ViewHolder{
            ImageView imageview;
    }
}
