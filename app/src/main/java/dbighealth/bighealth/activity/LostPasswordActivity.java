package dbighealth.bighealth.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.LostPasswordBean;
import dbighealth.bighealth.bean.LostpwSubmitBean;
import okhttp3.Call;
import utils.UrlUtils;

/**
 * 忘记密码
 */
public class LostPasswordActivity extends Activity implements View.OnClickListener {

    private static int LOSTPASSWORD_CODE = 1;
    private static int SUBMIT = 2;
    @Bind(R.id.tvTab)
    TextView tvTab;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.phone_edit)
    EditText phoneEdit;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.btn_verification)
    TextView btnVerification;
    @Bind(R.id.et1)
    EditText et1;
    @Bind(R.id.new_password_edit)
    EditText newPasswordEdit;
    @Bind(R.id.linearLayout1)
    LinearLayout linearLayout1;
    @Bind(R.id.new_register_btn)
    TextView newRegisterBtn;
    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.right_add)
    ImageView rightAdd;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    private String phone;
    private String et;
    private String new_password_edit;
    String verification;
    private TimeCount time;//验证码倒计时
    LostpwSubmitBean l;
    private boolean isHidden=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_lost_password);
        ButterKnife.bind(this);
     //   ifInput();
        rightAdd.setVisibility(View.GONE);
        tvTab.setText("忘记密码");
        btnVerification.setOnClickListener(this);
        newRegisterBtn.setOnClickListener(this);
        arrowLeft.setOnClickListener(this);
        checkBox.setOnClickListener(this);
        time = new TimeCount(60000, 1000);
    }

    private void ifInput() {
        phone = phoneEdit.getText().toString();
        et = et1.getText().toString();
        new_password_edit = newPasswordEdit.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (!isEmailValid(phone)) {//设置手机格式
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(et)) {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(new_password_edit)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!verification.equals(et)) {
            Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
            return;
        }else if (verification.equals(et)){
            submit();
            return;
        }
    }

    private boolean isEmailValid(String email) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(email);
        System.out.println(m.matches() + "---");
        return m.matches();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_verification://点击获取验证码
                phone = phoneEdit.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!isEmailValid(phone)) {//设置手机格式
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                time.start();// 开始计时
                sendVerification();

                break;
            case R.id.new_register_btn://完成
                ifInput();

                break;
            case R.id.arrow_left://
               finish();

                break;
            case R.id.checkBox:
            if (isHidden) {
                //设置EditText文本为可见的
                newPasswordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //设置EditText文本为隐藏的
                newPasswordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            isHidden = !isHidden;
                newPasswordEdit.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = newPasswordEdit.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                break;

        }
    }

    private void submit() {
        String url = UrlUtils.SUBMIT;
        OkHttpUtils.get().url(url).id(SUBMIT)
                .addParams("password", new_password_edit)
                .addParams("regphone", phone)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                System.out.println("忘记密码提交=" + response);
                Gson g = new Gson();

                l = g.fromJson(response, LostpwSubmitBean.class);
                String s = l.getCode();
                if (Integer.parseInt(s) == 200) {
                    String hint = l.getHint();
                    Toast.makeText(getApplicationContext(), hint, Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });


    }

    private void sendVerification() {
        String url = UrlUtils.LOSTPASSWORD_CODE;
        OkHttpUtils.get().url(url).id(LOSTPASSWORD_CODE)
                .addParams("regphone", phone)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                System.out.println("忘记密码验证码" + response);
                Gson g = new Gson();
                LostPasswordBean lpb = g.fromJson(response, LostPasswordBean.class);
                String s = lpb.getCode();
                if (Integer.parseInt(s) == 200) {
                    String hint = lpb.getHint();
                    verification = lpb.getVerification();
                    Toast.makeText(getApplicationContext(), hint, Toast.LENGTH_LONG).show();
                }else if(Integer.parseInt(s) == 400){
                    String hin = lpb.getHint();
                    Toast.makeText(getApplicationContext(), hin, Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            btnVerification.setText("重新获取验证码");
            btnVerification.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            btnVerification.setClickable(false);//防止重复点击
            btnVerification.setText(millisUntilFinished / 1000 + "s");
        }
    }

}
