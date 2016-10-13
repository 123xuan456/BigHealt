package dbighealth.bighealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.List;
import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.ColllectionBean;

/**
 * Created by mhysa on 2016/10/12.
 * 我的收藏
 */
public class CollectionAdapter extends BaseAdapter {

    private Context context;
    private List<ColllectionBean.MessageBean> list;
    private LayoutInflater inflater;

    public CollectionAdapter(Context context, List<ColllectionBean.MessageBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
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
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_collection, parent, false);
            holder = new ViewHolder();
            holder.rcvCollectionPic = (SimpleDraweeView) convertView.findViewById(R.id.rcv_collectionPic);
            holder.tvCollection = (TextView) convertView.findViewById(R.id.tv_collectionTitle);
            holder.tvCollectionDate = (TextView) convertView.findViewById(R.id.tv_collectionDate);
            holder.tvCollectionCount = (TextView) convertView.findViewById(R.id.tv_collectionCount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        /**
         * 解析接口数据
         */
        holder.rcvCollectionPic.setImageURI(list.get(position).getImages());
        holder.tvCollection.setText(list.get(position).getTitle());
        holder.tvCollectionDate.setText(list.get(position).getTime());
        holder.tvCollectionCount.setText(list.get(position).getCount()+"");
        return convertView;
    }
    public class ViewHolder {
        SimpleDraweeView rcvCollectionPic;
        TextView tvCollection;
        TextView tvCollectionDate;
        TextView tvCollectionCount;
    }
}
