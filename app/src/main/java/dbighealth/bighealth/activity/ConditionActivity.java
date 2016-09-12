package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.ConditionAdapter;
import dbighealth.bighealth.bean.ConditionBean;
import okhttp3.Call;
import utils.UrlUtils;

/**每日情况*/
public class ConditionActivity extends Activity implements View.OnClickListener{

    private static final int REQUEST_CODE = 1;
    private ListView listview;

    private ImageView arrow_left;
    private ImageView right_add;
    private TextView tvTab;
    private String userid;
    List<ConditionBean.MessageBean> message=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);
        listview=(ListView)findViewById(R.id.listView);
        userid=BaseApplication.userid;
        System.out.println("id="+userid);
        setView();
        getDate();

    }

    private void setView() {
        tvTab=(TextView)findViewById(R.id.tvTab);
        tvTab.setText("每日情况");
        arrow_left=(ImageView)findViewById(R.id.arrow_left);
        right_add=(ImageView)findViewById(R.id.right_add);
        right_add.setBackgroundResource(R.drawable.add_1);
        arrow_left.setOnClickListener(this);
        right_add.setOnClickListener(this);
    }

    public void getDate() {
       String url= UrlUtils.CONDITION+userid;//测试id用1，到时候把userid换过来就行
        OkHttpUtils.get().url(url)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                System.out.println("每日健康"+e);
            }

            @Override
            public void onResponse(String response, int id) {
                System.out.println("每日健康"+response);
                Gson g=new Gson();
                ConditionBean cb = g.fromJson(response, ConditionBean.class);
                if (cb.getCode()==200){
                   message = cb.getMessage();
                    ConditionAdapter adapter=new ConditionAdapter(getApplicationContext(),message);
                    listview.setAdapter(adapter);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrow_left:
                finish();
                break;
            case R.id.right_add:
                Intent i=new Intent(this,ConditionAddActivity.class);
                // 开始一个新的 Activity等候返回结果
                startActivityForResult(i, REQUEST_CODE);
                Log.i("芝麻开门", "过去!!!!");

        }
    }
    //处理从ConditionAddActivity返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.i("芝麻开门", "回来!!!!");
                getDate();
                }
            }
    }
}
