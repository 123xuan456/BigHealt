package dbighealth.bighealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.SecondCommntBean;

/**
 * Created by mhysa on 2016/9/22.
 * 眼病等二级界面评论
 */
public class SecondAdapter extends BaseAdapter{

    private Context context;
    private  List<SecondCommntBean.ComListBean> list;
    private LayoutInflater mInflater = null;
    public SecondAdapter(Context context , List<SecondCommntBean.ComListBean> list){
        this.context = context;
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
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
        ViewHolder holder = null;
        //如果缓存convertView为空，则需要创建View
        if(convertView == null)
        {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.item_comment, null);
            holder.username = (TextView) convertView.findViewById(R.id.tv_username);
            holder.userComment = (TextView) convertView.findViewById(R.id.tv_usercomment);
            holder.commentTime  = (TextView) convertView.findViewById(R.id.tv_commentTime);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag*/
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        if(list!=null&&list.get(position).getName()==null){
            holder.username.setText("匿名");
        }else{
            holder.username.setText(list.get(position).getName());
        }

        holder.userComment.setText(list.get(position).getComment());
        holder.commentTime.setText(list.get(position).getTime());
        return convertView;
    }

    public class ViewHolder{
        TextView username;
        TextView userComment;
        TextView commentTime;
    }
}
