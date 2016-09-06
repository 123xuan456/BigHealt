package dbighealth.bighealth.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import dbighealth.bighealth.R;
/* 医疗养生*/
public class MedicalActivity extends AppCompatActivity {

    private TextView right_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);
        setView();


    }

    private void setView() {
        right_tv=(TextView)findViewById(R.id.right_tv);
        right_tv.setText("加盟");
    }
}
