package dbighealth.bighealth.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import dbighealth.bighealth.R;

public class RemindSetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_remind_set);
    }
}
