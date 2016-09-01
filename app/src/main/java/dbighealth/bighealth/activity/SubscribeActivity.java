package dbighealth.bighealth.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dbighealth.bighealth.R;
/**预约页面*/
public class SubscribeActivity extends Activity implements View.OnClickListener{

    private TextView tvTab;
    private ImageView arrow_left;
    private ImageView right_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
        setView();

    }

    private void setView() {
        tvTab=(TextView)findViewById(R.id.tvTab);
        arrow_left=(ImageView)findViewById(R.id.arrow_left);
        right_add=(ImageView)findViewById(R.id.right_add);
        right_add.setVisibility(View.GONE);
        tvTab.setText("预约");
        arrow_left.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrow_left:
                finish();
                break;
        }
    }
}
