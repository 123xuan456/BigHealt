package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.ReportPicAdapter;
import dbighealth.bighealth.bean.HasCommitBean;
import okhttp3.Call;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;

/**
 *产品
 */
public class ProductActivity extends Activity implements View.OnClickListener {

    private ImageView arrow_left,right_tv;
    private ImageView iv_product;
    private TextView tv_title,price;
    private ImageView iv_addcar,iv_nowbuy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        arrow_left = (ImageView)findViewById(R.id.arrow_left);
        arrow_left.setOnClickListener(this);
        right_tv = (ImageView)findViewById(R.id.right_tv);
        right_tv.setOnClickListener(this);
        iv_product = (ImageView)findViewById(R.id.iv_product);//产品图片
        tv_title = (TextView)findViewById(R.id.tv_title);//产品文字描述
        price = (TextView)findViewById(R.id.price);//价格
        iv_addcar = (ImageView)findViewById(R.id.iv_addcar);//加入购物车
        iv_addcar.setOnClickListener(this);
        iv_nowbuy =(ImageView)findViewById(R.id.iv_nowbuy);//立即购买
        iv_nowbuy.setOnClickListener(this);
//        initIntenet();
    }

    /**
     * 加载网络
     * @param
     */
    public void initIntenet(){
        //url要换掉
//        String userid = SharedPreferencesUtils.getString(this, UrlUtils.LOGIN, "");
        OkHttpUtils.get()
                   .url(UrlUtils.SEARCHREPORT)
//                   .id(SEARCH)
//                   .addParams("userId",userid)
                   .build()
                   .execute(MyStringCallBack);
    }
    StringCallback MyStringCallBack = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {

            Log.i("liu",e.toString());
        }

        @Override
        public void onResponse(String response, int id) {
            //bean文件没有做还有赋值 adapter没有看这ItemDetailAdapter类，用这里的item
            //上边数据要在这里赋值
            Gson gson = new Gson();

//            HasCommitBean hasCommit = gson.fromJson(response,HasCommitBean.class);
//
//            int code = hasCommit.getCode();
//            if(code==200){
//                List<HasCommitBean.UrlsBean> urls = hasCommit.getUrls();
//                ReportPicAdapter reportPicAdapter = new ReportPicAdapter(getApplicationContext(),urls);
//                gridView1.setAdapter(reportPicAdapter);
//            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_tv:
                //跳转到购物车
                break;
            case R.id.arrow_left:
                finish();
                break;
            case R.id.iv_addcar:
                //加入到购物车
                //先跳到确认订单界面，因为购物车没有界面
                Intent intent = new Intent(this,Affirm_Indent_Activity.class);
                startActivity(intent);
                break;
            case R.id.iv_nowbuy:
                //立即购买
                break;
        }
    }

}
