package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import dbighealth.bighealth.R;
/**服药提醒页面*/
public class RemindActivity extends Activity implements View.OnClickListener{

    private ImageView arrow_left;
    private ImageView right_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_remind);
        
        setView();
    
    }

    private void setView() {
        arrow_left=(ImageView)findViewById(R.id.arrow_left);
        right_add=(ImageView)findViewById(R.id.right_add);
        arrow_left.setOnClickListener(this);
        right_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrow_left:
                finish();
                break;
            case R.id.right_add:
                Intent i=new Intent(this,RemindSetActivity.class);
                startActivity(i);

        }
    }
}
