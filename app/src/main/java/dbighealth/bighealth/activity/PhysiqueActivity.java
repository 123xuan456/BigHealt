package dbighealth.bighealth.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.PhysicalAdapter;
import dbighealth.bighealth.bean.PhysicalBean;
import dbighealth.bighealth.nozzle.UltraRefreshListener;
import dbighealth.bighealth.view.CustomUltraRefreshHeader;
import dbighealth.bighealth.view.UltraRefreshListView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import okhttp3.Call;
import utils.UrlUtils;

/**
 * created by mhysa
 * 我的体质
 */
public class PhysiqueActivity extends Activity implements View.OnClickListener, UltraRefreshListener {


    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.textView6)
    TextView textView6;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.view1)
    View view1;
    /*  @Bind(R.id.listview)
      ListView listview;*/
    @Bind(R.id.pr_commit)
    PercentRelativeLayout prCommit;
    @Bind(R.id.ultra_ptr)
    PtrClassicFrameLayout ultraPtr;
    @Bind(R.id.ultra_lv)
    UltraRefreshListView ultraLv;
    @Bind(R.id.btn_consititution_commit)
    Button btnConsititutionCommit;
    @Bind(R.id.tit)
    TextView tit;
    @Bind(R.id.right_tv)
    TextView rightTv;
    private int SYMPTOM = 1;
    private PhysicalAdapter physicalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mine_constitution);
        ButterKnife.bind(this);
        tit.setText("我的体质");
        rightTv.setText("保存");
        //创建我们的自定义头部视图
        CustomUltraRefreshHeader header = new CustomUltraRefreshHeader(this);

        //设置头部视图
        ultraPtr.setHeaderView(header);

        //设置视图修改的回调，因为我们的CustomUltraRefreshHeader实现了PtrUIHandler
        ultraPtr.addPtrUIHandler(header);

        //设置数据刷新的会回调，因为UltraRefreshListView实现了PtrHandler
        ultraPtr.setPtrHandler(ultraLv);

//        mAdapter = new SimpleBaseAdapter(datas);
//
//        mLv.setAdapter(mAdapter);
//
//        //设置数据刷新回调接口
        ultraLv.setUltraRefreshListener(this);

        InitInternet();
    }

    /**
     * 网络请求
     */
    public void InitInternet() {
        OkHttpUtils.get()
                .url(UrlUtils.Symptom)
                .id(SYMPTOM)
                .addParams("id", "1")
                .build()
                .execute(MyStringCallBack);
    }

    private StringCallback MyStringCallBack = new StringCallback() {


        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("mhysa--->", "体质请求失败");
        }

        @Override
        public void onResponse(String response, int id) {

            Log.e("mhysa--->", "体质请求成功" + response);
            if (id == SYMPTOM) {
                Gson gson = new Gson();
                PhysicalBean physicalBean = gson.fromJson(response, PhysicalBean.class);
                int code = physicalBean.getCode();
                List<PhysicalBean.ContentBean.ResultBean> result = physicalBean.getContent().getResult();
                physicalAdapter = new PhysicalAdapter(getApplicationContext(), result);
                // listview.setAdapter(physicalAdapter);
                ultraLv.setAdapter(physicalAdapter);
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

    @Override
    public void onRefresh() {
        ultraPtr.postDelayed(new Runnable() {
            @Override
            public void run() {
//                datas.clear();
//                for(int i = 0;i<20;i++){
//                    datas.add("添加了数据~~"+i);
//                }
                //刷新完成

                Toast.makeText(getApplicationContext(), "刷新完成", Toast.LENGTH_SHORT).show();
                ultraLv.refreshComplete();
                physicalAdapter.notifyDataSetChanged();
            }
        }, 1000);
    }

    @Override
    public void addMore() {
        ultraPtr.postDelayed(new Runnable() {
            @Override
            public void run() {
//                datas.clear();
//                for(int i = 0;i<20;i++){
//                    datas.add("添加了数据~~"+i);
//                }
                //刷新完成

                Toast.makeText(getApplicationContext(), "刷新完成", Toast.LENGTH_SHORT).show();
                ultraLv.refreshComplete();
                physicalAdapter.notifyDataSetChanged();
            }
        }, 1000);
    }
}
