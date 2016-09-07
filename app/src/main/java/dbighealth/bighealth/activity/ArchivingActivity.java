package dbighealth.bighealth.activity;

import android.app.Activity;
import android.os.Bundle;

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
