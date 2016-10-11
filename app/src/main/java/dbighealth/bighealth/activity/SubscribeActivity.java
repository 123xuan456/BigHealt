package dbighealth.bighealth.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.SubscribeBean;
import okhttp3.Call;
import utils.UrlUtils;

/**预约页面*/
public class SubscribeActivity extends Activity implements View.OnClickListener{

    private TextView tvTab,textView34,textView36,textView37,tel,imageView22;
    private ImageView arrow_left;
    private ImageView right_add,imageView21;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(new String[]{
                    Manifest.permission.CALL_PHONE
            },2);
        }
        setView();

    }

    private void setView() {
        tvTab=(TextView)findViewById(R.id.tvTab);
        arrow_left=(ImageView)findViewById(R.id.arrow_left);
        right_add=(ImageView)findViewById(R.id.right_add);
        right_add.setVisibility(View.GONE);
        tvTab.setText("预约");
        arrow_left.setOnClickListener(this);

        textView34 = (TextView)findViewById(R.id.textView34);//公司名字
        textView36 = (TextView)findViewById(R.id.textView36);//简介
        textView37 = (TextView)findViewById(R.id.textView37);//工作时间
        imageView21 = (ImageView)findViewById(R.id.imageView21);
        imageView22 = (TextView)findViewById(R.id.imageView22);
        imageView22.setOnClickListener(this);
        tel = (TextView)findViewById(R.id.tel);
        tel.setOnClickListener(this);
        http();


    }

    public void http(){
        try {
            OkHttpUtils.get()
                    .url(UrlUtils.SUBSCRIBE)
                    .build()
                    .execute(MyStringCallBack);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private StringCallback MyStringCallBack = new StringCallback(){


        @Override
        public void onError(Call call, Exception e, int id) {

            Toast.makeText(getApplicationContext(), "加盟失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            Message msg = new Message();
            msg.what = 1;
            Bundle bundle = new Bundle();
            bundle.putString("result",response);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }
    };

    private SubscribeBean subscribeBean;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Bundle data = msg.getData();
                    String result = data.getString("result");
                    Log.e("liu", "请求到的接口" + result);
                    Gson gson = new Gson();
                   subscribeBean = gson.fromJson(result, SubscribeBean.class);
                    int code = subscribeBean.getCode();
                    if(code ==200){
                        textView34.setText(subscribeBean.getTitle());
                        textView36.setText(subscribeBean.getContent());
                        textView37.setText(subscribeBean.getDatetime());
                    //    tel.setText(subscribeBean.getTelephone());
                        String imageurl = subscribeBean.getImageurl();

                        Glide.with(getApplication())
                                .load(imageurl)
                                .centerCrop()
                                .crossFade()
                                .into(imageView21);
                    }

                    break;
            }

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrow_left:
                finish();
                break;
            case R.id.imageView22:
                Intent intent = new Intent(this,Particular_subscribeActivity.class);
                startActivity(intent);
                break;
            case R.id.tel:
                Log.e("liu", "aaaaaa" +subscribeBean.getTelephone() );
                Uri telUri = Uri.parse("tel:" + "18211094821");
                Intent intent1 = new Intent(Intent.ACTION_DIAL, telUri); // 意图: 描述一个动作, 并且携带一些参数.
             /*   intent1.setAction(Intent.ACTION_CALL); // 指定当前意图的动作是打电话
                intent1.setData(Uri.parse("tel:" + subscribeBean.getTelephone())); // 设置拨号的信息*/
                startActivity(intent1); // 开启拨号应用

                break;
        }
    }
}
