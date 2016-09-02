package dbighealth.bighealth.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.TreatmentAdapter;

/**
 * A simple {@link Fragment} subclass.
 * https://git.oschina.net/qishaoxuan/new.git
 * 治疗页面
 */
public class TreatmentFragment extends Fragment implements View.OnClickListener{
    private TextView right_tv;
    RelativeLayout r;
    Spinner sp, sp1;
    private ListView listview;
    private String[] images;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        r = (RelativeLayout) inflater.inflate(R.layout.fragment_treatment, null);
        TextView tvTab = (TextView) r.findViewById(R.id.tvTab);
        tvTab.setText("医疗养生");
        right_tv = (TextView) r.findViewById(R.id.right_tv);
        right_tv.setText("加盟");
        setView();
        return r;
    }

    private void setView() {
        sp = (Spinner) r.findViewById(R.id.spinner);
        sp1 = (Spinner) r.findViewById(R.id.spinner1);
        sp.setOnClickListener(this);
        sp1.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, getData());
        adapter.setDropDownViewResource(R.layout.item_spinner);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, getData1());
        adapter1.setDropDownViewResource(R.layout.item_spinner);


        sp.setAdapter(adapter);
        sp1.setAdapter(adapter1);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 在选中之后触发
                Toast.makeText(getContext(),
                        parent.getItemAtPosition(position).toString(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 这个一直没有触发，我也不知道什么时候被触发。
                //在官方的文档上说明，为back的时候触发，但是无效，可能需要特定的场景
            }
        });

        listview = (ListView) r.findViewById(R.id.listView2);

        getImageUrls();
        TreatmentAdapter adapter2 = new TreatmentAdapter(getContext(), images);
        listview.setAdapter(adapter2);

    }


    public List<String> getData() {
        List<String> list = new ArrayList<>();
        list.add("医疗");
        list.add("地区");
        list.add("北京");
        list.add("上海");
        list.add("广州");
        list.add("深圳");
        list.add("山东");
        list.add("青岛");
        return list;
    }
    public List<String> getData1() {
        List<String> list = new ArrayList<>();
        list.add("养生");
        list.add("地区");
        list.add("北京");
        list.add("上海");
        list.add("广州");
        list.add("深圳");
        list.add("山东");
        list.add("青岛");
        return list;
    }

    public void getImageUrls() {
        images = new String[]{
                "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383291_8239.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383290_9329.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383290_1042.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383275_3977.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383265_8550.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383264_3954.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383264_4787.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383264_8243.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383248_3693.jpg",
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.spinner:
                sp.setBackgroundResource(R.drawable.yuanjiao_spinner1);
                sp1.setBackgroundResource(R.drawable.yuanjiao_spinner2);
                break;
            case R.id.spinner1:
                sp.setBackgroundResource(R.drawable.yuanjiao_spinner2);
                sp1.setBackgroundResource(R.drawable.yuanjiao_spinner1);
                break;
        }
    }
}