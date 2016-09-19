package dbighealth.bighealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.ConditionBean;

/**
 * Created by de on 2016/8/31.
 */
public class ConditionAdapter extends BaseAdapter{
    Context context;
    List<ConditionBean.MessageBean> list;
    private ConditionBean.MessageBean condition;

    public ConditionAdapter(Context context, List<ConditionBean.MessageBean> list1) {
        this.context=context;
        list=list1;
        System.out.println("list="+list);
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
        ViewHolder holder;
        if (convertView==null){
            convertView= View.inflate(context, R.layout.item_condition,null);
            holder=new ViewHolder();
            holder.time= (TextView) convertView.findViewById(R.id.time);
            holder.con= (TextView) convertView.findViewById(R.id.textView23);
            holder.year= (TextView) convertView.findViewById(R.id.textView53);

            holder.tv45= (TextView) convertView.findViewById(R.id.textView45);
            holder.tv47= (TextView) convertView.findViewById(R.id.textView47);
            holder.tv49= (TextView) convertView.findViewById(R.id.textView49);
            holder.tv52= (TextView) convertView.findViewById(R.id.textView52);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        if (list != null && list.size() > 0) {
            condition = list.get(position);
            holder.con.setText(condition.getLunchsize());
            holder.time.setText(condition.getLunchtime());
            holder.year.setText(condition.getSavedate());
            holder.tv45.setText(condition.getBreakfasttime());
            holder.tv47.setText(condition.getBreakfastsize());
            holder.tv49.setText(condition.getDinnertime());
            holder.tv52.setText(condition.getDinnersize());
        }
        return convertView;
    }

    class ViewHolder{
        TextView time;//时间
        TextView con;//餐量

        TextView tv45;//早晨
        TextView tv47;
        TextView tv49;//晚餐
        TextView tv52;
        TextView year;//年月

    }



}
