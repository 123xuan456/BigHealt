package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.DetailsMessageAdapter;
import dbighealth.bighealth.bean.DetailsMessageBean;
import okhttp3.Call;
import utils.UrlUtils;

//资讯详情
public class Details_MessageActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.right_tv)
    TextView rightTv;

    @Bind(R.id.et_content)
    EditText etContent;
    private TextView textView7, textView10;
    private ListView listview;
    DetailsMessageAdapter adapter;
    private EditText et_content;
    private TextView textView8;
    private int itemid;
    private int userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_details);
        ButterKnife.bind(this);
        initView();
        Intent intent = getIntent();
        itemid = intent.getIntExtra("itemid",0);
        userid = intent.getIntExtra("userid",0);
        Log.i("mhysa","itemid++++"+itemid);
        init();
        arrowLeft.setOnClickListener(this);

    }

    public void initView() {

        textView7 = (TextView) findViewById(R.id.textView7);//问题描述
        textView10 = (TextView) findViewById(R.id.textView10);//得到的帮助
        listview = (ListView) findViewById(R.id.listview);
        et_content = (EditText) findViewById(R.id.et_content);//追问输入框
        textView8 = (TextView) findViewById(R.id.textView8);//追问按钮
        rightTv.setVisibility(View.INVISIBLE);
        textView8.setOnClickListener(this);

    }

    public void initDate() {

        String addQuest = et_content.getText().toString();

        if (TextUtils.isEmpty(addQuest)) {
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("problemId", itemid);
            jsonObject.put("addQuest", addQuest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String u = UrlUtils.INFORMATION_DETAILES;
        OkHttpUtils.postString()
                .url(u)//内容
                .content(jsonObject.toString())
                .id(2)
                .build()
                .execute(MyStringCallBack);

    }


    public void init() {

        String a = UrlUtils.INFORMATION_DETAILES_LISTVIEW;//listview
        Log.i("mhysa","咨詢頁的接口："+a+"?problemId="+itemid+"&userId="+userid);
        OkHttpUtils.get()
                .url(a)
                .addParams("problemId", String.valueOf(itemid))
                .addParams("userId", String.valueOf(userid))
                .id(1)
                .build()
                .execute(MyStringCallBack);
    }

    public StringCallback MyStringCallBack = new StringCallback() {

        @Override
        public void onError(Call call, Exception e, int id) {

            Log.e("mhysa-->", "加载失败");
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {

                case 1:
                    Gson gson = new Gson();
                    DetailsMessageBean detailsMessageBean = gson.fromJson(response, DetailsMessageBean.class);
                    int code = detailsMessageBean.getCode();
                    if (code == 200) {
                        //problem
                        textView7.setText(detailsMessageBean.getProblem());
                        textView10.setText(detailsMessageBean.getGetHelp());
                        List<DetailsMessageBean.Message> result1 = detailsMessageBean.getMessage();
                        adapter = new DetailsMessageAdapter(getApplication(), result1);
                        listview.setAdapter(adapter);
                    }

                    break;
                case 2:
                    Toast.makeText(getApplication(), "提交成功!", Toast.LENGTH_LONG)
                            .show();
                    init();
                    break;

            }


        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.textView8:
                initDate();
                init();
                et_content.setText("");
                break;

         case R.id.arrow_left:
             finish();
           break;

        }
    }
}
