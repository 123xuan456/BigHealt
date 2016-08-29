package dbighealth.telephonymanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TelephonyManage extends AppCompatActivity {

    private ListView lv;
    private String[] statusNames;
    private String[] simStatus;
    private String[] phoneStatus;

    ArrayList<String> statusValus=new ArrayList<>();
    private TelephonyManager tManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_telephony_manager);

        lv=(ListView)findViewById(R.id.listView);
        //获取系统的TelephonyManager对象
        tManager = (TelephonyManager)
                getSystemService(Context.TELEPHONY_SERVICE);
        //获取各种状态名称的数组
        statusNames=getResources().getStringArray(R.array.statusNames);
        //获取代表SIM状态的数组
        simStatus=getResources().getStringArray(R.array.simstatus);
        //获取代表电话网络类型的数组
        phoneStatus=getResources().getStringArray(R.array.phoneType);
        //获取设备编号
        statusValus.add(tManager.getDeviceId());

        //获取系统平台的版本
        statusValus.add(tManager.getDeviceSoftwareVersion()!=null?tManager.getDeviceSoftwareVersion():"未知");
        //获取网络运行商代号
        statusValus.add(tManager.getNetworkOperator());
        //获取网络运营商名称

        statusValus.add(tManager.getNetworkOperatorName());
        //获取手机网络类型
        statusValus.add(phoneStatus[tManager.getPhoneType()]);
        //获取设备所在位置
        statusValus.add(tManager.getCellLocation()!=null?tManager.getCellLocation().toString():"未知地区");
        //获取SIM的国别
        statusValus.add(tManager.getSimCountryIso());
        //获取SIM卡序列号
        statusValus.add(tManager.getSimSerialNumber());
        //获取SIM的状态
        statusValus.add(simStatus[tManager.getSimState()]);
        //获取listView对象

        ArrayList<Map<String , String>> status=new ArrayList<>();
        for (int i=0;i<statusValus.size();i++){
            HashMap<String ,String> map=new HashMap<>();
            map.put("name",statusNames[i]);
            map.put("value",statusValus.get(i));
            status.add(map);
        }
        //使用
        SimpleAdapter adapter=new SimpleAdapter(this,status,R.layout.line,new String[]{"name","value"},new int[]{R.id.name,R.id.value});
        lv.setAdapter(adapter);


    }

}
