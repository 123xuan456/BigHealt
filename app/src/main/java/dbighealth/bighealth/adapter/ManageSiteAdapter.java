package dbighealth.bighealth.adapter;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.activity.EditSiteActivity;
import dbighealth.bighealth.activity.ManageSiteActivity;
import dbighealth.bighealth.bean.ManageSiteBean;
import dbighealth.bighealth.view.DeleteDialog;
import okhttp3.Call;
import utils.UrlUtils;

/**
 * Created by de on 2016/10/13.
 */
public class ManageSiteAdapter extends BaseAdapter{
    ManageSiteActivity context;
    List<ManageSiteBean.MessageBean> list;
    // 用于记录每个RadioButton的状态，并保证只可选一个
    HashMap<String, Boolean> states = new HashMap<String, Boolean>();
    String old;
    private int defaults;


    public ManageSiteAdapter( ManageSiteActivity context, List<ManageSiteBean.MessageBean> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        if(list != null && list.size() > 0){
            return list.size();
        }
        else{
            return 0;
        }
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
        final ViewHolder holder;
        if (convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.item_managesite,null);
            holder.tv= (TextView) convertView.findViewById(R.id.textView62);
            holder.tv1= (TextView) convertView.findViewById(R.id.textView63);
            holder.tv2= (TextView) convertView.findViewById(R.id.textView64);
            holder.tv3= (TextView) convertView.findViewById(R.id.textView65);
            holder.tv4= (TextView) convertView.findViewById(R.id.textView66);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }

        final RadioButton radio=(RadioButton) convertView.findViewById(R.id.radioButton2);
        holder.rb = radio;

        ManageSiteBean.MessageBean ms = list.get(position);
        holder.tv.setText(ms.getName());
        holder.tv1.setText(ms.getPhoneNumber());
        holder.tv2.setText(ms.getArea()+ms.getAddress());

        defaults = ms.getDefaults();//默认值
        if ("1".equals(defaults+"")){
            old = ms.getAddressId() + "";
            System.out.println("之前默认的id="+old);
            holder.rb.setChecked(true);
            states.put(String.valueOf(position), true);
        }
        else if ("0".equals(defaults+"")){
            holder.rb.setChecked(false);
        }

        holder.rb.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if(!TextUtils.isEmpty(old)){
                    alter(old,String.valueOf(list.get(position).getAddressId()));//修改地址
                    // 重置，确保最多只有一项被选中
                    for (String key : states.keySet()) {
                        states.put(key, false);
                    }
                    String newid = String.valueOf(position);

                    states.put(newid, radio.isChecked());

                    ManageSiteAdapter.this.notifyDataSetChanged();
                }else if (TextUtils.isEmpty(old)){//当默认地址为空时，设置第一条为默认
                    System.out.println("默认的第一条id="+ String.valueOf(list.get(0).getAddressId()));
                    alter("0",String.valueOf(list.get(0).getAddressId()));//新的id
                }


            }



        });
        if (states.get(String.valueOf(position)) == null
                || states.get(String.valueOf(position)) == false ) {
            holder.rb.setChecked(false);
            holder.rb.setText("  设置默认");
            states.put(String.valueOf(position), false);
        } else {
            holder.rb.setChecked(true);
            holder.rb.setText("  默认地址");
        }



        holder.tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, EditSiteActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("addressId",list.get(position).getAddressId()+"");
                bundle.putString("name",list.get(position).getName());
                bundle.putString("phoneNumber",list.get(position).getPhoneNumber());
                bundle.putString("area",list.get(position).getArea());
                bundle.putString("address",list.get(position).getAddress());
                bundle.putString("defaults",list.get(position).getDefaults()+"");
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

        if (list.size()==1){//当只有一条地址时，不能点击
            holder.tv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    return;
                }
            });
        }else if (list != null && list.size() > 1){
            holder.tv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new DeleteDialog(context, R.style.DeleteDialog);
                    //设置它的ContentView
                    dialog.setContentView(R.layout.dialog_delete);
                    dialog.show();
                    Button bt1= (Button) dialog.findViewById(R.id.button7);
                    bt1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String addressId = list.get(position).getAddressId()+"";
                            System.out.println("删除地址addressId="+addressId);
                            String u= UrlUtils.DELETE_MANAGESITE+addressId;
                            OkHttpUtils.post().
                                    url(u).
                                    build().
                                    execute(new StringCallback() {
                                        @Override
                                        public void onError(Call call, Exception e, int id) {
                                            System.out.println("删除地址失败="+e.toString());
                                        }

                                        @Override
                                        public void onResponse(String response, int id) {
                                            System.out.println("删除地址="+response);
                                            list.remove(position);
                                            alter("0",String.valueOf(list.get(0).getAddressId()));//新的id
                                            ManageSiteAdapter.this.notifyDataSetChanged();
                                            dialog.dismiss();
                                        }
                                    });
                        }
                    });


                }
            });

        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, EditSiteActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("addressId",list.get(position).getAddressId()+"");
                bundle.putString("name",list.get(position).getName());
                bundle.putString("phoneNumber",list.get(position).getPhoneNumber());
                bundle.putString("area",list.get(position).getArea());
                bundle.putString("address",list.get(position).getAddress());
                bundle.putString("defaults",list.get(position).getDefaults()+"");
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });

        return convertView;
    }

    private void alter(String  old,String newest) {

        System.out.println("默认的第一条id1="+newest);
        System.out.println("传递之前的id="+old);
        String u=UrlUtils.SET_MANAGESITE;
        OkHttpUtils.post().
                url(u).
                addParams("old",old).
                addParams("newest",newest).
                build().
                execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("修改默认失败="+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        System.out.println("修改默认地址="+response);
                        Intent i=new Intent("android.intent.action.SET");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(i);
                        Toast.makeText(context, "默认地址修改成功", Toast.LENGTH_SHORT).show();
                        ManageSiteAdapter.this.notifyDataSetChanged();
                    }
                });
    }
    class ViewHolder{
        TextView tv;//姓名
        TextView tv1;//电话
        TextView tv2;//地址
        TextView tv3;//编辑
        TextView tv4;//删除
        RadioButton rb;
    }
}
