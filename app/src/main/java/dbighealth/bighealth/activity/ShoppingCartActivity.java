package dbighealth.bighealth.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.R;

/**
 * 购物车
 */
public class ShoppingCartActivity extends Activity implements View.OnClickListener{

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tit)
    TextView tit;
    @Bind(R.id.right_tv)
    TextView rightTv;
    @Bind(R.id.lv_shopcart)
    ListView lvShopcart;
    @Bind(R.id.cb_shopping)
    CheckBox cbShopping;
    @Bind(R.id.tv_total)
    TextView tvTotal;
    @Bind(R.id.tv_balance)
    TextView tvBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        ButterKnife.bind(this);
        rightTv.setText("编辑");
        tit.setText("购物车(0)");
    }

    /**
     * 注册点击事件
     */
    public void initListener(){
        arrowLeft.setOnClickListener(this);
        rightTv.setOnClickListener(this);
        tvBalance.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
             case R.id.arrow_left:
                 finish();
               break;
            case R.id.right_tv:
                break;
        }
    }
}
