package dbighealth.bighealth.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

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

    private TextView tit,right_tv,submit;
    private ImageView arrow_left;
    private TextView shouhuoren,tel,address,heji,no_address;
    private NoScrollListview product_listview;
    List<AffirmIndentBean.MessageBean> urls;
    ItemProductAdapter reportPicAdapter;
    public Context context;
    List<Integer> zongjia;
    List<String> shoppId;
    List<String> productOnePrice;
    List<String> productNumber;
    int qian;
    int leijiahe;
    private AffirmIndentBean AffirmIndent;
    private RelativeLayout rl;
    private int BUYNOW =101;
    private PopupWindow pop;
    private RelativeLayout weixin,ali,yinhang;
    private LinearLayout ll_popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_affirm_indent);
        findView();
        Intent intent = getIntent();
        String articleId = intent.getStringExtra("articleId");
        Log.i("article","articleid="+articleId);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(articleId!=null){
            OkHttpUtils.get()
                       .url(UrlUtils.BUYNOW)
                       .id(BUYNOW)
                       .addParams("userId",SharedPreferencesUtils.getString(this, UrlUtils.LOGIN, ""))
                       .addParams("productId",articleId)
                       .build()
                       .execute(MyStringCallBack.get());
        }else{
            initIntenet();
        }

    }


    private void findView() {
        tit = (TextView)findViewById(R.id.tit);
        tit.setText("确认订单");
        right_tv = (TextView)findViewById(R.id.right_tv);
        right_tv.setVisibility(View.GONE);
        arrow_left = (ImageView)findViewById(R.id.arrow_left);
        arrow_left.setOnClickListener(this);
        rl = (RelativeLayout) findViewById(R.id.rl);
        //收货地址的findView
        shouhuoren = (TextView)findViewById(R.id.shouhuoren);//收货人
        tel = (TextView)findViewById(R.id.tel);//电话
        address = (TextView)findViewById(R.id.address);//地址
        //产品的findView
        product_listview = (NoScrollListview)findViewById(R.id.product_listview);//要提交的产品的listview
        //ItemProductAdapter这个类是listview的adapter，item的布局已经完成，坐等数据和bean文件

        //没有地址的时候显示的
        no_address = (TextView)findViewById(R.id.no_address);

        heji = (TextView)findViewById(R.id.heji);
        //提交
        submit = (TextView)findViewById(R.id.submit);
        submit.setOnClickListener(this);
        rl.setOnClickListener(this);
    }

    //连网操作
    public void initIntenet() {
        String useid = SharedPreferencesUtils.getString(this,UrlUtils.LOGIN,"");
        OkHttpUtils.get()
                .url(UrlUtils.DOBUYNOW)
                .addParams("userId",SharedPreferencesUtils.getString(this, UrlUtils.LOGIN, ""))//useid
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
                    Log.i("liu-->", response);
                   Gson gson = new Gson();
                    AffirmIndent = gson.fromJson(response, AffirmIndentBean.class);
                    int code = AffirmIndent.getCode();
                    if (code == 200) {
                        if(!AffirmIndent.getAddress().equals("")){
                            no_address.setVisibility(View.GONE);
                            shouhuoren.setVisibility(View.VISIBLE);
                            tel.setVisibility(View.VISIBLE);
                            address.setVisibility(View.VISIBLE);
                            shouhuoren.setText("收货人:" + AffirmIndent.getName());
                            tel.setText(AffirmIndent.getPhoneNumber());
                            address.setText("收货地址:" + AffirmIndent.getAddress());
                        }else {
                            no_address.setVisibility(View.VISIBLE);
                            shouhuoren.setVisibility(View.GONE);
                            tel.setVisibility(View.GONE);
                            address.setVisibility(View.GONE);
                        }
                        urls = AffirmIndent.getMessage();
                        reportPicAdapter = new ItemProductAdapter(getApplicationContext(), urls);
                        reportPicAdapter.onListener(Affirm_Indent_Activity.this);
                        product_listview.setAdapter(reportPicAdapter);
                        gross();

                    }

                }
            };
        }
    };

    public void onAdpterClick(int which,final int position) {
        AffirmIndentBean.MessageBean message =urls.get(position);

        switch (which) {
            case R.id.jia:
                Log.i("liuliuliu-->", "wakakjian");
                message.setNum(message.getNum() + 1);
                gross();
                //myAdpter.notifyDataSetChanged();这里如果点击速度过快的话，getView重绘时会使数据加载混乱，所以不能用myAdpter.notifyDataSetChanged();
                //应该写一个独立的方法 局部刷新

                break;
            case R.id.jian:
                if (message.getNum() - 1==0){
                    Toast.makeText(getApplicationContext(), "不能在减了", Toast.LENGTH_SHORT).show();
                    message.setNum(1);

                }else {
                    message.setNum(message.getNum() - 1);
                }
//                int e1 = Integer.valueOf(message.getPrice());
//                he = Integer.valueOf(message.getPrice()) * Integer.valueOf(message.getNum());
                gross();
                break;
            default:

                break;
        }

    }

    /**
     * 计算总价格
     */
    public void gross(){
        zongjia = new ArrayList<Integer>();
        shoppId = new ArrayList<String>();
        productOnePrice = new ArrayList<String>();
        productNumber = new ArrayList<String>();
        for (int i=0;i<urls.size();i++){
            int jiage = Integer.valueOf(urls.get(i).getPrice());
            int shuliang = Integer.valueOf(urls.get(i).getNum());
            int sid = urls.get(i).getShoppingId();
            qian =jiage *shuliang;
            zongjia.add(qian);
            shoppId.add(sid+"");
            productOnePrice.add(jiage+"");
            productNumber.add(shuliang+"");
        }
        leijiahe = 0;
        for (int b:zongjia){
            leijiahe +=b;
        }
        heji.setText(leijiahe + "");
    }

    public String submitdata(){
        JSONObject obj = new JSONObject();
        try {
            obj.put("receiverName", AffirmIndent.getName());
            obj.put("receiverPhone", tel.getText().toString());
            obj.put("receiverAddress", address.getText().toString());
            obj.put("productId", getListString(shoppId));
            obj.put("userId", 26);
            obj.put("productOnePrice", getListString(productOnePrice));
            obj.put("productNumber", getListString(productNumber));
            obj.put("orderPrice",heji.getText().toString());

            return obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getListString(List<String> list){
        String str="";
        for(int i =0 ;i<list.size();i++){
            if(i!=list.size()-1){
                str+=list.get(i)+",";
            }
        }
        return str;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.arrow_left:
                finish();
                break;

            case R.id.submit:
                OkHttpUtils.postString()
                        .url(UrlUtils.SUBMITS)
                        .content(submitdata())
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                Toast.makeText(getApplicationContext(), "提交失败", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_LONG).show();
                                initPopu();
                                ll_popup.startAnimation(AnimationUtils.loadAnimation(
                                        Affirm_Indent_Activity.this, R.anim.activity_translate_in));
                                pop.showAtLocation(rl, Gravity.CENTER, 10, 10);
                            }
                        });
                break;
            case R.id.rl:
                Intent intent = new Intent(Affirm_Indent_Activity.this, ManageSiteActivity.class);
                startActivityForResult(intent,1);
                break;
        }
    }

    private void initPopu() {
        pop = new PopupWindow(Affirm_Indent_Activity.this);
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows_1, null);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        weixin = (RelativeLayout) view.findViewById(R.id.weixin);
        ali = (RelativeLayout) view.findViewById(R.id.ali);
        yinhang = (RelativeLayout) view.findViewById(R.id.yinhang);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        weixin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Toast.makeText(Affirm_Indent_Activity.this,"你点击的微信支付",Toast.LENGTH_SHORT).show();

                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        ali.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.ECLAIR)
            public void onClick(View v) {
                Toast.makeText(Affirm_Indent_Activity.this,"你点击的支付宝支付",Toast.LENGTH_SHORT).show();

                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        yinhang.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(Affirm_Indent_Activity.this,"你点击的银联支付",Toast.LENGTH_SHORT).show();

                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&&resultCode == RESULT_OK){
            no_address.setVisibility(View.GONE);
            shouhuoren.setVisibility(View.VISIBLE);
            tel.setVisibility(View.VISIBLE);
            address.setVisibility(View.VISIBLE);
            shouhuoren.setText("收货人:" +  data.getStringExtra("name"));
            tel.setText(data.getStringExtra("phone"));
            address.setText("收货地址："+data.getStringExtra("area"));
        }


    }
}
