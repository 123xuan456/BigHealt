package dbighealth.bighealth.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dbighealth.bighealth.R;
import dbighealth.bighealth.wheel.JudgeDate;
import dbighealth.bighealth.wheel.ScreenInfo;
import dbighealth.bighealth.wheel.WheelMain;
import okhttp3.Call;
import utils.UrlUtils;

public class RemindSetActivity extends Activity implements View.OnClickListener {

    private RelativeLayout rl_time, rl_day;
    private Button btn_time, btn_day;
    private ImageView arrow_left;
    private TextView right_tv;
    private EditText text;
    WheelMain wheelMain;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar currentTime;
    String time,date;
//    String title;//输入的药品名字

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_remind_set);
        rl_time = (RelativeLayout) findViewById(R.id.rl_time);
        rl_time.setOnClickListener(this);
        rl_day = (RelativeLayout) findViewById(R.id.rl_day);
        rl_day.setOnClickListener(this);
        btn_time = (Button)findViewById(R.id.btn_time);
        btn_day = (Button)findViewById(R.id.btn_day);
        text = (EditText)findViewById(R.id.text);

        arrow_left = (ImageView)findViewById(R.id.arrow_left);
        arrow_left.setOnClickListener(this);

        right_tv = (TextView)findViewById(R.id.right_tv);
        right_tv.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){

        case R.id.rl_day:

            LayoutInflater inflater = LayoutInflater.from(RemindSetActivity.this);
            final View timepickerview = inflater.inflate(R.layout.timepicker,null);
            ScreenInfo screenInfo = new ScreenInfo(RemindSetActivity.this);
            wheelMain = new WheelMain(timepickerview);
            wheelMain.screenheight = screenInfo.getHeight();
            String time1 = btn_day.getText().toString();
            Calendar calendar = Calendar.getInstance();
            if (JudgeDate.isDate(time1, "yyyy-MM-dd")) {
                try {
                    calendar.setTime(dateFormat.parse(time1));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            wheelMain.initDateTimePicker(year, month, day);
            new AlertDialog.Builder(RemindSetActivity.this)
                    .setTitle("请选择时间")
                    .setView(timepickerview)
                    .setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    btn_day.setText(wheelMain.getTime());
                                }
                            })
                    .setNegativeButton("取消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                }
                            }).show();
            break;

        case R.id.rl_time:

            currentTime = Calendar.getInstance();

            new TimePickerDialog(RemindSetActivity.this, 0,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

//							PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                            Calendar c = Calendar.getInstance();
                            c.setTimeInMillis(System.currentTimeMillis());
                            c.set(Calendar.HOUR, hourOfDay);
                            c.set(Calendar.MINUTE, minute);

                        }
                    }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false).show();
            btn_time.setText(currentTime.get(Calendar.HOUR_OF_DAY)+":"+currentTime.get(Calendar.MINUTE));
            break;

        case R.id.arrow_left:
            finish();
            break;
        case R.id.right_tv:
            //调用保存的接口
            http();

            break;
        default:
            break;
        }
    }

    public void http(){
        int userId = 11;
        String medicineName = text.getText().toString();

        if(TextUtils.isEmpty(medicineName)){
            Toast.makeText(this, "标题不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(btn_time.getText().equals("")){
            Toast.makeText(this, "请设置时间", Toast.LENGTH_SHORT).show();
            return;
        }else
        {
            time = btn_time.getText().toString();
            Log.e("12345", time);
        }

        if(btn_day.getText().equals("")){
            Toast.makeText(this, "请设置日期", Toast.LENGTH_SHORT).show();
            return;
        }else {
            date = btn_day.getText().toString();
        }

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("userId",userId);
            jsonObject.put("medicineName",medicineName);
            jsonObject.put("time",time);
            jsonObject.put("date",date);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            OkHttpUtils.postString()
                    .url(UrlUtils.REMINDSET)
                    .content(jsonObject.toString())
                    .build()
                    .execute(MyStringCallBack);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public StringCallback MyStringCallBack=new StringCallback(){
            @Override
            public void onError(Call call, Exception e, int id) {

            Log.e("mhysa-->", "加载失败");
            }
            @Override
            public void onResponse(String response, int id) {
                    Toast.makeText(getApplication(), "okokok!", Toast.LENGTH_LONG)
                            .show();
//                finish();
            }
    };


}
