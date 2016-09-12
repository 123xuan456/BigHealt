package dbighealth.bighealth.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.CommonHealth;
import dbighealth.bighealth.view.ItemBaseAdapter;

/**
 * Created by mhysa on 2016/9/1.
 */
public class ItemAdapter1 extends BaseAdapter {

    List<CommonHealth.ItemListBean> listDatas;
    private Context context;
    private LayoutInflater mInflater = null;
    private ImageView iv_spcific_list;
    private TextView tv_description;
    private TextView tv_time;

    public ItemAdapter1(Context context, List<CommonHealth.ItemListBean>listDatas)
    {
        //super(context,listDatas);
        this.context = context;
        this.listDatas = listDatas;
        this.mInflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return listDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return listDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater.from(context);
        //View view =  mInflater.inflate(R.layout.item_specific, parent, false);
        String itemUrl = listDatas.get(position).getItemUrl();
        int id = listDatas.get(position).getId();
        Log.i("mhysa--->",itemUrl+"itemurl");
        String desciption = listDatas.get(position).getDesciption();
        String time = listDatas.get(position).getTime();
        MyViewHolder holder= null;


        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_specific, null);
            holder = new MyViewHolder();
            holder.iv_spcific_list = (ImageView)convertView.findViewById(R.id.iv_spcific_list);
            holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
            holder.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
            convertView.setTag(holder);
        }else
        {
            holder = (MyViewHolder)convertView.getTag();
        }

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

        return convertView;
    }

    public  class MyViewHolder  {
        ImageView iv_spcific_list;
        TextView tv_description;
        TextView tv_time;

    }
}
