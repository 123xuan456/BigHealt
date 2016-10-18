package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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
        }

        @Override
        public void onResponse(String response, int id) {

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
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_tv:
                //跳转到购物车
                break;
            case R.id.arrow_left:
                finish();
                break;
            case R.id.iv_addcar:
                //加入到购物车
                //先跳到确认订单界面，因为购物车没有界面
                Intent intent = new Intent(this, Affirm_Indent_Activity.class);
                startActivity(intent);
                break;
            case R.id.iv_nowbuy:
                //立即购买
                break;
        }
    }


}
