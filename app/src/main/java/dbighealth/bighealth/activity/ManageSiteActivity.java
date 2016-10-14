package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.ManageSiteAdapter;
import dbighealth.bighealth.bean.ManageSiteBean;
import okhttp3.Call;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;

/**
 * 管理收货地址
 */
public class ManageSiteActivity extends Activity {

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.right_add)
    ImageView rightAdd;
    @Bind(R.id.listView3)
    ListView listView3;
    @Bind(R.id.tvTab)
    TextView tvTab;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_manage_site);
            ButterKnife.bind(this);
            rightAdd.setVisibility(View.GONE);
            tvTab.setText("管理收货地址");
            userid = SharedPreferencesUtils.getString(ManageSiteActivity.this, UrlUtils.LOGIN, "");
            getDate();
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SET");//修改昵称
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("接收到了getview的广播");
                getDate();

            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
    }



    @OnClick({R.id.arrow_left,R.id.tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            case R.id.tv:

                break;
        }
    }
    public void getDate() {
        OkHttpUtils.post().url(UrlUtils.SEARCH_MANAGESITE+1).build().execute(new StringCallback() {
            public List<ManageSiteBean.MessageBean> mes;

            @Override
            public void onError(Call call, Exception e, int id) {
            System.out.println("收货地址失败"+e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                System.out.println("收货地址成功"+response);
                Gson gson=new Gson();
                ManageSiteBean ms = gson.fromJson(response, ManageSiteBean.class);
                mes = ms.getMessage();
                ManageSiteAdapter adapter=new ManageSiteAdapter( ManageSiteActivity.this,mes);
               listView3.setAdapter(adapter);
            }
        });
    }
}
