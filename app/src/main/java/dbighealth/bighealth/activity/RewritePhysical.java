package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.R;

/**
 * created by mhysa
 * 充填健康体质
 */
public class RewritePhysical extends Activity implements View.OnClickListener{

    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tit)
    TextView tit;
    @Bind(R.id.right_tv)
    TextView rightTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physique);
        ButterKnife.bind(this);
        tit.setText("我的体质");
        rightTv.setText("重新填写");
        arrowLeft.setOnClickListener(this);
        rightTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrow_left:
                finish();
                break;
            case R.id.right_tv:
                Intent intent=new Intent(RewritePhysical.this, PhysiqueActivity.class);//体质
                startActivity(intent);
                break;
        }
    }
}
