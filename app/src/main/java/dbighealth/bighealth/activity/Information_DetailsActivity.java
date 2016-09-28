package dbighealth.bighealth.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dbighealth.bighealth.BaseApplication;
import dbighealth.bighealth.R;
import dbighealth.bighealth.adapter.GridAdapter;
import dbighealth.bighealth.imageUtils.AlbumActivity;
import dbighealth.bighealth.imageUtils.BaseActivity;
import dbighealth.bighealth.imageUtils.Bimp;
import dbighealth.bighealth.imageUtils.FileUtils;
import dbighealth.bighealth.imageUtils.GalleryActivity;
import dbighealth.bighealth.imageUtils.ImageItem;
import okhttp3.Call;
import utils.UrlUtils;

/**
 * 资讯详情
 */
public class Information_DetailsActivity extends Activity implements View.OnClickListener {


    @Bind(R.id.arrow_left)
    ImageView arrowLeft;
    @Bind(R.id.tit)
    TextView tit;
    @Bind(R.id.right_tv)
    TextView rightTv;
    @Bind(R.id.textView6)
    TextView textView6;
    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.problem)
    EditText problem1;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.help)
    EditText help1;
    @Bind(R.id.textView3)
    TextView textView3;

    // 上传的地址
    String uploadUrl = "http://192.168.0.43:8080/JianKangChanYe/homepictures/getAppLog?";
    @Bind(R.id.grid_view)
    GridView mGridView;
    @Bind(R.id.rl_grid_view)
    RelativeLayout parentView;

    private PopupWindow pop = null;
    private LinearLayout ll_popup;
    private GridAdapter adapter;
    public static Bitmap bimap;
    private static final int TAKE_PICTURE = 0;
    private String problem;
    private String help;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information__details);
        ButterKnife.bind(this);
        tit.setVisibility(View.GONE);
        rightTv.setText("提交");
        id= BaseApplication.userid;
        BaseActivity.activityList.add(this);
        initViews();

    }

    private void initViews() {
        initPopu();//提示框
        // 点击GridView时出现背景色设置为透明
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == Bimp.tempSelectBitmap.size()) {
                    ll_popup.startAnimation(AnimationUtils.loadAnimation(
                            Information_DetailsActivity.this, R.anim.activity_translate_in));
                    pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
                } else {
                    Intent intent = new Intent(Information_DetailsActivity.this,
                            GalleryActivity.class);
                    intent.putExtra("ID", position);
                    startActivity(intent);
                }
            }
        });

    }

    private void initPopu() {
        pop = new PopupWindow(Information_DetailsActivity.this);
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                photo();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Information_DetailsActivity.this,
                        AlbumActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_translate_in,
                        R.anim.activity_translate_out);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        mGridView.setAdapter(adapter);
    }
    protected void photo() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {

                    String fileName = String.valueOf(System.currentTimeMillis());
                    Bitmap bm = (Bitmap) data.getExtras().get("data");
                    FileUtils.saveBitmap(bm, fileName);

                    ImageItem takePhoto = new ImageItem();
                    takePhoto.setBitmap(bm);
                    Bimp.tempSelectBitmap.add(takePhoto);
                }
                break;
        }
    }

    @OnClick({R.id.arrow_left, R.id.right_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.arrow_left:
                finish();
                break;

            case R.id.right_tv://提交
                present();
                for (int i = 0; i < BaseActivity.activityList.size(); i++) {
                    if (null != BaseActivity.activityList.get(i)) {
                        BaseActivity.activityList.get(i).finish();
                    }
                }
                break;

        }
    }

    private void present() {
        String url= UrlUtils.INFORMATION;
        OkHttpUtils.postString().url(url).content(getUserInfo()).
                build().
                execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        System.out.println("咨询上传失败"+e);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                    System.out.println("咨询上传成功"+response);
                        Toast.makeText(getApplicationContext(),"上传成功",Toast.LENGTH_SHORT).show();
                    }
                });

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            for (int i = 0; i < BaseActivity.activityList.size(); i++) {
                if (null != BaseActivity.activityList.get(i)) {
                    BaseActivity.activityList.get(i).finish();
                }
            }
            System.exit(0);
        }
        return true;
    }

    public String getUserInfo() {
        problem=problem1.getText().toString();
        help=help1.getText().toString();
        System.out.println("problem="+problem);
        System.out.println("help="+help);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId",id);
            jsonObject.put("problem",problem);
            jsonObject.put("getHelp",help);
            jsonObject.put("helpPic","");
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}