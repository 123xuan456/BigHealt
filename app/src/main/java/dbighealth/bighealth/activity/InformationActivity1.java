package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.InformationAdapter1;
import dbighealth.bighealth.bean.InformationBean;
import dbighealth.bighealth.imageUtils.BaseActivity;
import okhttp3.Call;
import utils.UrlUtils;
/*
* 我的咨询页
* */
public class InformationActivity1 extends Activity implements View.OnClickListener{

    private static final int INFORMATION_CODE =1 ;
    private ListView recyclerView;
    private ImageView arrow_left;
    private TextView right_tv;
    private LinearLayoutManager linearLayoutManager;
    InformationAdapter1 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        BaseActivity.activityList.add(this);
        initView();
        init();
    }
    public void initView() {
        recyclerView = (ListView)findViewById(R.id.recyclerView);
        arrow_left = (ImageView)findViewById(R.id.arrow_left);
        right_tv = (TextView)findViewById(R.id.right_tv);
        linearLayoutManager = new LinearLayoutManager(getApplication());
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        arrow_left.setOnClickListener(this);
        right_tv.setText("我要咨询");
        right_tv.setOnClickListener(this);

    }

    public void init(){
        String id = BaseApplication.userid;
//        if (TextUtils.isEmpty(id)){
//            Toast.makeText(getApplication(), "请先登录", Toast.LENGTH_LONG)
//                    .show();
//            return;
//        }else{
            OkHttpUtils.get()
                    .url(UrlUtils.INFORMATIONLISTVIEW)
                    .addParams("userId",id)//id
                    .build()
                    .execute(MyStringCallBack);
//        }
    }
    public StringCallback MyStringCallBack=new StringCallback(){

        @Override
        public void onError(Call call, Exception e, int id) {

            Log.e("mhysa-->", "加载失败");
        }

        @Override
        public void onResponse(String response, int id) {

            Gson gson = new Gson();
            InformationBean informationBean = gson.fromJson(response, InformationBean.class);
            int code = informationBean.getCode();
            if (code == 200) {
                final List<InformationBean.Message> result1 = informationBean.getMessage();
                Log.e("liuliuliu",result1.toString());
                adapter = new InformationAdapter1(getApplication(), result1);
                Log.i("mhysa-->",recyclerView.toString()+"recyclerview长度");
                recyclerView.setAdapter(adapter);
                recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     //   Intent intent = new Intent();
                     //   Toast.makeText(getApplication(),"打印的item值："+result1.get(position).getProblemId(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(),Details_MessageActivity.class);
                        intent.putExtra("itemid",result1.get(position).getProblemId());
                        intent.putExtra("userid",result1.get(position).getUserId());
                        startActivity(intent);
                    }
                });

            }


        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_tv:
                Intent i=new Intent(getApplication(),Information_DetailsActivity.class);
                startActivity(i);
                // 开始一个新的 Activity等候返回结果
                startActivityForResult(i, INFORMATION_CODE);
                Log.i("芝麻开门", "过去!!!!");

                break;
            case R.id.arrow_left:
                finish();
                break;
        }
    }

    //处理从ConditionAddActivity返回的结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INFORMATION_CODE) {
            if (resultCode == RESULT_OK) {
                Log.i("芝麻开门", "回来!!!!");
                init();
            }
        }
    }

}
