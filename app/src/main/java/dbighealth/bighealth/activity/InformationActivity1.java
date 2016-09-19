package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.InformationAdapter1;
import dbighealth.bighealth.bean.InformationBean;
import okhttp3.Call;

public class InformationActivity1 extends Activity implements View.OnClickListener{

    private ListView recyclerView;
    private ImageView arrow_left;
    private TextView right_tv;
    private LinearLayoutManager linearLayoutManager;
    InformationAdapter1 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
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
        right_tv.setOnClickListener(this);

    }

    public void init(){
        String a = "http://192.168.0.120:8081/JianKangChanYe/advice/advicelist?userId=2";
        OkHttpUtils.get()
                .url(a)
                .build()
                .execute(MyStringCallBack);
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
                List<InformationBean.Message> result1 = informationBean.getMessage();
                Log.e("liuliuliu",result1.toString());
                adapter = new InformationAdapter1(getApplication(), result1);
                //  Log.e("mhysa",infoAdapter.toString());
//                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                Log.i("mhysa-->",recyclerView.toString()+"recyclerview长度");
                recyclerView.setAdapter(adapter);
                recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     //   Intent intent = new Intent();
                        Toast.makeText(getApplication(),"打印的item值："+position,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(),Details_MessageActivity.class);
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

                break;
            case R.id.arrow_left:
                finish();
                break;
        }
    }
}
