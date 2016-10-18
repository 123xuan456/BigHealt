package dbighealth.bighealth.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.ShopcartAdapter;
import dbighealth.bighealth.bean.ShoppingCartBean;
import okhttp3.Call;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;

/**
 * 购物车
 */
public class ShoppingCartActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tit)
    TextView tit;
    @Bind(R.id.right_tv)
    TextView rightTv;
    @Bind(R.id.lv_shopcart)
    ListView lvShopcart;
    @Bind(R.id.cb_shopping)
    CheckBox cbShopping;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    @Bind(R.id.tv_balance)
    TextView tvBalance;
    @Bind(R.id.tv_money)
    TextView tvMoney;
    private int SEARCHSHOP = 101;
    private int DELETESHOP = 102;
    private List<ShoppingCartBean.MessageBean> message;
    ShopcartAdapter shopcartAdapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        rightTv.setText("编辑");
        tit.setText("购物车(0)");
        initListener();
        OkHttpUtils.get()
                .url(UrlUtils.SELECTSHOPCART)
                .addParams("userId", SharedPreferencesUtils.getString(getApplicationContext(), UrlUtils.LOGIN, ""))
                .id(SEARCHSHOP)
                .build()
                .execute(MyStringCallBack);
        cbShopping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    for(int i=0;i<message.size();i++){
                        ShopcartAdapter.getIsSelected().put(i,true);
                        shopcartAdapter.notifyDataSetChanged();
                    }
                }else{
                    for(int i=0;i<message.size();i++){
                        ShopcartAdapter.getIsSelected().put(i,false);
                        shopcartAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }
    StringCallback MyStringCallBack = new StringCallback() {


        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {

            if (id == SEARCHSHOP) {
                Gson gson = new Gson();
                ShoppingCartBean shoppingCartBean = gson.fromJson(response, ShoppingCartBean.class);
                message = shoppingCartBean.getMessage();
                shopcartAdapter = new ShopcartAdapter(getApplicationContext(), message, 1);
                tit.setText("购物车("+message.size()+")");

            } else if (id == DELETESHOP) {
                Gson gson = new Gson();
                ShoppingCartBean shoppingCartBean = gson.fromJson(response, ShoppingCartBean.class);
                message = shoppingCartBean.getMessage();
                shopcartAdapter = new ShopcartAdapter(getApplicationContext(), message, 2);
                tit.setText("购物车("+message.size()+")");
            }
            lvShopcart.setAdapter(shopcartAdapter);
        }
    };
    /**
     * 注册点击事件
     */
    public void initListener() {
        arrowLeft.setOnClickListener(this);
        rightTv.setOnClickListener(this);
        tvBalance.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            case R.id.right_tv:
                if (rightTv.getText().toString().equals("编辑")) {
                    rightTv.setText("完成");
                    tvTotal.setVisibility(View.GONE);
                    tvMoney.setVisibility(View.GONE);
                    OkHttpUtils.get()
                            .url(UrlUtils.SELECTSHOPCART)
                            .addParams("userId", SharedPreferencesUtils.getString(getApplicationContext(), UrlUtils.LOGIN, ""))
                            .id(DELETESHOP)
                            .build()
                            .execute(MyStringCallBack);
                }else if (rightTv.getText().toString().equals("完成")) {
                    rightTv.setText("编辑");
                    tvTotal.setVisibility(View.VISIBLE);
                    tvMoney.setVisibility(View.VISIBLE);
                    OkHttpUtils.get()
                            .url(UrlUtils.SELECTSHOPCART)
                            .addParams("userId", SharedPreferencesUtils.getString(getApplicationContext(), UrlUtils.LOGIN, ""))
                            .id(SEARCHSHOP)
                            .build()
                            .execute(MyStringCallBack);
                }
                break;
        }
    }
}
