package dbighealth.bighealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbighealth.bighealth.R;
import dbighealth.bighealth.activity.CollectionActivity;
import dbighealth.bighealth.bean.ColllectionBean;

/**
 * Created by mhysa on 2016/10/12.
 * 我的收藏
 */
public class CollectionAdapter extends BaseAdapter {

    private Context context;
    private List<ColllectionBean.MessageBean> list;
    private LayoutInflater inflater;
    private boolean Type = false;
    private static HashMap<Integer,Boolean> isSelected;
    private boolean flage;
    private boolean[] checks;//用于保存checkBox的选择状态
    private List<Integer> savePostion = new ArrayList<Integer>();
    public CollectionAdapter(Context context, List<ColllectionBean.MessageBean> list,Boolean flage) {
        checks = new boolean[list.size()];
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        isSelected = new HashMap<Integer, Boolean>();
        for(int i=0; i<list.size();i++) {
            getIsSelected().put(i,false);
        }
         this.flage = flage;

    }

    public static HashMap<Integer,Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer,Boolean> isSelected) {
        CollectionAdapter.isSelected = isSelected;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final int pos  = position;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_collection, parent, false);
            holder = new ViewHolder();
            holder.rcvCollectionPic = (SimpleDraweeView) convertView.findViewById(R.id.rcv_collectionPic);
            holder.tvCollection = (TextView) convertView.findViewById(R.id.tv_collectionTitle);
            holder.tvCollectionDate = (TextView) convertView.findViewById(R.id.tv_collectionDate);
            holder.tvCollectionCount = (TextView) convertView.findViewById(R.id.tv_collectionCount);
            holder.cbcollecion = (CheckBox) convertView.findViewById(R.id.cb_collecion);
          if(flage){
                holder.cbcollecion.setVisibility(View.VISIBLE);
            }else{
                holder.cbcollecion.setVisibility(View.GONE);
            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.cbcollecion.setTag(position);
        holder.cbcollecion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("mhysa-->","选中的状态时："+position+"status ="+isChecked);

                int  post = (int) buttonView.getTag();
                if(isChecked){
                    checks[pos] = isChecked;
                    isSelected.put(position,isChecked);
                    savePostion.add(position);
                    Intent intent = new Intent("android.intent.action.CART_COLLECTION");
                    intent.putExtra("productposition",position);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }else{
                    if(savePostion.size()!=0&&savePostion.contains(position)){
                        isSelected.put(position,isChecked);
                        for(int i=0;i<savePostion.size();i++){
                            if(savePostion.get(i)==position){
                                savePostion.remove(i);
                            }
                        }
                        Intent intent = new Intent("android.intent.action.CART_COLLECTION");
                        intent.putExtra("productposition",position);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }
                }
             /*   if(post==position){
                    checks[pos] = isChecked;
                    isSelected.put(position,isChecked);
                    Intent intent = new Intent("android.intent.action.CART_COLLECTION");
                    intent.putExtra("productposition",position);
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }*/

            }
        });
        /**
         * 解析接口数据
         */
        holder.cbcollecion.setChecked(isSelected.get(position));
        holder.rcvCollectionPic.setImageURI(list.get(position).getImages());
        holder.tvCollection.setText(list.get(position).getTitle());
        holder.tvCollectionDate.setText(list.get(position).getTime());
        holder.tvCollectionCount.setText(list.get(position).getCount()+"");

        return convertView;
    }
    public class ViewHolder {
        public CheckBox cbcollecion;
        SimpleDraweeView rcvCollectionPic;
        TextView tvCollection;
        TextView tvCollectionDate;
        TextView tvCollectionCount;
    }

}
