package dbighealth.bighealth.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import dbighealth.bighealth.R;

public class ConditionAddActivity extends Activity implements View.OnClickListener{

    private ImageView arrow_left;
    private TextView right_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition_add);
        setView();
    }

    private void setView() {
        arrow_left=(ImageView)findViewById(R.id.arrow_left);
        right_tv=(TextView)findViewById(R.id.right_tv);
        right_tv.setOnClickListener(this);
        arrow_left.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrow_left:
                finish();
                break;
            case R.id.right_tv:
                Toast.makeText(this,"保存成功",Toast.LENGTH_LONG).show();
                break;

        }
    }
}
