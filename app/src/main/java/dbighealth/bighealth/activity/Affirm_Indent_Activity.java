package dbighealth.bighealth.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import dbighealth.bighealth.R;
import dbighealth.bighealth.view.NoScrollListview;
import okhttp3.Call;
import utils.UrlUtils;

/**
 * 确认订单
 * liu
 */
public class Affirm_Indent_Activity extends Activity implements View.OnClickListener {

    private TextView tit,right_tv;
    private ImageView arrow_left;
    private TextView shouhuoren,tel,address;
    private NoScrollListview product_listview;

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
    }

    //连网操作
    public void initIntenet() {
        OkHttpUtils.get()
                .url(UrlUtils.SEARCHREPORT)//url要换掉
//                   .id(SEARCH)
//                   .addParams("userId",userid)
                .build()
                .execute(MyStringCallBack);
    }


    /**
     * 1.这里应该有2个接口
     * 2.1个是进来拿到的数据的接口（地址和产品的listview的）
     * 3.1个是提交，把拿到的数据给后台传过去
     */
    StringCallback MyStringCallBack = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {

            Log.i("liu", e.toString());
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
        switch(v.getId()){
            case R.id.arrow_left:
                finish();
                break;
        }
    }
}
