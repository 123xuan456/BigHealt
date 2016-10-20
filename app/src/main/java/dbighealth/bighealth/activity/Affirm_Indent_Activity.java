package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.AdpterOnItemClick;
import dbighealth.bighealth.adapter.ItemProductAdapter;
import dbighealth.bighealth.bean.AffirmIndentBean;
import dbighealth.bighealth.view.NoScrollListview;
import okhttp3.Call;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;

/**
 * 确认订单
 * liu
 */
public class Affirm_Indent_Activity extends Activity implements View.OnClickListener, AdpterOnItemClick {

    private TextView tit,right_tv;
    private ImageView arrow_left;
    private TextView shouhuoren,tel,address,heji;
    private NoScrollListview product_listview;
    List<AffirmIndentBean.Message> urls;
    ItemProductAdapter reportPicAdapter;
    public Context context;
    int he;
    List<Integer> zongia;
     int qian2;
    int qian;
    int a;
    int w;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_affirm_indent);
        findView();


        initIntenet();



    }


    private void findView() {
        tit = (TextView)findViewById(R.id.tit);
        tit.setText("确认订单");
        right_tv = (TextView)findViewById(R.id.right_tv);
        right_tv.setVisibility(View.GONE);
        arrow_left = (ImageView)findViewById(R.id.arrow_left);
        arrow_left.setOnClickListener(this);

        //收货地址的findView
        shouhuoren = (TextView)findViewById(R.id.shouhuoren);//收货人
        tel = (TextView)findViewById(R.id.tel);//电话
        address = (TextView)findViewById(R.id.address);//地址
        //产品的findView
        product_listview = (NoScrollListview)findViewById(R.id.product_listview);//要提交的产品的listview
        //ItemProductAdapter这个类是listview的adapter，item的布局已经完成，坐等数据和bean文件

        heji = (TextView)findViewById(R.id.heji);
    }

    //连网操作
    public void initIntenet() {
        String useid = SharedPreferencesUtils.getString(this,UrlUtils.LOGIN,"");
        OkHttpUtils.get()
                .url(UrlUtils.DOBUYNOW)
//                   .id(SEARCH)
                .addParams("userId", 26 + "")//useid
//                .addParams("shoppingId", 1+ ""+","+2+"")
                .build()
                .execute(MyStringCallBack.get());
    }


    /**
     * 1.这里应该有2个接口
     * 2.1个是进来拿到的数据的接口（地址和产品的listview的）
     * 3.1个是提交，把拿到的数据给后台传过去
     */
    public final ThreadLocal<StringCallback> MyStringCallBack = new ThreadLocal<StringCallback>() {
        @Override
        protected StringCallback initialValue() {
            return new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {

                    Log.i("liu", e.toString());
                }

                @Override
                public void onResponse(String response, int id) {

                    Gson gson = new Gson();

                    AffirmIndentBean AffirmIndent = gson.fromJson(response, AffirmIndentBean.class);
//
                    int code = AffirmIndent.getCode();
                    if (code == 200) {
                        shouhuoren.setText("收货人:" + AffirmIndent.getName());
                        tel.setText(AffirmIndent.getPhoneNumber());
                        address.setText(AffirmIndent.getAddress());
                        urls = AffirmIndent.getMessage();
                        reportPicAdapter = new ItemProductAdapter(getApplicationContext(), urls);
                        reportPicAdapter.onListener(Affirm_Indent_Activity.this);
                        product_listview.setAdapter(reportPicAdapter);

                        zongia = new ArrayList<Integer>();
                        for (int i=0;i<urls.size();i++){
                            int p = Integer.valueOf(urls.get(i).getPrice());
                            int o = Integer.valueOf(urls.get(i).getNum());
                            qian =p *o;
                            zongia.add(qian);
                        }
//                        int qian = Integer.valueOf(urls.get(0).getPrice()) * Integer.valueOf(urls.get(0).getNum());
                        a = 0;
                        for (int b:zongia){
//                           qian2 += zongia.get(j);
                            a +=b;
                        }
                        heji.setText(a + "");

                    }

                }
            };
        }
    };

    public void onAdpterClick(int which,final int position) {
        AffirmIndentBean.Message message =urls.get(position);

        switch (which) {
            case R.id.jia:
                Log.i("liuliuliu-->","wakakjian");
//                for (int i =0;i<=urls.size();i++){
//                    String a = urls.get(i).getNum();
//      lists.get(0).;
//                    String a = inProduct.getLittleImages().get(i).getLittle();
//                }
//                AffirmIndentBean.Message message =urls.get(position);

                message.setNum(message.getNum() + 1);
                ItemProductAdapter.ViewHolder viewHolder = new ItemProductAdapter.ViewHolder();
                int e = Integer.valueOf(message.getPrice());
                he = Integer.valueOf(message.getPrice()) * Integer.valueOf(message.getNum());
//                message.setPrice(he+"");
                w = a+he-e;
                heji.setText(w+"");
                a= w;
                Log.i("liuliuliu-->","num："+a);
               // viewHolder.count
//                viewHolder.num.setText(message.getNum() +"");

             //   reportPicAdapter.notifyDataSetChanged();
//                lists.get(position).getNum();
//                lists.get(0).setAge(1+1);
                //myAdpter.notifyDataSetChanged();这里如果点击速度过快的话，getView重绘时会使数据加载混乱，所以不能用myAdpter.notifyDataSetChanged();
                //应该写一个独立的方法 局部刷新

                break;
            case R.id.jian:

//                person1.setAge(person1.getAge()-1);
//                AffirmIndentBean.Message message1 =urls.get(position);
                message.setNum(message.getNum() - 1);
                int e1 = Integer.valueOf(message.getPrice());
                he = Integer.valueOf(message.getPrice()) * Integer.valueOf(message.getNum());
//                message1.setPrice(he+"");
//                a= w;
                w = a-he+e1;

                a= w;
                Log.i("liuliuliu-->", "qwe"+a);
                heji.setText(w + "");
                break;
            default:

                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.arrow_left:
                finish();
                break;
        }
    }
}
