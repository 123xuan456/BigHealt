package dbighealth.bighealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.TreatmentBean;

/**
 * Created by de on 2016/9/2.
 */
public class TreatmentAdapter extends BaseAdapter {
  //  private String images[];
    private List<TreatmentBean.ResultBean.ResultsBean> list;
    private Context context;
    private LayoutInflater mInflat;

    public TreatmentAdapter(Context context, List<TreatmentBean.ResultBean.ResultsBean> list) {
        this.list = list;
        this.context=context;
        if(context!=null){
            mInflat= LayoutInflater.from(context);
        }


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
            TextView tv_companyDescribe = (TextView) convertView.findViewById(R.id.tv_companyDescribe);
            Glide.with(context)
                    .load(list.get(position).getImageUrl())
                    .centerCrop()
                    .crossFade()
                    .into(iv);
            tv_companyDescribe.setText(list.get(position).getName());
        }

        return convertView;

    }

}
