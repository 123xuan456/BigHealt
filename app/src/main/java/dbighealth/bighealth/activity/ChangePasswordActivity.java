package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.LostpwSubmitBean;
import dbighealth.bighealth.imageUtils.BaseActivity;
import okhttp3.Call;
import utils.UrlUtils;
/**
 * 修改密码
 * */
public class ChangePasswordActivity extends Activity {

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tvTab)
    TextView tvTab;
    @Bind(R.id.right_add)
    ImageView rightAdd;
    @Bind(R.id.textView58)
    TextView textView58;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.editText1)
    EditText editText1;
    @Bind(R.id.editText12)
    EditText editText12;
    @Bind(R.id.button3)
    Button button3;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    private String password;
    private String regphone;
    private String phone;
    private String et;
    private LostpwSubmitBean l;
    private String et1;
    private boolean isHidden=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        //吧次activity放到集合里，等到修改密码成功之后统一取消
        BaseActivity.activityList.add(this);
        tvTab.setText("修改密码");
        rightAdd.setVisibility(View.GONE);

        password=BaseApplication.password;//之前的密码
        regphone=BaseApplication.regphone;//手机号

    }

    @OnClick({R.id.arrow_left, R.id.button3,R.id.checkBox})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            case R.id.button3:
                ifInput();
                break;
            case R.id.checkBox:
                if (isHidden) {
                    //设置EditText文本为可见的
                    editText12.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editText1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    editText12.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editText1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                editText12.postInvalidate();
                editText1.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = editText12.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                CharSequence charSequence1 = editText1.getText();
                if (charSequence1 instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence1;
                    Selection.setSelection(spanText, charSequence1.length());
                }
                break;
        }
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }
    private void ifInput() {
        phone = editText.getText().toString();//旧密码
        et = editText1.getText().toString();//新密码
        et1 = editText12.getText().toString();//确认密码
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入原密码", Toast.LENGTH_SHORT).show();
            return;
        }else
        if (!isPasswordValid(et)) {
            Toast.makeText(this, "密码不能低于6位", Toast.LENGTH_SHORT).show();
            return;
        }else
        if (!password.equals(phone)) {
            Toast.makeText(this, "请输入正确密码", Toast.LENGTH_SHORT).show();
            return;
        }else

        if (TextUtils.isEmpty(et)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }else
        if (et.equals(password)) {
            Toast.makeText(this, "修改密码与原密码相同，修改失败", Toast.LENGTH_SHORT).show();
            return;
        }else
        if (!et.equals(et1)) {
            Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }else
         {
            JSONObject jsonObject = new JSONObject();
            try {
                        jsonObject.put("regphone",regphone);
                        jsonObject.put("password",et);
                String url= UrlUtils.CHANGPASSWORD;
                OkHttpUtils.postString().url(url)
                        .content(jsonObject.toString())
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("修改密码失败"+e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        System.out.println("修改密码"+response);
                        Gson g = new Gson();
                        l = g.fromJson(response, LostpwSubmitBean.class);
                        String s = l.getCode();
                        if (Integer.parseInt(s) == 200) {
                            BaseApplication.password="";
//                            BaseApplication.userid="";
                            BaseApplication.username="";
                            String hint = l.getHint();
                            Toast.makeText(getApplicationContext(), hint, Toast.LENGTH_LONG).show();

                            //修改密码成功之后，跳转到登录页面，并把之前的页面清除掉
                            for (int i = 0; i < BaseActivity.activityList.size(); i++) {
                                if (null != BaseActivity.activityList.get(i)) {
                                    BaseActivity.activityList.get(i).finish();
                                }
                            }
                            //通知我的页面把页面换成没有登录状态
                            Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                            intent.putExtra("username", "");
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

                            Intent i1=new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(i1);
                        }else if (Integer.parseInt(s) == 400){
                            String hint = l.getHint();
                            Toast.makeText(getApplicationContext(), hint, Toast.LENGTH_LONG).show();
                        }
                        
                    }
                });
                } catch (JSONException e) {
                e.printStackTrace();
            }

        }



    }
}
