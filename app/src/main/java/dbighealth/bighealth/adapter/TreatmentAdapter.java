package dbighealth.bighealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import dbighealth.bighealth.R;

/**
 * Created by de on 2016/9/2.
 */
public class TreatmentAdapter extends BaseAdapter{
    private String images[];
    private Context context;
    private LayoutInflater mInflat;


    public TreatmentAdapter(Context context, String[] images) {
        this.images = images;
        this.context=context;
        mInflat= LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
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
                    .load(images[position])
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade()
                    .into(iv);
        }

        return convertView;

    }
}
