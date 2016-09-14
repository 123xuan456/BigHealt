package dbighealth.bighealth.fragment;

import android.content.Intent;
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

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbighealth.bighealth.R;
import dbighealth.bighealth.activity.CooparateActivity;
import dbighealth.bighealth.adapter.TreatmentAdapter;
import dbighealth.bighealth.bean.HealthCare;
import dbighealth.bighealth.bean.TreatmentBean;
import okhttp3.Call;
import utils.UrlUtils;

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
    private int HOME_TREATMENT = 1;
    private int REGION =2 ;
    private Map map;
    private List<String> province_list;
    private Map<Integer, Storage> map1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        r = (RelativeLayout) inflater.inflate(R.layout.fragment_treatment, null);
        TextView tvTab = (TextView) r.findViewById(R.id.tvTab);
        tvTab.setText("医疗养生");
        right_tv = (TextView) r.findViewById(R.id.right_tv);
        right_tv.setText("加盟");
        right_tv.setOnClickListener(this);
        sp1 = (Spinner) r.findViewById(R.id.spinner1);
        map = new HashMap<Integer,String>();
        map1 = new HashMap<Integer,Storage>();
        province_list = new ArrayList<String>();
        province_list.add("地区");
        map.put(0,"地区");
        map1.put(0,new Storage(0,"地区"));
        setView();

        return r;
    }

    public static Fragment newInstance() {
        TreatmentFragment f = new TreatmentFragment();
        return f;
    }

    /**
     * 存储地区和编号
     */
    public class Storage{
        private int AddressId;
        private String AddressName;
        public Storage(int AddressId,String AddressName){
            this.AddressId =AddressId;
            this.AddressName = AddressName;
        }

        public String getAddressName() {
            return AddressName;
        }

        public void setAddressName(String addressName) {
            AddressName = addressName;
        }



        public int getAddressId() {
            return AddressId;
        }

        public void setAddressId(int addressId) {
            AddressId = addressId;
        }
    }


    private void setView() {
        sp = (Spinner) r.findViewById(R.id.spinner);
        sp1 = (Spinner) r.findViewById(R.id.spinner1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, getData());
        adapter.setDropDownViewResource(R.layout.item_spinner);

     /*   ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, getData1());
        adapter1.setDropDownViewResource(R.layout.item_spinner);*/
        sp.setAdapter(adapter);
        //   sp1.setAdapter(adapter1);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 在选中之后触发
                Toast.makeText(getContext(),
                        position+"",
                        Toast.LENGTH_SHORT).show();
                if(sp1.getSelectedItemPosition()==0){
                    getInternet();
                }else{


                    int selectedItemPosition = sp1.getSelectedItemPosition();
                    Storage storage = (Storage) map.get(selectedItemPosition);
                    int addressId = storage.getAddressId();
                    int postionType = position+1;
                    OkHttpUtils.get()
                            .url(UrlUtils.TREATMENT)
                            .id(REGION)
                            .addParams("med",postionType+"")
                            .addParams("region",addressId+"")
                            .build()
                            .execute(MyStringCallBack);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // 这个一直没有触发，我也不知道什么时候被触发。
                //在官方的文档上说明，为back的时候触发，但是无效，可能需要特定的场景
            }
        });

        listview = (ListView) r.findViewById(R.id.listView2);

       /* getImageUrls();
        TreatmentAdapter adapter2 = new TreatmentAdapter(getContext(), images);
        listview.setAdapter(adapter2);*/

    }
    /**
     * 请求网络
     * @return
     */
    public void getInternet(){
        OkHttpUtils.get()
                .url(UrlUtils.TREATMENT)
                .id(HOME_TREATMENT)
                .build()
                .execute(MyStringCallBack);
    }

    public StringCallback MyStringCallBack = new StringCallback(){

        private List<TreatmentBean.ResultBean.ResultsBean> results;

        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {

            if(id==HOME_TREATMENT){
                Gson gson = new Gson();
                TreatmentBean treatmentBean = gson.fromJson(response, TreatmentBean.class);
                List<TreatmentBean.ResultBean> result = treatmentBean.getResult();

                for(int i= 0 ; i<result.size();i++){
                    List<TreatmentBean.ResultBean.RegionBean> region = result.get(i).getRegion();
                    for(int j =0;j<region.size();j++){
                        int id1 = region.get(j).getId();
                        String province = region.get(j).getProvince();
                        map.put(j,new Storage(id1,province));
                        //   map.put(id1,province);
                        province_list.add(province);
                    }
                    results = result.get(i).getResults();

                    TreatmentAdapter adapter2 = new TreatmentAdapter(getContext(), results);
                    listview.setAdapter(adapter2);
                }

                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_spinner_item, province_list);
                adapter1.setDropDownViewResource(R.layout.item_spinner);
                sp1.setAdapter(adapter1);
            }
            if(id == REGION){
                Gson gson1 = new Gson();
                HealthCare healthCare = gson1.fromJson(response, HealthCare.class);
                List<HealthCare.MedicalListBean> medicalList = healthCare.getMedicalList();
                TreatmentAdapter adapter2 = new TreatmentAdapter(getContext(), results);
                listview.setAdapter(adapter2);
            }
        }
    };
    public List<String> getData() {
        List<String> list = new ArrayList<>();
        list.add("医疗");
        list.add("养生");
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
            case R.id.right_tv:
                Intent i=new Intent(getContext(),CooparateActivity.class);
                startActivity(i);
                break;
        }
    }
}