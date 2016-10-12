package dbighealth.bighealth.activity;

import android.app.Activity;
import android.os.Bundle;
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
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.CollectionAdapter;
import dbighealth.bighealth.bean.ColllectionBean;
import okhttp3.Call;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;

/**
 * 我的收藏
 */
public class CollectionActivity extends Activity implements View.OnClickListener{

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tit)
    TextView tit;
    @Bind(R.id.right_tv)
    TextView rightTv;
    @Bind(R.id.lv_collection)
    ListView lvCollection;

    private int COLLECTION=101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        arrowLeft.setOnClickListener(this);
        tit.setText("我的收藏");
        rightTv.setText("编辑");
        rightTv.setOnClickListener(this);
        InitInternet();
    }

    /**
     * 请求网络接口
     */
   public void InitInternet(){
        OkHttpUtils.get()
                   .url(UrlUtils.COLLECTIONSHOW)
                   .addParams("userId", SharedPreferencesUtils.getString(getApplicationContext(),UrlUtils.LOGIN,""))
                   .id(COLLECTION)
                   .build()
                   .execute(MyStringCallBack);
    }
    StringCallback MyStringCallBack = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {
            if(id==COLLECTION){

            }
        }

        @Override
        public void onResponse(String response, int id) {
            if(id==COLLECTION){
                Gson  gson = new Gson();
                ColllectionBean colllectionBean = gson.fromJson(response, ColllectionBean.class);
                List<ColllectionBean.MessageBean> message = colllectionBean.getMessage();
                CollectionAdapter collectionAdapter = new CollectionAdapter(getApplicationContext(),message);
                lvCollection.setAdapter(collectionAdapter);
            }
        }
    };
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.arrow_left:
                finish();
                break;
            //编辑
            case R.id.right_tv:
                break;
        }
    }
}
