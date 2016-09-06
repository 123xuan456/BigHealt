package dbighealth.bighealth.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import dbighealth.bighealth.R;

/**
 * Created by mhysa on 2016/9/6.
 * 健康档案
 */
public class ArchivingActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.health_file);
       // ButterKnife.bind(this);
    }
}
