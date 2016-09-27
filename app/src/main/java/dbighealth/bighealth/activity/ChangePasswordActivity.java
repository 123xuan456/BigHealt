package dbighealth.bighealth.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import okhttp3.Call;
import utils.UrlUtils;

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
    @Bind(R.id.button3)
    Button button3;
    private String password;
    private String regphone;
    private String phone;
    private String et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        tvTab.setText("修改密码");
        rightAdd.setVisibility(View.GONE);

        password=BaseApplication.password;
        regphone=BaseApplication.regphone;

    }

    @OnClick({R.id.arrow_left, R.id.button3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            case R.id.button3:
                ifInput();
                break;
        }
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }
    private void ifInput() {
        phone = editText.getText().toString();
        et = editText1.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
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
        if (!isPasswordValid(et)) {
            Toast.makeText(this, "密码不能低于6位", Toast.LENGTH_SHORT).show();
            return;
        }else {
            JSONObject jsonObject = new JSONObject();
            try {
                        jsonObject.put("regphone",regphone);
                        jsonObject.put("password",password);
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
                    }
                });
                } catch (JSONException e) {
                e.printStackTrace();
            }

        }



    }
}
