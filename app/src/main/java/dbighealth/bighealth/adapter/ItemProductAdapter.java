package dbighealth.bighealth.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.activity.Affirm_Indent_Activity;
import dbighealth.bighealth.bean.AffirmIndentBean;

public class ItemProductAdapter extends BaseAdapter  {

    private Context context;

    private final int NUM = 1;
    private AdpterOnItemClick myAdpterOnclick;
    private List<AffirmIndentBean.Message> list;
    private LayoutInflater mInflater = null;
    private int count ;
//    private Integer[] mCounts;
//    public int q;
//    int b = 1;

    public ItemProductAdapter(Context context, List<AffirmIndentBean.Message> list) {
        this.context = context;
        this.list = list;
//        mCounts = new Integer[999];
        mInflater = LayoutInflater.from(context);

    }

    public void onListener(Affirm_Indent_Activity listener){
        this.myAdpterOnclick  = listener;
    }

    @Override
    public int getCount() {
        System.out.println("list长度" + list.size());
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
            convertView = mInflater.inflate(R.layout.item_product, null);
            holder = new ViewHolder();
            holder.iv_product = (ImageView) convertView.findViewById(R.id.iv_product);
            holder.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
            holder.tv_product_tit = (TextView) convertView.findViewById(R.id.tv_product_tit);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.count = (TextView) convertView.findViewById(R.id.count);
            holder.jian = (ImageView) convertView.findViewById(R.id.jian);
            holder.jia = (ImageView) convertView.findViewById(R.id.jia);

            holder.num = (TextView) convertView.findViewById(R.id.num);
//            holder.jian.setOnClickListener(this);
//            holder.jia.setOnClickListener(this);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();


        }
        Glide.with(context)
                .load(list.get(position).getImages())
                .placeholder(R.mipmap.home)
                .error(R.mipmap.home)
                .centerCrop()
                .crossFade()
                .into(holder.iv_product);
        holder.name_tv.setText(list.get(position).getTitle());
        holder.tv_product_tit.setText(list.get(position).getContent());
        holder.price.setText(list.get(position).getPrice());
        holder.count.setText("x" + list.get(position).getNum() + "");
        count = list.get(position).getNum();
        holder.num.setText(count + "");
        /**
         * 这里做标志
         */
        holder.jia.setTag(position);
        holder.jian.setTag(position);
        final int fpostion = position;
        final ViewHolder fholder = holder;

        holder.jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myAdpterOnclick != null) {
                    int which = v.getId();
                    myAdpterOnclick.onAdpterClick(which, fpostion);
                    Log.i("liuliuliuliu-->", "num：" + fpostion);
                    show(fpostion, fholder);

                }
            }
        });

        holder.jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myAdpterOnclick != null) {
                    int which = v.getId();
                    myAdpterOnclick.onAdpterClick(which, fpostion);
                    Log.i("liuliuliuliu-->", "num1：" + fpostion);
                    show(fpostion, fholder);
                }
            }
        });
//        q = count;
//        holder.jian.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ViewHolder.num.setText(count--);
//
//            }
//        });
//        holder.jia.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                    ViewHolder.num.setText(count++);
////                    TextView vs =(TextView) ViewHolder.num.getTag(position);
//
//
//                b++;
//                holder.num.setText(b + "");
//                int a = b;
//                b = a;
//                Log.i("liuliu-->", "是否为空：" + b);
////                    vs.setText(count++);
//            }
//        });

//        setData(convertView,position);
//        final ViewHolder finalHolder = holder;


        // 减 一样

        return convertView;
    }
//    public void setData(View convertView , int position){
//        holder.jia.getTag(position);
//        holder.jia.setOnClickListener(new OnClickListener() {

//            public void onClick(View v,int a ) {
////                    ViewHolder.num.setText(count++);
////                    TextView vs =(TextView) ViewHolder.num.getTag(position);
//
//
//                holder.num.getTag(position);
//                b++;
//                holder.num.setText(b + "");
//                int a = b;
//                b = a;
//                Log.i("liuliu-->", "是否为空：" + b);
////                    vs.setText(count++);
//            }
//        });
//    }

//    public int qwe = 10;
//    private AddListener mAddListener = new AddListener();
//    private RemoveListener mRoveListener = new RemoveListener();
////    String a ;
//    private class AddListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//                int pos = (Integer)v.getTag();
//                String a =(q++)+"";
//                mCounts[pos] ++;
//                q = q++;
//            ViewHolder.num.setText(mCounts[pos] ++);
    //update item = pos
//        }
//    }

//    private class RemoveListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            int pos = (Integer)v.getTag();
////            int a = 100;
//            String a = ((q--)+"");
//            q = q--;
//            ViewHolder.num.setText(a);
//            //update item = pos
//        }
//    }


//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.jian:
//
//                Log.i("mhysa-->","是否为空："+holder.num.getText());
//                if(holder.num.getText().toString().equals("1")){
////                    String jian=Integer.valueOf(holder.num.getText().toString())-NUM+"";
//                    holder.num.setText(1+"");
//                    holder.count.setText("x"+NUM);
//                }else {
//                    String jian=Integer.valueOf(holder.num.getText().toString())-NUM+"";
//                    holder.num.setText(jian);
//                    holder.count.setText("x"+jian);
//                }
//                break;

//            case R.id.jia:
//                if(holder.num.getText().toString().equals("999")){
//                    String jia=Integer.valueOf(holder.num.getText().toString())+NUM+"";
//                    holder.num.setText(jia);
//                    holder.count.setText("x"+jia);
//                }
//                break;
//        }
//    }

//    @Override
//    public void onAdpterClick(int which, int postion) {
//
//    }

    public static class ViewHolder {
        ImageView iv_product;//产品图片
        TextView name_tv;//店铺名称
        TextView tv_product_tit;//商品描述
        TextView price;//价格
         TextView count;//数量
        ImageView jian;//减
        ImageView jia;//加
         TextView num;
    }

    /**
     * 局部刷新
     */
    public void show(int index, ViewHolder holder) {
        if (holder != null) {
            int nums = list.get(index).getNum();
            Log.i("liuliuliuliu-->", "num：" + nums);
            holder.num.getTag();
            holder.num.setText(String.valueOf(nums));
            holder.count.setText("x" + String.valueOf(nums));

        }
    }
}
