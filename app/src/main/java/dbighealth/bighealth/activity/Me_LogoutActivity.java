package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;

/**
 * 退出登录
 */
public class Me_LogoutActivity extends Activity implements View.OnClickListener {

    @Bind(R.id.rl_collection)
    RelativeLayout rlCollection;
    @Bind(R.id.rl_shoppingCart)
    RelativeLayout rlShoppingCart;
    @Bind(R.id.user)
    ImageView user;
    private Button email_sign_in_button;
    private RelativeLayout remind;
    private TextView tvTab;
    private ImageView arrow_left;
    private ImageView right_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_logout);
        ButterKnife.bind(this);

        setView();
    }

    private void setView() {
        tvTab = (TextView) findViewById(R.id.tvTab);
        tvTab.setText("我的");
        arrow_left = (ImageView) findViewById(R.id.arrow_left);
        arrow_left.setOnClickListener(this);
        right_add = (ImageView) findViewById(R.id.right_add);
        right_add.setVisibility(View.GONE);

        email_sign_in_button = (Button) findViewById(R.id.email_sign_in_button);
        email_sign_in_button.setOnClickListener(this);
        remind = (RelativeLayout) findViewById(R.id.remind);
        remind.setOnClickListener(this);
        rlCollection.setOnClickListener(this);
        rlShoppingCart.setOnClickListener(this);
        user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            case R.id.email_sign_in_button:
                BaseApplication.userid = "";
                Toast.makeText(this, "退出成功", Toast.LENGTH_LONG).show();
                //退出成功之后发送一个广播
                Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                intent.putExtra("username", "");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                finish();
                break;
            case R.id.remind:
                Intent i = new Intent(this, RemindActivity.class);
                startActivity(i);
                break;
            case R.id.rl_collection:
                Toast.makeText(getApplicationContext(),"暂未开通，敬请期待！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rl_shoppingCart:
                Toast.makeText(getApplicationContext(),"暂未开通，敬请期待！",Toast.LENGTH_SHORT).show();
                break;
            case R.id.user:
                Intent i1 = new Intent(this, EditdataActivity.class);
                startActivity(i1);
                break;
        }


    }
}
