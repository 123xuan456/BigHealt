package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.InProductAdapter;
import dbighealth.bighealth.bean.InProductBean;
import dbighealth.bighealth.view.NoScrollListview;
import okhttp3.Call;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;

/**
 * 产品
 */
public class ProductActivity extends Activity implements View.OnClickListener {

    private ImageView arrow_left, right_tv;
    private ImageView iv_product;
    private TextView tv_title, price;
    private ImageView iv_addcar, iv_nowbuy;
    private NoScrollListview lv_companyDescribe;
    public Context context;
    private String ids;
    InProductBean inProduct;
    List<InProductBean.Littleimages> lists;
    private int DOBUYNOW =101;
    private int ADDCART = 102;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
//    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        arrow_left = (ImageView) findViewById(R.id.arrow_left);
        arrow_left.setOnClickListener(this);
        right_tv = (ImageView) findViewById(R.id.right_tv);
        right_tv.setOnClickListener(this);
        iv_product = (ImageView) findViewById(R.id.iv_product);//产品图片
        tv_title = (TextView) findViewById(R.id.tv_title);//产品文字描述
        price = (TextView) findViewById(R.id.price);//价格
        iv_addcar = (ImageView) findViewById(R.id.iv_addcar);//加入购物车
        iv_addcar.setOnClickListener(this);
        iv_nowbuy = (ImageView) findViewById(R.id.iv_nowbuy);//立即购买
        iv_nowbuy.setOnClickListener(this);

        lv_companyDescribe = (NoScrollListview) findViewById(R.id.lv_companyDescribe);//listView


        initIntenet();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * 加载网络
     *
     * @param
     */
    public void initIntenet() {

        Intent intent = getIntent();
        ids = intent.getStringExtra("imgId");
        Log.i("mhysa-->",ids);
        OkHttpUtils.get()
                .url(UrlUtils.INPRODUCT)
//                   .id(SEARCH)
                .addParams("id", ids)
                .build()
                .execute(MyStringCallBack);
    }

    StringCallback MyStringCallBack = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {

            Log.i("liu", e.toString());
            if(id==ADDCART){
                Toast.makeText(getApplicationContext(),"加入购物车失败！",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onResponse(String response, int id) {

            if(id==DOBUYNOW){
              Log.i("mhysa-->","跳转购买页面成功！");
            }else if(id ==ADDCART){
                Toast.makeText(getApplicationContext(),"加入购物车成功！",Toast.LENGTH_SHORT).show();
            } else{
                Gson gson = new Gson();
                inProduct = gson.fromJson(response, InProductBean.class);
                int code = inProduct.getCode();
                if (code == 200) {

                    Glide.with(ProductActivity.this)
                            .load(inProduct.getImages())
//                        .placeholder(R.mipmap.home)
//                        .error(R.mipmap.home)
                            .centerCrop()
                            .crossFade()
                            .into(iv_product);

                    tv_title.setText(inProduct.getContent());
                    price.setText(inProduct.getPrice());
                    lists = inProduct.getLittleImages();
                    InProductAdapter reportPicAdapter = new InProductAdapter(getApplicationContext(), lists);
                    lv_companyDescribe.setAdapter(reportPicAdapter);

                }
            }

        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_tv:
                //跳转到购物车
                if(!TextUtils.isEmpty(SharedPreferencesUtils.getString(ProductActivity.this, UrlUtils.LOGIN, ""))){

                    Intent intent = new Intent(ProductActivity.this,ShoppingCartActivity.class);
                    startActivity(intent);

                }else {
                   Toast.makeText(getApplicationContext(),"请先登录！",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.arrow_left:
                finish();
                break;
            case R.id.iv_addcar:
                //加入到购物车
                //先跳到确认订单界面，因为购物车没有界面

                String useid = SharedPreferencesUtils.getString(ProductActivity.this, UrlUtils.LOGIN, "");
                Log.i("1234567890","url："+UrlUtils.ADDSHOPCART+"?userId="+useid+"&shoppingId="+ids);
                if(!TextUtils.isEmpty(useid)){
                    OkHttpUtils.get()
                            .url(UrlUtils.ADDSHOPCART)
                            .addParams("userId",SharedPreferencesUtils.getString(getApplicationContext(),UrlUtils.LOGIN,""))
                            .addParams("shoppingId",ids)
                            .id(ADDCART)
                            .build()
                            .execute(MyStringCallBack);

                }else {
                    Intent intent = new Intent(this,LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.iv_nowbuy:
                //立即购买
                OkHttpUtils.get()
                        .url(UrlUtils.SETTLEMENTORDER)
                        .addParams("userId",SharedPreferencesUtils.getString(getApplicationContext(),UrlUtils.LOGIN,""))
                        .addParams("shoppingId",ids)
                        .id(DOBUYNOW)
                        .build()
                        .execute(MyStringCallBack);
                String useid1 = SharedPreferencesUtils.getString(this, UrlUtils.LOGIN, "");
                if(!TextUtils.isEmpty(useid1)){
                    Intent intent = new Intent(this, Affirm_Indent_Activity.class);
                    startActivity(intent);

                }else {
                    Intent intent = new Intent(this,LoginActivity.class);
                    startActivity(intent);

                }
                break;
        }
    }


}
