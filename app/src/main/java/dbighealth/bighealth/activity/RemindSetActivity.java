package dbighealth.bighealth.activity;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import dbighealth.bighealth.MainActivity;
import dbighealth.bighealth.R;
import dbighealth.bighealth.wheel.JudgeDate;
import dbighealth.bighealth.wheel.ScreenInfo;
import dbighealth.bighealth.wheel.WheelMain;

public class RemindSetActivity extends Activity implements View.OnClickListener {

    private RelativeLayout rl_time, rl_day;
    private Button btn_time, btn_day;
    WheelMain wheelMain;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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

            Calendar currentTime = Calendar.getInstance();

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
        default:
            break;


    }
    }


}
