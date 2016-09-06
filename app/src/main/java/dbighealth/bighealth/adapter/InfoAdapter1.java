package dbighealth.bighealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.activity.LoginActivity;
import dbighealth.bighealth.bean.CommonHomeBean;
import dbighealth.bighealth.view.BaseAdapter2;

/**
 * Created by mhysa .
 */
public class InfoAdapter1 extends BaseAdapter2<InfoAdapter1.MyViewHolder> {

    private List<CommonHomeBean.ResultBean.ResultsBean> results;

    public InfoAdapter1(Context context, List<CommonHomeBean.ResultBean> listDatas, OnViewClickListener onViewClickListener) {
        super(context, listDatas, onViewClickListener);
    }
    public InfoAdapter1(Context context, List<CommonHomeBean.ResultBean> listDatas) {
        super(context, listDatas);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.item_list_home, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        CommonHomeBean.ResultBean infoBean = (CommonHomeBean.ResultBean) listDatas.get(position);//转换
        Log.e("mhysa",infoBean.toString()+"aaa");
        holder.tv_diseaseType.setText(infoBean.getTitle());//填充数据
        //infoBean.getResults();
         results = infoBean.getResults();
        List<Object> list = new ArrayList<Object>();
        if(this.results !=null&& this.results.size()>0){
            list.add(this.results);
            ImageAdapter imageAdapter = new ImageAdapter(context, this.results);
            holder.gridview.setLayoutManager(new GridLayoutManager(context, 3));
            holder.gridview.setAdapter(imageAdapter);
            holder.gridview.setVisibility(View.VISIBLE);
        } else{
            holder.gridview.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return listDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        RecyclerView gridview;
        TextView tv_diseaseType;

        public MyViewHolder(View view) {
            super(view);
            tv_diseaseType = (TextView)view.findViewById(R.id.tv_diseaseType);
             gridview = (RecyclerView) view.findViewById(R.id.gridview);

        }
    }

    /**
     * view的点击事件
     */
    class ViewClikListener implements View.OnClickListener {

        OnViewClickListener onViewClickListener;
        int position;
        int viewtype;

        public ViewClikListener(OnViewClickListener onViewClickListener, int position, int viewtype) {
            this.onViewClickListener = onViewClickListener;
            this.position = position;
            this.viewtype = viewtype;

        }

        @Override
        public void onClick(View v) {

            onViewClickListener.onViewClick(position, viewtype);

        }
    }

}
