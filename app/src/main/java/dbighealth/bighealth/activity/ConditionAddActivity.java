package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import okhttp3.Call;
import utils.UrlUtils;

/**
 * 每日二级页面
 */
public class ConditionAddActivity extends Activity implements View.OnClickListener {


    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tit)
    TextView tit;
    @Bind(R.id.right_tv)
    TextView rightTv;
    @Bind(R.id.textView25)
    TextView textView25;
    @Bind(R.id.textView26)
    TextView textView26;
    @Bind(R.id.breakfast)
    EditText breakfast;
    @Bind(R.id.textView27)
    TextView textView27;
    @Bind(R.id.breakfasttime)
    EditText breakfasttime;
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;
    @Bind(R.id.textView28)
    TextView textView28;
    @Bind(R.id.lunchsize)
    EditText lunchsize;
    @Bind(R.id.textView29)
    TextView textView29;
    @Bind(R.id.textView30)
    TextView textView30;
    @Bind(R.id.lunchtime)
    EditText lunchtime;
    @Bind(R.id.linearLayout2)
    LinearLayout linearLayout2;
    @Bind(R.id.textView31)
    TextView textView31;
    @Bind(R.id.dinnersize)
    EditText dinnersize;
    @Bind(R.id.textView32)
    TextView textView32;
    @Bind(R.id.textView33)
    TextView textView33;
    @Bind(R.id.dinnertime)
    EditText dinnertime;
    @Bind(R.id.l1)
    LinearLayout l1;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.addsize)
    EditText addsize;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.addtime)
    EditText addtime;
    private String bf;
    private String bft;
    private String ls;
    private String lt;
    private String as;
    private String at;
    private String ds;
    private String dt;
    private String id;
    private  static int Condition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition_add);
        ButterKnife.bind(this);
        id= BaseApplication.userid;
        rightTv.setOnClickListener(this);
        arrowLeft.setOnClickListener(this);

    }
    //拿到用户输入的数据
    public String submit() {
       getUserData();
        JSONObject obj=new JSONObject();
        try {
            obj.put("userId",id);
            obj.put("breakfastsize",bf);
            obj.put("breakfasttime",bft);
            obj.put("lunchsize",ls);
            obj.put("lunchtime",lt);
            obj.put("addsize",as);
            obj.put("addtime",at);
            obj.put("dinnersize",ds);
            obj.put("dinnertime",dt);

            return obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    private void getUserData() {
        bf=breakfast.getText().toString();
        bft=breakfasttime.getText().toString();
        ls=lunchsize.getText().toString();
        lt=lunchtime.getText().toString();
        as=addsize.getText().toString();
        at=addtime.getText().toString();
        ds=dinnersize.getText().toString();
        dt=dinnertime.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            case R.id.right_tv:
                getUserData();//先判断用户输入的数据
                if(TextUtils.isEmpty(bf)||TextUtils.isEmpty(bft)||
                        TextUtils.isEmpty(ls)||TextUtils.isEmpty(lt)||
                        TextUtils.isEmpty(as)||TextUtils.isEmpty(at)||
                        TextUtils.isEmpty(ds)||TextUtils.isEmpty(dt)
                        ){
                    Toast.makeText(getApplicationContext(),"信息填写不全",Toast.LENGTH_SHORT).show();
                }else{
                    String s = submit();
                    System.out.println("报存传递s="+s);
                    OkHttpUtils.postString()
                            .url(UrlUtils.CONDITIONADD)
                            .content(submit())//吧用户输入的数据传给服务器
                            .id(Condition)
                            .build()
                            .execute(new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    System.out.println("今日健康失败"+e);
                                    Toast.makeText(getApplicationContext(),"保存失败,请稍后提交",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    System.out.println("今日健康成功"+response);
                                    Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent();
                                    // 返回intent
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            });
                }
                break;


        }
    }
}
