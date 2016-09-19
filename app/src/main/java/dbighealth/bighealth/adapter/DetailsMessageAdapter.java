package dbighealth.bighealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.DetailsMessageBean;
import dbighealth.bighealth.bean.InformationBean;

/**
 * Created by de on 2016/9/18.
 */
public class DetailsMessageAdapter extends BaseAdapter {


    private LayoutInflater mInflater = null;
    List<DetailsMessageBean.Message> listDatas;
    Context context;
    public DetailsMessageAdapter(Context context, List<DetailsMessageBean.Message> listDatas){
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
            convertView = mInflater.inflate(R.layout.item_consult, null);
            holder = new MyViewHolder();

            holder.textView6 = (TextView) convertView.findViewById(R.id.textView6);
            holder.textView1 = (TextView)convertView.findViewById(R.id.textView1);
            convertView.setTag(holder);
        }else
        {
            holder = (MyViewHolder)convertView.getTag();
        }
        holder.textView6.setText(listDatas.get(position).getModel());
        holder.textView1.setText(listDatas.get(position).getText());
        return convertView;
    }


    public  class MyViewHolder  {
        TextView textView6;//问题人
        TextView textView1;//内容

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
