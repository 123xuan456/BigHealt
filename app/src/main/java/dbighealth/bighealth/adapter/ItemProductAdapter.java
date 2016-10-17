package dbighealth.bighealth.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.CompanyDetail;
import dbighealth.bighealth.view.ItemBaseAdapter;

/**
 * Created by mhysa on 2016/10/13.
 */

public class ItemProductAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context;
    ViewHolder holder =null;
    private final int NUM = 1;
    private List<CompanyDetail.MessageBean.LittlePicBean> list;
    private LayoutInflater mInflater = null;
    public ItemProductAdapter(Context context, List<CompanyDetail.MessageBean.LittlePicBean> list){
         this.context = context;
         this.list = list;
         mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        System.out.println("list长度"+list.size());
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
            convertView = mInflater.inflate(R.layout.item_product, null);
            holder = new ViewHolder();
            holder.iv_product = (ImageView) convertView.findViewById(R.id.iv_product);
            holder.name_tv = (TextView)convertView.findViewById(R.id.name_tv);
            holder.tv_product_tit = (TextView)convertView.findViewById(R.id.tv_product_tit);
            holder.price = (TextView)convertView.findViewById(R.id.price);
            holder.count = (TextView)convertView.findViewById(R.id.count);
            holder.jian = (ImageView)convertView.findViewById(R.id.jian);
            holder.jia = (ImageView)convertView.findViewById(R.id.jia);
            holder.num = (TextView)convertView.findViewById(R.id.num);
            holder.jian.setOnClickListener(this);
            holder.jia.setOnClickListener(this);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        Glide.with(context)
                .load(list.get(position).getImageUrl())
                .placeholder(R.mipmap.home)
                .error(R.mipmap.home)
                .centerCrop()
                .crossFade()
                .into(holder.iv_product);
        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jian:

                if(holder.num.getText().toString().equals("1")){
                    String jian=Integer.valueOf(holder.num.getText().toString())-NUM+"";
                    holder.num.setText(jian);
                }
                break;

            case R.id.jia:
                if(holder.num.getText().toString().equals("999")){
                    String jia=Integer.valueOf(holder.num.getText().toString())+NUM+"";
                    holder.num.setText(jia);
                }
                break;
        }
    }

    public class ViewHolder{
            ImageView iv_product;//产品图片
            TextView name_tv;//店铺名称
            TextView tv_product_tit;//商品描述
            TextView price;//价格
            TextView count;//数量
            ImageView jian;//减
            ImageView jia;//加
            TextView num;
    }
}
