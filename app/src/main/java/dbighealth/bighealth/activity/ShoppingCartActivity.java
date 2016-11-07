package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.CollectionAdapter;
import dbighealth.bighealth.adapter.ShopcartAdapter;
import dbighealth.bighealth.bean.ShoppingCartBean;
import okhttp3.Call;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;

/**
 * 购物车
 */
public class ShoppingCartActivity extends Activity implements View.OnClickListener{

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
    private int DELETEPRODUCT = 103;
    private int UPDATEPRODUCT = 104;
    private int SETTLEMENTPRODUCT = 105;
    private List<ShoppingCartBean.MessageBean> message;
    ShopcartAdapter shopcartAdapter = null;
    List<Integer> deleteIndex = new ArrayList<Integer>();
    private String  deleteshopId = "";
    private String productIdlist = "";//拼接所有商品的id
    private String settleProduct ="";//要结算的商品
    //计数选中的条目个数
    public int count = 0;
    //存储item选中状态
    private HashMap<Integer, Boolean> saveStatus;
    private int totalPrice = 0;
    //存储item非选中状态
    private HashMap<Integer,Boolean> unSelectStatus;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        rightTv.setText("编辑");
        tit.setText("购物车(0)");
        rightTv.setVisibility(View.INVISIBLE);
        saveStatus = new HashMap<Integer, Boolean>();
        unSelectStatus = new HashMap<Integer, Boolean>();
        initListener();
        InitInternet(SEARCHSHOP);
        checkBoxClick();
        lvShopcart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /**
                 * 跳转到商品详情页
                 */
                Intent intent = new Intent(ShoppingCartActivity.this,ProductActivity.class);
                intent.putExtra("imgId",message.get(position).getShoppingId());
                startActivity(intent);
            }
        });
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_PRODUCTNUM");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                tit.setText("购物车(" + message.size() + ")");

                /**
                 * 总计
                 */
                totalPrice = 0;
                Log.i("count-->", "count111=" + count);
                if (ShopcartAdapter.getIsSelected().containsValue(false)) {

                    cbShopping.setChecked(false);

                } else {
                    cbShopping.setChecked(true);
                }


               if (intent.getIntExtra("getTotal", 0) == 1) {

                 for (int i = 0; i < message.size(); i++) {
                     Log.i("count-->", "getselect="+ShopcartAdapter.getIsSelected().get(i));
                        if (ShopcartAdapter.getIsSelected().get(i)!=null&&ShopcartAdapter.getIsSelected().get(i)) {


                            if (!saveStatus.containsKey(i)) {
                                count=count+1;
                                Log.i("count-->", "count=" + count + "i=" + i);
                            }

                            saveStatus.put(i, true);
                            String price = message.get(i).getPrice();
                            int singlePrice = Integer.parseInt(price);
                            int num = message.get(i).getNum();
                            int ProductPrice = singlePrice * num;
                            totalPrice += ProductPrice;
                        }else{
                         /*   if(saveStatus.containsKey(i)){
                                count-=1;
                            }else if(unSelectStatus.get(i)!=null&&unSelectStatus.get(i)){
                                count -=1;
                            }*/
                          if(saveStatus.containsKey(i)){
                             //   Log.i("count-->","包含了数字：：：i="+i+"选中的"+unSelectStatus.get(i));
                              /*  if(unSelectStatus.get(i)!=null&&unSelectStatus.get(i)){
                                    count -=1;
                                }*/
                                Log.i("count-->","包含了数字：：：i="+i);
                                if(!saveStatus.get(i)){
                                    saveStatus.remove(i);
                                    count--;
                                }else{
                                    saveStatus.remove(i);
                                    count--;
                               }

                            }
                        }

                    }

                   if(intent.getBooleanExtra("delete",false)){
                       int sum =0;
                       for(int i=0;i<message.size();i++){
                          if(ShopcartAdapter.getIsSelected().get(i)) {
                              sum+=1;
                          }
                       }
                       count = sum;
                   }
                  //  tvBalance.setText("结算("+count+")");
                   tvBalance.setText("结算("+count+")");
                }
                if (intent.getIntExtra("getTotal", 0) == 2){
                  /*  int position = intent.getIntExtra("position", 0);
                   if(ShopcartAdapter.getIsSelected().get(position)) {
                       saveStatus.put(position,true);
                   }else{
                       count -=1;
                   }*/
                   for(int i = 0;i<message.size();i++){
                        if (ShopcartAdapter.getIsSelected().get(i)){
                            if(saveStatus.get(i)!=null&&!saveStatus.get(i)){
                                count++;
                            }
                            saveStatus.put(i,true);
                        }else{
                            if(saveStatus.get(i)!=null&&saveStatus.get(i)){
                                saveStatus.remove(i);
                            }
                            unSelectStatus.put(i,true);
                        //   saveStatus.put(i,false);
                        }
                    }

                }
                tvMoney.setText("￥" + totalPrice);
