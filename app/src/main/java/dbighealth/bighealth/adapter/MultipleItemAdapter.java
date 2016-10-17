package dbighealth.bighealth.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.CompanyDetail;

/**
 * Created by mhysa on 2016/9/18.
 */
public class MultipleItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private int TYPE_HEAD = 1;
    private int TYPE_CONTENT =2;
    private LayoutInflater mLayoutInflater;
    private Context context;
    private List<CompanyDetail.MessageBean.LittlePicBean> list;
    private JSONObject jsonObject ;
    private String name;
    private String address;
    private String tel;
    private String content;
    private String ImgUrl;
    public MultipleItemAdapter(Context context , List<CompanyDetail.MessageBean.LittlePicBean> list, String name,String address,String tel,String content,String ImgUrl){
        this.context = context ;
        this.list = list;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.content = content;

        mLayoutInflater = LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            return new Item1ViewHolder(mLayoutInflater.inflate(R.layout.recyclerviewitem_head, parent, false));
        } else {
            return new Item2ViewHolder(mLayoutInflater.inflate(R.layout.item_product, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Item1ViewHolder) {
           // ((Item1ViewHolder) holder).mTextView.setText(titles[position]);
            ((Item1ViewHolder) holder).tv_companyName.setText("名称："+name);
            ((Item1ViewHolder) holder).tv_companyAddress.setText("地址："+address);
            ((Item1ViewHolder) holder).tv_companyTel.setText("电话："+tel);
            ((Item1ViewHolder) holder).tv_companyArea.setText("可容纳："+content+"人");

            Glide.with(context)
                    .load(ImgUrl)
                    .placeholder(R.mipmap.home)
                    .error(R.mipmap.home)
                    .centerCrop()
                    .crossFade()
                    .into(((Item1ViewHolder) holder).iv_detail);
        } else if (holder instanceof Item2ViewHolder) {
            //((Item2ViewHolder) holder).mTextView.setText(titles[position]);
            Glide.with(context)
                    .load(list.get(position).getImageUrl())
                    .placeholder(R.mipmap.home)
                    .error(R.mipmap.home)
                    .centerCrop()
                    .crossFade()
                    .into(((Item2ViewHolder) holder).iv_item_detail);
        }

    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEAD;
        }else{
            return TYPE_CONTENT;
        }
       // return super.getItemViewType(position);
    }

    //item1 的ViewHolder
    public static class Item1ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView iv_detail;
        private final TextView tv_companyName;
        private final TextView tv_companyAddress;
        private final TextView tv_companyTel;
        private final TextView tv_companyArea;

        //  TextView mTextView;
        public Item1ViewHolder(View itemView) {
            super(itemView);
         //   mTextView=(TextView)itemView.findViewById(R.id.tv_item1_text);
            iv_detail = (ImageView) itemView.findViewById(R.id.iv_detail);
            tv_companyName = (TextView) itemView.findViewById(R.id.tv_companyName);
            tv_companyAddress = (TextView) itemView.findViewById(R.id.tv_companyAddress);
            tv_companyTel = (TextView) itemView.findViewById(R.id.tv_companyTel);
            tv_companyArea = (TextView) itemView.findViewById(R.id.tv_companyArea);
        }
    }
    public static class Item2ViewHolder extends RecyclerView.ViewHolder{


        private final ImageView iv_item_detail;

        public Item2ViewHolder(View itemView) {
            super(itemView);
            iv_item_detail = (ImageView) itemView.findViewById(R.id.iv_item_detail);
        }
    }


}
