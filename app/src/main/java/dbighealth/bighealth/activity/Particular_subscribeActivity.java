package dbighealth.bighealth.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dbighealth.bighealth.R;
import okhttp3.Call;
import utils.UrlUtils;

public class Particular_subscribeActivity extends Activity implements View.OnClickListener {

    TextView tvTab;
    ImageView rightAdd;
    ImageView arrowLeft;
    Button btnquiz;

    private RadioGroup rg_yiliaoyangsheng;
    private EditText tv_detail,et_tel;
    private RadioButton rbmedical;

    String yiliaoyangsheng;//选择医疗or养生
    String genre;//类型
    String tels;//电话
    boolean cancel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_subscribe);
        btnquiz = (Button)findViewById(R.id.btn_quiz);//预约按钮
        btnquiz.setOnClickListener(this);
        arrowLeft = (ImageView)findViewById(R.id.arrow_left);
        arrowLeft.setOnClickListener(this);
        rightAdd =(ImageView)findViewById(R.id.right_add);
        rightAdd.setVisibility(View.INVISIBLE);
        tvTab = (TextView)findViewById(R.id.tvTab);
        tvTab.setText("在线预约");
        rbmedical = (RadioButton)findViewById(R.id.rb_medical);

        rg_yiliaoyangsheng = (RadioGroup)findViewById(R.id.rg_yiliaoyangsheng);
        tv_detail = (EditText)findViewById(R.id.tv_detail);//预约类型
        et_tel = (EditText)findViewById(R.id.et_tel);//电话


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrow_left:
                finish();
            break;

            case R.id.btn_quiz:

                yiliaoyangsheng =rbmedical.isChecked() ? "医疗" : "养生";
                genre = tv_detail.getText().toString().trim();
                tels = et_tel.getText().toString().trim();
                if (TextUtils.isEmpty(genre)){
                    Toast.makeText(this, "预约类型不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(tels)){
                    Toast.makeText(this, "联系方式不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!tel(tels)){
                    Toast.makeText(this, "请正确输入联系方式", Toast.LENGTH_SHORT).show();
                    cancel=true;
                    return;
                }

                try {
                    OkHttpUtils.get().url(UrlUtils.SUBSCRIBE_PARTICULAR)
                            .addParams("title", yiliaoyangsheng)
                            .addParams("content", genre)
                            .addParams("inform", tels)
                            .build()
                            .execute(MyStringCallBack);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            break;
        }
    }

    private boolean tel(String tel) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(tel);
        System.out.println(m.matches() + "---");
        return m.matches();
    }

    private StringCallback MyStringCallBack = new StringCallback(){


        @Override
        public void onError(Call call, Exception e, int id) {

            Toast.makeText(getApplicationContext(), "失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            Toast.makeText(getApplicationContext(),"预约成功！！",Toast.LENGTH_SHORT).show();
            finish();
        }
    };
}
