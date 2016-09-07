package dbighealth.bighealth.activity;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.R;

/**
 * created by mhysa
 * 我的体质
 */
public class PhysiqueActivity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tvTab)
    TextView tvTab;
    @Bind(R.id.right_add)
    ImageView rightAdd;
    @Bind(R.id.textView6)
    TextView textView6;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.view1)
    View view1;
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.btn_consititution_commit)
    Button btnConsititutionCommit;
    @Bind(R.id.pr_commit)
    PercentRelativeLayout prCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mine_constitution);
        ButterKnife.bind(this);
        tvTab.setText("我的体质");
        rightAdd.setVisibility(View.INVISIBLE);


    }


    @Override
    public void onClick(View v) {

    }
}
