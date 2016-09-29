package dbighealth.bighealth.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import dbighealth.bighealth.imageUtils.BaseActivity;
import dbighealth.bighealth.view.RoundImageView;
import okhttp3.Call;
import utils.UrlUtils;

public class EditdataActivity extends Activity {

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tvTab)
    TextView tvTab;
    @Bind(R.id.right_add)
    ImageView rightAdd;
    @Bind(R.id.textView54)
    TextView textView54;
    @Bind(R.id.relativeLayout1)
    RelativeLayout relativeLayout1;
    @Bind(R.id.textView55)
    TextView textView55;
    @Bind(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @Bind(R.id.textView56)
    TextView textView56;
    @Bind(R.id.imageView13)
    ImageView imageView13;
    @Bind(R.id.relativeLayout3)
    RelativeLayout relativeLayout3;
    @Bind(R.id.image)
    RoundImageView image;
    @Bind(R.id.linearLayout4)
    LinearLayout linearLayout4;
    @Bind(R.id.textView57)
    TextView textView57;
    @Bind(R.id.textView59)
    TextView textView59;
    @Bind(R.id.sex)
    TextView sex1;
    @Bind(R.id.relativeLayout5)
    RelativeLayout relativeLayout5;
    private String name;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle b=msg.getData();
            String name1=b.getString("name");
            System.out.println("name1"+name1);
            textView59.setText(name1);
        }
    };
    private String sex;
    private String sexa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdata);
        ButterKnife.bind(this);
        //吧次activity放到集合里，等到修改密码成功之后统一取消
        BaseActivity.activityList.add(this);
        name=BaseApplication.username;
        sexa=BaseApplication.sex;//打开拿到性别
        sex1.setText(sexa);

        textView59.setText(name);
        tvTab.setText("个人信息");
        rightAdd.setVisibility(View.GONE);
        image.setType(RoundImageView.TYPE_ROUND);//圆角

        broadcast();//接收修改性别的广播

    }

    private void broadcast() {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(EditdataActivity.this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_SEX");//修改性别
        BroadcastReceiver mItemViewListClickReceiver1 = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                sex = intent.getStringExtra("sex");
                System.out.println("接收到了a="+sex);
                sex1.setText(sex);
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver1, intentFilter);

    }


    @OnClick({R.id.relativeLayout1, R.id.relativeLayout2, R.id.relativeLayout3, R.id.relativeLayout5,R.id.arrow_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            case R.id.relativeLayout1:

                break;
            case R.id.relativeLayout2://修改昵称
                //初始化一个自定义的Dialog
                Dialog dialog = new MyDialog(EditdataActivity.this,
                        R.style.MyDialog);
                dialog.show();
                break;
            case R.id.relativeLayout3://修改性别
                Intent i=new Intent(this,SexActivity.class);
                startActivity(i);
                break;
            case R.id.relativeLayout5:
                Intent intent=new Intent(this,ChangePasswordActivity.class);
                startActivity(intent);
                break;
        }
    }

    // 修改昵称的时候弹出dialog
    public class MyDialog extends Dialog {

        Context context;
        private EditText editText2;
        private String et;

        public MyDialog(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
            this.context = context;
        }
        public MyDialog(Context context, int theme){
            super(context, theme);
            this.context = context;
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
            this.setContentView(R.layout.dialog);
            final String phone = BaseApplication.regphone;
            editText2=(EditText)findViewById(R.id.editText2);
            editText2.setText(name);


            Button b1= (Button) findViewById(R.id.button4);
            Button b= (Button) findViewById(R.id.button5);//取消
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JSONObject obj=new JSONObject();
                    et=editText2.getText().toString();
                    System.out.println("et1="+et);
                    try {
                        obj.put("regphone",phone);
                        obj.put("reguser",et);
                    String url= UrlUtils.CHANGUSER;
                    OkHttpUtils.postString().url(url).content(obj.toString()).
                            build().
                            execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            System.out.println("修改失败"+e);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            System.out.println("修改成功"+response);
                            Message message=new Message();
                            Bundle bundle=new Bundle();
                            System.out.println("et="+et);
                            bundle.putString("name",et);
                            message.setData(bundle);
                            handler.sendMessage(message);
                            Toast.makeText(getContext(),"修改成功",Toast.LENGTH_LONG).show();
                            //登录成功之后发送一个广播
                            Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                            intent.putExtra("username",et);
                            System.out.println("过去！！username"+et);
                            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                            dismiss();
                        }
                    });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


        }


    }



}
