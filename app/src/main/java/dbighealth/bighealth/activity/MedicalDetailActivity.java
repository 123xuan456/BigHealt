package dbighealth.bighealth.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.MultipleItemAdapter;
import dbighealth.bighealth.bean.CompanyDetail;
import okhttp3.Call;
import utils.UrlUtils;

public class MedicalDetailActivity extends AppCompatActivity {

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tit)
    TextView tit;
    @Bind(R.id.right_tv)
    TextView rightTv;
    @Bind(R.id.rv_detail_medical)
    RecyclerView rvDetailMedical;
    private int imgId;
    private int ITEM_SELECTED = 1;
    @TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail2);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvDetailMedical.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        imgId = intent.getIntExtra("imgId", 0);

    }

    public void InitInternet(){
        OkHttpUtils.get()
                .url(UrlUtils.DETAIL_TREATMENT)
                .id(ITEM_SELECTED)
                .addParams("picId", String.valueOf(imgId))
                .build()
                .execute(MyStringCallBack);
    }

    public StringCallback MyStringCallBack = new StringCallback(){

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
        @Override
        public void onResponse(String response, int id) {

            if(id==ITEM_SELECTED) {
                Gson gson = new Gson();
                CompanyDetail companyDetail = gson.fromJson(response, CompanyDetail.class);
                if (companyDetail.getCode() == 200){
                    String name = companyDetail.getMessage().getName();
                    String address = companyDetail.getMessage().getAddress();
                    String telephone = companyDetail.getMessage().getTelephone();
                    String size = companyDetail.getMessage().getSize();
                    String bigPic = companyDetail.getMessage().getBigPic();
                    List<CompanyDetail.MessageBean.LittlePicBean> littlePic = companyDetail.getMessage().getLittlePic();
                    MultipleItemAdapter multipleItemAdapter = new MultipleItemAdapter(getApplicationContext(),littlePic,name,address,telephone,size,bigPic);
                    rvDetailMedical.setAdapter(multipleItemAdapter);
                }
            }

        }
    };
}
