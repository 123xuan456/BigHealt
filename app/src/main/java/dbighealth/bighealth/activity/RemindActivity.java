package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.RemindAdapter;
import dbighealth.bighealth.bean.RemindBean;
import okhttp3.Call;
import utils.UrlUtils;

/**
 * 服药提醒页面
 */
public class RemindActivity extends Activity implements View.OnClickListener {

    private ImageView arrow_left;
    private ImageView right_add;
    private RelativeLayout rl;
    private ListView listview;
    RemindBean remindBean;
    RemindAdapter adapter;
    Boolean isshow = true;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_remind);
        setView();

        http();// 接口
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setView() {
        arrow_left = (ImageView) findViewById(R.id.arrow_left);
        right_add = (ImageView) findViewById(R.id.right_add);
        arrow_left.setOnClickListener(this);
        right_add.setOnClickListener(this);
        rl = (RelativeLayout) findViewById(R.id.rl);
        listview = (ListView) findViewById(R.id.listview);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            case R.id.right_add:
                Intent i = new Intent(this, RemindSetActivity.class);
                startActivity(i);

        }
    }

    public void http() {
        OkHttpUtils.get()
                .url(UrlUtils.REMIND)
                .addParams("userId", 11 + "")//id
                .build()
                .execute(MyStringCallBack);
    }

    public StringCallback MyStringCallBack = new StringCallback() {

        @Override
        public void onError(Call call, Exception e, int id) {

            rl.setVisibility(View.GONE);
            listview.setVisibility(View.VISIBLE);
        }

        @Override
        public void onResponse(String response, int id) {

            Gson gson = new Gson();
            remindBean = gson.fromJson(response, RemindBean.class);
            int code = remindBean.getCode();
            if (code == 200) {

                List<RemindBean.Message> result1 = remindBean.getMessage();
                if (result1.size() != 0) {
                    isshow = true;
                    rl.setVisibility(View.GONE);
                    listview.setVisibility(View.VISIBLE);
                }
                Log.e("liuyongzhen",response);
                adapter = new RemindAdapter(getApplication(), result1);
                listview.setAdapter(adapter);

            }
        }

    };

    @Override
    protected void onResume() {

        if (isshow == false) {
            rl.setVisibility(View.VISIBLE);
            listview.setVisibility(View.GONE);
        } else {
            rl.setVisibility(View.GONE);
            listview.setVisibility(View.VISIBLE);
            http();
        }
        super.onResume();
    }

}
