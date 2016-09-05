package dbighealth.bighealth.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.CommonHealth;
import dbighealth.bighealth.view.ItemBaseAdapter;

/**
 * Created by mhysa on 2016/9/1.
 */
public class ItemAdapter extends ItemBaseAdapter<ItemAdapter.MyViewHolder> {

    List<CommonHealth.ItemListBean> listDatas;
    private Context context;
    public ItemAdapter(Context context, List<CommonHealth.ItemListBean> listDatas, OnViewClickListener onViewClickListener) {
        super(context, listDatas, onViewClickListener);
        this.listDatas = listDatas;
        this.context = context ;
    }

    public ItemAdapter(Context context,List<CommonHealth.ItemListBean>listDatas)
    {
        super(context,listDatas);
        this.context = context;
        this.listDatas = listDatas;
       // Log.i("mhysa","list数据"+listDatas);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.item_specific, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String itemUrl = listDatas.get(position).getItemUrl();
        int id = listDatas.get(position).getId();
        String desciption = listDatas.get(position).getDesciption();
        String time = listDatas.get(position).getTime();
        Glide.with(context)
                .load(itemUrl)
                .centerCrop()
                .placeholder(R.mipmap.spcificpic)
                .error(R.mipmap.spcificpic)
                .crossFade()
                .into(holder.iv_spcific_list);
        holder.tv_description.setText(desciption);
        //转换时间格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = df.parse(time);
            //判断时间
            int year = date.getYear();
            int month = date.getMonth();
            int day = date.getDay();
            //获取当前时间
            Date currentDate = new Date();
           // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
            int currentYear = currentDate.getYear();
            int currentMonth = currentDate.getMonth();
            int currentDay = currentDate.getDay();
            if(year!=currentYear){
                holder.tv_time.setText(time);

            }else{
                if(month!=currentMonth){
                    holder.tv_time.setText(time);
                }else{
                    if(currentDay-day==1){
                        holder.tv_time.setText("昨天");
                    }else if(day == currentDay){
                        holder.tv_time.setText("今天");
                    }else{
                        holder.tv_time.setText(time);
                    }
                }
            }
            System.out.println(df.format(date));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_spcific_list;
        TextView tv_description;
        TextView tv_time;

        public MyViewHolder(View view) {
            super(view);
             iv_spcific_list = (ImageView)view.findViewById(R.id.iv_spcific_list);
             tv_description = (TextView) view.findViewById(R.id.tv_description);
             tv_time = (TextView)view.findViewById(R.id.tv_time);
        }
    }
}
