package dbighealth.bighealth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import dbighealth.bighealth.R;

/**
 * Created by de on 2016/8/31.
 */
public class ConditionAdapter extends BaseAdapter{
    Context context;
    List<Map<String, String>> list;
    private Map<String, String> type;

    public ConditionAdapter(Context context, List<Map<String, String>> list1) {
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
            holder.year= (TextView) convertView.findViewById(R.id.textView24);
            holder.morn= (TextView) convertView.findViewById(R.id.textView21);
            convertView.setTag(holder);
        }else{
           holder= (ViewHolder) convertView.getTag();
        }
        if (list != null && list.size() > 0) {
            type = list.get(position);
            holder.con.setText(type.get("c"));
            holder.year.setText(type.get("d"));
            holder.morn.setText(type.get("a"));
        }
        return convertView;
    }

    class ViewHolder{
     TextView time;//时间
     TextView con;//内容
     TextView year;//年月
     TextView morn;//早晨
    }


}
