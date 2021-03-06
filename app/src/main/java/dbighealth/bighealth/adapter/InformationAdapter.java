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
 * Created by de on 2016/9/1.
 */
public class InformationAdapter extends BaseAdapter{
    Context context;
    List<Map<String, String>> list;
    private Map<String, String> type;
    public InformationAdapter(Context context, List<Map<String, String>> list1) {
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
            convertView= View.inflate(context, R.layout.item_information,null);
            holder=new ViewHolder();
            holder.time= (TextView) convertView.findViewById(R.id.time);
            holder.wen= (TextView) convertView.findViewById(R.id.textView24);
            holder.morn= (TextView) convertView.findViewById(R.id.textView22);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        if (list != null && list.size() > 0) {
            type = list.get(position);
            holder.wen.setText(type.get("d"));
            holder.morn.setText(type.get("c"));
        }
        return convertView;
    }


    class ViewHolder{
        TextView time;//时间
        TextView wen;//问
        TextView morn;
    }
}