//                tvBalance.setText("结算(" + count + ")");

            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);

    }

    /**
     * 全选/全不选
     */
    public void checkBoxClick() {
        cbShopping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //全选
                if (isChecked) {
                    if(message!=null){
                        for (int i = 0; i < message.size(); i++) {
                            //   ShopcartAdapter.getSelect().add(true);
                            ShopcartAdapter.getIsSelected().put(i, true);
                            shopcartAdapter.notifyDataSetChanged();
                        }
                    }


                } else {
                    if (ShopcartAdapter.getIsSelected().containsValue(false)) {

                    } else {

                        for (int i = 0; i < message.size(); i++) {
                            ShopcartAdapter.getIsSelected().put(i, false);
                            shopcartAdapter.notifyDataSetChanged();
                        }
                    }
                    totalPrice = 0;
                }
                if (tvMoney.getVisibility() == View.VISIBLE) {
                    tvMoney.setText("￥" + totalPrice);
                }
            }
        });
    }

    StringCallback MyStringCallBack = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            if(id == DELETEPRODUCT){

                Toast.makeText(getApplicationContext(),"删除失败，请检查网络！",Toast.LENGTH_SHORT).show();
            }
            if(id == UPDATEPRODUCT){
                Toast.makeText(getApplicationContext(),"修改失败，请检查网络！",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onResponse(String response, int id) {

            if(id == SETTLEMENTPRODUCT){
                Log.i("mhysa-->","结算成功！");
            }
            if(id == UPDATEPRODUCT){
                Toast.makeText(getApplicationContext(),"修改成功！",Toast.LENGTH_SHORT).show();

                InitInternet(SEARCHSHOP);
            }
            if (id == SEARCHSHOP) {
                Gson gson = new Gson();
                ShoppingCartBean shoppingCartBean = gson.fromJson(response, ShoppingCartBean.class);
                message = shoppingCartBean.getMessage();
                if(message.size()!=0){
                    rightTv.setVisibility(View.VISIBLE);
                }
                shopcartAdapter = new ShopcartAdapter(getApplicationContext(), message, 1);
                for(int i=0;i<message.size();i++){
                    if(saveStatus.get(i)!=null){
                        ShopcartAdapter.getIsSelected().put(i,saveStatus.get(i));
                    }else{
                        ShopcartAdapter.getIsSelected().put(i,false);
                    }

                    Log.i("mhysa-->","地址："+i+"状态："+saveStatus.get(i));
                }

            } else if (id == DELETESHOP) {
                Gson gson = new Gson();
                ShoppingCartBean shoppingCartBean = gson.fromJson(response, ShoppingCartBean.class);
                message = shoppingCartBean.getMessage();
                shopcartAdapter = new ShopcartAdapter(getApplicationContext(), message, 2);
                 for(int i=0;i<message.size();i++){
                     if(saveStatus.get(i)!=null){
                         ShopcartAdapter.getIsSelected().put(i,saveStatus.get(i));
                     }else{
                         ShopcartAdapter.getIsSelected().put(i,false);
                     }

                     Log.i("mhysa-->","地址："+i+"状态："+saveStatus.get(i));
                }
                tvBalance.setText("删除");
                tvBalance.setBackgroundColor(Color.parseColor("#d83231"));

            }
            /**
             * 删除条目
             */
            if(id == DELETEPRODUCT){


                Toast.makeText(getApplicationContext(),"删除成功！",Toast.LENGTH_SHORT).show();
                deleteshopId = "";

            }
            tit.setText("购物车(" + message.size() + ")");
            lvShopcart.setAdapter(shopcartAdapter);
            shopcartAdapter.notifyDataSetChanged();

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
                    tvBalance.setBackgroundColor(Color.parseColor("#d83231"));
                    tvBalance.setText("结算(" + count + ")");
                   //cbShopping.setChecked(false);
                    InitInternet(DELETESHOP);
                } else if (rightTv.getText().toString().equals("完成")) {
                    rightTv.setText("编辑");
                    tvTotal.setVisibility(View.VISIBLE);
                    tvMoney.setVisibility(View.VISIBLE);
                    tvBalance.setBackgroundColor(Color.parseColor("#f58661"));
                    int prouctCount =0;
                    for(int i=0;i<message.size();i++){
                        Boolean flag = ShopcartAdapter.getIsSelected().get(i);
                        if(flag){
                            prouctCount++;
                        }
                    }
                    count = prouctCount;
                    tvBalance.setText("结算(" + count + ")");

                    //上传修改过的商品信息
                   OkHttpUtils.get()
                              .url(UrlUtils.UPDATENUMS)
                              .id(UPDATEPRODUCT)
                              .addParams("userId",SharedPreferencesUtils.getString(getApplicationContext(),UrlUtils.LOGIN,""))
                              .addParams("shoppingId",productList())
                              .addParams("num",productNums())
                              .build()
                              .execute(MyStringCallBack);

                    shopcartAdapter.notifyDataSetChanged();

                }
                break;

            /**
             * 结算或删除
             */
        case  R.id.tv_balance:
            if(tvBalance.getText().toString().equals("删除")){


                    for(int j= 0;j<message.size();j++){
                        if(ShopcartAdapter.getIsSelected().get(j)){
                            deleteIndex.add(j);
                        }
                    }

                for(int i=0;i<deleteIndex.size();i++) {
                    if (i == 0) {
                        int index = deleteIndex.get(i);
                        if (i != deleteIndex.size() - i - 1) {
                            deleteshopId+=message.get(index).getShoppingId()+",";
                        } else {
                            deleteshopId+=message.get(index).getShoppingId();
                        }
                        message.remove(index);
                        Log.i("delete-->","删除了："+message.size());
                        for(int j = 0;j<message.size();j++){
                            ShopcartAdapter.getIsSelected().put(j, ShopcartAdapter.getIsSelected().get(j+1));
                        }
                        ShopcartAdapter.getIsSelected().remove(message.size());
                    } else {
                        int index = deleteIndex.get(i) - i;

                        if (i != deleteIndex.size() - 1) {
                           // saveAritcleId += message.get(index).getArticleId() + ",";
                            deleteshopId+=message.get(index).getShoppingId()+",";
                        } else {
                           // saveAritcleId += message.get(index).getArticleId();
                            deleteshopId+=message.get(index).getShoppingId();
                        }
                        message.remove(index);
                        Log.i("delete-->","删除了："+message.size());
                        for(int j = index;j<message.size();j++){
                            ShopcartAdapter.getIsSelected().put(j, ShopcartAdapter.getIsSelected().get(j+1));
                        }
                        ShopcartAdapter.getIsSelected().remove(message.size());
                    }
                }
            /* for(int m= 0;m<message.size();m++){

                   Log.i("delete-->","size："+message.size());
                    ShopcartAdapter.getIsSelected().put(m,false);

                }*/

                //将存储选中的集合清空，避免第二次删除时集合长度递增的情况
                deleteIndex.clear();
                for(int i=0;i<message.size();i++){
                    ShopcartAdapter.getIsSelected().put(i,false);
                }
                shopcartAdapter.notifyDataSetChanged();
              /*  if(cbShopping.isChecked()){
                    cbShopping.setChecked(false);

                }*/

                Log.i("delete-->","size："+deleteshopId);

                OkHttpUtils.get()
                           .url(UrlUtils.DELETESHOPCART)
                           .addParams("userId",SharedPreferencesUtils.getString(getApplicationContext(),UrlUtils.LOGIN,""))
                           .addParams("shoppingId",deleteshopId)
                           .id(DELETEPRODUCT)
                           .build()
                           .execute(MyStringCallBack);

            }else{
                settleProduct = "";
                for(int i = 0;i<message.size();i++){
                   if(ShopcartAdapter.getIsSelected().get(i)){
                       if(i!=message.size()-1){
                           settleProduct +=message.get(i).getShoppingId()+",";
                       }else{
                           settleProduct +=message.get(i).getShoppingId();
                       }

                   }
                }
                Log.i("liu-->",settleProduct);
                //结算
                OkHttpUtils.get()
                        .url(UrlUtils.SETTLEMENTORDER)
                        .addParams("userId",SharedPreferencesUtils.getString(getApplicationContext(),UrlUtils.LOGIN,""))
                        .addParams("shoppingId",settleProduct)
                        .id(SETTLEMENTPRODUCT)
                        .build()
                        .execute(MyStringCallBack);
                String useid = SharedPreferencesUtils.getString(this, UrlUtils.LOGIN, "");
                if(!TextUtils.isEmpty(useid)){
                    if(settleProduct.isEmpty()){
                        Toast.makeText(getApplicationContext(),"您还没有添加宝贝哦！",Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent1 = new Intent(ShoppingCartActivity.this,Affirm_Indent_Activity.class);
                        startActivity(intent1);
                    }

                }else {
                    Intent intent = new Intent(this,LoginActivity.class);
                    startActivity(intent);

                }


            }
            break;

        }
    }
    /**
     * 拼接列表所有商品的Id
     */
    public String productList(){
        productIdlist = "";
        for(int i=0;i<message.size();i++){

            if(i!=message.size()-1){
                productIdlist +=message.get(i).getShoppingId()+",";
            }else{
                productIdlist +=message.get(i).getShoppingId();
            }
        }
        return productIdlist;
    }

    /**
     * 获取每件商品的数量的集合
     */
    public String productNums(){
        productIdlist = "";
        for(int i=0;i<message.size();i++){

            if(i!=message.size()-1){
                productIdlist +=message.get(i).getNum()+",";
            }else{
                productIdlist +=message.get(i).getNum();
            }
        }
        return productIdlist;
    }
    /**
     * 请求网络
     *
     * @param id
     */
    public void InitInternet(int id) {
        OkHttpUtils.get()
                .url(UrlUtils.SELECTSHOPCART)
                .addParams("userId", SharedPreferencesUtils.getString(getApplicationContext(), UrlUtils.LOGIN, ""))
                .id(id)
                .build()
                .execute(MyStringCallBack);
    }
}
