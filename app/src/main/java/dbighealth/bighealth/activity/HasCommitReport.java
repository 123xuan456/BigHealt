package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.HasCommitBean;
import dbighealth.bighealth.imageUtils.Bimp1;
import okhttp3.Call;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;

public class HasCommitReport extends Activity implements View.OnClickListener{

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tit)
    TextView tit;
    @Bind(R.id.right_tv)
    TextView rightTv;
    @Bind(R.id.tv_reportTitle)
    TextView tvReportTitle;
    @Bind(R.id.grid_view1)
    GridView gridView1;

    private int SEARCH =101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_report);
        ButterKnife.bind(this);
        arrowLeft.setOnClickListener(this);
        rightTv.setOnClickListener(this);
        rightTv.setText("重新上传");
        tvReportTitle.setText("您上传的体检报告");
    }

    /**
     * 加载网络
     * @param
     */
    public void initIntenet(){
        String userid = SharedPreferencesUtils.getString(this, UrlUtils.LOGIN, "");
        OkHttpUtils.get()
                   .url(UrlUtils.SEARCHREPORT)
                   .id(SEARCH)
                   .addParams("userId",userid)
                   .build()
                   .execute(MyStringCallBack);
    }
    StringCallback MyStringCallBack = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {

            Gson gson = new Gson();
            HasCommitBean hasCommit = gson.fromJson(response,HasCommitBean.class);
            int code = hasCommit.getCode();
            if(code==200){
                List<HasCommitBean.UrlsBean> urls = hasCommit.getUrls();
            }

        }
    };
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.arrow_left:
                finish();
              break;
            case R.id.right_tv:

                Intent intent = new Intent(HasCommitReport.this,MedicalReportActivity.class);
                startActivity(intent);
                break;
        }
    }
}
