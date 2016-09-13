package dbighealth.bighealth.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.HealthCare;

/**
 * Created by mhysa on 2016/9/12.
 * 医疗养生
 */
public class HealthCareAdpter extends BaseAdapter {

    private List<HealthCare.MedicalListBean> list;
    private Context context;
    private LayoutInflater mInflat;

    public HealthCareAdpter(Context context,List<HealthCare.MedicalListBean> list){
        this.context = context;
        this.list = list ;
        mInflat= LayoutInflater.from(context);
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


        if(convertView==null){
            convertView=  mInflat.inflate(R.layout.item_list_treatment,null,false);
            ImageView iv= (ImageView) convertView.findViewById(R.id.line);
            Glide.with(context)
                    .load(list.get(position).getImageUrl())
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade()
                    .into(iv);
        }

        return convertView;

    }
    public class ViewHolder{
            ImageView line;
    }
}
