package dbighealth.bighealth.bean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.InformationBean;
import dbighealth.bighealth.bean.RemindBean;

/**
 * Created by de on 2016/9/13.
 */
public class RemindAdapter extends BaseAdapter {


    private LayoutInflater mInflater = null;
    List<RemindBean.Message> listDatas;
    Context context;
    public RemindAdapter(Context context, List<RemindBean.Message> listDatas){
        this.context=context;
        this.listDatas = listDatas;
        mInflater = LayoutInflater.from(context);

    }


    public int getItemCount() {
        return listDatas.size();
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
        MyViewHolder holder= null;


        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_remind, null);
            holder = new MyViewHolder();

            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.time = (TextView)convertView.findViewById(R.id.time);
            holder.day =(TextView)convertView.findViewById(R.id.day);
            convertView.setTag(holder);
        }else
        {
            holder = (MyViewHolder)convertView.getTag();
        }
        holder.time.setText(listDatas.get(position).getTime());
        holder.day.setText(listDatas.get(position).getDate());
        holder.name.setText(listDatas.get(position).getMedicineName());


        return convertView;
    }


    public  class MyViewHolder  {
        TextView time;
        TextView day;
        TextView name;

    }

//    class MyViewHolder extends RecyclerView.ViewHolder {
//
//        TextView time;
//        TextView textView22;
//        public MyViewHolder(View view) {
//            super(view);
//            time = (TextView)view.findViewById(R.id.time);
//            textView22 = (TextView) view.findViewById(R.id.textView22);
//
//        }
//    }

}
