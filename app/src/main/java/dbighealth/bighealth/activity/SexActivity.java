package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import okhttp3.Call;
import utils.UrlUtils;

public class SexActivity extends Activity {

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tvTab)
    TextView tvTab;
    @Bind(R.id.right_add)
    ImageView rightAdd;
    @Bind(R.id.imageView14)
    ImageView imageView14;
    @Bind(R.id.rl)
    RelativeLayout rl;
    @Bind(R.id.imageView11)
    ImageView imageView11;//女
    @Bind(R.id.rl1)
    RelativeLayout rl1;//女
    @Bind(R.id.man)
    TextView man;
    @Bind(R.id.woman)
    TextView woman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sex);
        ButterKnife.bind(this);
        tvTab.setVisibility(View.GONE);
        rightAdd.setVisibility(View.GONE);

    }

    @OnClick({R.id.arrow_left, R.id.rl, R.id.rl1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            case R.id.rl:
                imageView14.setVisibility(View.VISIBLE);
                imageView11.setVisibility(View.GONE);
                String m = man.getText().toString();
                send(m);//将选择的性别传给服务器
                break;
            case R.id.rl1:
                imageView14.setVisibility(View.GONE);
                imageView11.setVisibility(View.VISIBLE);
                String w = woman.getText().toString();
                send(w);
                break;
        }
    }
    //
    private void send(final String m) {
        String url = UrlUtils.CHANGESEX;
        String re = BaseApplication.regphone;
        JSONObject obj=new JSONObject();
        try {
            obj.put("regphone",re);
            obj.put("sex",m);
            OkHttpUtils.postString().url(url).content(obj.toString()).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    System.out.println("修改性别失败"+e);
                }

                @Override
                public void onResponse(String response, int id) {
                    System.out.println("修改性别成功"+response);
                    //修改性别之后发送一个广播
                    BaseApplication.sex=m;
                    Intent intent = new Intent("android.intent.action.CART_SEX");
                    intent.putExtra("sex",m);
                    System.out.println("过去！！性别"+m);
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                    finish();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
