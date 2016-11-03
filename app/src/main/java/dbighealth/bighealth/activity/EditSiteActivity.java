package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import utils.UrlUtils;

/**
 * 编辑地址
 * */
public class EditSiteActivity extends Activity {

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
    private String addressId="";
    private String name="";
    private String phoneNumber="";
    private String area="";
    private String address="";
    private City city;
    private ArrayList<City> toCitys;
    private String phone;
    private String defaults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_site);
        ButterKnife.bind(this);

        Intent i=getIntent();
        Bundle bundle=i.getExtras();
        Log.i("Mhysa-->",bundle+"");
        if(bundle!=null){
            addressId =bundle.getString("addressId");
            name=bundle.getString("name");
            phoneNumber=bundle.getString("phoneNumber");
            area=bundle.getString("area");
            address=bundle.getString("address");
            defaults=bundle.getString("defaults");
            tit.setText("编辑地址");
            etShouhuoren.setText(name);
            etMobile.setText(phoneNumber);
            tvCity1.setText(area);
            etXiangxidizhi.setText(address);
        }


    }

    @OnClick({R.id.arrow_left, R.id.right_tv, R.id.tv_city1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            case R.id.right_tv:
                name=etShouhuoren.getText().toString();
                phone=etMobile.getText().toString();
                area=tvCity1.getText().toString();//城市
                address=etXiangxidizhi.getText().toString();//详细地址
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
                }else {
                    String url = UrlUtils.EDIT_MANAGESITE;
                    try {
                        JSONObject obj=new JSONObject();
                        obj.put("addressId", addressId);
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
                                System.out.println("修改地址失败" + e);
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                System.out.println("添加地址成功" + response);
                                Intent i=new Intent("android.intent.action.SET");
                                LocalBroadcastManager.getInstance(EditSiteActivity.this).sendBroadcast(i);
                                finish();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case R.id.tv_city1:
                Intent in = new Intent(this, CitySelect1Activity.class);
                in.putExtra("city", city);
                startActivityForResult(in, 1);
                break;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 8) {
            if (requestCode == 1) {
                city = data.getParcelableExtra("city");
                System.out.println("拿到了城市" + city.getProvince() + city.getCity() + city.getDistrict());
                tvCity1.setText(city.getProvince() + city.getCity() + city.getDistrict());

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


}
