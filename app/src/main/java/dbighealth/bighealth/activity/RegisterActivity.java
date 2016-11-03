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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.CodeBean;
import dbighealth.bighealth.bean.RegisterBean;
import okhttp3.Call;
import utils.ConfigUsers;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;

/**注册页面*/
public class RegisterActivity extends Activity implements View.OnClickListener{
    private static int REGISTER_CODE;//发送验证码id
    private static int REGISTER;//注册id
    private ImageView arrow_left,right_add;
    private TextView tvTab;
    private EditText nickname;
    private EditText phone;
    private EditText code;
    private EditText password1,password2;
    private TextView getcode;//获取验证码
    private Button register;
    public CodeBean codebean;
    String verification;
    private RegisterBean registerbean;
    private TimeCount time;//验证码倒计时
    private boolean isHidden=true;
    private CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setView();//初始化控件

    }

    private void attemptLogin() {
        System.out.println("verification="+verification);
        String nickname1 = nickname.getText().toString();
        System.out.println("nickname1=" + nickname1);
        final String phone1 = phone.getText().toString();
        System.out.println("phone1=" + phone1);
        String code1 = code.getText().toString();
        System.out.println("code1=" + code1);
        String password = password1.getText().toString();
        System.out.println("password=" + password);
        String passwor = password2.getText().toString();
        System.out.println("passwor=" + passwor);
        if (TextUtils.isEmpty(nickname1)) {
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(phone1)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }else
        if (!isEmailValid(phone1)) {//设置手机格式
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (TextUtils.isEmpty(code1)) {
            Toast.makeText(this, "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
         else if (!code1.equals(verification)) {
            Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
            return;
        }else
         if (!password.equals(passwor)){
            Toast.makeText(this,"两次密码不相同",Toast.LENGTH_SHORT).show();
             return;
       }else
        if (!isPasswordValid(password)) {//设置密码必须不少于6个
            Toast.makeText(this, "密码不能低于六位", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            String url = UrlUtils.REGISTER;
            OkHttpUtils.get().url(url).id(REGISTER)
                    .addParams("regphone", phone1)
                    .addParams("verification", code1)
                    .addParams("password", passwor)
                    .addParams("reguser", nickname1)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    System.out.println("传递注册手机号失败" + e);
                }

                @Override
                public void onResponse(String response, int id) {
                    System.out.println("注册手机号成功" + response);
                    SharedPreferencesUtils.saveString(getApplication(), ConfigUsers.USERPHONE, phone1);//把注册的手机存储到了sp中
                    Gson gson = new Gson();
                    registerbean = gson.fromJson(response, RegisterBean.class);
                    String c = registerbean.getCode();
                    int code = Integer.parseInt(c);
                    if (code == 200) {
                        String hint = registerbean.getHint();
                        Toast.makeText(getApplicationContext(), hint, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }


    }

    private boolean isEmailValid(String email) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(email);
        System.out.println(m.matches() + "---");
        return m.matches();
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }

    private void setView() {
        arrow_left=(ImageView)findViewById(R.id.arrow_left);
        arrow_left.setOnClickListener(this);
        tvTab=(TextView)findViewById(R.id.tvTab);
        tvTab.setText("注册");
        right_add=(ImageView)findViewById(R.id.right_add);
        right_add.setVisibility(View.GONE);

        nickname = (EditText) findViewById(R.id.editText6);
        phone = (EditText) findViewById(R.id.editText7);
        code = (EditText) findViewById(R.id.editText8);
        password1 = (EditText) findViewById(R.id.editText9);
        password2 = (EditText) findViewById(R.id.editText10);
        getcode=(TextView)findViewById(R.id.button2);
        getcode.setOnClickListener(this);
        register=(Button)findViewById(R.id.button3);
        register.setOnClickListener(this);
        checkbox=(CheckBox)findViewById(R.id.checkBox);
        checkbox.setOnClickListener(this);

        time = new TimeCount(60000, 1000);
    }
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            getcode.setText("重新获取验证码");
            getcode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            getcode.setClickable(false);//防止重复点击
            getcode.setText(millisUntilFinished / 1000 + "s");
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.arrow_left:
                finish();
                break;
            case  R.id.button2:
                String nickname1 = nickname.getText().toString();
                String phone1 = phone.getText().toString();
                if (TextUtils.isEmpty(nickname1)) {
                    Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }else

                if (!isEmailValid(phone1)) {//设置手机格式
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    sendServer(phone1);//向服务器发送验证码
                    time.start();// 开始计时
                }
                break;
            case  R.id.button3:
                attemptLogin();//拿到用户输入的数据
                break;
            case R.id.checkBox:
                if (isHidden) {
                    //设置EditText文本为可见的
                    password1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    password2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    password1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    password2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                password1.postInvalidate();
                password2.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = password2.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                CharSequence charSequence1 = password1.getText();
                if (charSequence1 instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence1;
                    Selection.setSelection(spanText, charSequence1.length());
                }
                break;
        }
    }

    private void sendServer(String s) {
        String url= UrlUtils.REGISTER_CODE;
        OkHttpUtils.get().url(url).id(REGISTER_CODE)
                .addParams("regphone",s)
                .build().execute(new StringCallback() {


            @Override
            public void onError(Call call, Exception e, int id) {
                System.out.println("传递验证码失败"+e);
            }

            @Override
            public void onResponse(String response, int id) {
                System.out.println("传递验证码成功"+response);
                Gson gson = new Gson();
                codebean=gson.fromJson(response, CodeBean.class);
                String  c= codebean.getCode();
                int code=Integer.parseInt(c);
                if (code==200){//验证码发送成功
                    String res = codebean.getHint();
                    verification = codebean.getVerification();
                    Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                }else if(code==400){//发送失败
                    String res = codebean.getHint();
                    Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

}
