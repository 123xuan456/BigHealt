package dbighealth.bighealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.ShoppingCartBean;
import okhttp3.Call;
import utils.GlideRoundTransform;
import utils.UrlUtils;

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
    ViewHolder holder = null;
    private TextView tvEditShop;
    private boolean[] checks;//用于保存checkBox的选择状态
    private List<String> datas2;
   private static List<Boolean> Nums; //用于保存item的数量
    private int SingleNum;
    private int count = 0;
    private int compareCount = 0;
    private int SINGLEDELTE = 101;
    private int UPDATENUMS = 102;
    List<Integer> deleteIndex = new ArrayList<Integer>();
    private MyClickListener mListener;
    public ShopcartAdapter(Context context, List<ShoppingCartBean.MessageBean> list,int flage) {
        this.context = context;
        this.list = list;
        this.flage = flage;
       isSelected = new HashMap<Integer, Boolean>();
        checks = new boolean[list.size()];
        count = compareCount;
        for(int i=0; i<list.size();i++) {
            getIsSelected().put(i,false);
        }
        checks = new boolean[list.size()];
        datas2 = new ArrayList<String>();
        Nums = new ArrayList<Boolean>();
        for(int i=0;i<list.size();i++){
            datas2.add(i,"编辑");
          //  Nums.add(false);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int pos  = position;
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
            holder.tvDelete = (TextView) convertView.findViewById(R.id.tv_delete);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.cbShopcheck.setText(list.get(position).getTitle());
        Log.i("mhysa-->","weizhi："+pos);

     //
        Glide.with(context)
                .load(list.get(position).getImages())
                .centerCrop()
                .crossFade()
                .transform(new GlideRoundTransform(context,10))
                .into(holder.ivShopImg);

        /**
         * 编辑下的界面
         */
        if(flage==1){
            // 根据isSelected来设置checkbox的选中状况
            holder.cbShopcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.i("mhysa-->","选中的状态时："+pos);
                    isSelected.put(position,isChecked);
                    checks[pos] = isChecked;
                   // mListener.clickListener(buttonView,isChecked);
                     Intent intent = new Intent("android.intent.action.CART_PRODUCTNUM");
                    intent.putExtra("position",position);
                     intent.putExtra("getTotal",1);
                   //  intent.putExtra("postion",position);
                     LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                }
            });
            holder.cbShopcheck.setChecked(isSelected.get(pos));

            holder.tvProductDescribe.setText(list.get(position).getContent());
            holder.tvProductPrice.setText("￥"+list.get(position).getPrice());
            /**
             * 如果条目编辑按钮可见，点击编辑按钮修改item内容
             */
            holder.tvEditShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(datas2.get(pos).equals("编辑")){
                        datas2.set(pos,"完成");
                    }else if(datas2.get(pos).equals("完成")){
                        datas2.set(pos,"编辑");
                        Intent intent = new Intent("android.intent.action.CART_PRODUCTNUM");
                        intent.putExtra("getTotal",1);

                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        /**
                         * 修改数量
                         */
                       OkHttpUtils.get()
                                  .url(UrlUtils.UPDATENUMS)
                                  .addParams("userId",String.valueOf(list.get(position).getUserId()))
                                  .addParams("shoppingId",list.get(pos).getShoppingId())
                                  .addParams("num",String.valueOf(list.get(position).getNum()))
                                  .id(UPDATENUMS)
                                  .build()
                                  .execute(MyStringCallback);

                    }
                    notifyDataSetChanged();
                }
            });
            /**
             * item中的编辑或完成
             */
            if(datas2.get(pos).equals("完成")){
                holder.tvProductDescribe.setVisibility(View.GONE);
                holder.tvProductPrice.setVisibility(View.GONE);
                holder.tvProductNum.setVisibility(View.GONE);
                holder.tvJianProduct.setVisibility(View.VISIBLE);
                holder.tvSingleNum.setVisibility(View.VISIBLE);
                holder.tvDelete.setVisibility(View.VISIBLE);
                holder.tvAddProduct.setVisibility(View.VISIBLE);

            }else if(datas2.get(pos).equals("编辑")){
                holder.tvProductDescribe.setVisibility(View.VISIBLE);
                holder.tvProductPrice.setVisibility(View.VISIBLE);
                holder.tvProductNum.setVisibility(View.VISIBLE);
                holder.tvJianProduct.setVisibility(View.GONE);
                holder.tvSingleNum.setVisibility(View.GONE);
                holder.tvDelete.setVisibility(View.GONE);
                holder.tvAddProduct.setVisibility(View.GONE);
                holder.tvProductNum.setText(SingleNum+"");
            }
            holder.tvSingleNum.setText(""+list.get(position).getNum());
            holder.tvEditShop.setText(datas2.get(pos));
            /**
             * 加数量操作
             */
            holder.tvAddProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.get(position).setNum( list.get(position).getNum()+1);
                    Intent intent = new Intent("android.intent.action.CART_PRODUCTNUM");
                    intent.putExtra("getTotal",1);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    notifyDataSetChanged();
                }
            });

            /**
             * 减数量操作
             */
           holder.tvJianProduct.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(list.get(position).getNum()>0){
                       list.get(position).setNum( list.get(position).getNum()-1);
                   }
                   Intent intent = new Intent("android.intent.action.CART_PRODUCTNUM");
                   intent.putExtra("getTotal",1);
                   LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                   notifyDataSetChanged();

               }
           });

            /**
             * 单个条目删除
             */
            holder.tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    OkHttpUtils.get()
                            .url(UrlUtils.DELETESHOPCART)
                            .addParams("userId",list.get(position).getUserId()+"")
                            .addParams("shoppingId",list.get(position).getShoppingId())
                            .id(SINGLEDELTE)
                            .build()
                            .execute(MyStringCallback);
                    list.remove(position);
                    datas2.remove(pos);
                    Intent intent = new Intent("android.intent.action.CART_PRODUCTNUM");
                    if(getIsSelected().get(position)){
                        intent.putExtra("delete",true);
                        intent.putExtra("getTotal",1);
                    }
                    for(int i =0;i<list.size();i++){
                        if(position<=i){
                            getIsSelected().put(i,getIsSelected().get(i+1));
                        }else{
                            getIsSelected().put(i,getIsSelected().get(i));
                        }
                    }
                    getIsSelected().remove(list.size());
                //    Nums.remove(position);
                    notifyDataSetChanged();
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);



                }
            });

            holder.tvProductNum.setText("x"+list.get(position).getNum());

        }
        /**
         * 完成下的界面展示
         */
        if(flage==2){
            holder.cbShopcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.i("mhysa-->","选中的状态时："+position);

                    isSelected.put(position,isChecked);
                    Intent intent = new Intent("android.intent.action.CART_PRODUCTNUM");
                    intent.putExtra("getTotal",2);
                    intent.putExtra("postion",position);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                }
            });
            holder.cbShopcheck.setChecked(isSelected.get(position));
            holder.tvEditShop.setVisibility(View.GONE);
            holder.tvProductDescribe.setVisibility(View.GONE);
            holder.tvProductPrice.setVisibility(View.GONE);
            holder.tvProductNum.setVisibility(View.GONE);
            holder.tvJianProduct.setVisibility(View.VISIBLE);
            holder.tvSingleNum1.setVisibility(View.VISIBLE);
            holder.tvAddProduct1.setVisibility(View.VISIBLE);
            holder.tvSingleNum1.setText(list.get(position).getNum()+"");
            /**
             * 加数量操作
             */
            holder.tvAddProduct1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.get(position).setNum( list.get(position).getNum()+1);
                    notifyDataSetChanged();
                    Intent intent = new Intent("android.intent.action.CART_PRODUCTNUM");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            });

            /**
             * 减数量操作
             */
            holder.tvJianProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(list.get(position).getNum()>0){
                        list.get(position).setNum( list.get(position).getNum()-1);
                    }
                    notifyDataSetChanged();
                    Intent intent = new Intent("android.intent.action.CART_PRODUCTNUM");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            });
        }
        return convertView;
    }

    StringCallback MyStringCallback = new StringCallback(){

        @Override
        public void onError(Call call, Exception e, int id) {

            if(id==SINGLEDELTE){
                Toast.makeText(context,"删除失败，请稍后重试！",Toast.LENGTH_SHORT).show();
            }
            if(id ==UPDATENUMS){
                Toast.makeText(context,"修改失败，检查网络问题！",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onResponse(String response, int id) {

            if(id == SINGLEDELTE){
                Toast.makeText(context,"删除成功！",Toast.LENGTH_SHORT).show();
            }
            if(id ==UPDATENUMS){
                Toast.makeText(context,"修改成功！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void removeItem(int position){
        list.remove(position);
        this.notifyDataSetChanged();
    }
    public static HashMap<Integer,Boolean> getIsSelected() {
        return isSelected;
    }

    public static List<Boolean> getSelect() {
        return Nums;
    }
    public static void setIsSelected(HashMap<Integer,Boolean> isSelected) {
        ShopcartAdapter.isSelected = isSelected;
    }
    public   class ViewHolder {
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
        TextView tvDelete;
    }

    //自定义接口，用于回调按钮点击事件到Activity
    public interface MyClickListener{
        public void clickListener(CompoundButton buttonView, boolean isChecked);
    }
}
