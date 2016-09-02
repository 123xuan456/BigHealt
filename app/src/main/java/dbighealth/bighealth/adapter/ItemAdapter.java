package dbighealth.bighealth.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.view.BaseAdapter;

/**
 * Created by mhysa on 2016/9/1.
 */
public class ItemAdapter extends BaseAdapter<ItemAdapter.MyViewHolder> {

    List<Object> listDatas;
    public ItemAdapter(Context context, List<Object> listDatas, OnViewClickListener onViewClickListener) {
        super(context, listDatas, onViewClickListener);
        this.listDatas = listDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.item_specific, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_spcific_list;
        TextView tv_description;
        TextView tv_time;


        public MyViewHolder(View view) {
            super(view);
             iv_spcific_list = (ImageView)view.findViewById(R.id.iv_spcific_list);
             tv_description = (TextView) view.findViewById(R.id.tv_description);
             tv_time = (TextView)view.findViewById(R.id.tv_time);
        }
    }
}
