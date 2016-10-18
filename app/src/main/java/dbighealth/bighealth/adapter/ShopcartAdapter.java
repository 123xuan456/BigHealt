package dbighealth.bighealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.ShoppingCartBean;
import utils.GlideRoundTransform;

/**
 * Created by mhysa on 2016/10/18.
 * 购物车
 */
public class ShopcartAdapter extends BaseAdapter{

    private List<ShoppingCartBean.MessageBean> list;
    private Context context;
    private LayoutInflater mInflat;
    private static HashMap<Integer,Boolean> isSelected;
    private int flage;
    public ShopcartAdapter(Context context, List<ShoppingCartBean.MessageBean> list,int flage) {
        this.context = context;
        this.list = list;
        this.flage = flage;
        isSelected = new HashMap<Integer, Boolean>();
        for(int i=0; i<list.size();i++) {
            getIsSelected().put(i,false);
        }
        mInflat = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
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
        ViewHolder holder = null;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = mInflat.inflate(R.layout.item_shoppintcart, null, false);
            holder.cbShopcheck = (CheckBox) convertView.findViewById(R.id.cb_shopcheck);
            holder.tvEditShop = (TextView) convertView.findViewById(R.id.tv_edit_shop);
            holder.ivShopImg = (ImageView) convertView.findViewById(R.id.iv_shopImg);
            holder.tvProductDescribe = (TextView) convertView.findViewById(R.id.tv_product_describe);
            holder.tvProductPrice = (TextView) convertView.findViewById(R.id.tv_product_price);
            holder.tvJianProduct = (TextView) convertView.findViewById(R.id.tv_jianProduct);
            holder.tvSingleNum = (TextView) convertView.findViewById(R.id.tv_singleNum);
            holder.tvSingleNum1 = (TextView) convertView.findViewById(R.id.tv_singleNum1);
            holder.tvAddProduct = (TextView) convertView.findViewById(R.id.tv_addProduct);
            holder.tvAddProduct1 = (TextView) convertView.findViewById(R.id.tv_addProduct1);
            holder.tvProductNum = (TextView) convertView.findViewById(R.id.tv_productNum);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.cbShopcheck.setText(list.get(position).getTitle());
        Glide.with(context)
                .load(list.get(position).getImages())
                .centerCrop()
                .crossFade()
                .transform(new GlideRoundTransform(context,10))
                .into(holder.ivShopImg);
        // 根据isSelected来设置checkbox的选中状况
        holder.cbShopcheck.setChecked(getIsSelected().get(position));

        if(flage==1){
            holder.tvProductDescribe.setText(list.get(position).getContent());
            holder.tvProductPrice.setText(list.get(position).getPrice());
            holder.tvProductNum.setText("x"+list.get(position).getNum());
        }
        if(flage==2){

            holder.tvEditShop.setVisibility(View.GONE);
            holder.tvProductDescribe.setVisibility(View.GONE);
            holder.tvProductPrice.setVisibility(View.GONE);
            holder.tvProductNum.setVisibility(View.GONE);
            holder.tvJianProduct.setVisibility(View.VISIBLE);
            holder.tvSingleNum1.setVisibility(View.VISIBLE);
            holder.tvAddProduct1.setVisibility(View.VISIBLE);

        }

        return convertView;
    }
    public static HashMap<Integer,Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer,Boolean> isSelected) {
        ShopcartAdapter.isSelected = isSelected;
    }
   public  class ViewHolder {
        CheckBox cbShopcheck;
        TextView tvEditShop;
        ImageView ivShopImg;
        TextView tvProductDescribe;
        TextView tvProductPrice;
        TextView tvJianProduct;
        TextView tvSingleNum;
        TextView tvSingleNum1;
        TextView tvAddProduct;
        TextView tvAddProduct1;
        TextView tvProductNum;

    }
}
