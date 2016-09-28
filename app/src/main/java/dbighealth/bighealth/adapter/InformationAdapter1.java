package dbighealth.bighealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.InformationBean;

/**
 * Created by de on 2016/9/13.
 */
public class InformationAdapter1 extends BaseAdapter {


    private LayoutInflater mInflater = null;
    List<InformationBean.Message> listDatas;
    Context context;
    public InformationAdapter1(Context context, List<InformationBean.Message> listDatas){
        this.context=context;
        this.listDatas = listDatas;
        mInflater = LayoutInflater.from(context);

    }



//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        MyViewHolder holder;
//        View view = LayoutInflater.from(context).inflate(R.layout.item_information, parent, false);
//        holder = new MyViewHolder(view);
//        return holder;
//    }

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
            convertView = mInflater.inflate(R.layout.item_information, null);
            holder = new MyViewHolder();

            holder.textView22 = (TextView) convertView.findViewById(R.id.textView22);
            holder.time = (TextView)convertView.findViewById(R.id.time);
            convertView.setTag(holder);
        }else
        {
            holder = (MyViewHolder)convertView.getTag();
        }
        holder.time.setText(listDatas.get(position).getQuestDate());
        holder.textView22.setText(listDatas.get(position).getProblem());
        return convertView;
    }


    public  class MyViewHolder  {
        TextView time;
        TextView textView22;

    }


}
