package dbighealth.bighealth.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.R;

/**
 * 医疗养生加盟
 */
public class CooparateActivity extends Activity implements View.OnClickListener{


    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tvTab)
    TextView tvTab;
    @Bind(R.id.right_add)
    ImageView rightAdd;
    @Bind(R.id.rb_medical)
    RadioButton rbMedical;
    @Bind(R.id.nourishing)
    RadioButton nourishing;
    @Bind(R.id.et_company)
    EditText etCompany;
    @Bind(R.id.tv_detail)
    EditText tvDetail;
    @Bind(R.id.tv_Tel)
    TextView tvTel;
    @Bind(R.id.et_tel)
    EditText etTel;
    @Bind(R.id.btn_cooperate)
    Button btnCooperate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperate);
        ButterKnife.bind(this);
        btnCooperate.setOnClickListener(this);
        arrowLeft.setOnClickListener(this);
        rightAdd.setVisibility(View.INVISIBLE);
        tvTab.setText("医疗养生");

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrow_left:
                finish();
                break;
            case R.id.btn_cooperate:

               break;
        }
    }
}
