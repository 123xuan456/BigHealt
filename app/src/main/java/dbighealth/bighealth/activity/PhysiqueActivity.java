package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    private int SAVE_SYMPTON =2;
    private SharedPreferences sp;
    private int count =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mine_constitution);
        ButterKnife.bind(this);
        tit.setText("我的体质");
       // rightTv.setText("保存");
        rightTv.setVisibility(View.INVISIBLE);
        //创建我们的自定义头部视图
        CustomUltraRefreshHeader header = new CustomUltraRefreshHeader(this);

        //设置头部视图
        ultraPtr.setHeaderView(header);

        //设置视图修改的回调，因为我们的CustomUltraRefreshHeader实现了PtrUIHandler
        ultraPtr.addPtrUIHandler(header);

        //设置数据刷新的会回调，因为UltraRefreshListView实现了PtrHandler
        ultraPtr.setPtrHandler(ultraLv);

//
//        //设置数据刷新回调接口
        ultraLv.setUltraRefreshListener(this);

        arrowLeft.setOnClickListener(this);
        rightTv.setOnClickListener(this);
        btnConsititutionCommit.setOnClickListener(this);
        InitInternet();
    }

    /**
     * 网络请求
     */
    public void InitInternet() {
        OkHttpUtils.get()
                .url(UrlUtils.Symptom)
                .id(SYMPTOM)
                .addParams("id", String.valueOf(count))
                .build()
                .execute(MyStringCallBack);
    }

    private StringCallback MyStringCallBack = new StringCallback() {


        @Override
        public void onError(Call call, Exception e, int id) {
           // Log.e("mhysa--->", "体质请求失败");
            if(id==SYMPTOM){
                Toast.makeText(getApplicationContext(),"体质请求失败",Toast.LENGTH_SHORT).show();
            }
            if(id==SAVE_SYMPTON){
                Toast.makeText(getApplicationContext(),"体质测试提交失败",Toast.LENGTH_SHORT).show();
            }
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
            }if(id== SAVE_SYMPTON){
                Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();

                finish();
            }
        }
    };

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            //提交所有
            case R.id.btn_consititution_commit:
                int childCount = ultraLv.getChildCount();
                JSONObject jsonObject = new JSONObject();
                int FLAG =0;
                try {
                    jsonObject.put("userId",16);
                    for(int i=1;i<childCount+1;i++){
                        int id = getItemId(i-1);
                        if(id!=0){
                            FLAG =1;
                            jsonObject.put("symptomId_foreign"+i,i);
                            jsonObject.put("score"+i,id);
                        }

                    }
                    /**
                     * 提交接口
                     */
                if(FLAG==1){

                    OkHttpUtils.postString()
                               .url(UrlUtils.SAVESYMPTON)
                               .content(jsonObject.toString())
                               .id(SAVE_SYMPTON)
                               .build()
                               .execute(MyStringCallBack);

                    sp = getSharedPreferences("commit",
                            Activity.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putBoolean("First",true);
                    edit.commit();

                }else{
                    Toast.makeText(getApplicationContext(),"请填写信息后再提交！！！",Toast.LENGTH_SHORT).show();
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }

    }
    /**
     * 获取点击的itemId
     */
    public int getItemId(int itemIndex){

            RadioGroup rg_selector = (RadioGroup) ultraLv.getChildAt(itemIndex).findViewById(R.id.rg_selector);
            RadioButton btn_sometimes = (RadioButton) ultraLv.getChildAt(itemIndex).findViewById(R.id.btn_sometimes);
            RadioButton btn_rarely = (RadioButton) ultraLv.getChildAt(itemIndex).findViewById(R.id.btn_rarely);
            RadioButton often = (RadioButton) ultraLv.getChildAt(itemIndex).findViewById(R.id.often);
            RadioButton btn_no = (RadioButton) ultraLv.getChildAt(itemIndex).findViewById(R.id.btn_no);
           /* rg_selector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                }
            });*/
        return btn_no.isChecked()?1:(btn_rarely.isChecked()?2:(btn_sometimes.isChecked()?3:(often.isChecked()?4:0)));
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
                if(physicalAdapter!=null){
                    physicalAdapter.notifyDataSetChanged();
                }

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

              /*  Toast.makeText(getApplicationContext(), "刷新完成", Toast.LENGTH_SHORT).show();*/
                count++;
                ultraLv.refreshComplete();
                physicalAdapter.notifyDataSetChanged();
            }
        }, 200);
    }

}
