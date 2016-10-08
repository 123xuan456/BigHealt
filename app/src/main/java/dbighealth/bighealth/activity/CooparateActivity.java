package dbighealth.bighealth.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.R;
import okhttp3.Call;
import utils.UrlUtils;

/**
 * 医疗养生加盟
 */
public class CooparateActivity extends Activity implements View.OnClickListener {


    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tvTab)
    TextView tvTab;
    @Bind(R.id.right_add)
    ImageView rightAdd;
    @Bind(R.id.rb_medical)
    RadioButton rbMedical;
    @Bind(R.id.nourishing)
    RadioButton nourishing;
    @Bind(R.id.et_company)
    EditText etCompany;
    @Bind(R.id.tv_detail)
    EditText tvDetail;
    @Bind(R.id.tv_Tel)
    TextView tvTel;
    @Bind(R.id.et_tel)
    EditText etTel;
    @Bind(R.id.btn_cooperate)
    Button btnCooperate;
    @Bind(R.id.rg_yiliaoyangsheng)
    RadioGroup rgYiliaoyangsheng;

    //选择医疗or养生
    String yiliaoyangsheng;
    String company;//名称内容
    String details;//详情
    String tels;//电话
    boolean cancel = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperate);
        ButterKnife.bind(this);
        btnCooperate.setOnClickListener(this);
        arrowLeft.setOnClickListener(this);
        rightAdd.setVisibility(View.INVISIBLE);
        tvTab.setText("医疗养生");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            case R.id.btn_cooperate:

                //判断点击按钮
                if(rbMedical.isChecked()){
                    Log.i("mhysa","点击了医疗");
                }else if(nourishing.isChecked()){
                    Log.i("mhysa","点击了养生");
                }
                yiliaoyangsheng =rbMedical.isChecked() ? "医疗" : "养生";
                company = etCompany.getText().toString().trim();
                details = tvDetail.getText().toString().trim();
                tels = etTel.getText().toString().trim();
                if (TextUtils.isEmpty(company)){
                    Toast.makeText(this, "公司名称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(details)){
                    Toast.makeText(this, "详情不能为空", Toast.LENGTH_SHORT).show();
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
                    OkHttpUtils.get().url(UrlUtils.LEAGUE)
                            .addParams("type", yiliaoyangsheng)
                            .addParams("title", company)
                            .addParams("content", details)
                            .addParams("information", tels)
                            .build()
                            .execute(MyStringCallBack);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private boolean tel(String tel) {
       /* Pattern p = Pattern
                .compile("^((17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(tel);
        System.out.println(m.matches() + "---");
        return m.matches();*/

        String telRegex = "13\\d{9}|14[57]\\d{8}|15[012356789]\\d{8}|18[01256789]\\d{8}|17[0678]\\d{8}";
              if (TextUtils.isEmpty(tel)) return false;
             else return tel.matches(telRegex);
    }

    private StringCallback MyStringCallBack = new StringCallback(){


        @Override
        public void onError(Call call, Exception e, int id) {

            Toast.makeText(getApplicationContext(), "加盟失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResponse(String response, int id) {
            Toast.makeText(getApplicationContext(),"加盟成功！！",Toast.LENGTH_SHORT).show();
            finish();
        }
    };

}
