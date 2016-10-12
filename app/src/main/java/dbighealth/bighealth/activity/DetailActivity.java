package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.ItemDetailAdapter;
import dbighealth.bighealth.bean.CompanyDetail;
import dbighealth.bighealth.view.NoScrollListview;
import okhttp3.Call;
import utils.UrlUtils;

public class DetailActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tit)
    TextView tit;
    @Bind(R.id.right_tv)
    TextView rightTv;
    @Bind(R.id.iv_detail)
    ImageView ivDetail;
    @Bind(R.id.tv_companyName)
    TextView tvCompanyName;
    @Bind(R.id.tv_companyAddress)
    TextView tvCompanyAddress;
    @Bind(R.id.tv_companyTel)
    TextView tvCompanyTel;
    @Bind(R.id.tv_companyArea)
    TextView tvCompanyArea;
    @Bind(R.id.lv_companyDescribe)
    NoScrollListview lvCompanyDescribe;
    /*    @Bind(R.id.lv_companyDescribe)
        ListView lvCompanyDescribe;*/
    private int imgId;
    private int ITEM_SELECTED = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        arrowLeft.setOnClickListener(this);
        tit.setText("详情");
        rightTv.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        imgId = intent.getIntExtra("imgId", 0);
        initInternet();
    }

    /**
     * 请求网络
     *
     * @param
     */
    public void initInternet() {

        Log.i("mhysa-->", UrlUtils.DETAIL_TREATMENT + "?picId=" + imgId);
        OkHttpUtils.get()
                .url(UrlUtils.DETAIL_TREATMENT)
                .id(ITEM_SELECTED)
                .addParams("picId", String.valueOf(imgId))
                .build()
                .execute(MyStringCallBack);

    }

    public StringCallback MyStringCallBack = new StringCallback() {

        @Override
        public void onError(Call call, Exception e, int id) {


        }

        @Override
        public void onResponse(String response, int id) {


            if (id == ITEM_SELECTED) {
                Gson gson = new Gson();
                CompanyDetail companyDetail = gson.fromJson(response, CompanyDetail.class);
                if (companyDetail.getCode() == 200) {
                    String name = companyDetail.getMessage().getName();
                    String address = companyDetail.getMessage().getAddress();
                    String telephone = companyDetail.getMessage().getTelephone();
                    String size = companyDetail.getMessage().getSize();
                    String bigPic = companyDetail.getMessage().getBigPic();
                    List<CompanyDetail.MessageBean.LittlePicBean> littlePic = companyDetail.getMessage().getLittlePic();
                    Glide.with(getApplicationContext())
                            .load(bigPic)
                            .placeholder(R.mipmap.home)
                            .error(R.mipmap.home)
                            .centerCrop()
                            .crossFade()
                            .into(ivDetail);
                    tvCompanyName.setText("名称：" + name);
                    tvCompanyAddress.setText("地址：" + address);
                    tvCompanyTel.setText("电话：" + telephone);
                    tvCompanyArea.setText("可容纳：" + size + "人");
                    ItemDetailAdapter itemdetailadapter = new ItemDetailAdapter(getApplicationContext(), littlePic);
                    lvCompanyDescribe.setAdapter(itemdetailadapter);
                }
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_left:
                finish();
                break;
        }
    }

}
