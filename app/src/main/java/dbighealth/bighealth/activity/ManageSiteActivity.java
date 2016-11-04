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
    @Bind(R.id.tvTab)
    TextView tvTab;
    @Bind(R.id.listView3)
    ListView listView;
    private String userid;
    public List<ManageSiteBean.MessageBean> mes;
    ManageSiteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_site);
        ButterKnife.bind(this);
        //listView3= (ListView) findViewById(R.id.listView3);
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
                System.out.println("接收到了广播SET");
                getDate();

            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);


    }


    @OnClick({R.id.arrow_left, R.id.tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrow_left:

                Intent intent=new Intent();
                intent.putExtra("name", mes.get(0).getName());
                intent.putExtra("phone",  mes.get(0).getPhoneNumber());
                intent.putExtra("area",  mes.get(0).getArea()+mes.get(0).getAddress());
                setResult(RESULT_OK, intent);
                finish();

                break;
            case R.id.tv:
                Intent i = new Intent(this, AddSiteActivity.class);
                startActivity(i);
                break;
        }
    }

    public void getDate() {
        OkHttpUtils.post().url(UrlUtils.SEARCH_MANAGESITE + userid).build().execute(new StringCallback() {


            @Override
            public void onError(Call call, Exception e, int id) {
                System.out.println("收货地址失败" + e.toString());
            }
            @Override
            public void onResponse(String response, int id) {
                System.out.println("收货地址成功" + response);
                Gson gson = new Gson();
                ManageSiteBean ms = gson.fromJson(response, ManageSiteBean.class);
                mes = ms.getMessage();
                adapter = new ManageSiteAdapter(ManageSiteActivity.this, mes);
                listView.setAdapter(adapter);



            }
        });
    }
}
