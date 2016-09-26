package dbighealth.bighealth.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;
import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.activity.SecondActivity;
import dbighealth.bighealth.bean.CommonHomeBean;
import dbighealth.bighealth.view.BaseAdapter1;

/**
 * Created by baiyuliang on 2016-5-27.
 */
public class ImageAdapter extends BaseAdapter1<ImageAdapter.MyViewHolder> {

    FinalBitmap fb = null;
    DisplayMetrics dm;
  // private Context context;
    public ImageAdapter(Context context, List<CommonHomeBean.ResultBean.ResultsBean> listDatas) {
        super(context, listDatas);
       // List<CommonHomeBean.ResultBean.ResultsBean> list = ( List<CommonHomeBean.ResultBean.ResultsBean>)listDatas;

        fb = FinalBitmap.create(context);
        dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_grid_home, parent, false);
        //动态设置ImageView的宽高，根据自己每行item数量计算
        //dm.widthPixels-dip2px(20)即屏幕宽度-左右10dp+10dp=20dp再转换为px的宽度，最后/3得到每个item的宽高
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((dm.widthPixels - dip2px(20)) / 3, (dm.widthPixels - dip2px(20)) / 3-dip2px(40));
        view.setLayoutParams(lp);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        String url = (String) listDatas.get(position).getImages();//转换
        int picid = listDatas.get(position).getPicid();
        Glide.with(context)
                .load(url)
                .centerCrop()
                .crossFade()
                .into(holder.iv);
      //  fb.display(holder.iv, url);
        holder.tv_PicTxt.setText(listDatas.get(position).getTitle());
        /**
         * 跳转到二级界面
         */
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("mhysa", "" + position);
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("picId",listDatas.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView tv_PicTxt;

        public MyViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv_gridImg);
            tv_PicTxt = (TextView) view.findViewById(R.id.tv_PicTxt);
        }
    }
    int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
