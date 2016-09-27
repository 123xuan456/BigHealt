package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dbighealth.bighealth.R;
import dbighealth.bighealth.view.RoundImageView;

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
    @Bind(R.id.relativeLayout5)
    RelativeLayout relativeLayout5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdata);
        ButterKnife.bind(this);
        tvTab.setText("个人信息");
        rightAdd.setVisibility(View.GONE);
        image.setType(RoundImageView.TYPE_ROUND);//圆角
    }


    @OnClick({R.id.relativeLayout1, R.id.relativeLayout2, R.id.relativeLayout3, R.id.relativeLayout5,R.id.arrow_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrow_left:
                finish();
                break;
            case R.id.relativeLayout1:

                break;
            case R.id.relativeLayout2:

                break;
            case R.id.relativeLayout3:

                break;
            case R.id.relativeLayout5:
                Intent intent=new Intent(this,ChangePasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
