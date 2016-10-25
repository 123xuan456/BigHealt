package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dbighealth.bighealth.R;
import dbighealth.bighealth.bean.City;
import okhttp3.Call;
import utils.ConfigUsers;
import utils.SharedPreferencesUtils;
import utils.UrlUtils;

/**
 * 添加收货地址
 */
public class AddSiteActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tit)
    TextView tit;
    @Bind(R.id.right_tv)
    TextView rightTv;
    @Bind(R.id.et_shouhuoren)
    EditText etShouhuoren;
    @Bind(R.id.et_Mobile)
    EditText etMobile;
    @Bind(R.id.tv_city1)
    TextView tvCity1;
    @Bind(R.id.et_xiangxidizhi)
    EditText etXiangxidizhi;
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    private TextView tv_city1;
    private City city;
    private ArrayList<City> toCitys;
    String name, phone,area,//城市
            street,//街道
            address;//详细地址
    private String userid;
    private String defaults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_site);
        ButterKnife.bind(this);
        tv_city1 = (TextView) findViewById(R.id.tv_city1);
        tit.setText("添加新地址");
        tv_city1.setOnClickListener(this);
        city = new City();
        toCitys = new ArrayList<City>();



    }

    private boolean isEmailValid(String email) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(email);
        System.out.println(m.matches() + "---");
        return m.matches();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 8) {
            if (requestCode == 1) {
                city = data.getParcelableExtra("city");
                System.out.println("拿到了城市" + city.getProvince() + city.getCity() + city.getDistrict());
                tv_city1.setText(city.getProvince() + city.getCity() + city.getDistrict());

            } else if (requestCode == 2) {
                toCitys = data.getParcelableArrayListExtra("toCitys");
                StringBuffer ab = new StringBuffer();
                for (int i = 0; i < toCitys.size(); i++) {
                    if (i == toCitys.size() - 1) {//如果是最后一个城市就不需要逗号
                        ab.append(toCitys.get(i).getCity());
                    } else {
                        ab.append(toCitys.get(i).getCity() + "， ");//如果不是最后一个城市就需要逗号
                    }
                }
            }
        }
    }

    @OnClick({R.id.arrow_left, R.id.right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_city1:
                Intent in = new Intent(this, CitySelect1Activity.class);
                in.putExtra("city", city);
                startActivityForResult(in, 1);
                break;
            case R.id.arrow_left:
                finish();
                break;
            case R.id.right_tv:
                name=etShouhuoren.getText().toString();
                phone=etMobile.getText().toString();
                area=tvCity1.getText().toString();//城市
                address=etXiangxidizhi.getText().toString();//详细地址
                userid = SharedPreferencesUtils.getString(AddSiteActivity.this, ConfigUsers.USERID, "");

                if (TextUtils.isEmpty(name)){
                    Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(phone)){
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }else
                if (!isEmailValid(phone)) {//设置手机格式
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(area)){
                    Toast.makeText(this, "请填写详细地址", Toast.LENGTH_SHORT).show();
                    return;
                }else if (TextUtils.isEmpty(address)){
                    Toast.makeText(this, "请填写详细地址", Toast.LENGTH_SHORT).show();
                    return;
                }else if(checkBox.isChecked()){
                    defaults="1";
                    tijia(defaults);
                }else{
                    defaults="0";
                    tijia(defaults);
                }
                break;
        }
    }

    private void tijia(String defaults) {
        System.out.println("用户是否点击了="+defaults);
        System.out.println("用户id拿到="+userid);
        String url = UrlUtils.ADD_MANAGESITE;
        try {
            JSONObject obj=new JSONObject();
            obj.put("userId", userid);
            obj.put("name", name);
            obj.put("phoneNumber", phone);
            obj.put("area", area);
            obj.put("street", area);
            obj.put("address", address);
            obj.put("defaults", defaults);
            OkHttpUtils.postString().url(url).content(obj.toString())
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    System.out.println("添加地址失败" + e);
                }

                @Override
                public void onResponse(String response, int id) {
                    System.out.println("添加地址成功" + response);
                    Intent i=new Intent("android.intent.action.SET");
                    LocalBroadcastManager.getInstance(AddSiteActivity.this).sendBroadcast(i);
                    finish();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }




    }
}
