package dbighealth.bighealth.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import dbighealth.bighealth.R;

public class Home_Page_Details extends Activity {

    private ListView mcomment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_details);
        mcomment = (ListView)findViewById(R.id.mcomment);

    }
}
