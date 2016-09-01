package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.ConditionAdapter;

/**每日情况*/
public class ConditionActivity extends Activity implements View.OnClickListener{

    private ListView listview;

    List<Map<String,String>> list=new ArrayList<>();
    private ImageView arrow_left;
    private ImageView right_add;
    private TextView tvTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condition);
        listview=(ListView)findViewById(R.id.listView);
        setView();
        getDate();

    }

    private void setView() {
        tvTab=(TextView)findViewById(R.id.tvTab);
        tvTab.setText("每日情况");
        arrow_left=(ImageView)findViewById(R.id.arrow_left);
        right_add=(ImageView)findViewById(R.id.right_add);
        right_add.setBackgroundResource(R.drawable.add_1);
        arrow_left.setOnClickListener(this);
        right_add.setOnClickListener(this);
    }

    public void getDate() {
        Map<String , String> map=new HashMap<String, String>();
        map.put("a","早晨");
        map.put("b ","9:00-10:00");
        map.put("c","鸡蛋豆浆油条");
        map.put("d","2016-08-31");
        list.add(map);
        ConditionAdapter adapter=new ConditionAdapter(this,list);
        listview.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.arrow_left:
                finish();
                break;
            case R.id.right_add:
                Intent i=new Intent(this,ConditionAddActivity.class);
                startActivity(i);

        }
    }
}
