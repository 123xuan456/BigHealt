package dbighealth.bighealth.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.InfoBean;
import dbighealth.bighealth.view.BaseAdapter;

/**
 * Created by mhysa .
 */
public class InfoAdapter extends BaseAdapter<InfoAdapter.MyViewHolder> {

    public InfoAdapter(Context context, List<Object> listDatas, BaseAdapter.OnViewClickListener onViewClickListener) {
        super(context, listDatas, onViewClickListener);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.item_list_home, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        InfoBean infoBean = (InfoBean) listDatas.get(position);//转换
        holder.tv_diseaseType.setText(infoBean.getText());//填充数据
        if(infoBean.getImgList()!=null&&infoBean.getImgList().size()>0){
            ImageAdapter imageAdapter = new ImageAdapter(context, infoBean.getImgList());
            holder.gridview.setLayoutManager(new GridLayoutManager(context,3));
            holder.gridview.setAdapter(imageAdapter);
            holder.gridview.setVisibility(View.VISIBLE);
        }else{
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
