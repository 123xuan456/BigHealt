package dbighealth.bighealth.activity;

import android.app.Activity;
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
import dbighealth.bighealth.adapter.InformationAdapter;

/**我的资讯页面*/
public class InformationActivity extends Activity implements View.OnClickListener{

    private ListView listview;
    List<Map<String,String>> list=new ArrayList<>();
    private ImageView arrow_left;
    private TextView right_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
         setView();

    }

    private void setView() {
        listview=(ListView)findViewById(R.id.list);
        arrow_left=(ImageView)findViewById(R.id.arrow_left);
        right_tv=(TextView)findViewById(R.id.right_tv);
        arrow_left.setOnClickListener(this);
        right_tv.setOnClickListener(this);


        String []a={"提问于1","提问于2","提问于3","提问于4"};
        String []b={"2016-01-01","2016-01-02","2016-01-03","2016-01-04"};
        String []c={"晚上多梦1","晚上多梦2","晚上多梦3","晚上多梦4"};
        String []d={"望","闻","问","切"};
        Map<String,String> map=new HashMap<String, String>();
        for (int i=0;i<a.length;i++){
            for (int i1=0;i1<b.length;i1++){
                for (int i2=0;i2<c.length;i2++){
                    for (int i3=0;i3<d.length;i3++){
                        map.put("a",a[i]);
                        map.put("b ",b[i1]);
                        map.put("c",c[i2]);
                        map.put("d",d[i3]);
                        list.add(map);
                    }
                }
            }
        }
        System.out.println("s="+list);
        InformationAdapter adapter=new InformationAdapter(this,list);
        listview.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.right_tv:

                break;
            case R.id.arrow_left:
                finish();
                break;
        }
    }
}
